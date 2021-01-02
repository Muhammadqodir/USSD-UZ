package uz.mq.mobilussduzb;

public class item_view {
    String title;
    String code;
    int color;

    public item_view(String title, String code, int color) {
        this.title = title;
        this.code = code;
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public int getColor() {
        return color;
    }
}
