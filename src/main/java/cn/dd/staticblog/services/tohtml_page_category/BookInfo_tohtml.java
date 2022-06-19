package cn.dd.staticblog.services.tohtml_page_category;

import cn.dd.staticblog.services.tohtml_page_item.ServiceBookinfo;
import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.BookInfo;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cn.dd.staticblog.services.tohtml_page_category.BookList_tohtml.find_bookinfo_md;

public class BookInfo_tohtml {

    public static void main(String[] args) {

        // 对所有的book,  生成  bookinfo html
        String md_files_home_path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books";

        // 循环找到文件夹,  并且这个文件夹下含  bookinfo 文件
        File[] folders = Files.ls(new File(md_files_home_path), ".", Files.LsMode.DIR);
        System.out.println(Json.toJson(folders));

        List<File> bookinfo_list = new ArrayList<>();
        for (int i = 0; i < folders.length; i++) {
            File bookinfo_exsit = find_bookinfo_md(folders[i]);
            if (!Lang.isEmpty(bookinfo_exsit)) {
                bookinfo_list.add(folders[i]);
            }
        }

        for (int i = 0; i < bookinfo_list.size(); i++) {
            File book_info_file = bookinfo_list.get(i);
            BookInfo_tohtml.create_bookinfo_page(book_info_file.getAbsolutePath());
        }

    }

    public static void create_bookinfo_page(String md_book_folder) {
        File[] fs = Files.lsFile(md_book_folder, "bookinfo");
        System.out.println(fs);
        if (fs.length != 1) {
            System.out.println("error , not found bookinfo.md");
            return;
        }

        String bookinfo_file_path = fs[0].getAbsolutePath();
        BookInfo bookInfo = ServiceBookinfo.md_to_bookinfo(bookinfo_file_path);
        String url = Lang.md5(fs[0]);
        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-bookinfo.html";
        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\book\\" + url + ".html";

        CommonUtil.update_file(template_file, target_file, NutMap.NEW().setv("bookinfo", bookInfo.toHTML("")));

    }
}
