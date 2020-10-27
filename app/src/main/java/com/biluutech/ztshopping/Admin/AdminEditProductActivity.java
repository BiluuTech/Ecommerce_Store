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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminEditProductActivity extends AppCompatActivity {

    private ImageView editProductImage;
    private EditText editProductName, editProductDesc, editProductPrice;

    private Button editProductBtn;

    private String subcategory, pid;

    private DatabaseReference editRef;
    private DatabaseReference editRef2;

    private ProgressDialog loadingBar;

    private static final int GalleryPick = 1;

    private Uri ImageUri;

    private StorageReference ProductImagesRef;

    private String downloadImageUrl, productRandomKey, saveCurrentDate, saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_product);

        editProductImage = findViewById(R.id.select_edit_product_image);
        editProductName = findViewById(R.id.edit_product_name);
        editProductDesc = findViewById(R.id.edit_product_description);
        editProductPrice = findViewById(R.id.edit_product_price);
        editProductBtn = findViewById(R.id.edit_product_btn);

        loadingBar = new ProgressDialog(this);

        subcategory = getIntent().getStringExtra("subcategory");
        pid = getIntent().getStringExtra("pid");

        editRef = FirebaseDatabase.getInstance().getReference().child("AllProducts");
        editRef2 = FirebaseDatabase.getInstance().getReference().child("Products").child(subcategory);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        editRef.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String pname = snapshot.child("pname").getValue().toString();
                String description = snapshot.child("description").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                String image = snapshot.child("image").getValue().toString();

                editProductName.setText(pname);
                editProductDesc.setText(description);
                editProductPrice.setText(price);

                Picasso.get().load(image).into(editProductImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        editProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editProductName.getText().toString())){
                    editProductName.setError("Product name is mandatory");
                }
                else if(TextUtils.isEmpty(editProductDesc.getText().toString())){
                    editProductDesc.setError("Product description is mandatory");
                }
                else if(TextUtils.isEmpty(editProductPrice.getText().toString())){
                    editProductPrice.setError("Product price is mandatory");
                }

                else {

                    loadingBar.setTitle("Saving Data");
                    loadingBar.setMessage("Dear Admin, please wait while we are saving the new data.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate = currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime = currentTime.format(calendar.getTime());

                    productRandomKey = saveCurrentDate + saveCurrentTime;

                    final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + pid + ".jpg");

                    final UploadTask uploadTask = filePath.putFile(ImageUri);


                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            String message = e.toString();
                            Toast.makeText(AdminEditProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Toast.makeText(AdminEditProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                                        Toast.makeText(AdminEditProductActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                                        HashMap<String, Object> updateMap = new HashMap<>();
                                        updateMap.put("pname", editProductName.getText().toString());
                                        updateMap.put("description", editProductDesc.getText().toString());
                                        updateMap.put("price", editProductPrice.getText().toString());
                                        updateMap.put("image", downloadImageUrl);

                                        editRef2.updateChildren(updateMap);

                                        editRef.child(pid).updateChildren(updateMap);

                                        loadingBar.dismiss();

                                        startActivity(new Intent(AdminEditProductActivity.this,AdminAllProductsActivity.class));
                                        finish();
                                    }
                                }
                            });
                        }
                    });



                }

            }
        });

    }

    private void OpenGallery() {

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
            editProductImage.setImageURI(ImageUri);
        }
    }

}