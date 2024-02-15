package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button register ;
    TextView backlogin;
    FirebaseAuth mauth;
    EditText firstname , lastname , contactnumber, password, confirmpassword, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mauth = FirebaseAuth.getInstance();
        firstname = findViewById(R.id.reg_first_name_id);
        lastname = findViewById(R.id.reg_last_name_id);
        contactnumber = findViewById(R.id.reg_Contact_id);
        password = findViewById(R.id.reg_password_id);
        confirmpassword = findViewById(R.id.reg_confirm_password_id);
        email = findViewById(R.id.reg_email_id);
        register = findViewById(R.id.register_btn_id);
        backlogin = findViewById(R.id.register_to_login_id);
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mauth.getCurrentUser() != null) {


                    String fname = firstname.getText().toString();
                    String lname = lastname.getText().toString();
                    String emails = email.getText().toString();
                    String pass = password.getText().toString();
                    String confirmpass = confirmpassword.getText().toString();
                    String contact = contactnumber.getText().toString();
                    if (fname.isEmpty() || lname.isEmpty() ||
                            emails.isEmpty() || pass.isEmpty() ||
                            confirmpass.isEmpty() || contact.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "fill up this all requirements", Toast.LENGTH_SHORT).show();

                        if (fname.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter First NAME", Toast.LENGTH_SHORT).show();
                        } else if (lname.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter Last NAME", Toast.LENGTH_SHORT).show();
                        } else if (emails.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                        } else if (pass.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter PASSWORD", Toast.LENGTH_SHORT).show();
                        } else if (confirmpass.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter CONFIRM_PASSWORD", Toast.LENGTH_SHORT).show();
                        } else if (contact.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter CONTACT NUMBER", Toast.LENGTH_SHORT).show();
                        } else if (fname.isEmpty()) {
                            Toast.makeText(RegisterActivity.this, "Enter First NAME", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (pass.equals(confirmpass)) {
                        Toast.makeText(RegisterActivity.this, "password didn't match", Toast.LENGTH_SHORT).show();
                    }
                    mauth.createUserWithEmailAndPassword(emails,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                User user = new User(fname,lname,emails,contact);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                                    Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
    }
}