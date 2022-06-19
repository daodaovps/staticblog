package cn.dd.staticblog.services.tohtml_page_item;

import cn.dd.staticblog.vo.BookInfo;
import cn.dd.staticblog.vo.Pageinfo;
import org.commonmark.Extension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;

import java.io.File;
import java.util.*;

public class ServicePageinfo {

    public static void main(String[] args) {

        //   全部 page 生成 html
        String md_files_home_path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books";
        File[] folders = Files.ls(new File(md_files_home_path), ".", Files.LsMode.DIR);
        System.out.println(Json.toJson(folders));

        List<File> pages_all = new ArrayList<>();
        for (int i = 0; i < folders.length; i++) {
            File[] pages = Files.lsFile(folders[i], ".");
            pages_all.addAll(Lang.array2list(pages));
        }

        File[] arr_pages_all = Lang.collection2array(pages_all);
        Arrays.sort(arr_pages_all, Comparator.comparingLong(File::lastModified).reversed());
        pages_all = Lang.array2list(arr_pages_all);

        // 去掉bookinfo的文档
        List<Pageinfo> pageinfo_list = new ArrayList<>();
        for (int i = 0; i < pages_all.size(); i++) {
            File f = pages_all.get(i);
            if (f.getName().contains("bookinfo")) {
                pages_all.remove(f);
                i--;
                continue;
            }
            Pageinfo pageinfo = ServicePageinfo.get_pageinfo(f.getAbsolutePath());
            pageinfo_list.add(pageinfo);
        }

        //        test_page_one();

    }

    private static void test_page_one() {
        String file_markdown = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books\\book1\\page1.md";   //  有bookinfo的情况
        //        String file_markdown = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books\\pages\\2022-06-01  shell学习.md";  //  没有bookinfo的情况
        Pageinfo pageinfo = get_pageinfo(file_markdown);
        System.out.println(Json.toJson(pageinfo));
    }

    public static Pageinfo get_pageinfo(String file_markdown) {
        String markdown_txt = Files.read(file_markdown);

        Set<Extension> EXTENSIONS = Collections.singleton(HeadingAnchorExtension.create());

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown_txt);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(EXTENSIONS).build();
        String html_body_content = renderer.render(document);
        String side_nav = get_sider(html_body_content);

        //  page title
        String page_filename_as_title = new File(file_markdown).getName().replaceFirst("[.][^.]+$", "");

        //  目录  数据   beging
        String bookpath = new File(file_markdown).getParent();
        File[] fs_bookinfo = Files.lsFile(bookpath, "bookinfo");

        BookInfo bookInfo = null;
        String bookinfo_html = "";
        String url = Lang.md5(new File(file_markdown));
        String top_nav = "";
        if (fs_bookinfo != null && !Lang.isEmpty(fs_bookinfo)) {
            System.out.println(fs_bookinfo);
            if (fs_bookinfo.length != 1) {
                System.out.println("error , not found bookinfo.md");
            }
            bookInfo = ServiceBookinfo.md_to_bookinfo(fs_bookinfo[0].getAbsolutePath());
            bookinfo_html = bookInfo.toHTML(page_filename_as_title);
            top_nav = bookInfo.get_top_nav(bookInfo, page_filename_as_title);
        } else {
            top_nav = page_filename_as_title;
        }

        //  目录  数据   end
        Pageinfo pageinfo = new Pageinfo();
        pageinfo.setTitle(page_filename_as_title);
        pageinfo.setContent_html(html_body_content);
        pageinfo.setNav_side_html(side_nav);
        pageinfo.setBookinfo(bookInfo);
        pageinfo.setUrl(url);
        pageinfo.setTop_nav(top_nav);
        pageinfo.setMd_file(file_markdown);

        // 临时写入到一个特定的 html 文件  , 方便调试
        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\pages\\" + url + ".html";
        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-pageinfo.html";
        String template_txt = Files.read(template_file);
        Segment seg = new CharSegment(template_txt);
        seg.set("page_title", page_filename_as_title);
        seg.set("body_content", html_body_content);
        seg.set("side_nav", side_nav);
        seg.set("bookinfo", bookinfo_html);
        System.out.println(seg.toString());
        Files.write(target_file, seg.toString());
        // 写入文件end
        File file_page = new File(target_file);
        pageinfo.setFile_md5(Lang.md5(file_page));
        pageinfo.setLast_modify_date(Times.D(file_page.lastModified()));

        System.out.println(Json.toJson(pageinfo));
        return pageinfo;

    }

    /**
     * 提取H2 标签,  生成  sider  html
     *
     * @param html_body_content
     * @return
     */
    private static String get_sider(String html_body_content) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html_body_content);

        Elements h2List = doc.getElementsByTag("h2");
        StringBuilder strb = new StringBuilder();
        for (Element h2 : h2List) {
            String h2_id = h2.attr("id");
            String h2_text = h2.text();

            String item_html = "<li class=\"tocify-item\" style=\"cursor: pointer;\"><a href=\"#" + h2_id + "\">" + h2_text + "</a></li>";
            strb.append(item_html);
        }
        return strb.toString();
    }

}
