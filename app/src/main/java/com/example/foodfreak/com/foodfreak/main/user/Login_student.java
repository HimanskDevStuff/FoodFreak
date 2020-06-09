package com.example.foodfreak.com.foodfreak.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfreak.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Login_student extends AppCompatActivity {
    TextView signup_btn, first, second, before_signup, resend_otp;
    TextInputLayout mobilenum_login, password_login;
    CheckBox remember_me;
    Button login_btn,go_btn;
    FirebaseDatabase parentNode;
    String verification_code_by_sytem;
    ProgressBar progress_bar_login;
    String otp_entered_by_user;
    FirebaseUser curr_user;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    DatabaseReference referenceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        signup_btn = findViewById(R.id.signupclickable_tv);
        first = findViewById(R.id.head1);
        second = findViewById(R.id.textView2);
        mobilenum_login = findViewById(R.id.usernameInputlayout);
        password_login = findViewById(R.id.passwordInputlayout);
        remember_me = findViewById(R.id.remember_me_cb);
        resend_otp = findViewById(R.id.resend_otp_login);
        login_btn = findViewById(R.id.login_btn);
        progress_bar_login=findViewById(R.id.progress_bar_login);
        go_btn=findViewById(R.id.go_btn);
        before_signup = findViewById(R.id.signupnonclickable_tv);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_student.this, student_signup.class);
                Pair pairs[] = new Pair[8];
                pairs[0] = new Pair<View, String>(first, "first");
                pairs[1] = new Pair<View, String>(second, "second");
                pairs[2] = new Pair<View, String>(mobilenum_login, "third");
                pairs[3] = new Pair<View, String>(password_login, "fourth");
                pairs[4] = new Pair<View, String>(remember_me, "fifth");
                pairs[5] = new Pair<View, String>(resend_otp, "sixth");
                pairs[6] = new Pair<View, String>(login_btn, "seventh");
                pairs[7] = new Pair<View, String>(before_signup, "eight");
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login_student.this, pairs);
                    startActivity(i, options.toBundle());
                }
            }
        });
        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_login.setVisibility(View.VISIBLE);
                isUserexist(mobilenum_login.getEditText().getText().toString());
            }
        });
        //if we want to manually enter the otp
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_entered_by_user=password_login.getEditText().getText().toString();
                if(otp_entered_by_user.isEmpty() || otp_entered_by_user.length()<6)
                {
                    password_login.setErrorEnabled(true);
                    password_login.setError("Invalid OTP");
                }
                else
                {
                    password_login.setErrorEnabled(false);
                    password_login.setError(null);
                    progress_bar_login.setVisibility(View.VISIBLE);
                    verifyCode(otp_entered_by_user);
                }

            }
        });


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

                password_login.getEditText().setText(code);
                progress_bar_login.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Login_student.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private void verifyCode(String verification_codebyuser)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verification_code_by_sytem,verification_codebyuser);
        signInTheUserByCredential(credential);
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential)
    {
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(Login_student.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                        Toast.makeText(Login_student.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login_student.this, Home_page.class));
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            finishAffinity();
                }
                else
                {
                    Toast.makeText(Login_student.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void isUserexist(final String mobile_num_str)
    {

        referenceUser = FirebaseDatabase.getInstance().getReference("userdetails");
        referenceUser.orderByChild("mobile_num_hc").equalTo(mobile_num_str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    password_login.setVisibility(View.VISIBLE);
                    resend_otp.setVisibility(View.VISIBLE);
                    remember_me.setVisibility(View.VISIBLE);
                    mobilenum_login.setError(null);
                    mobilenum_login.setErrorEnabled(false);
                    progress_bar_login.setVisibility(View.GONE);
                    go_btn.setVisibility(View.GONE);
                    login_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(Login_student.this,"We have sent 6 digit OTP via SMS",Toast.LENGTH_SHORT).show();
                    sendVerificationCodeToUser(mobilenum_login.getEditText().getText().toString());
                }
                else
                    showError();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void showError()
    {
        mobilenum_login.setError("User doesn't exist");

    }
}
