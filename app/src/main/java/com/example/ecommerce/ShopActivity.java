package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    ViewFlipper imgBanner;
    DatabaseReference mDatabaseRe;
    List<Popular> mPopular;
    RecyclerView mRecyclerview;
    PopularAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        imgBanner = findViewById(R.id.imgBanner_id);
        int sliders[] ={
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3
        };
        for (int slide:sliders){
            bannerFliper(slide);
        }
        showPopularProduct();
    }

    private void showPopularProduct() {
        mRecyclerview = findViewById(R.id.recyclerview_id);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mPopular = new ArrayList<>();
        mDatabaseRe = FirebaseDatabase.getInstance().getReference("popular");
        mDatabaseRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren()){
                    Popular popular = postSnapshot.getValue(Popular.class);
                    mPopular.add(popular);
                }
                mAdapter = new PopularAdapter(ShopActivity.this,mPopular);
                mRecyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ShopActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void bannerFliper(int image){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(image);
        imgBanner.addView(imageView);
        imgBanner.setFlipInterval(6000);
        imgBanner.setAutoStart(true);
        imgBanner.setInAnimation(this, android.R.anim.fade_in);
        imgBanner.setOutAnimation(this, android.R.anim.fade_out);

    }
}