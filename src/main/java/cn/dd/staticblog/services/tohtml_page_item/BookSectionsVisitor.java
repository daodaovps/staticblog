package cn.dd.staticblog.services.tohtml_page_item;

import cn.dd.staticblog.vo.BookInfo;
import cn.dd.staticblog.vo.BookSection;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Text;
import org.nutz.lang.Lang;

import java.util.ArrayList;
import java.util.List;

class BookSectionsVisitor extends AbstractVisitor {

    BookInfo bookInfo = new BookInfo();

    @Override
    public void visit(Text text) {
        // This is called for all Text nodes. Override other visit methods for other node types.

        // Count words (this is just an example, don't actually do it this way for various reasons).

        String txt = text.getLiteral();
        int level = ((Heading) text.getParent()).getLevel();
        if (level == 1) {
            bookInfo.setBookname(txt);
        }

        if (level == 2) {
            BookSection section = new BookSection();
            List<String> subs = new ArrayList<>();
            section.setSection_sub(subs);
            section.setSection(txt);

            List<BookSection>  sections=bookInfo.getSections();
            if(Lang.isEmpty(sections)){
                sections=new ArrayList<>();
            }
            sections.add(section);
            bookInfo.setSections(sections);

        }

        if (level == 3) {

            List<BookSection> sections = bookInfo.getSections();
            BookSection last_booksection = sections.get(sections.size() - 1);
            List<String> subs = last_booksection.getSection_sub();
            if (Lang.isEmpty(subs)) {
                subs = new ArrayList<>();
            }
            subs.add(txt);
            last_booksection.setSection_sub(subs);
        }

        // Descend into children (could be omitted in this case because Text nodes don't have children).
        visitChildren(text);
    }
}
