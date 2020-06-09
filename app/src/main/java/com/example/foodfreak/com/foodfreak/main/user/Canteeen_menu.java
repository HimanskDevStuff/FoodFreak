package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.foodfreak.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Canteeen_menu extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference reffCanteenDetails;
    FoodData foodData;
    FirebaseStorage storage;
    StorageReference storageReferenceofImagedata;
    List<FoodData> foodDataList;
    List<Bitmap> imageDataList;
    static List<FoodData> cartItemList;

    // Views
    TextView cartCounter;
    FrameLayout cartIcon;
    int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteeen_menu);
        //initialize item count
        totalItemCount=0;
        //cartCounter
        cartCounter=findViewById(R.id.icr_dec_cart_tv);
        //cartIcon
        cartIcon=findViewById(R.id.cartIcon);
        recyclerView=findViewById(R.id.remommended_recyclerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Canteeen_menu.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        foodDataList=new ArrayList<>();
        cartItemList=new ArrayList<>();
        reffCanteenDetails= FirebaseDatabase.getInstance().getReference("canteen_details").child("E1_canteen");
        storage=FirebaseStorage.getInstance();
        storageReferenceofImagedata=storage.getReference("E1_canteen");
        reffCanteenDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    foodData=ds.getValue(FoodData.class);
                    foodDataList.add(foodData);
                }
                MyAdapterRecyclerView myAdapterRecyclerView=new MyAdapterRecyclerView(Canteeen_menu.this,foodDataList);

                recyclerView.setAdapter(myAdapterRecyclerView);
                myAdapterRecyclerView.setOnItemClickedListener(new MyAdapterRecyclerView.OnItemListener() {
                    @Override
                    public void onItemClicked(int position, int itemCount) {
                        //Setting the itemCount to each food Item
                        //inc the total itemitemcount
                        totalItemCount=totalItemCount+1;
                        //Setting cart icon to increment and decrement
                        if(totalItemCount>0)
                        {
                            cartCounter.setVisibility(View.VISIBLE);
                            cartCounter.setText(String.valueOf(totalItemCount));
                        }
                        else
                        {
                            cartCounter.setText(String.valueOf(totalItemCount));
                            cartCounter.setVisibility(View.GONE);
                        }

                        int i;
                        for(i=0;i<cartItemList.size();i++)
                        {
                            if(cartItemList.get(i)==foodDataList.get(position))
                            {
                                break;
                            }
                        }
                        if(i==cartItemList.size())
                        cartItemList.add(foodDataList.get(position));

                    }
                });
                cartIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i=new Intent(Canteeen_menu.this,Cart_student.class);
                        if(Build.VERSION.SDK_INT>17)
                        {
                            if(totalItemCount>0)
                            {
                                startActivity(i);
                            }

                        }
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public static List<FoodData> getCartItemList()
    {
        return cartItemList;
    }
}
