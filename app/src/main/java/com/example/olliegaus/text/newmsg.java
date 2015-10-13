package com.example.olliegaus.text;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class newmsg extends AppCompatActivity {

    //declare list
    ListView lv;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmsg);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //page title
        setTitle("Create message to:");


        // Defined Array values to show in ListView
        List<String> values = new ArrayList<String>();
        List<String> images = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();

        //run through list of contacts
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    //System.out.println("name : " + name + ", ID : " + id);
                    //complile all into list
                    values.add(name);
                    //compile list of id's to use to recognise recipient
                    ids.add(id);

                    //get image and add to list
                    String photo = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                    images.add(photo);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //System.out.println("phone" + phone);
                    }
                    pCur.close();


                }
            }
        }

        //convert contact names
        String[] vals = new String[values.size()];
        vals = values.toArray(vals);
        //convert contact photos
        String[] prgmImages = new String[images.size()];
        prgmImages = images.toArray(prgmImages);
        //convert contact id's
        String[] idList = new String[ids.size()];
        idList = ids.toArray(idList);

        //listview
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, vals,prgmImages,idList));




    }


    //menu controller
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    //menu button controller
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            //if settings button is pressed
            case R.id.action_settings:
                startActivity(new Intent(newmsg.this, settings.class));
                return true;
            //if back button is pressed
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}


