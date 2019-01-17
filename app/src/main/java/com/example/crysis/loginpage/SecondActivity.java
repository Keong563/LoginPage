package com.example.crysis.loginpage;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private Button logout;
    RecyclerView mRecycleView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);

        firebaseAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mRecycleView = findViewById(R.id.recycleView);
        mRecycleView.setHasFixedSize(true);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("foods");

        logout = (Button)findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu: {
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model , ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef
                ) {


                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {

                        viewHolder.setDetail(getApplicationContext() , model.getFoodName() ,model.getFoodId(),model.getEmail(), model.getNewFoodImage() , model.getPickUpDetail() , model.getPrice() , model.getLocation() , model.getExpiredDate() , model.getTodayDate());


                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent , viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                TextView mTitleTv = view.findViewById(R.id.rTitleTv);
//                                TextView mDescTv = view.findViewById(R.id.rDescTv);
//                                TextView mPriceTv = view.findViewById(R.id.rPriceTv);
//                                ImageView mImageView = view.findViewById(R.id.rImageView);

                                String mFoodId = getItem(position).getFoodId();
                                String mTitle = getItem(position).getFoodName();
                                String mDesc = getItem(position).getPickUpDetail();
                                String mPrice = getItem(position).getPrice();
                                String mImage = getItem(position).getNewFoodImage();
                                String mLocation = getItem(position).getLocation();
                                String mExpiredDate = getItem(position).getExpiredDate();
                                String mTodayDate = getItem(position).getTodayDate();
                                String mEmail = getItem(position).getEmail();

                                //int mCurrentTime = ;
                                //mCurrentTime += position;
//                                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("email", mEmail);
                                //intent.putExtra()
                                intent.putExtra("foodId", mFoodId);
                                intent.putExtra("NewFoodImage", mImage);
                                intent.putExtra("foodName", mTitle);
                                intent.putExtra("pickUpDetail", mDesc);
                                intent.putExtra("price" , mPrice);
                                intent.putExtra("location" , mLocation);
                                intent.putExtra("expiredDate" , mExpiredDate);
                                intent.putExtra("todayDate" , mTodayDate);
                                startActivity(intent);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {



                            }
                        });

                        return viewHolder;
                    }
                };

        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upload_food) {
            startActivity(new Intent(SecondActivity.this,insertFood.class));
        }
        else if (id == R.id.nav_just_gone) {
            startActivity(new Intent(SecondActivity.this,justGone.class));

        } else if (id == R.id.nav_profile) {
            //startActivity(new Intent(MainActivity.this, PostDetailActivity.class));

        } else if (id == R.id.nav_logout) {
           // Toast.makeText(this, "LogOut", Toast.LENGTH_LONG).show();
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}