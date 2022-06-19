package cn.dd.staticblog.services.tohtml_page_category;

import cn.dd.staticblog.services.tohtml_page_item.ServiceBookinfo;
import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.BookInfo;
import org.nutz.lang.Files;
import org.nutz.lang.util.NutMap;

import java.io.File;

public class Bookinfo_tohtml {

    public static void main(String[] args) {

        String bookpath = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books\\book1";
        File[] fs = Files.lsFile(bookpath, "bookinfo");
        System.out.println(fs);
        if (fs.length != 1) {
            System.out.println("error , not found bookinfo.md");
            return;
        }

        String bookinfo_file_path = fs[0].getAbsolutePath();
        BookInfo bookInfo = ServiceBookinfo.md_to_bookinfo(bookinfo_file_path);

        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-bookinfo.html";
        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\book\\book1.html";

        CommonUtil.update_file(template_file, target_file, NutMap.NEW().setv("bookinfo", bookInfo.toHTML("")));

    }
}
