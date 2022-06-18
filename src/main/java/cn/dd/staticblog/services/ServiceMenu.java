package cn.dd.staticblog.services;

import cn.dd.staticblog.vo.BookInfo;
import org.commonmark.Extension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.nutz.json.Json;
import org.nutz.lang.Files;

import java.util.Collections;
import java.util.Set;

public class ServiceMenu {

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

        ServiceMenu serviceMenu = new ServiceMenu();

        String path = "D:\\work_nutz\\website--all-md\\books\\book1\\AAA--bookinfo.md";
        serviceMenu.md_to_bookinfo(path);


    }
}
