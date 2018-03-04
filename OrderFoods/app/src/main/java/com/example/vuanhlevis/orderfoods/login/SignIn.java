package com.example.vuanhlevis.orderfoods.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vuanhlevis.orderfoods.Home;
import com.example.vuanhlevis.orderfoods.R;
import com.example.vuanhlevis.orderfoods.common.Common;
import com.example.vuanhlevis.orderfoods.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignIn";
    EditText et_phone;
    EditText et_pass;
    Button bt_connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setupUI();

        //Init database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference data_user = database.getReference("User");

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Waiting ...");
                progressDialog.show();

                Log.d(TAG, "onClick: datauser" + data_user.toString());

                data_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.d(TAG, "onDataChange: +datasho" + dataSnapshot.toString());
                        //check user exist
                        if (dataSnapshot.child(et_phone.getText().toString()).exists()) {

                            progressDialog.dismiss();
                            // get user infor

                            User user = dataSnapshot.child(et_phone.getText().toString()).getValue(User.class);
                            user.setPhone(et_phone.getText().toString());

                            if (et_pass.getText().length() > 0 && et_phone.getText().length() > 0) {
                                if (user.getPassword().equals(et_pass.getText().toString())) {
                                    Intent intenthome = new Intent(SignIn.this, Home.class);
                                    Common.currUser = user;
                                    startActivity(intenthome);
                                    finish();

                                } else {
                                    Toast.makeText(SignIn.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignIn.this, "Please Enter Your Phone And Password", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressDialog.dismiss();
                            Log.d(TAG, "onDataChange1: " + et_phone.getText().toString());
                            Log.d(TAG, "onDataChange2: " + dataSnapshot.child(et_phone.getText().toString()).exists());


                            Toast.makeText(SignIn.this, "User not exist", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    private void setupUI() {
        et_pass = findViewById(R.id.et_pass);
        et_phone = findViewById(R.id.et_phone);
        bt_connect = findViewById(R.id.bt_connect);
    }
}
