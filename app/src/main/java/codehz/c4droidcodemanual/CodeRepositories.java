package codehz.c4droidcodemanual;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class CodeRepositories extends BmobObject {
    private String Editor;
    private CodeCategories Category;
    private String Author;
    private String Title;
    private String Preview;
    private String InnerHTML;
    private BmobRelation InnerPic;
    private BmobFile Source;
    private BmobRelation Comments;

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

    public String getInnerHTML() {
        return InnerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        InnerHTML = innerHTML;
    }

    public BmobRelation getInnerPic() {
        return InnerPic;
    }

    public void setInnerPic(BmobRelation innerPic) {
        InnerPic = innerPic;
    }

    public BmobFile getSource() {
        return Source;
    }

    public void setSource(BmobFile source) {
        Source = source;
    }

    public BmobRelation getComments() {
        return Comments;
    }

    public void setComments(BmobRelation comments) {
        Comments = comments;
    }
}
