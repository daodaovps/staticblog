package cn.dd.staticblog.services.tohtml_page_item;

import cn.dd.staticblog.vo.BookInfo;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.nutz.json.Json;
import org.nutz.lang.Files;

public class ServiceBookinfo {

    public static BookInfo md_to_bookinfo(String bookinfo_md_path) {

        String markdown_txt = Files.read(bookinfo_md_path);
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown_txt);
        BookSectionsVisitor visitor = new BookSectionsVisitor();
        document.accept(visitor);
        System.out.println(Json.toJson(visitor.bookInfo));
        return visitor.bookInfo;
    }

    public static void main(String[] args) {

        ServiceBookinfo serviceMenu = new ServiceBookinfo();

        String path = "D:\\work_nutz\\website--all-md\\books\\book1\\AAA--bookinfo.md";
        BookInfo bookInfo = serviceMenu.md_to_bookinfo(path);
        System.out.println(Json.toJson(bookInfo));

    }
}
