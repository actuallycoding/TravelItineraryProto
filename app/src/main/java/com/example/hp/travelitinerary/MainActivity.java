package com.example.hp.travelitinerary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvItems;
    ArrayList<Item> itemList = new ArrayList<Item>();
    CustomAdapter caItem;
    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.dayListView);
        itemList = db.getAllItems();
        caItem = new CustomAdapter(this, R.layout.row, itemList);
        lvItems.setAdapter(caItem);
        caItem.notifyDataSetChanged();
        Intent i = getIntent();
        int id = i.getIntExtra("id", 01);
//        if (id != 01) {
//            db.deleteItem(db.getItem(id));
//            itemList.remove(id);
//            caItem.notifyDataSetChanged();
//        }
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                final Item selectedItem = itemList.get(position);
                Intent i = new Intent(v.getContext(), DayActivity.class);
                i.putExtra("id", selectedItem.getId());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_removeAll) {
            //clear database
            db.removeAllItems();
            itemList.clear();
            caItem.notifyDataSetChanged();
            return true;
        } else if (id == R.id.action_add) {
            //add
            Intent intent = new Intent(this, AddActivity.class);
            intent.putExtra("id", itemList.size() + 1);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
