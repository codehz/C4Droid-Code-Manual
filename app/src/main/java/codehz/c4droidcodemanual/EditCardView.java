package codehz.c4droidcodemanual;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.ArrowKeyMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

public class EditCardView extends TextCardView {
    public EditCardView(Context context) {
        super(context);
    }

    public EditCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditCardView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void decode() {
        String[] list = content.split("`====`");
        for (String node : list) {
            Log.d("EditCardView", "Node:" + node);
            if (node.startsWith("`Pic")) {
                viewDataModelList.add(new EditImageDataModel(getContext(),
                        pic.get(Integer.parseInt(node.substring(5, -1)))));
            } else {
                viewDataModelList.add(new EditTextDataModel(getContext(), node));
            }
        }
        Log.d("EditCardView", "List" + viewDataModelList.toString());
        setAdapter(new RecyclerViewMaterialAdapter(new DataAdapter()));
    }

    public String encode() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (ViewDataModel model : viewDataModelList) {
            if (!first) builder.append("`====`");
            EditViewDataModel editModel = (EditViewDataModel) model;
            builder.append(editModel.getContent());
            first = false;
        }
        return builder.toString();
    }

    abstract class EditViewDataModel extends ViewDataModel {
        protected EditViewDataModel(Context context) {
            super(context);
        }

        public abstract String getContent();
    }

    class EditTextDataModel extends EditViewDataModel {
        private String text;
        private AppCompatEditText view;

        protected EditTextDataModel(Context context, String text) {
            super(context);
            this.text = text
                    .replaceAll("`br`", " \n")
                    .replaceAll("/`/", "`");
        }

        @Override
        public View GetView() {
            view = new AppCompatEditText(context);
            view.setText(text);
            view.setTextColor(0xff000000);
            view.setMovementMethod(ArrowKeyMovementMethod.getInstance());
            view.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            return view;
        }

        @Override
        public String getContent() {
            text = view.getText().toString();
            return text.replaceAll("`", "/`/")
                    .replaceAll(" \n", "`br`")
                    .replaceAll("\n", " ");
        }

        @Override
        public String toString() {
            return "\"" + text + "\"";
        }
    }

    class EditImageDataModel extends EditViewDataModel {

        protected EditImageDataModel(Context context, String pic) {
            super(context);
        }

        @Override
        public String getContent() {
            return null;
        }

        @Override
        public View GetView() {
            return null;
        }
    }
}
