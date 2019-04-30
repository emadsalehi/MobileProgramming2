package com.example.mobileprograming2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment> list;

    public CommentAdapter(Context context, List<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_comment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Comment comment = list.get(i);
        viewHolder.textPostID.setText(String.valueOf(comment.getPostID()));
        viewHolder.textCommentId.setText(String.valueOf(comment.getCommentID()));
        viewHolder.textName.setText(comment.getName());
        viewHolder.textBody.setText(comment.getBody());
        viewHolder.textEmail.setText(comment.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textPostID, textCommentId, textName, textEmail, textBody;

        public ViewHolder(View itemView) {
            super(itemView);

            textCommentId = itemView.findViewById(R.id.comment_id);
            textPostID = itemView.findViewById(R.id.comment_post_id);
            textBody = itemView.findViewById(R.id.comment_body);
            textName = itemView.findViewById(R.id.comment_name);
            textEmail = itemView.findViewById(R.id.comment_email);
        }
    }
}
