package com.example.hp.travelitinerary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Item> itemList;

    public CustomAdapter(Context context, int resource, ArrayList<Item> objects) {

        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        itemList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate a new view hiearchy from the specified xml resource(layout_id)
        //and return the root View of the inflated hierarchy.
        View rowView = inflater.inflate(layout_id, parent, false);
        //Obtain the UI Element and assign to a variable
        TextView tvDay = (TextView) rowView.findViewById(R.id.textViewDay);
        TextView tvDetails = (TextView) rowView.findViewById(R.id.textTitle);
        ImageView imgView = (ImageView)rowView.findViewById(R.id.imageView3);
        //Obtain the to-do item based on the 'position'.
        Item currentItem = itemList.get(position);
        //Set the TextView to display corresponding information
        byte[] image = currentItem.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgView.setImageBitmap(bitmap);
        tvDay.append(" - " + currentItem.getDay());
        tvDetails.setText(currentItem.getItemName());
        //return the View corresponding to the data at the specified position.
        return rowView;
    }

}
