package cn.dd.staticblog.test;

import cn.dd.staticblog.util.PagerUT;

public class Test_page {


    public static void main(String[] args) {


        int pagesize = 2;
        int allcount = 100;
        int pageno = 7;
        String pagehtml = PagerUT.pages(pagesize, allcount, pageno, "javascript:page(%s,'" + args + "');", 3);

        System.out.println(pagehtml);

    }

}
