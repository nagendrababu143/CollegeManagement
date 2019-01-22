package com.example.nagendra.collegemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nagendra.collegemanagement.models.AdminInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AdminActivity extends AppCompatActivity {

    MaterialEditText editText_emailid_login,editText_password_login,editText_admincode_login;
    Button login;
    TextView forgotpassword,register;
    FirebaseAuth auth;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bar_admin_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();

        editText_emailid_login = (MaterialEditText)findViewById(R.id.loginemail_admin);
        editText_password_login = (MaterialEditText)findViewById(R.id.loginpassword_admin);
        editText_admincode_login = (MaterialEditText)findViewById(R.id.verify_admin_code);
        forgotpassword = (TextView)findViewById(R.id.forgotpassword_admin);
        register = (TextView)findViewById(R.id.register_admin);

        login = (Button)findViewById(R.id.btn_login_admin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,AdminRegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = editText_emailid_login.getText().toString().trim();
                String password = editText_password_login.getText().toString().trim();
                final String verifycode = editText_admincode_login.getText().toString().trim();

                if(TextUtils.isEmpty(emailid) || TextUtils.isEmpty(password) || TextUtils.isEmpty(verifycode))
                {
                    Toast.makeText(AdminActivity.this, "All fields are Required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final ProgressDialog progressDialog = new ProgressDialog(AdminActivity.this);
                    progressDialog.setMessage("Logging wait..");
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String userid = auth.getCurrentUser().getUid();
                                if(verifycode.equals("111"))
                                {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                int count=0;
                                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                            {
                                                AdminInformation adminInformation = dataSnapshot1.getValue(AdminInformation.class);

                                                String userids = adminInformation.getAdminuid();

                                                if(user.equals(userids))
                                                {
                                                    count = count+1;
                                                }


                                            }
                                            if(count>0)
                                            {
                                                Intent intent = new Intent(AdminActivity.this,AdminMainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(AdminActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(AdminActivity.this, "Login cancelled try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(AdminActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
