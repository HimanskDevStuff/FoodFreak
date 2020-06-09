package com.example.foodfreak.com.foodfreak.main.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.user.Canteen;
import com.example.foodfreak.com.foodfreak.main.user.Favourite;
import com.example.foodfreak.com.foodfreak.main.user.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_home_page extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        //Hooks for bottomnavigation
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        //Instances of all the fragments
        final Account_admin Account_fragment=new Account_admin();
        final Update_item Update_fragment=new Update_item();
        final Add_new_item Add_item_fragment=new Add_new_item();
        //What happens on selecting each item in BottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.account_admin:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home_admin,Account_fragment).commit();
                        return true;
                    case R.id.updateitem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home_admin,Update_fragment).commit();
                        return true;
                    case R.id.additem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Home_admin,Add_item_fragment).commit();
                        return true;
                }
                return false;
            }
        });
        //Setting the default selected item as Account
        bottomNavigationView.setSelectedItemId(R.id.account_admin);
    }
}
