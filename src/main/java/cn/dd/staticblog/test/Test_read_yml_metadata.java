package cn.dd.staticblog.test;

import cn.dd.staticblog.util.CommonUtil;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.util.NutMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test_read_yml_metadata {


    private static final Set<Extension> EXTENSIONS = Collections.singleton(YamlFrontMatterExtension.create());
    private static final Parser PARSER = Parser.builder().extensions(EXTENSIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().extensions(EXTENSIONS).build();

//    解析库 https://github.com/commonmark/commonmark-java
//    解析库  插件读取yaml  commonmark-ext-yaml-front-matter
    public static void main(String[] args) {

        String file_markdown = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books\\pages\\2022-06-24  metadata demo.md";
        NutMap data = CommonUtil.find_metadata(file_markdown);
        System.out.println(Json.toJson(data));

    }

    private static void test_02() {
        String file_markdown = "D:\\work_nutz\\staticblog\\doc\\typora-sample\\books\\pages\\2022-06-24  metadata demo.md";

        String markdown_txt = Files.read(file_markdown);

        Map<String, List<String>> data = getFrontMatter(markdown_txt);
        System.out.println(Json.toJson(data));
    }


    public static Map<String, List<String>> getFrontMatter(String input) {
        YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        document.accept(visitor);

        Map<String, List<String>> data = visitor.getData();
        return data;
    }


}
