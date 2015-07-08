package codehz.c4droidcodemanual;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<DataModel> mModels;

    public DataAdapter(final Context context, final List<DataModel> models) {
        mContext = context;
        mModels = models;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        final DataModel model = mModels.get(i);
        ViewHolder viewHolderSelf = (ViewHolder) viewHolder;
        viewHolderSelf.Title.setText(model.Title);
        viewHolderSelf.Preview.setText(model.Preview);
    }

    @Override
    public int getItemCount() {
        return mModels == null ? 0 : mModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, Preview;

        public ViewHolder(final View view) {
            super(view);
            Title = (TextView) view.findViewById(R.id.card_view_title);
            Preview = (TextView) view.findViewById(R.id.card_view_preview);
        }
    }
}
