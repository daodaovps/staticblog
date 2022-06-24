package cn.dd.staticblog.util;

import cn.dd.staticblog.consts.Consts;
import cn.dd.staticblog.services.tohtml_page_item.ServicePageinfo;
import cn.dd.staticblog.vo.Pageinfo;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.lang.util.NutMap;

import java.io.File;
import java.util.*;

public class CommonUtil {

    public static void main(String[] args) {

        // 验证工具类
        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-pageinfo.html";
        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\test-pageinfo.html";
        NutMap k_v = new NutMap();
        k_v.setv("body_content", "test content 123 中文\r\neng");
        k_v.setv("side_nav", "");
        k_v.setv("bookinfo", "");

        CommonUtil.update_file(template_file, target_file, k_v);

    }

    /**
     * 现状只有3个字段,
     * <p>
     * <p>
     * create: 2022-06-24
     * tags: java,spring,shell
     * draft: true
     *
     * @return
     */
    public static NutMap find_metadata(String md_page_file) {

        String md_txt_content = Files.read(md_page_file);
        Set<Extension> EXTENSIONS = Collections.singleton(YamlFrontMatterExtension.create());
        Parser PARSER = Parser.builder().extensions(EXTENSIONS).build();

        YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor();
        Node document = PARSER.parse(md_txt_content);
        document.accept(visitor);

        Map<String, List<String>> data = visitor.getData();

        NutMap out = new NutMap();
        if (Lang.isEmpty(data)) {
            out.setv("create", Times.sD(new Date()));
            out.setv("tags", new ArrayList<>());
            out.setv("draft", true);
            return out;
        } else {

            if (!Lang.isEmpty(data.get("create"))) {
                out.setv("create", Strings.trim(data.get("create").get(0)));
            } else {
                out.setv("create", Times.sD(new Date()));
            }


            if (!Lang.isEmpty(data.get("tags"))) {
                String tags = data.get("tags").get(0);
                if (!Strings.isBlank(tags)) {
                    String[] arr = tags.split(",");
                    String[] arr2 = Arrays.stream(arr).map(String::trim).toArray(String[]::new);
                    out.setv("tags", Lang.array2list(arr2));
                } else {
                    out.setv("tags", new ArrayList<>());
                }
            } else {
                out.setv("tags", new ArrayList<>());
            }

            if (!Lang.isEmpty(data.get("draft"))) {
                out.setv("draft", Boolean.valueOf(data.get("draft").get(0)));
            } else {
                out.setv("draft", true);  // 默认是草稿
            }

        }
        return out;

    }

    public static void update_file(String templ_file, String target_file, NutMap k_v) {
        //        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\pages\\6\\d2ec19786dd84cddb0176b841075e302.html";
        //        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\pages\\6\\template.html";
        String template_txt = Files.read(templ_file);
        Segment seg = new CharSegment(template_txt);
        //        seg.set("body_content", html_body_content);
        //        seg.set("side_nav", side_nav);
        //        seg.set("bookinfo", bookinfo_html);
        Iterator entries = k_v.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String k = entry.getKey().toString();
            String v = entry.getValue().toString();
            seg.set(k, v);
        }
        Files.write(target_file, seg.toString());
    }



    public static List<Pageinfo>  get_all_pageinfo_list()
    {
        //   全部 page 生成 html
        String md_files_home_path = Consts.md_files_home_path;
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
        return pageinfo_list;

    }

}
