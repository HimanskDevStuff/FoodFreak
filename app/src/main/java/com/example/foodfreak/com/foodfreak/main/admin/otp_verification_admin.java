package com.example.foodfreak.com.foodfreak.main.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfreak.com.foodfreak.main.user.HelperClass;
import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.user.Succesfully_sign_up;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class otp_verification_admin  extends AppCompatActivity {
    Button verify_btn;
    TextView phone_num_tv;
    TextInputLayout otp;
    ProgressBar progress_bar_otp;
    String verification_code_by_sytem;
    FirebaseDatabase parentNode;
    DatabaseReference referenceUser;
    String otp_entered_by_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification_admin);
        verify_btn=findViewById(R.id.verify_btn_otp);
        progress_bar_otp=findViewById(R.id.progressbar_verify);
        phone_num_tv=findViewById(R.id.mobile_num_otp_tv);
        otp=findViewById(R.id.et_otp);

        final String phone_num_of_user_str;
        phone_num_of_user_str = getIntent().getStringExtra("mobile_num_str");
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_entered_by_user=otp.getEditText().getText().toString();
                if(otp_entered_by_user.isEmpty() || otp_entered_by_user.length()<6)
                {
                    otp.setErrorEnabled(true);
                    otp.setError("Invalid OTP");
                }
                else
                {
                    otp.setErrorEnabled(false);
                    otp.setError(null);
                    progress_bar_otp.setVisibility(View.VISIBLE);
                    verifyCode(otp_entered_by_user);
                }

            }
        });

        phone_num_tv.setText(phone_num_of_user_str);
        sendVerificationCodeToUser(phone_num_of_user_str);

    }


    private void sendVerificationCodeToUser(String phone_num_of_user_str) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phone_num_of_user_str,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verification_code_by_sytem=s;
        }

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                progress_bar_otp.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(otp_verification_admin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private void verifyCode(String verification_codebyuser)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verification_code_by_sytem,verification_codebyuser);
        signInTheUserByCredential(credential);
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential)
    {
        final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(otp_verification_admin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progress_bar_otp.setVisibility(View.VISIBLE);
                    parentNode = FirebaseDatabase.getInstance();
                    referenceUser = parentNode.getReference("admindetails");
                    String phone_num_of_user= getIntent().getStringExtra("mobile_num_str");
                    final String full_name_of_user=getIntent().getStringExtra("fullname_str");
                    final String amizone_id_of_user=getIntent().getStringExtra("amizone_id_str");
                    final String email_id_of_user=getIntent().getStringExtra("email_id_str");
                    final String canteen_name_str=getIntent().getStringExtra("canteen_name_str");
                    HelperClass_admin data= new HelperClass_admin(full_name_of_user,phone_num_of_user,amizone_id_of_user,canteen_name_str,email_id_of_user);
                    referenceUser.child(firebaseAuth.getUid()).setValue(data, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                progress_bar_otp.setVisibility(View.GONE);
                                Toast.makeText(otp_verification_admin.this, "Registraion Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(otp_verification_admin.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(otp_verification_admin.this, Admin_home_page.class);
                                startActivity(i);
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                    finishAffinity();
                                else
                                    finish();
                            }
                        }
                    });

                }
                else {

                    Toast.makeText(otp_verification_admin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
