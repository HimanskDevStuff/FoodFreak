package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfreak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Succesfully_sign_up extends AppCompatActivity {

    DatabaseReference reff;
    TextView name;
    FirebaseUser curr_user;
    String curr_user_str;
    public static HelperClass data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesfully_sign_up);
        name = findViewById(R.id.name);
         curr_user=FirebaseAuth.getInstance().getCurrentUser();
        curr_user_str = curr_user.getUid();
        if(curr_user!=null) {
             curr_user_str = curr_user.getUid();
            reff = FirebaseDatabase.getInstance().getReference("userdetails");
            reff.child(curr_user_str).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   data = dataSnapshot.getValue(HelperClass.class);
                    if (data != null) {
                        name.setText(data.getFullname_hc());
                    } else {
                        Toast.makeText(Succesfully_sign_up.this, "User does't exist", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
         else
            {
                Toast.makeText(this, "Please login to your account!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Succesfully_sign_up.this,Login_student.class));
            }
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent i=new Intent(Succesfully_sign_up.this,Home_page.class);

                 startActivity(i);
                 if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                     finishAffinity();
             }
         },5000);

    }
}
