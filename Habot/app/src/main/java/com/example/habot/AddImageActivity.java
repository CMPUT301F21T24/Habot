package com.example.habot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class AddImageActivity extends AppCompatActivity {
    ImageView imageView;

    Button gallery_button;
    Button btnCaptureImage;
    Button upload_button;
    Button return_button;

    private static final int PICK_IMAGE = 100;

    Uri imageUri;

    String Username;

    private static final int CAMERA_REQUEST = 1888;

    private static final int REQUEST_CODE = 123456;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addimage);
        imageView = findViewById(R.id.imageView);
        gallery_button = findViewById(R.id.gallery);
        upload_button = findViewById(R.id.upload_btn);
        btnCaptureImage = (Button) findViewById(R.id.camera);
        return_button = findViewById(R.id.return_button);


        Bundle bundle = getIntent().getExtras();
        Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);


        gallery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }

                else{
                    ActivityCompat.requestPermissions(AddImageActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE);
                }

            }
        });


        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(AddImageActivity.this, AddNewHabitEventActivity.class);
                bundle.putString("Uri",imageUri.toString());
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(AddImageActivity.this, AddNewHabitEventActivity.class);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"bit2Uri",null);

        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            if (data != null) {
                //get image uri from the selected image in Gallery
                imageUri = data.getData();

                //Display the image to the user
                imageView.setImageURI(imageUri);
            }

        }

        else if (resultCode == RESULT_OK){

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                //get bitmap info. from the photo taken by users
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                //get Uri from Bitmap
                imageUri = getImageUri(getApplicationContext(), bitmap);
                //Display the URI to the users
                imageView.setImageURI(imageUri);
            }

            else{
                ActivityCompat.requestPermissions(AddImageActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE);
            }

        }

        if (imageUri != null){
            upload_button.setVisibility(View.VISIBLE);
        }
    }
}
