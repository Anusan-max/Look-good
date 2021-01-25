package com.example.lookgood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends AppCompatActivity {

    EditText email,password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);

        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.loginBtn);

        email = findViewById(R.id.log_email);
        password = findViewById(R.id.log_password);

        fAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent inten = new Intent(LoginFragment.this,RegisterFragment.class);
            startActivity(inten);
            }
        });



        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString();
                final String passwordText = password.getText().toString();

                if(TextUtils.isEmpty(emailText)) {
                    email.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(passwordText)){
                    password.setError("Password is Required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //  Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();

                            Intent inten = new Intent(LoginFragment.this, NavDrawerActivity.class);
                            startActivity(inten);

                        }else {
                            Toast.makeText(getApplicationContext(), "Invalid User Name or Password !", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }

}
