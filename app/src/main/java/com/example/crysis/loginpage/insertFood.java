package com.example.crysis.loginpage;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class insertFood extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText editTextFoodName;
    EditText editTextPickUp;
    EditText editTextLocation;
    EditText editTextPrice;
    TextView textViewExp;
    TextView textViewTodayDate;
    Button buttonAdd;
    ImageView imageViewFood;
    Uri imgUri;

    private StorageReference mStorageRef;
    private FirebaseAuth firebaseAuth;



    Date today = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    String date = formatter.format(today);

    DatabaseReference databaseFood;

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_food);

        databaseFood = FirebaseDatabase.getInstance().getReference("foods");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        editTextFoodName = (EditText) findViewById(R.id.editTextFoodName);
        editTextPickUp = (EditText) findViewById(R.id.editTextPickUp);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        textViewExp = (TextView) findViewById(R.id.textViewExp);
        textViewTodayDate = (TextView) findViewById(R.id.textViewTodayDate);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        imageViewFood = (ImageView) findViewById(R.id.imageViewFood);


        textViewTodayDate.setText(date);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
            }
        });

        Button buttonCal = (Button) findViewById(R.id.buttonCal);
        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    public void btnBrowse_Click(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"),REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CODE&& resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imgUri=data.getData();

            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageViewFood.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        TextView textViewCal = (TextView) findViewById(R.id.textViewExp);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        textViewCal.setText(currentDateString);

    }


    private void addFood() {

        if(imgUri!=null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading Image");
            dialog.show();

            //Get the storage reference
            final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));
            //Add file to reference
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUrl = uri.toString();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image uploaded", Toast.LENGTH_LONG).show();
                            String id  = databaseFood.push().getKey();

                            String foodName = editTextFoodName.getText().toString().trim();
                            String pickUp = editTextPickUp.getText().toString().trim();
                            String location = editTextLocation.getText().toString().trim();
                            String price = editTextPrice.getText().toString().trim();
                            //Date currentTime = Calendar.getInstance().getTime();
                            //int currentTime = 0;
                            TextView textViewCal = (TextView) findViewById(R.id.textViewExp);
                            String exp = textViewCal.getText().toString();
                            textViewCal.setText(exp);

                            firebaseAuth = FirebaseAuth.getInstance();

                            String email = firebaseAuth.getCurrentUser().getEmail();

                            Model food = new Model(id,foodName,downloadUrl,location,pickUp,price,date,exp,email);
                            databaseFood.child(id).setValue(food);
                        }
                    });

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded" + (int)progress);
                        }
                    });
        }

        else {
            Toast.makeText(this, "Please select image", Toast.LENGTH_LONG).show();
        }
    }
}
