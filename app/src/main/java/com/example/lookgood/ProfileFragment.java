package com.example.lookgood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    String uid;
    EditText name,email,phoneNumber;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    View view;
    User userModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        name = view.findViewById(R.id.profile_name_txt);
        email = view.findViewById(R.id.profile_email_txt);
        phoneNumber = view.findViewById(R.id.profile_phone_txt);




        if(fAuth.getCurrentUser()!=null) {
            uid = fAuth.getUid();
            final FirebaseDatabase database =FirebaseDatabase.getInstance();
            reference=database.getReference("users").child(uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userModel=  dataSnapshot.getValue(User.class);
                    System.out.println(userModel.getName());
                    name.setText(userModel.name);
                    email.setText(userModel.email);
                    phoneNumber.setText(userModel.phone);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        Button button = view.findViewById(R.id.btnProfileUpdate);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String nameTxt = name.getText().toString();
                String emailTxt = email.getText().toString();
                String phoneTxt = phoneNumber.getText().toString();

                User helperClass = new User(emailTxt,nameTxt, phoneTxt);
                reference.setValue(helperClass);
                Toast.makeText(view.getContext().getApplicationContext(), "Enter something !", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
