package com.example.hp.travelitinerary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY = 999;
    TextView tv;
    EditText etTitle;
    EditText etNotes;
    EditText etDay;
    ImageView imageView;
    Button btnChoose, btnAdd;
    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tv = (TextView) findViewById(R.id.textEditDay);
        etTitle = (EditText) findViewById(R.id.editETitle);
        etNotes = (EditText) findViewById(R.id.editEText);
        etDay = (EditText)findViewById(R.id.editTextDay);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        imageView = (ImageView) findViewById(R.id.imageView3);
        Intent intent = getIntent();
        tv.append(" - " );
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = etTitle.getText().toString();
                String itemDescription = etNotes.getText().toString();
                int itemDay = Integer.parseInt(etDay.getText().toString());
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                if( bitmap.getWidth() <=  1654 && bitmap.getHeight() <= 1654 ) {
                    Log.d("Insert: ", "Inserting...");
//                    db.addItem(item);
                    db.insertItem(itemDay,itemName,itemDescription, imageViewToByte(imageView));
                    Log.d("Reading: ", "Reading all items...");
                    ArrayList<Item> items = db.getAllItems();
                    for (Item thing : items) {
                        String log = "Id: " + thing.getId() + ",Name: " + thing.getItemName() + " ,Price: " + thing.getImage().toString();
                        ;
                        Log.d("item : :", log);
                    }
                    Intent i = new Intent(AddActivity.this,MainActivity.class);
                    startActivity(i);
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Toast.makeText(AddActivity.this, "The image is larger than 1654 x 1654! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
