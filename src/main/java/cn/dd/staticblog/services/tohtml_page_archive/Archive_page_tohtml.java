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

        List<String> sortedKeys = new ArrayList(month_pageinfo_list.keySet());
        Collections.sort(sortedKeys);

        //  每年1份 ,   每年里面 list <月,count>
        List<Map<String, List<NutMap>>> data = new ArrayList<>();
        for (int i = 0; i < sortedKeys.size(); i++) {
            String yyyyMM = sortedKeys.get(i);
            String yyyy = yyyyMM.substring(0, 4);
            Map<String, List<NutMap>> yyyy_data = fetch_yyyy(data, yyyy);

            List<NutMap> yyyyMM_data = yyyy_data.get(yyyy);
            if (Lang.isEmpty(yyyyMM_data)) {
                yyyyMM_data = new ArrayList<>();
                yyyy_data.put(yyyy,yyyyMM_data);
            }
            yyyyMM_data.add(NutMap.NEW().setv(yyyyMM, month_pageinfo_list.get(yyyyMM).size()));
        }

        // 生成html
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            Map<String, List<NutMap>> yyyy_data = data.get(i);
            String yyyy = yyyy_data.keySet().stream().findFirst().get();
            List<NutMap> month_data = yyyy_data.get(yyyy);

            stringBuilder.append("<div class=\"col-md-4\">");
            stringBuilder.append("<dl>");
            stringBuilder.append("<dt>"+yyyy+"</dt>");
            for (int j = 0; j < month_data.size(); j++) {
                NutMap  item=month_data.get(j);
                String month=item.keySet().stream().findFirst().get();
                int count=month_pageinfo_list.get(month).size();
                stringBuilder.append("<dd><a href=\"/archive/month_"+month+".html\">"+Times.format("yyyy年MM月",Times.parseq("yyyyMM",month))+" ("+count+"篇文章)</a></dd>   ");
            }
            stringBuilder.append("</dl>");
            stringBuilder.append("</div>");

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
