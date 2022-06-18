package cn.dd.staticblog.vo;

import java.util.List;

public class BookSection {

    private String section;
    List<String> section_sub;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<String> getSection_sub() {
        return section_sub;
    }

    public void setSection_sub(List<String> section_sub) {
        this.section_sub = section_sub;
    }
}
