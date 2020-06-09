package com.example.foodfreak.com.foodfreak.main.admin;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.foodfreak.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_signup extends AppCompatActivity
{
    TextInputLayout fullname,mobile_num,email_id,working_id;
    RadioGroup canteen_name;
    ImageView back_btn;
    Button SignUp_btn_db,already_hv_ac_btn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase parentNode;
    DatabaseReference referenceUser;
    ProgressBar bar;
    DatabaseReference canteenrefData;

    public Boolean validateFullname()
    {
        String val;
        val = fullname.getEditText().getText().toString();
        if(val.isEmpty())
        {
            fullname.setError("Field can't be empty");
            return false;
        }
        else
        {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }

    }
    public Boolean validateMobile()
    {
        String val;
        val = mobile_num.getEditText().getText().toString();
        if(val.isEmpty())
        {
            mobile_num.setError("Field can't be empty");
            return false;
        }
        else if(val.length()<10 || val.length()>10)
        {
            mobile_num.setError("Invalid mobile number");
            return false;
        }
        else
        {
            mobile_num.setError(null);
            mobile_num.setErrorEnabled(false);
            return true;
        }

    }

    public Boolean validateWorkingID()
    {
        String val;
        val = working_id.getEditText().getText().toString();
        if(val.isEmpty())
        {
            working_id.setError("Field can't be empty");
            return false;
        }
        else if(val.length()>7||val.length()<7)
        {
            working_id.setError("Invalid amizone ID");
            return false;
        }
        else
        {
            working_id.setError(null);
            working_id.setErrorEnabled(false);
            return true;
        }

    }
    public Boolean validateEmailid()
    {
        String val;
        val = fullname.getEditText().getText().toString();
        if(val.isEmpty())
        {
            email_id.setError("Field can't be empty");
            return false;
        }
        else
        {
            email_id.setError(null);
            email_id.setErrorEnabled(false);
            return true;
        }

    }
    public Boolean validateCanteenname()
    {
        String canteen_name_str;
        int itemid=canteen_name.getCheckedRadioButtonId();
        if(itemid==-1)
        {
            Toast.makeText(admin_signup.this,"Please select canteen name",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }

    }
    public Boolean registerUser(View view)
    {
        if(validateWorkingID() & validateEmailid() & validateFullname() & validateMobile() & validateCanteenname())
        {
            return true;
        }
        else
            return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);
        fullname=findViewById(R.id.fullname_et);
        back_btn=findViewById(R.id.back_btn_create_ac);
        mobile_num=findViewById(R.id.mobile_num_et);
        email_id=findViewById(R.id.email_id_et);
        working_id=findViewById(R.id.amizone_id_et);
        canteen_name=findViewById(R.id.canteen_nsme_admin_signup_rg);
        SignUp_btn_db=findViewById(R.id.create_account_student_db_btn);
        already_hv_ac_btn=findViewById(R.id.already_have_ac_btn);


        //Retrieving canteen list from data base and displaying with radio buttons
        canteenrefData=FirebaseDatabase.getInstance().getReference("canteen_details");
        canteenrefData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String data = ds.getKey();
                    RadioButton rb=new RadioButton(getApplicationContext());
                    rb.setText(data);
                    rb.setTextColor(Color.BLACK);
                    canteen_name.addView(rb);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_signup.this,admin_login.class));
                finish();
            }
        });
        already_hv_ac_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_signup.this,admin_login.class));
                finish();
            }
        });
        SignUp_btn_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar=findViewById(R.id.progressbar_ac);

                if(registerUser(v))
                {
                    bar.setVisibility(View.VISIBLE);
                    String mobile_num_str = mobile_num.getEditText().getText().toString();
                    isUserexist(mobile_num_str);

                  /*  HelperClass helperclass=new HelperClass(fullname_str,amizone_id_str,mobile_num_str,email_id_str,password_signup_str);
                    referenceUser.child(mobile_num_str).setValue(helperclass, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError!=null)
                            {
                                bar.setVisibility(View.GONE);
                                Toast.makeText(student_signup.this,"Registraion Failed",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(student_signup.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(student_signup.this, Succesfully_sign_up.class);
                               i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);
                                finish();
                            }
                        }
                    });

                     */
                }

            }
        });

    }
    private void isUserexist(String mobile_num_str)
    {

        referenceUser = FirebaseDatabase.getInstance().getReference("admindetails");
        referenceUser.orderByChild("manager_contact_num").equalTo(mobile_num_str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                {
                    mobile_num.setError("User already exist with this number");
                }
                else
                {
                    String fullname_str=fullname.getEditText().getText().toString();
                    String mobile_num_str = mobile_num.getEditText().getText().toString();
                    String email_id_str=email_id.getEditText().getText().toString().trim();
                    String amizone_id_str=working_id.getEditText().getText().toString();
                    String canteen_name_str;
                    int itemid=canteen_name.getCheckedRadioButtonId();
                    RadioButton rd=findViewById(itemid);
                    canteen_name_str=rd.getText().toString();
                    Intent i = new Intent(admin_signup.this, otp_verification_admin.class);
                    i.putExtra("mobile_num_str", mobile_num_str);
                    i.putExtra("fullname_str", fullname_str);
                    i.putExtra("email_id_str", email_id_str);
                    i.putExtra("amizone_id_str", amizone_id_str);
                    i.putExtra("canteen_name_str", canteen_name_str);
                    startActivity(i);
                    finish();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
