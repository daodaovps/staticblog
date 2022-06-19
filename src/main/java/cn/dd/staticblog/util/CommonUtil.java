package cn.dd.staticblog.util;

import org.nutz.lang.Files;
import org.nutz.lang.segment.CharSegment;
import org.nutz.lang.segment.Segment;
import org.nutz.lang.util.NutMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

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

}
