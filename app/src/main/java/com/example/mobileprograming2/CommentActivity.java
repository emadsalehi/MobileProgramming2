package com.example.mobileprograming2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends FragmentActivity implements PopupMenu.OnMenuItemClickListener {

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private ArrayList<Comment> commentList;
    private RecyclerView.Adapter mAdapter;
    private String commentUrl = "https://jsonplaceholder.typicode.com/comments?postId=";
    private static final String post_ID = "";
    private static final int comment_length = 0;
    private String postID;

    public static void start(Context context, int postID) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(post_ID, String.valueOf(postID));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        postID = intent.getStringExtra(post_ID);
        commentUrl += postID;

        setContentView(R.layout.activity_comment);
        mList = findViewById(R.id.recycler_view);

        commentList = new ArrayList<>();
        mAdapter = new CommentAdapter(getApplicationContext(), commentList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        gridLayoutManager = new GridLayoutManager(this, 2);

        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.setAdapter(mAdapter);

        getCommentData();

    }

    public void showDialogAction(View v) {
        NamesDialogFragment namesDialogFragment = new NamesDialogFragment();
        namesDialogFragment.show(getFragmentManager(), "Hello");
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.view_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_view:
                mList.setLayoutManager(linearLayoutManager);
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.grid_view:
                mList.setLayoutManager(gridLayoutManager);
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void getCommentData() {
        final IntegerLengthWrapper length = new IntegerLengthWrapper();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(commentUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setPostID(jsonObject.getInt("postId"));
                        comment.setEmail(jsonObject.getString("email"));
                        comment.setBody(jsonObject.getString("body"));
                        comment.setName(jsonObject.getString("name"));
                        comment.setCommentID(jsonObject.getInt("id"));
                        commentList.add(comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                length.setLength(commentList.size());
                headerTextUpdater(length);
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        mAdapter.notifyDataSetChanged();
    }

    private void headerTextUpdater(IntegerLengthWrapper lengthWrapper) {
        int length = lengthWrapper.getLength();
        TextView headerText =findViewById(R.id.comment_header);
        String header = "Post " + postID + "," + " " + length + " comments";
        headerText.setText(header);
    }
}

