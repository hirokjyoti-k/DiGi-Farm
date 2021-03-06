package com.infosyialab.digifarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    Button register;
    EditText name,email,deviceId,password;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String userName, userEmail, userDeviceId, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        deviceId = (EditText) findViewById(R.id.deviceId);
        password = (EditText) findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

    }

    private void  userRegister(){

        userName = name.getText().toString().trim();
        userEmail = email.getText().toString().trim();
        userDeviceId = deviceId.getText().toString().trim();
        userPassword = password.getText().toString().trim();

        if(userName.isEmpty()){
            name.setError("Name can't be empty");
            return;
        }

        if(userEmail.isEmpty()){
            email.setError("Email can't be empty");
            return;
        }


        if(userDeviceId.isEmpty()){
            deviceId.setError("Device ID can't be empty");
            return;
        }

        if(userPassword.isEmpty()){
            password.setError("Password can't be empty");
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Please wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userEmail , userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            uploadUserInfo();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(register.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void uploadUserInfo() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference name = database.getReference("UserDetails/"+userDeviceId+"/Name");
        DatabaseReference email = database.getReference("UserDetails/"+userDeviceId+"/Email");
        DatabaseReference deviceID = database.getReference("UserDetails/"+userDeviceId+"/DeviceID");

        name.setValue(userName);
        email.setValue(userEmail);
        deviceID.setValue(userDeviceId);

        Intent intent = new Intent(register.this ,MainActivity.class );
        startActivity(intent);
        finish();

        progressDialog.dismiss();
    }

    public void gotoLogin(View view) {
        Intent i = new Intent(register.this, login.class);
        startActivity(i);
        finish();
    }
}
