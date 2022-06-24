package cn.dd.staticblog.services.tohtml_page_tags;

import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import java.util.*;
import java.util.stream.Collectors;

public class Tags_index {


    /**
     * 遍历全部的 pages ,  得到 pageinfo   ( 含metadata 信息)
     * 对全部的tags 计数,   并做反向关联
     */
    public static void main(String[] args) {

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

        StringBuilder stringBuilder = new StringBuilder();
        tag_pagelist.forEach((key, value) -> {
            stringBuilder.append("<a href=\"/tags/tag-" + key + ".html\">" + key + " (" + value.size() + ")</a>");
        });

        String template_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\模板\\template-tags.html";
        String target_file = "D:\\work_nutz\\staticblog\\doc\\static-website-blog-theme\\tags.html";
        NutMap params = new NutMap();
        params.setv("tags", stringBuilder.toString());
        CommonUtil.update_file(template_file, target_file, params);

    }


}
