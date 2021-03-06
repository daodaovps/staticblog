package cn.dd.staticblog.vo;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import java.io.File;
import java.util.List;

public class BookInfo {

    String bookinfo_md_file;
    String url; //   md5( new File( bookinfo_md_file ) )
    String bookname;
    List<BookSection> sections;

    public String getBookinfo_md_file() {
        return bookinfo_md_file;
    }

    public void setBookinfo_md_file(String bookinfo_md_file) {
        this.bookinfo_md_file = bookinfo_md_file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public List<BookSection> getSections() {
        return sections;
    }

    public void setSections(List<BookSection> sections) {
        this.sections = sections;
    }

    public String toHTML(String current_title) {

        String html = "<div class=\"container-fluid\">\n" + "                            <div class=\"row\">\n" + "                                " + "<div class=\"col-md-12 \">\n" + "                                    " + "<p class=\"bg-warning  menu_title\">\n" + "                                        " + "<a href=\"/book/1/95c291ba9e0845a5983ea9eeb6c7d60e.html\">" + this.getBookname() + "</a>\n" + "                                    " + "</p>\n" + "                                </div>\n" + "                            </div>\n" + "                            " + "<div class=\"row\">\n" + "                                <div class=\"col-md-12 \">\n" + "                                  " + "  <p class=\"bg-warning  menu_title\"><a href=\"/book/1/95c291ba9e0845a5983ea9eeb6c7d60e.html\">目录</a></p>\n" + "   " + "                             </div>\n" + "                            </div>\n" + "\n" + "                          " + "    <div class=\"row contents-detail\">\n" + get_sections_html(this, current_title) + "\t\t\t\t\t\t\t\t\n" + "\t\t\t\t\t\t\t\t\n" + "                       " + "         </div>\n" + "\n" + "\n" + "</div>";

        return html;
    }

    private String get_sections_html(BookInfo bookInfo, String current_title) {
        String folder = new File(bookinfo_md_file).getParent();

        StringBuilder strbuider = new StringBuilder();
        List<BookSection> sections = bookInfo.getSections();
        for (int i = 0; i < sections.size(); i++) {
            BookSection section = sections.get(i);

            strbuider.append("<div class=\"col-md-4\">");
            strbuider.append("<dl>");
            strbuider.append("<dt>第" + (i + 1) + "章  " + section.getSection() + "</dt>");
            List<String> subs = section.getSection_sub();
            for (int j = 0; j < subs.size(); j++) {

                String clss = "";
                String txt = subs.get(j);
                if (!Strings.isBlank(current_title) && current_title.equals(txt)) {
                    clss = " class=\"current_s\" ";
                }

                String page_md5 = Lang.md5(new File(folder + "/" + subs.get(j) + ".md"));
                strbuider.append("<dd><a " + clss + " href=\"/pages/" + page_md5 + ".html\">" + (i + 1) + "." + (j + 1) + "  " + subs.get(j) + "</a></dd>");
            }
            strbuider.append("</dl>");
            strbuider.append("</div>");
        }
        return strbuider.toString();
    }

    public  String get_top_nav(BookInfo bookInfo, String current_title) {
        StringBuilder strbuider = new StringBuilder();
        List<BookSection> sections = bookInfo.getSections();
        for (int i = 0; i < sections.size(); i++) {
            BookSection section = sections.get(i);
            List<String> subs = section.getSection_sub();
            for (int j = 0; j < subs.size(); j++) {
                String txt = subs.get(j);
                if (current_title.equals(txt)) {
                    strbuider.append(bookname +" > ");
                    strbuider.append("第" + (i + 1) + "章  " + section.getSection()+" > ");
                    strbuider.append( (i + 1) + "." + (j + 1) + "  " + subs.get(j) );
                    return strbuider.toString();
                }
            }
        }
        return strbuider.toString();
    }



}
