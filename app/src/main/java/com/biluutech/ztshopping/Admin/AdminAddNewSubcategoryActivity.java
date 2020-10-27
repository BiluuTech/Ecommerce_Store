package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.biluutech.ztshopping.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewSubcategoryActivity extends AppCompatActivity {


    private String CategoryName, Cname, saveCurrentDate, saveCurrentTime;
    private Button AddNewsubcategoryButton;
    private ImageView InputsubcategoryImage;
    private EditText InputsubcategoryName;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String subcategoryRandomKey, downloadImageUrl;
    private StorageReference subcategoryImagesRef;
    private DatabaseReference subcategoryRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_subcategory);

        CategoryName = getIntent().getExtras().get("category").toString();
        subcategoryImagesRef = FirebaseStorage.getInstance().getReference().child("subcategory Images");
        subcategoryRef = FirebaseDatabase.getInstance().getReference().child(CategoryName);

        AddNewsubcategoryButton = (Button) findViewById(R.id.add_new_subcategory);
        InputsubcategoryImage = (ImageView) findViewById(R.id.select_subcategory_image);
        InputsubcategoryName = (EditText) findViewById(R.id.subcategory_name);
        loadingBar = new ProgressDialog(this);


        InputsubcategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        AddNewsubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidatesubcategoryData();
            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputsubcategoryImage.setImageURI(ImageUri);
        }
    }

    private void ValidatesubcategoryData()
    {

        Cname = InputsubcategoryName.getText().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "subcategory image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Cname))
        {
            Toast.makeText(this, "Please write subcategory name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoresubcategoryInformation();
        }
    }


    private void StoresubcategoryInformation()
    {
        loadingBar.setTitle("Add New subcategory");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new subcategory.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        subcategoryRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = subcategoryImagesRef.child(ImageUri.getLastPathSegment() + subcategoryRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewSubcategoryActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewSubcategoryActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewSubcategoryActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SavesubcategoryInfoToDatabase();
                        }
                    }
                });
            }
        });
    }


    private void SavesubcategoryInfoToDatabase()
    {
        HashMap<String, Object> subcategoryMap = new HashMap<>();

        subcategoryMap.put("scimage", downloadImageUrl);
        subcategoryMap.put("scname", Cname);
        subcategoryMap.put("catname", CategoryName);

        subcategoryRef.child(Cname).updateChildren(subcategoryMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewSubcategoryActivity.this, AdminHomeActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewSubcategoryActivity.this, "subcategory is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewSubcategoryActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        DatabaseReference allCategories = FirebaseDatabase.getInstance().getReference().child("AllCategories");

        allCategories.child(Cname).updateChildren(subcategoryMap);

    }



}