package uz.mq.mobilussduzb;

public class DataModel {

    private int image;
    private String title;
    private String desc;
    private int color;
    private String balans, daqida;

    public DataModel(int image, String title, String desc, int color, String balans, String daqida) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.color = color;
        this.balans = balans;
        this.daqida = daqida;
    }

    public String getBalans() {
        return balans;
    }

    public void setBalans(String balans) {
        this.balans = balans;
    }

    public String getDaqida() {
        return daqida;
    }

    public void setDaqida(String daqida) {
        this.daqida = daqida;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
