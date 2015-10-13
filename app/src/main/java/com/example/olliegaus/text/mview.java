package com.example.olliegaus.text;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class mview extends AppCompatActivity {

    //variables
    Button btn;
    EditText msg;
    String recipient;
    String ContactID;
    String name;
    String photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mview);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //get info about recipient
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipient = extras.getString("num");
            ContactID = extras.getString("id");
        }
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
                }
            }
        }



        //page title
        setTitle(name + ":");


        //set buttons
        btn = (Button) findViewById(R.id.btnsend);
        msg = (EditText) findViewById(R.id.message);

        //sending process
        btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        try {
                            //start sms service
                            SmsManager smsManager = SmsManager.getDefault();
                            Log.v("EditText", msg.getText().toString());
                            smsManager.sendTextMessage(recipient, null, msg.getText().toString(), null, null);
                            msg.setText("");
                            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                    }
                });
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
                startActivity(new Intent(mview.this, settings.class));
                return true;
            //if back button is pressed
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
