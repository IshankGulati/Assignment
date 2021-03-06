package com.carwale.ishankgulati.assignment2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 10;
    private GridView grid;
    private ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            ArrayList<Bitmap> list = savedInstanceState.getParcelableArrayList("bitmapList");
            imageAdapter.setBitmapList(list);
        }

        final ImageButton button = (ImageButton) findViewById(R.id.cameraButton);
        grid = (GridView)findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);
        grid.setAdapter(imageAdapter);

        Intent galleryIntent = getIntent();
        String action = galleryIntent.getAction();
        String type = galleryIntent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null){
            if(type.startsWith("image/")){
                handleReceivedImage(galleryIntent);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
    }

    private void handleReceivedImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageAdapter.addBitmap(b);
                imageAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outBundle){
        super.onSaveInstanceState(outBundle);
        outBundle.putParcelableArrayList("bitmapList", imageAdapter.getBitmapList());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAPTURE_IMAGE_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");

                imageAdapter.addBitmap(b);
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

}
