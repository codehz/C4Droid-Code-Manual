package codehz.c4droidcodemanual;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CodeRepositories> contents;
    private int category;
    private int color;

    public RecyclerViewAdapter(final List<CodeRepositories> contents, int category, int color) {
        this.contents = contents;
        this.category = category;
        this.color = color;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_view, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CodeRepositories model = contents.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppApplication.Get(), ContentActivity.class);
                intent.putExtra("category", category);
                intent.putExtra("num", position);
                intent.putExtra("color", color);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppApplication.Get().startActivity(intent);
            }
        });
        ((TextView) holder.itemView.findViewById(R.id.card_view_title)).setText(model.getTitle());
        ((TextView) holder.itemView.findViewById(R.id.card_view_preview))
                .setText(model.getPreview());
    }
}
