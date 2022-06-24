package cn.dd.staticblog.services.tohtml_page_tags;

import cn.dd.staticblog.util.CommonUtil;
import cn.dd.staticblog.vo.Pageinfo;
import org.nutz.dao.impl.NutDao;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import sun.jvm.hotspot.debugger.Page;

import java.util.ArrayList;
import java.util.List;

public class Tags_home {


    /**
     * 遍历全部的 pages ,  得到 pageinfo   ( 含metadata 信息)
     * 对全部的tags 计数,   并做反向关联
     */
    public static void main(String[] args) {

        List<Pageinfo> list = CommonUtil.get_all_pageinfo_list();
        System.out.println(list);

        NutMap tag_pagelist = new NutMap();

        for (int i = 0; i < list.size(); i++) {
            Pageinfo pageinfo = list.get(i);

            List<String> tags = pageinfo.getTags();
            if (!Lang.isEmpty(tags)) {
                for (int j = 0; j < tags.size(); j++) {
                     String tag=tags.get(j);
                     List<Pageinfo> sub_tag_pageino_list=tag_pagelist.getAsList(tag,Pageinfo.class);
                     if(Lang.isEmpty(sub_tag_pageino_list)){
                         sub_tag_pageino_list=new ArrayList<>();
                     }

                }
            }

        }
    }


}
