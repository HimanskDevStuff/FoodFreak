package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.foodfreak.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student_signup extends AppCompatActivity
{
    TextInputLayout fullname,mobile_num,email_id,password_signup,confirm_password_signnup,amizone_id;
    ImageView back_btn;
    Button SignUp_btn_db,already_hv_ac_btn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //String fullname_str, mobile_num_str,email_id_str,password_signup_str,amizone_id_str;
    // Firebase
    FirebaseDatabase parentNode;
    DatabaseReference referenceUser;
    ProgressBar bar;

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

    public Boolean validateAmizoneID()
    {
        String val;
        val = amizone_id.getEditText().getText().toString();
        if(val.isEmpty())
        {
            amizone_id.setError("Field can't be empty");
            return false;
        }
        else if(val.length()>7||val.length()<7)
        {
            amizone_id.setError("Invalid amizone ID");
            return false;
        }
        else
        {
            amizone_id.setError(null);
            amizone_id.setErrorEnabled(false);
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
    public Boolean validatePassword()
    {
        String val,val_1;
        val = password_signup.getEditText().getText().toString();
        val_1=confirm_password_signnup.getEditText().getText().toString();
        if(val.isEmpty())
        {
            password_signup.setError("Field can't be empty");
            return false;
        }
        else if(val_1.isEmpty())
        {
            confirm_password_signnup.setError("Field can't be empty");
            return false;
        }
        else if(val.length()<8)
        {
            password_signup.setError("Password is too short");
            return false;
        }
        else if(val_1.length()<8)
        {
            confirm_password_signnup.setError("Password is too short");
            return false;
        }
        else if(!val.equals(val_1))
        {
            password_signup.setError("Password should match");
            confirm_password_signnup.setError("Password should match");
            return false;
        }
        else
        {
            password_signup.setError(null);
            confirm_password_signnup.setError(null);
            password_signup.setErrorEnabled(false);
            confirm_password_signnup.setErrorEnabled(false);
            return true;
        }

    }
    public Boolean registerUser(View view)
    {
        if(validateAmizoneID() & validateEmailid() & validateFullname() & validateMobile() & validatePassword())
        {
              return true;
        }
        else
            return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        fullname=findViewById(R.id.fullname_et);
        back_btn=findViewById(R.id.back_btn_create_ac);
        mobile_num=findViewById(R.id.mobile_num_et);
        email_id=findViewById(R.id.email_id_et);
        password_signup=findViewById(R.id.password_et);
        confirm_password_signnup=findViewById(R.id.confirm_password_et);
        amizone_id=findViewById(R.id.amizone_id_et);
        SignUp_btn_db=findViewById(R.id.create_account_student_db_btn);
        already_hv_ac_btn=findViewById(R.id.already_have_ac_btn);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_signup.this,Login_student.class));
                finish();
            }
        });
        already_hv_ac_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_signup.this,Login_student.class));
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
                   //parentNode = FirebaseDatabase.getInstance();
                    //referenceUser = parentNode.getReference("userdetails");
                    String fullname_str=fullname.getEditText().getText().toString();
                    String mobile_num_str = mobile_num.getEditText().getText().toString();
                    String email_id_str=email_id.getEditText().getText().toString().trim();
                    String password_signup_str=password_signup.getEditText().getText().toString();
                    String amizone_id_str=amizone_id.getEditText().getText().toString();
                    if(isUserexist(mobile_num_str))
                    {
                        mobile_num.setError("User already exist with this number");
                    }
                    else {
                        Intent i = new Intent(student_signup.this, Otp_verification.class);
                        i.putExtra("mobile_num_str", mobile_num_str);
                        i.putExtra("fullname_str", fullname_str);
                        i.putExtra("email_id_str", email_id_str);
                        i.putExtra("password_signup_str", password_signup_str);
                        i.putExtra("amizone_id_str", amizone_id_str);

                        startActivity(i);
                        finish();
                    }
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
    int flag = 0;
    private boolean isUserexist(String mobile_num_str)
    {

        referenceUser = FirebaseDatabase.getInstance().getReference("userdetails");
        referenceUser.orderByChild("mobile_num_hc").equalTo(mobile_num_str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                    flag = 1;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (flag == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
