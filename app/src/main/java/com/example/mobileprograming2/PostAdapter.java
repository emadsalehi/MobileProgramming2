package com.example.mobileprograming2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> list;

    public PostAdapter(Context context, List<Post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = list.get(i);
        viewHolder.textTitle.setText(post.getTitle());
        viewHolder.textId.setText(post.getId());
        viewHolder.textUserId.setText(post.getId());
        viewHolder.textBody.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textUserId, textId, textBody, textTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            textUserId = itemView.findViewById(R.id.main_user_id);
            textId = itemView.findViewById(R.id.main_id);
            textBody = itemView.findViewById(R.id.main_body);
            textTitle = itemView.findViewById(R.id.main_title);
        }
    }
}
