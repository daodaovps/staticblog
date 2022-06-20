package cn.dd.staticblog.services.tohtml_page_archive;

import cn.dd.staticblog.services.tohtml_page_item.ServicePageinfo;
import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.Nutz;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.mapl.Mapl;

import java.io.File;
import java.util.*;

public class Archive_page_tohtml {

    public static void main(String[] args) {

        // 汇总出   每月总共有多少篇文章

        String md_files_home_path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books";

        String archive_page_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\archive.html";
        String archive_page_file_template = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-archive.html";

        File[] folders = Files.ls(new File(md_files_home_path), ".", Files.LsMode.DIR);
        System.out.println(Json.toJson(folders));

        List<File> pages_all = new ArrayList<>();
        for (int i = 0; i < folders.length; i++) {
            File[] pages = Files.lsFile(folders[i], ".");
            pages_all.addAll(Lang.array2list(pages));
        }

        // 去掉bookinfo的文档
        Map<String, List<Pageinfo>> month_pageinfo_list = new HashMap<>();
        for (int i = 0; i < pages_all.size(); i++) {
            File f = pages_all.get(i);
            if (f.getName().contains("bookinfo")) {
                pages_all.remove(f);
                i--;
                continue;
            }
            Pageinfo pageinfo = ServicePageinfo.get_pageinfo(f.getAbsolutePath());

            String month = Times.format("yyyyMM", pageinfo.getLast_modify_date());
            if (month_pageinfo_list.get(month) != null) {
                List<Pageinfo> pageinfo_list = month_pageinfo_list.get(month);
                pageinfo_list.add(pageinfo);
            } else {
                List<Pageinfo> pageinfo_list = new ArrayList<>();
                pageinfo_list.add(pageinfo);
                month_pageinfo_list.put(month, pageinfo_list);
            }
        }

        //循环每个月 ,  每个月生成一个list page
        for (String key : month_pageinfo_list.keySet()) {
            //            System.out.println(key + " ：" + month_pageinfo_list.get(key));

            String yyyyMM=key;
            List<Pageinfo> pageinfos = month_pageinfo_list.get(key);



        }

        // 写入文件
        CommonUtil.update_file(
                archive_page_file_template,
                archive_page_file,
                NutMap.NEW().setv("archivelist", stringBuilder.toString())
        );

    }

    /**
     * 从 List<年>  中查找年, 找到了 , 就返回   Map<年 , List<月,count>
     *
     * @param data
     * @param yyyy
     * @return
     */
    private static Map<String, List<NutMap>> fetch_yyyy(List<Map<String, List<NutMap>>> data, String yyyy) {

        for (int i = 0; i < data.size(); i++) {
            Map<String, List<NutMap>> data_yyyy = data.get(i);
            if (data_yyyy.get(yyyy) != null) {
                return data_yyyy;
            }
        }

        Map<String, List<NutMap>> data_yyyy = new HashMap<>();
        data_yyyy.put(yyyy, new ArrayList<>());
        data.add(data_yyyy);
        return data_yyyy;

    }
}
