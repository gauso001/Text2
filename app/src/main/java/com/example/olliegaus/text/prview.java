package com.example.olliegaus.text;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class prview extends AppCompatActivity {

    TextView id;
    String ContactID;
    String name;
    ListView listView ;
    ImageView img;
    String photo;
    TextView c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prview);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set page title
        setTitle("Create message to:");
        //get bundled contact id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ContactID = extras.getString("id");
        }

        //create array for containing phone number
        List<String> phoneNumbers = new ArrayList<String>();


        //search contacts until matching id is found
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

                //if correct contact found
                if(id.equals(ContactID)) {
                    //get name
                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //get photo
                    photo = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                    // get phone number(s)
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumbers.add(phone);
                    }
                    pCur.close();
                }
            }
        }

        c = (TextView) findViewById(R.id.bigchar);
        //display contact photo
        img=(ImageView) findViewById(R.id.photo);
        if (photo != null) {
            Uri imgUri = Uri.parse(photo);
            img.setImageURI(imgUri);
            c.setText("");
        }
        else
        {
            //if image does not exist, create one
            String first = name.substring(0, 1);
           c.setText(first);
            Random rnd = new Random();
            img.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        }




        //display contact name
        id = (TextView) findViewById(R.id.idnum);
        id.setText(name);


        //display list of numbers
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, phoneNumbers);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                Intent i = new Intent(getApplicationContext(), mview.class);
                i.putExtra("num",itemValue);
                i.putExtra("id",ContactID);
                startActivity(i);
            }

        });


    }


    //menu controller
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    //menu button controller
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
            // Handle presses on the action bar items
            switch (item.getItemId()) {
                //if settings button is pressed
                case R.id.action_settings:
                    startActivity(new Intent(prview.this, settings.class));
                    return true;
                //if back button is pressed
                case android.R.id.home:
                    finish();

                default:
                    return super.onOptionsItemSelected(item);
            }


    }
}
