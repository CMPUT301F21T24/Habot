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

/**
This activity is designed for adding image to the habit event
 */
public class AddImageActivity extends AppCompatActivity {

    //initialize some variables
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

    /**
     * This will created when the activity is started
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addimage);

        //find views from layout files
        imageView = findViewById(R.id.imageView);
        gallery_button = findViewById(R.id.gallery);
        upload_button = findViewById(R.id.upload_btn);
        btnCaptureImage = (Button) findViewById(R.id.camera);
        return_button = findViewById(R.id.return_button);

        //get current username from the bundle
        Bundle bundle = getIntent().getExtras();
        Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //This will open gallery when the user tap the gallery button
        gallery_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Click the button and open the gallery
             * @param v
             */
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            /**
             * This will check the app permission of writing, if there's not, request for permission.
             * If there is permission, allow to take photo
             * @param v
             */
            @Override
            public void onClick(View v) {

                //Check for permission
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }

                //ask for permission
                else{
                    ActivityCompat.requestPermissions(AddImageActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE);
                }

            }
        });


        upload_button.setOnClickListener(new View.OnClickListener() {
            /**
             * When user click the upload button, put image uri to the bundle and take it back to the former activity
             * @param v
             */
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
            /**
             * Press this button to get back to the former activity
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(AddImageActivity.this, AddNewHabitEventActivity.class);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


    }

    /**
     * This will open gallery and allow users to pick image
     */
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /**
     * This will take bitmap as input and returns an Uri.
     * A Bitmap to Uri converter
     * @param context
     * @param bitmap
     * @return
     */
    public Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"bit2Uri",null);

        return Uri.parse(path);
    }

    /**
     * This method will get the result from corresponding Code
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //Get result from gallery
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            if (data != null) {
                //get image uri from the selected image in Gallery
                imageUri = data.getData();

                //Display the image to the user
                imageView.setImageURI(imageUri);
            }

        }

        //Get result from Camera
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

        //if imageUri is null, no image detected, the upload button is not available
        if (imageUri != null){
            upload_button.setVisibility(View.VISIBLE);
        }
    }
}
