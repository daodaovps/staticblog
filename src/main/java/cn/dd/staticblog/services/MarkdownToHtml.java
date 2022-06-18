package cn.dd.staticblog.services;

import cn.dd.staticblog.vo.BookInfo;
import org.commonmark.Extension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.lang.Files;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;

import java.io.File;
import java.util.Collections;
import java.util.Set;

public class MarkdownToHtml {

    public static void main(String[] args) {
        String file_markdown = "D:\\work_nutz\\website--all-md\\books\\book1\\page1.md";

        String markdown_txt = Files.read(file_markdown);

        Set<Extension> EXTENSIONS = Collections.singleton(HeadingAnchorExtension.create());

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown_txt);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(EXTENSIONS).build();
        String html_body_content = renderer.render(document);
        String side_nav = get_sider(html_body_content);

        //  目录  数据   beging
        String bookpath = new File(file_markdown).getParent();
        File[] fs = Files.lsFile(bookpath, "bookinfo");
        System.out.println(fs);
        if (fs.length != 1) {
            System.out.println("error , not found bookinfo.md");
            return;
        }
        BookInfo bookInfo = ServiceMenu.md_to_bookinfo(fs[0].getAbsolutePath());
        String  bookinfo_html=bookInfo.toHTML(new File(file_markdown).getName());
        //  目录  数据   end


        // 临时写入到一个特定的 html 文件  , 方便调试

        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\pages\\6\\d2ec19786dd84cddb0176b841075e302.html";
        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\pages\\6\\template.html";
        String template_txt = Files.read(template_file);

        Segment seg = new CharSegment(template_txt);
        seg.set("body_content", html_body_content);
        seg.set("side_nav", side_nav);
        seg.set("bookinfo", bookinfo_html);

        System.out.println(seg.toString());
        Files.write(target_file, seg.toString());

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
