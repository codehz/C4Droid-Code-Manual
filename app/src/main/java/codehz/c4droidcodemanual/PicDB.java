package codehz.c4droidcodemanual;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class PicDB extends BmobObject {
    private CodeRepositories Target;
    private BmobFile Data;
    private String Description;

    public CodeRepositories getTarget() {
        return Target;
    }

    public void setTarget(CodeRepositories target) {
        Target = target;
    }

    public BmobFile getData() {
        return Data;
    }

    public void setData(BmobFile data) {
        Data = data;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
