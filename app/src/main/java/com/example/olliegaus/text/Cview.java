package com.example.olliegaus.text;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Cview extends AppCompatActivity {

    ImageButton newmsg;



    //main function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main();

    }


    //main function mirror
    public void main() {
        setContentView(R.layout.activity_cview);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //page title
        setTitle("Text");


        //create new message button
        newmsg = (ImageButton) findViewById(R.id.new_message);
        newmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cview.this, newmsg.class));
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
            case R.id.action_settings:
                startActivity(new Intent(Cview.this, settings.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }


    //end program
}
