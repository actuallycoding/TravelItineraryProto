package com.example.hp.travelitinerary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity {
    DBHandler db = new DBHandler(this);
    TextView tvDay;
    TextView tvTitle;
    ImageView img;
    TextView tvActivities;
    Item currentItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        tvDay = (TextView)findViewById(R.id.textDay);
        tvTitle = (TextView)findViewById(R.id.textDayTitle);
        tvActivities = (TextView)findViewById(R.id.textNotes);
        img = (ImageView) findViewById(R.id.imageView2);
        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        Log.d("id",""+id);
        currentItem = db.getItem(id);
        tvDay.setText("Day - "+ currentItem.getDay());
        tvTitle.setText(""+currentItem.getItemName());
        tvActivities.setText("Activities: \n"+currentItem.getItemDescription());
        byte[] image = currentItem.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        img.setImageBitmap(bitmap);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayActivity.this, FullScreen.class);

                img.buildDrawingCache();
                Bitmap image= img.getDrawingCache();

                Bundle extras = new Bundle();
                extras.putParcelable("imagebitmap", image);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_day, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_delete) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id" ,currentItem.getId()-1);
            db.deleteItem(currentItem);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_edit) {
            //add
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("id" ,currentItem.getId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
