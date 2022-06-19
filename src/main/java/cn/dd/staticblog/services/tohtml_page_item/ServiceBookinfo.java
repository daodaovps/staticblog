package cn.dd.staticblog.services.tohtml_page_item;

import cn.dd.staticblog.vo.BookInfo;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;

import java.io.File;

public class ServiceBookinfo {

    public static BookInfo md_to_bookinfo(String bookinfo_md_path) {

        String markdown_txt = Files.read(bookinfo_md_path);
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown_txt);
        BookinfoVisitor visitor = new BookinfoVisitor();
        visitor.bookInfo.setBookinfo_md_file(bookinfo_md_path);
        visitor.bookInfo.setUrl(Lang.md5(new File(bookinfo_md_path)));

        document.accept(visitor);
        System.out.println(Json.toJson(visitor.bookInfo));

        return visitor.bookInfo;
    }

    public static void main(String[] args) {

        ServiceBookinfo serviceMenu = new ServiceBookinfo();

        String path = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books\\book1\\AAA--bookinfo.md";
        BookInfo bookInfo = serviceMenu.md_to_bookinfo(path);
        System.out.println(Json.toJson(bookInfo));

    }
}
