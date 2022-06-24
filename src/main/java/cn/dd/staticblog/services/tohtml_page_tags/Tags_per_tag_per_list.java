package cn.dd.staticblog.services.tohtml_page_tags;

import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tags_per_tag_per_list {


    /**
     * 遍历全部的 pages ,  得到 pageinfo   ( 含metadata 信息)
     * 对全部的tags 计数,   并做反向关联
     */
    public static void main(String[] args) {

        String tag_page_file_base = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\tags\\";
        String tag_page_file_template = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-tag-page.html";


        List<Pageinfo> list = CommonUtil.get_all_pageinfo_list();

        Map<String, List<Pageinfo>> tag_pagelist = new LinkedHashMap<>();

        for (int i = 0; i < list.size(); i++) {
            Pageinfo pageinfo = list.get(i);

            List<String> tags = pageinfo.getTags();
            if (!Lang.isEmpty(tags)) {
                for (int j = 0; j < tags.size(); j++) {
                    String tag = tags.get(j);
                    List<Pageinfo> sub_tag_pageino_list = tag_pagelist.get(tag);
                    if (Lang.isEmpty(sub_tag_pageino_list)) {
                        sub_tag_pageino_list = new ArrayList<>();
                        tag_pagelist.put(tag, sub_tag_pageino_list);
                    }
                    sub_tag_pageino_list.add(pageinfo);
                }
            }
        }

        System.out.println(tag_pagelist);
        tag_pagelist = CommonUtil.sortByValue(tag_pagelist);
        System.out.println(tag_pagelist);

        tag_pagelist.forEach((key, value) -> {


            String tag = key;
            List<Pageinfo> pageinfos = value;


            StringBuilder stringBuilder = new StringBuilder();
            String target_file = tag_page_file_base + "/tag-" + tag + ".html";
            for (int i = 0; i < pageinfos.size(); i++) {
                Pageinfo pageinfo = pageinfos.get(i);
                stringBuilder.append("<li class=\"li_index\">\n" +
                        "\n" +
                        "                                <h3>\n" +
                        "                                    <a class=\"page_title\" target=\"_blank\" href=\"/pages/" + pageinfo.getUrl() + ".html\">" + pageinfo.getTitle() + "</a>\n" +
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


            //  每个tag  写入一份文件
            CommonUtil.update_file(
                    tag_page_file_template,
                    target_file,
                    NutMap.NEW().setv("tag",tag).setv("pageinfo_list_size",pageinfos.size()).setv("pageinfo_list", stringBuilder.toString()
                    )
            );

        });


    }


}
