package com.example.foodfreak.com.foodfreak.main.user;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Canteen extends Fragment {
    private HelperClass data;
    private TextView greeting_msg,greeting_time_msg;
    private String GOOD_MORNING="Good Morning",GOOD_AFTERNOON="Good Afternoon",GOOD_EVE="Good Evening",GOOD_NIGHT="Good Night";
    FirebaseUser cur_user;
    DatabaseReference reff;
    String curr_user_str;
    CardView canteen_mega_bite;


    public Canteen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_canteen, container, false);
        greeting_msg= v.findViewById(R.id.good_morning_tv);
        greeting_time_msg=v.findViewById(R.id.time_for_tv);
        cur_user= FirebaseAuth.getInstance().getCurrentUser();
        canteen_mega_bite=v.findViewById(R.id.canteen_cardview);
        if(cur_user!=null)
        {
            curr_user_str=cur_user.getUid();
        }
        else
        {
            Toast.makeText(getActivity(),"Error occurred ! Please login again.",Toast.LENGTH_SHORT).show();
            return v;
        }


        /*Accessing data of user*/
        reff= FirebaseDatabase.getInstance().getReference("userdetails");
        reff.child(curr_user_str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*getting the refferenec of the current user data*/
                HelperClass data= dataSnapshot.getValue(HelperClass.class);
                if(data!=null) {
                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int hour = cal.get(Calendar.HOUR_OF_DAY);
                    /*Retrieving data from firebase and displaying user details*/
                    if (hour >= 12 && hour < 17) {
                        String greet_msg = GOOD_AFTERNOON + "\n" + data.getFullname_hc();
                        greeting_msg.setText(greet_msg);
                        greeting_time_msg.setText(("It's time for Lunch"));
                    } else if (hour >= 17 && hour < 21) {
                        String greet_msg = GOOD_EVE + "\n" + data.getFullname_hc();
                        greeting_msg.setText(greet_msg);
                        greeting_time_msg.setText(("It's time for Dinner"));

                    } else if (hour >= 21 && hour < 24) {
                        String greet_msg = GOOD_NIGHT + "\n" + data.getFullname_hc();
                        greeting_msg.setText(greet_msg);
                        greeting_time_msg.setText(("Sleep Well"));

                    } else {
                        String greet_msg = GOOD_MORNING + "\n" + data.getFullname_hc();
                        greeting_msg.setText(greet_msg);
                        greeting_time_msg.setText(("It's time for Breakfast"));
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Error occurred ! Please login again",Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        canteen_mega_bite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Canteeen_menu.class);
                i.putExtra("canteen_name","E1_canteen");
                startActivity(i);
            }
        });






        return v;
    }

}
