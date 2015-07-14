package codehz.c4droidcodemanual;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

public class CodeRepositories extends BmobObject {
    private String Editor;
    private CodeCategories Category;
    private String Author;
    private String Title;
    private String Preview;
    private String Content;
    private BmobFile Source;

    public String getEditor() {
        return Editor;
    }

    public void setEditor(String editor) {
        Editor = editor;
    }

    public CodeCategories getCategory() {
        return Category;
    }

    public void setCategory(CodeCategories category) {
        Category = category;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPreview() {
        return Preview;
    }

    public void setPreview(String preview) {
        Preview = preview;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public BmobFile getSource() {
        return Source;
    }

    public void setSource(BmobFile source) {
        Source = source;
    }

    public void getPicUrl() {
        BmobQuery<PicDB> picDBBmobQuery = new BmobQuery<>();
        picDBBmobQuery.addWhereEqualTo("Target", this);
        picDBBmobQuery.findObjects(AppApplication.Get(), new FindListener<PicDB>() {
            @Override
            public void onSuccess(List<PicDB> list) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
