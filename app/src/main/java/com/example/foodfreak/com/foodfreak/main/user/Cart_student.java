package com.example.foodfreak.com.foodfreak.main.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foodfreak.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cart_student extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapterRecyclerView myAdapterRecyclerView;
    List<FoodData> cartItemList;
    Spinner spinner;
    String selectedtimeslot;
    int totalprice;
    TextView total_price_tv;
    Button placeorder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_student);
        total_price_tv=findViewById(R.id.totalprice);
        recyclerView=findViewById(R.id.cart_recyclerview);
        cartItemList = Canteeen_menu.getCartItemList();
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Cart_student.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapterRecyclerView=new MyAdapterRecyclerView(Cart_student.this,cartItemList);
        recyclerView.setAdapter(myAdapterRecyclerView);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        final String timeslot[]={"Time Slot",hour+":00 - "+(hour+1)+":00",(hour+1)+":00 - "+(hour+2)+":00"};

        spinner=findViewById(R.id.spinner_time_slot);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedtimeslot=timeslot[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,timeslot);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        for(FoodData reff_food : cartItemList)
        {

            totalprice+=Integer.valueOf(reff_food.getItemPrice())*reff_food.getItemCount();
        }
        total_price_tv.append(String.valueOf(totalprice));


    }
}
