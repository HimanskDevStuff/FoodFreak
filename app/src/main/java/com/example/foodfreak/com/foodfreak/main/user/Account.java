package com.example.foodfreak.com.foodfreak.main.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.admin.HelperClass_admin;
import com.example.foodfreak.com.foodfreak.main.admin.admin_login;
import com.example.foodfreak.com.foodfreak.main.user.HelperClass;
import com.example.foodfreak.com.foodfreak.main.user.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends Fragment {


    public static HelperClass data;
    String user_str;
    ConstraintLayout constraintLayout;
    TextView username,email,mobile_num,workingid;
    Button logout;
    DatabaseReference reff;
    FirebaseUser user;
    ProgressBar progressBar;


    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_account, container, false);
        username=v.findViewById(R.id.admin_username_tv);
        email=v.findViewById(R.id.admin_email_id_tv);
        mobile_num=v.findViewById(R.id.admin_phone_num_tv);
        workingid=v.findViewById(R.id.admin_working_id_tv);
        constraintLayout=v.findViewById(R.id.Account_admin_frag);
        progressBar=v.findViewById(R.id.progress_account_admin);
        logout=v.findViewById(R.id.logout_admin);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                FirebaseAuth.getInstance().signOut();
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    if(getActivity()!=null)
                        getActivity().finishAffinity();
                }
            }
        });
        reff= FirebaseDatabase.getInstance().getReference("userdetails");
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            user_str=user.getUid();
            reff.child(user_str).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    data= dataSnapshot.getValue(HelperClass.class);
                    username.setText(data.getFullname_hc());
                    mobile_num.setText(data.getMobile_num_hc());
                    email.setText(data.getEmail_id_hc());
                    workingid.setText(data.getAmizone_id_hc());
                    progressBar.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(getActivity(),"Pleaase log in again !",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), admin_login.class));
        }


        return v;
    }
    public static HelperClass getAdminData()
    {
        return data;
    }

}

