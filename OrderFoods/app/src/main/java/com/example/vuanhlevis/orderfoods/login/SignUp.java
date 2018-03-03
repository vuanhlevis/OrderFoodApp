package com.example.vuanhlevis.orderfoods.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vuanhlevis.orderfoods.R;
import com.example.vuanhlevis.orderfoods.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText et_name;
    EditText et_phone;
    EditText et_pass;

    Button bt_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUI();

        //Init database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference data_user = database.getReference("User");
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Waiting ...");
                progressDialog.show();

                data_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // check if already user phone

                        if (et_name.getText().length() > 0 && et_pass.getText().length() > 0
                                && et_phone.getText().length() > 0) {
                            if (dataSnapshot.child(et_phone.getText().toString()).exists()) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "This PhoneNumber Already Register!", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                User user = new User(et_name.getText().toString(),et_pass.getText().toString());
                                data_user.child(et_phone.getText().toString()).setValue(user);

                                Toast.makeText(SignUp.this, "Successfully!!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Please Enter Full Information!!", Toast.LENGTH_SHORT).show();
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
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        et_phone = findViewById(R.id.et_phone);

        bt_signup = findViewById(R.id.bt_signup);
    }
}
