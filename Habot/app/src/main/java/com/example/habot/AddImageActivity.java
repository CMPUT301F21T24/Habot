package com.example.habot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
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

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    UploadTask uploadTask;

    Button upload_button;

    String Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addimage);
        imageView = findViewById(R.id.imageView);
        gallery_button = findViewById(R.id.gallery);
        upload_button = findViewById(R.id.upload_btn);
        btnCaptureImage = (Button) findViewById(R.id.camera);


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

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
            StorageReference imageReference = mStorageRef.getReference().child(Username+"/image");

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .build();

            imageReference.putFile(imageUri,metadata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddImageActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
                        }
                    });


        }

        else{
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
            StorageReference imageReference = mStorageRef.getReference().child(Username+"/image2");

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();

            Bitmap bitmappp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream tempBit = new ByteArrayOutputStream();
            bitmappp.compress(Bitmap.CompressFormat.JPEG,100,tempBit);
            byte[] byteData = tempBit.toByteArray();

            imageReference.putBytes(byteData);

            //Log.d("TAG","11111111111111111111"+mStorageRef.getName());

        }
    }





}
