package codehz.c4droidcodemanual;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CodeRepositories> contents;

    public RecyclerViewAdapter(final List<CodeRepositories> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false)){};
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CodeRepositories model = contents.get(position);
        ((TextView) holder.itemView.findViewById(R.id.card_view_title)).setText(model.getTitle());
        ((TextView) holder.itemView.findViewById(R.id.card_view_preview)).setText(model.getPreview());
    }
}
