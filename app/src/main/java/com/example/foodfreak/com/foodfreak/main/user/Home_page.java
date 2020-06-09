package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodfreak.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class Home_page extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //Hooks for bottomnavigation
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        //Setting the default selected item as canteen_fragment
        bottomNavigationView.setSelectedItemId(R.id.canteen_list_item);
        //Instances of all the fragments
        final Account Account_fragment=new Account();
        final Favourite Favourite_fragment=new Favourite();
        final Orders Order_fragment= new Orders();
        final Canteen Canteen_fragment=new Canteen();
        //What happens on selecting each item in BottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.account_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home,Account_fragment).commit();
                        return true;
                    case R.id.order_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home,Order_fragment).commit();
                        return true;
                    case R.id.favourite_items:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home,Favourite_fragment).commit();
                        return true;
                    case R.id.canteen_list_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home,Canteen_fragment).commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.canteen_list_item);
    }
}
