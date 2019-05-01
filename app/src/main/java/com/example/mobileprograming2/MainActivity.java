package com.example.mobileprograming2;

import android.app.Activity;
import android.os.Bundle;
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

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Post> postList;
    private RecyclerView.Adapter mAdapter;
    private String postUrl = "https://jsonplaceholder.typicode.com/posts";
    private int activityListItemSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = findViewById(R.id.recycler_view);

        postList = new ArrayList<>();
        mAdapter = new PostAdapter(getApplicationContext(), postList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        gridLayoutManager = new GridLayoutManager(this, 2);

        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.setAdapter(mAdapter);

        getPostData();
        mList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int id = postList.get(position).getId();
                        CommentActivity.start(getApplicationContext(), id);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        onItemClick(view, position);
                    }
                })
        );
    }

    public void showDialogAction(View v) {
        NamesDialogFragment namesDialogFragment = new NamesDialogFragment();
        namesDialogFragment.show(getFragmentManager(), "Hello");
    }

    public void showPopup(View v) {
        //activityListItemSelected = listItemSelected;
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

    private void getPostData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(postUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Post post = new Post();
                        post.setUserID(jsonObject.getInt("userId"));
                        post.setBody(jsonObject.getString("body"));
                        post.setId(jsonObject.getInt("id"));
                        post.setTitle(jsonObject.getString("title"));
                        Log.i(post.getTitle(), "Title");
                        postList.add(post);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
}
