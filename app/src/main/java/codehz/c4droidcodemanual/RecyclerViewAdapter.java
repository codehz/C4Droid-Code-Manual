package codehz.c4droidcodemanual;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataModel> contents;

    public RecyclerViewAdapter(List<DataModel> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false)){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataModel model = contents.get(position);
        ((TextView)holder.itemView.findViewById(R.id.card_view_title)).setText(model.Title);
        ((TextView)holder.itemView.findViewById(R.id.card_view_preview)).setText(model.Preview);
        return;
    }
}
