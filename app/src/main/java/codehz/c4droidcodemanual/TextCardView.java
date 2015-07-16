package codehz.c4droidcodemanual;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.markdownj.MarkdownProcessor;

import java.util.ArrayList;
import java.util.List;

public class TextCardView extends RecyclerView {
    private final static MarkdownProcessor markdownProcessor = new MarkdownProcessor();
    private String content;
    private List<String> pic;
    private List<ViewDataModel> viewDataModelList = new ArrayList<>();

    public TextCardView(Context context) {
        this(context, null);
    }

    public TextCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextCardView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void decode() {
        String[] list = content.split("````");
        for (String node : list) {
            Log.d("TextCardView", "Node:" + node);
            if (node.startsWith("```Pic")) {
                viewDataModelList.add(new ImageViewDataModel(getContext(),
                        pic.get(Integer.parseInt(node.substring(7, -2)))));
            } else {
                viewDataModelList.add(new TextViewDataModel(getContext(), node));
            }
        }
        Log.d("TextCardView", "List" + viewDataModelList.toString());
        setAdapter(new RecyclerViewMaterialAdapter(new DataAdapter()));
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    class DataAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_card_text, parent, false)) {
            };
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ((CardView) holder.itemView).removeAllViews();
            ((CardView) holder.itemView).addView(viewDataModelList.get(position).GetView());
        }

        @Override
        public int getItemCount() {
            return viewDataModelList.size();
        }
    }

    abstract class ViewDataModel {
        protected Context context;

        protected ViewDataModel(Context context) {
            this.context = context;
        }

        abstract public View GetView();
    }

    class TextViewDataModel extends ViewDataModel {

        final static private String Format = "<html>" +
                "<body style='padding: 0'>%s</body></html>";
        private String html;

        public TextViewDataModel(Context context, String text) {
            super(context);
            html = markdownProcessor.markdown(text.replaceAll("``", "  \n")).trim();
        }

        @Override
        public View GetView() {
            WebView view = new MyWebView(context);
            view.setVisibility(View.INVISIBLE);
            view.setVerticalScrollBarEnabled(false);
            view.loadData(String.format(Format, html), "text/HTML", "utf-8");
            view.setBackgroundColor(0);
            view.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setVisibility(View.VISIBLE);
            return view;
        }

        @Override
        public String toString() {
            return "\"" + html + "\"";
        }

        class MyWebView extends WebView {
            //private boolean isFirst = true;
            private Handler handler;

            @SuppressLint("AddJavascriptInterface")
            public MyWebView(Context context) {
                super(context);
                getSettings().setJavaScriptEnabled(true);
                handler = new Handler();
                setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //App.resize(document.body.getBoundingClientRect().height
                        view.loadUrl("javascript:" +
                                "App.resize(document.body.getBoundingClientRect().bottom + " +
                                "document.body.getBoundingClientRect().top);");
                        super.onPageFinished(view, url);
                    }
                });
                addJavascriptInterface(this, "App");
            }

            @JavascriptInterface
            public void resize(final float height) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("interface", height + "");
                        setLayoutParams(new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                (int) ((height) * getResources().getDisplayMetrics().density)));
                    }
                });
            }
        }
    }

    class ImageViewDataModel extends ViewDataModel {
        private String url;

        protected ImageViewDataModel(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Override
        public View GetView() {
            ImageView view = new ImageView(context);
            ImageLoader.getInstance().displayImage(url, view);
            view.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            return view;
        }

        @Override
        public String toString() {
            return "[Image]";
        }
    }
}
