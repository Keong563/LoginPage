package com.example.crysis.loginpage;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class PostDetailActivity extends AppCompatActivity implements LifecycleObserver {

    TextView mTitleTv, mDetailTv , mPriceTv , mLocationTv , mExpiredDateTv , mTodayDateTv,mEmailTv;
    ImageView mImageIv;

    private FirebaseDatabase foodData;
    private DatabaseReference getData = FirebaseDatabase.getInstance().getReference("requestFoods");
    private DatabaseReference getData2;
    private StorageReference dataStore;

    private FirebaseAuth firebaseAuth;


    //getData = FirebaseDatabase.getInstance().getReference("requestFoods");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Post Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mTitleTv = findViewById(R.id.titleTv);
        mDetailTv = findViewById(R.id.descriptionTv);
        mExpiredDateTv = findViewById(R.id.expiredDateTv);
        mLocationTv = findViewById(R.id.locationTv);
        mTodayDateTv = findViewById(R.id.todayDateTv);
        mPriceTv = findViewById(R.id.priceTv);
        mImageIv = findViewById(R.id.imageView);
        mEmailTv = findViewById(R.id.email);

        String image = getIntent().getStringExtra("NewFoodImage");
        String title = getIntent().getStringExtra("foodName");
        String desc = getIntent().getStringExtra("pickUpDetail");
        String expDate = getIntent().getStringExtra("expiredDate");
        String location = getIntent().getStringExtra("location");
        String tdyDate = getIntent().getStringExtra("todayDate");
        String price = getIntent().getStringExtra("price");
        //int currentTime = getIntent().getIntExtra("time", 0);
//        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String email = getIntent().getStringExtra("email");


        mTitleTv.setText(title);
        mEmailTv.setText(email);
        mDetailTv.setText(desc);
        mExpiredDateTv.setText(expDate);
        mLocationTv.setText(location);
        mTodayDateTv.setText(tdyDate);
        mPriceTv.setText(price);
        Picasso.get().load(image).into(mImageIv);
//        mImageIv.setImageBitmap(bmp);

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    public void onClickRequest(View view) {

       // Date currentTime = Calendar.getInstance().getTime();
        //int test1 = Calendar.getInstance().getTime().

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(yourdate);
        //int hours = calendar.get(Calendar.HOUR_OF_DAY);
        //int minutes = calendar.get(Calendar.MINUTE);
        //int currentTime = getIntent().getIntExtra("time", 0);
        //int add = 1;



        getData.push().getKey();
        String foodKey = getIntent().getStringExtra("foodId");

//        for (int i = 0;i<3;i++){
//
//            if(foodKey.equals(getData.child("foodId"))){
//                currentTime += i ;
//                break;
//            }
//
//
//        }

        firebaseAuth = FirebaseAuth.getInstance();

        String email = firebaseAuth.getCurrentUser().getEmail();

        //getData2.orderByKey("foodId");
        //getData2.
        //Toast.makeText(this, "Whatever click at position: " + image, Toast.LENGTH_SHORT).show();

        //StorageReference imgUrl = imageStore.child(image);
        //startActivity(new Intent(MainActivity.this,PostDetailActivity.class));
        //private FirebaseDatabase foodData;
        //private DatabaseReference getData;

        //imgUrl.delete();
        //mDatabaseRef2 = FirebaseDatabase.getInstance().getReference("requestFoods");
        //String key


        String image = getIntent().getStringExtra("NewFoodImage");
        String title = getIntent().getStringExtra("foodName");
        String desc = getIntent().getStringExtra("pickUpDetail");
        String expDate = getIntent().getStringExtra("expiredDate");
        String location = getIntent().getStringExtra("location");
        String tdyDate = getIntent().getStringExtra("todayDate");
        String price = getIntent().getStringExtra("price");


        //databaseFood = FirebaseDatabase.getInstance().getReference("foods");

        Model food = new Model(foodKey,title,image,location,desc,price,tdyDate,expDate,email);

        getData.child(foodKey).setValue(food);
        //getData.child(foodKey).set
        //Model food = new Model(id,foodName,downloadUrl,location,pickUp,price,date,exp);

        sendMail();

        //super.onPause();
        //String uploadId2 = getData.push().getKey();

        //private FirebaseAuth firebaseAuth;
        getData = FirebaseDatabase.getInstance().getReference("foods").child(foodKey);
        getData.removeValue();

//        Toast.makeText(this, "Whatever click at position: " + foodKey, Toast.LENGTH_SHORT).show();


    }

    private void sendMail() {

        String title = getIntent().getStringExtra("foodName");
        String emailI = getIntent().getStringExtra("email");

        String[] receiver = emailI.split(",");
        String subject = "Hello Sir/Miss I would like to request for your food" ;

        String a = title;


        String message = "I would like to request for " + a;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, receiver);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));
        String foodKey = getIntent().getStringExtra("foodId");
        //Toast.makeText(this, "Whatever click at position: " + foodKey, Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this,SecondActivity.class));
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    public void releaseCamera() {
//        if (sendMail; != null) {
//            camera.release();
//            camera = null;
//        }
//    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    public void initializeCamera() {
//        if (camera == null) {
//            getCamera();
//        }
//    }

//    @Override
//    public void onClickRequest(View view , int position) {
//        Toast.makeText(this, "Whatever click at position: " + "HI", Toast.LENGTH_SHORT).show();
//    }
}
