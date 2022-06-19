package cn.dd.staticblog.services.tohtml_page_home;

import cn.dd.staticblog.services.tohtml_page_item.ServicePageinfo;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Home_page_tohtml {

    public static void main(String[] args) {

        String md_files_home_path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books";

        String index_page_file = md_files_home_path + "\\" + "index.html";
        String index_page_file_template = md_files_home_path + "\\" + "index-template.html";

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


    }
}
