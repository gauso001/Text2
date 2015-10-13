package com.example.olliegaus.text;

/**
 * Created by Ollie Gaus on 10/12/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    String [] imageId;
    String[] ids;
    private static LayoutInflater inflater=null;
    public CustomAdapter(newmsg mainActivity, String[] vals, String[] prgmImages, String[] idList) {
        // Auto-generated constructor stub
        result=vals;
        context=mainActivity;
        imageId=prgmImages;
        ids = idList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView c;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.c=(TextView) rowView.findViewById(R.id.character);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.c.setText("");

        //display image if exists
        if (imageId[position] != null) {
            Uri imgUri = Uri.parse(imageId[position]);
            holder.img.setImageURI(imgUri);
        }
        else
        {
            //if image does not exist, create one
            String first=result[position].substring(0, 1);
            holder.c.setText(first);
            Random rnd = new Random();
            holder.img.setBackgroundColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
        }

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // jump to potential recipient view
                Intent i = new Intent(context.getApplicationContext(), prview.class);
                i.putExtra("id",ids[position]);
                context.startActivity(i);

            }
        });
        return rowView;
    }

}