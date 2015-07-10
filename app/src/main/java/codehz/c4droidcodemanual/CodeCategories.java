package codehz.c4droidcodemanual;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class CodeCategories extends BmobObject {
    private String NameEN;
    private String NameCN;
    private BmobFile ThemePic;
    private Integer ThemeColor;
    private Integer Order;

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String nameEN) {
        NameEN = nameEN;
    }

    public String getNameCN() {
        return NameCN;
    }

    public void setNameCN(String nameCN) {
        NameCN = nameCN;
    }

    public BmobFile getThemePic() {
        return ThemePic;
    }

    public void setThemePic(BmobFile themePic) {
        ThemePic = themePic;
    }

    public Integer getThemeColor() {
        return ThemeColor;
    }

    public void setThemeColor(Integer themeColor) {
        ThemeColor = themeColor;
    }

    public Integer getOrder() {
        return Order;
    }

    public void setOrder(Integer order) {
        Order = order;
    }
}
