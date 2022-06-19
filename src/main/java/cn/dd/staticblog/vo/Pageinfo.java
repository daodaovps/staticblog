package cn.dd.staticblog.vo;

import java.util.Date;

public class Pageinfo {

    private String title;
    private String nav_side_html;
    private String content_html;
    private BookInfo bookinfo;
    private Date last_modify_date;
    private String file_md5;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNav_side_html() {
        return nav_side_html;
    }

    public void setNav_side_html(String nav_side_html) {
        this.nav_side_html = nav_side_html;
    }

    public String getContent_html() {
        return content_html;
    }

    public void setContent_html(String content_html) {
        this.content_html = content_html;
    }

    public BookInfo getBookinfo() {
        return bookinfo;
    }

    public void setBookinfo(BookInfo bookinfo) {
        this.bookinfo = bookinfo;
    }

    public Date getLast_modify_date() {
        return last_modify_date;
    }

    public void setLast_modify_date(Date last_modify_date) {
        this.last_modify_date = last_modify_date;
    }

    public String getFile_md5() {
        return file_md5;
    }

    public void setFile_md5(String file_md5) {
        this.file_md5 = file_md5;
    }
}
