package com.example.mobileprograming2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

abstract public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDialogAction(View v) {
        NamesDialogFragment namesDialogFragment = new NamesDialogFragment();
        namesDialogFragment.show(getFragmentManager(), "Hello");
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.view_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_view:
                return true;
            case R.id.grid_view:
                return true;
            default:
                return false;
        }
    }
}
