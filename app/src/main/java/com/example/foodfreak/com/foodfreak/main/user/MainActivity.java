package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.admin.Admin_home_page;
import com.example.foodfreak.com.foodfreak.main.admin.admin_login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.InternalTokenResult;

public class MainActivity extends AppCompatActivity {
    private Button studentlogin;
    private Button adminlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentlogin = (Button)findViewById(R.id.studentloginbtn);
        adminlogin = (Button)findViewById(R.id.adminloginbtn);

            //Changes the activity from MainActvity to Login Activity

            studentlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(MainActivity.this,Login_student.class);
                    startActivity(i);
                }
            });
            adminlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, admin_login.class));
                }
            });





    }

}
