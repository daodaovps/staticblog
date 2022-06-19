package cn.dd.staticblog.services.tohtml_page_category;

import cn.dd.staticblog.services.tohtml_page_item.ServiceBookinfo;
import cn.dd.staticblog.services.tohtml_page_item.ServicePageinfo;
import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.BookInfo;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BookList_tohtml {

    public static void main(String[] args) {

        String md_files_home_path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books";

        String template_booklist = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-booklist.html";
        String target_booklist = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\book\\booklist.html";

        // 循环找到文件夹,  并且这个文件夹下含  bookinfo 文件
        File[] folders = Files.ls(new File(md_files_home_path), ".", Files.LsMode.DIR);
        System.out.println(Json.toJson(folders));

        List<File> bookinfo_list = new ArrayList<>();
        for (int i = 0; i < folders.length; i++) {
            File bookinfo_exsit = find_bookinfo_md(folders[i]);
            if (!Lang.isEmpty(bookinfo_exsit)) {
                bookinfo_list.add(bookinfo_exsit);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bookinfo_list.size(); i++) {
            File bookinfo_file = bookinfo_list.get(i);
            BookInfo bookInfo = ServiceBookinfo.md_to_bookinfo(bookinfo_file.getAbsolutePath());

            stringBuilder.append("                            <li class=\"li_index\">\n" +
                    "\n" +
                    "                                <h3>\n" +
                    "                                    <a class=\"page_title\" target=\"_blank\"\n" +
                    "                                       href=\"/book/" + bookInfo.getUrl() + ".html\">" + bookInfo.getBookname() + "</a>\n" +
                    "                                    <a class=\"series\" href=\"javascript:void(0)\">\n" +
                    "                                        BOOK\n" +
                    "                                    </a>\n" +
                    "                                </h3>\n" +
                    "\n" +
                    "                                <span class=\"difficulty-level Beginner\">初级</span>\n" +
                    "\n" +
                    "                                <div class=\"meta\">\n" +
                    "                                    <div class=\"points\">3\n" +
                    "                                        <span class=\"icon-upvote-heart-full\"></span>\n" +
                    "                                    </div>\n" +
                    "                                <span class=\"author\">\n" +
                    "                                          by <a href=\"#\">刀刀</a>\n" +
                    "                                  </span>\n" +
                    "\n" +
                    "                                    <span class=\"time\">\n" +
                    "                                    2022-6-11\n" +
                    "                                    </span>\n" +
                    "                                </div>\n" +
                    "\n" +
                    "                            </li>");
        }

        CommonUtil.update_file(template_booklist, target_booklist, NutMap.NEW().setv("booklist", stringBuilder.toString()));


    }

    public static File find_bookinfo_md(File folder) {
        List<File> pages = Lang.array2list(Files.lsFile(folder, "."));
        for (int j = 0; j < pages.size(); j++) {
            File f = pages.get(j);
            if (f.getName().contains("bookinfo")) {
                return f;
            }
        }
        return null;
    }

}
