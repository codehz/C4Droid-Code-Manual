package codehz.c4droidcodemanual;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Feedback extends BmobObject {
    public String Title;
    public String Content;
    public BmobFile Screenshot;

    public Feedback() {
        super();
    }

    public Feedback(String Title, String Content, BmobFile Screenshot) {
        setTitle(Title);
        setContent(Content);
        setScreenshot(Screenshot);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public BmobFile getScreenshot() {
        return Screenshot;
    }

    public void setScreenshot(BmobFile screenshot) {
        Screenshot = screenshot;
    }
}
