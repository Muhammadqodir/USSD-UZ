package uz.mq.mobilussduzb;

public class DataItem {
    String Title;
    String Value;
    String Cost;
    String Description;
    String Code;
    int icon;

    public void setTitle(String title) {
        Title = title;
    }

    public void setValue(String value) {
        Value = value;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return Title;
    }

    public String getValue() {
        return Value;
    }

    public String getCost() {
        return Cost;
    }

    public String getDescription() {
        return Description;
    }

    public String getCode() {
        return Code;
    }

    public DataItem(String title, String value, String cost, String description, String code, int icon) {
        Title = title;
        Value = value;
        Cost = cost;
        Description = description;
        Code = code;
        this.icon = icon;
    }
}
