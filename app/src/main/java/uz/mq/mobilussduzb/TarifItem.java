package uz.mq.mobilussduzb;

public class TarifItem {
    String title;
    String description;
    String code;
    String term;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public String getTerm() {
        return term;
    }

    public TarifItem(String title, String description, String code, String term) {
        this.title = title;
        this.description = description;
        this.code = code;
        this.term = term;
    }
}
