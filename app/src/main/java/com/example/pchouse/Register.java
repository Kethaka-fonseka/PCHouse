package com.example.pchouse;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText name,email,phone,password,conpassword;
    FirebaseAuth fAuth;
    Button regBtn;
    User user;
    ProgressBar progressBar;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.RegName);
        email=findViewById(R.id.RegMail);
        phone=findViewById(R.id.RegPass);
        password=findViewById(R.id.RegPass);
        conpassword=findViewById(R.id.Reg_conpass);
        progressBar=findViewById(R.id.progressBar);

        user=new User();

        regBtn=findViewById(R.id.btnReg);
        fAuth=FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name=name.getText().toString().trim();
                final String Email=email.getText().toString().trim();
                final String Phone=phone.getText().toString().trim();
                final String  Password=password.getText().toString().trim();
                String ConPassword=conpassword.getText().toString().trim();

                if(TextUtils.isEmpty(Name)){
                    name.setError("Please enter name!!");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    email.setError("Please enter email!!");
                    return;
                }

                if(TextUtils.isEmpty(Phone)){
                    phone.setError("Please enter phone!!");
                    return;
                }
                if(TextUtils.isEmpty(Password)) {
                    password.setError("Please enter password!!");
                    return;
                }
                if(TextUtils.isEmpty(ConPassword)){
                    conpassword.setError("Please confirm your password!!");
                    return;
                }
                if(!Password.equals(ConPassword)){
                    password.setError("Password not matching!!");
                    return;

                }
                if(Password.length()<6){
                    password.setError("Password must have length at least 6");
                    return;
                }


                progressBar.setVisibility(view.VISIBLE);
                dbRef= FirebaseDatabase.getInstance().getReference().child("User");
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user.setId(fAuth.getCurrentUser().getUid());
                            user.setName(Name);
                            user.setEmail(Email);
                            user.setPhone(Integer.parseInt(Phone));
                            user.setPassword(Password);
                            dbRef.child(String.valueOf(user.getId())).setValue(user);


                            Toast.makeText(getApplicationContext(),"registration successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"registration unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}