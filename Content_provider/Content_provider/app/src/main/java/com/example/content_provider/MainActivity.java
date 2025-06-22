package com.example.content_provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.view.ActionMode;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    ArrayList<String> listdata;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        listdata = new ArrayList<String>();
        registerForContextMenu(list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                if(actionMode != null){
                    return false;
                }
                actionMode = startSupportActionMode(callback);
                return true;
            }
        });

        fetchContact();
    }

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.themeoptionalmenu, menu);
            mode.setTitle("Choose option");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(R.id.option_1 == item.getItemId()){
                Toast.makeText(MainActivity.this, "Option 1 selected",
                        Toast.LENGTH_LONG).show();
                mode.finish();
                return true;
            } else if (R.id.option_2 == item.getItemId()) {
                Toast.makeText(MainActivity.this, "Option 2 selected",
                        Toast.LENGTH_LONG).show();
                mode.finish();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.themeoptionalmenu, menu);
        return true;
    }

    private void fetchContact(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 123);
        }

        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionargs = null;
        String order = null;
        Cursor cursor = resolver.query(uri, projection, selection, selectionargs, order);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                String fullcontact = name + number;
                listdata.add(fullcontact);


            }
        }
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<String >(this,
                android.R.layout.simple_list_item_1, listdata);
        list.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.itRed == item.getItemId()){
            list.setBackgroundColor
                    (getResources().getColor(R.color.red));
            return true;

        } else if(R.id.itPurple == item.getItemId()){
            list.setBackgroundColor
                    (getResources().getColor(R.color.purple));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.themeoptionalmenu, menu);
        menu.setHeaderTitle("Select the Action");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (R.id.itRed == item.getItemId()){
            list.setBackgroundColor(getResources().getColor(R.color.red));
            return true;

        } else if(R.id.itPurple == item.getItemId()){
            list.setBackgroundColor(getResources().getColor(R.color.purple));
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}