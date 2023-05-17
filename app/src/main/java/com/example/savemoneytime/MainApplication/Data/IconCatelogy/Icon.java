package com.example.savemoneytime.MainApplication.Data.IconCatelogy;

public class Icon {
    private String titleIcon;
    private int srcIcon;
    private int idIcon;

    public Icon(String titleIcon, int srcIcon, int idIcon) {
        this.titleIcon = titleIcon;
        this.srcIcon = srcIcon;
        this.idIcon = idIcon;
    }

    public String getTitleIcon() {
        return titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }

    public int getSrcIcon() {
        return srcIcon;
    }

    public void setSrcIcon(int srcIcon) {
        this.srcIcon = srcIcon;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }
}
