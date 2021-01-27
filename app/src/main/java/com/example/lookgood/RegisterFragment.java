package com.example.lookgood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends AppCompatActivity {
    TextInputLayout regName, regEmail, regPassword, regPhone;
    Button regBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_register);

        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regPhone = findViewById(R.id.reg_phone);
        regBtn = findViewById(R.id.reg_btn);

        fAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String name = regName.getEditText().getText().toString();
                String phone = regPhone.getEditText().getText().toString();

                if (TextUtils.isEmpty(email)) {
                    regEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    regPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    regPassword.setError("Password Must be more than 6 Characters");
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");



                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            User helperClass = new User(user.getUid(), email, name, phone);

                            reference.child(user.getUid()).setValue(helperClass);
                            Intent inten = new Intent(RegisterFragment.this, LoginFragment.class);
                            startActivity(inten);

                        } else {
                            Toast.makeText(getApplicationContext(), "User not created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }


}
