package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.admin.Admin_home_page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen_foodfreak extends AppCompatActivity {
    public static int SPLASH_SCREEN_TIME=2000;
    Animation topAnim,bottomAnim;
    AppCompatImageView image_logo_amity;
    ImageView image_logo_foodfreak;
    LottieAnimationView loading_anim;
    private DatabaseReference reffofStudent;
    private  DatabaseReference reffofAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_foodfreak);
        //Animation hooks
        topAnim= AnimationUtils.loadAnimation(this,R.anim.splash_top_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.splash_bottom_anim);
       //View hooks
        image_logo_foodfreak=findViewById(R.id.splash_foodfreak_logo);
        image_logo_amity=findViewById(R.id.splash_amity_logo);
        //setting animation
        image_logo_foodfreak.setAnimation(topAnim);
        image_logo_amity.setAnimation(bottomAnim);

        loading_anim=findViewById(R.id.loading_anim_splashscreen_lottie);
        //Delay set for animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    loading_anim.setVisibility(View.VISIBLE);
                    final String Uid=user.getUid();
                    //checking the student database
                    reffofStudent= FirebaseDatabase.getInstance().getReference("userdetails");
                    reffofStudent.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int flag=0;
                            loading_anim.setVisibility(View.VISIBLE);
                            for(DataSnapshot ds : dataSnapshot.getChildren())
                            {


                                if(Uid.equals(ds.getKey()))
                                {
                                    flag=1;
                                    startActivity(new Intent(SplashScreen_foodfreak.this,Home_page.class));
                                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                        finishAffinity();
                                }
                            }
                            if(flag==0)
                            {
                                //if loop ends and does not matches the Uid -> Check in Admin database
                                reffofAdmin= FirebaseDatabase.getInstance().getReference("admindetails");
                                reffofAdmin.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds : dataSnapshot.getChildren())
                                        {
                                            if(Uid.equals(ds.getKey()))
                                            {
                                                startActivity(new Intent(SplashScreen_foodfreak.this, Admin_home_page.class));
                                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                                    finishAffinity();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
                else {
                    loading_anim.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=new Intent(SplashScreen_foodfreak.this,MainActivity.class);
                            Pair pairs[] = new Pair[2];
                            pairs[0] = new Pair<View, String>(image_logo_foodfreak, "first");
                            pairs[1] = new Pair<View, String>(image_logo_amity, "second");
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            {
                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen_foodfreak.this, pairs);
                                startActivity(i, options.toBundle());
                                finish();

                            }

                        }
                    },4000);


                }
            }
        },SPLASH_SCREEN_TIME);
    }
}
