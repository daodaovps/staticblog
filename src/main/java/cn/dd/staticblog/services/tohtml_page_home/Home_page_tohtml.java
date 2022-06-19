package cn.dd.staticblog.services.tohtml_page_home;

import cn.dd.staticblog.services.tohtml_page_item.ServicePageinfo;
import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Home_page_tohtml {

    public static void main(String[] args) {

        String md_files_home_path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books";

        String index_page_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\index.html";
        String index_page_file_template = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-index.html";

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
            if (f.getName().contains("bookinfo.md")) {
                pages_all.remove(f);
                i--;
            }
            Pageinfo pageinfo = ServicePageinfo.get_pageinfo(f.getAbsolutePath());
            pageinfo_list.add(pageinfo);
        }

        System.out.println(Json.toJson(pageinfo_list));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pageinfo_list.size(); i++) {
            Pageinfo pageinfo = pageinfo_list.get(i);
            stringBuilder.append("<li class=\"li_index\">\n" +
                    "\n" +
                    "                                <h3>\n" +
                    "                                    <a class=\"page_title\" target=\"_blank\" href=\"/pages/" + pageinfo.getUrl() + "\">" + pageinfo.getTitle() + "</a>\n" +
                    "                                    <a class=\"series\" href=\"javascript:void(0)\" title=\"\" style=\"\">\n" +
                    "                                    Series 1/1</a>\n" +
                    "                                </h3>\n" +
                    "\n" +
                    "                                <span class=\"difficulty-level Beginner\">初级</span>\n" +
                    "\n" +
                    "                                <div class=\"meta\">\n" +
                    "\n" +
                    "                                    <div class=\"points\">3\n" +
                    "                                        <span class=\"icon-upvote-heart-full\"></span>\n" +
                    "                                    </div>\n" +
                    "\n" +
                    "                                <span class=\"author\">\n" +
                    "                                          by <a href=\"#\">刀刀</a>\n" +
                    "                                  </span>\n" +
                    "\n" +
                    "                                        <span class=\"meta-value\">标签\n" +
                    "                                         <a href=\"/filter/tag/10/1.html\">tag1</a>\n" +
                    "                                            </span>\n" +
                    "\n" +
                    "                                    <span class=\"time\">\n" +
                    "                                    " + Times.sD(pageinfo.getLast_modify_date()) + "\n" +
                    "                                    </span>\n" +
                    "                                </div>\n" +
                    "\n" +
                    "                            </li>");
        }

        CommonUtil.update_file(index_page_file_template, index_page_file , NutMap.NEW().setv("indexlist",stringBuilder.toString()));



    }
}
