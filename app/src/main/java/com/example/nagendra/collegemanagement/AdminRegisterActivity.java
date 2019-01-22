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
import android.widget.Toast;

import com.example.nagendra.collegemanagement.models.AdminInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AdminRegisterActivity extends AppCompatActivity {

    MaterialEditText editText_username,editText_emailid,editText_password,editText_phonenumber,editText_collegeid,editText_department;
    Button register_admin;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    String admin_username,admin_emailid,admin_password,admin_phonenumber,admin_collegeid,admin_department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.bar_adminregister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin - Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();


        editText_username = (MaterialEditText)findViewById(R.id.adminusername_register);
        editText_emailid = (MaterialEditText)findViewById(R.id.adminemailid_register);
        editText_password = (MaterialEditText)findViewById(R.id.adminpassword_register);
        editText_phonenumber = (MaterialEditText)findViewById(R.id.adminphonenumber_register);
        editText_collegeid = (MaterialEditText)findViewById(R.id.admincollegeid_register);
        editText_department = (MaterialEditText)findViewById(R.id.admindepartment_register);

        register_admin = (Button)findViewById(R.id.btn_register_admin);

        register_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                admin_username = editText_username.getText().toString().trim();
                admin_emailid = editText_emailid.getText().toString().trim();
                admin_password = editText_password.getText().toString().trim();
                admin_phonenumber = editText_phonenumber.getText().toString().trim();
                admin_collegeid = editText_collegeid.getText().toString().trim();
                admin_department = editText_department.getText().toString().trim();

                if(TextUtils.isEmpty(admin_username) || TextUtils.isEmpty(admin_emailid) || TextUtils.isEmpty(admin_password) || TextUtils.isEmpty(admin_phonenumber) || TextUtils.isEmpty(admin_collegeid) || TextUtils.isEmpty(admin_department))
                {
                    Toast.makeText(AdminRegisterActivity.this, "All fields are Required", Toast.LENGTH_SHORT).show();
                }
                else if(admin_password.length() < 6 )
                {
                    Toast.makeText(AdminRegisterActivity.this, "Password Length should be at least 7 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog = new ProgressDialog(AdminRegisterActivity.this);
                    progressDialog.setMessage("Registering wait..");
                    progressDialog.show();

                    register(admin_username,admin_emailid,admin_password,admin_phonenumber,admin_collegeid,admin_department);
                }
            }
        });
    }

    private void register(final String admin_username, final String admin_emailid, final String admin_password, final String admin_phonenumber, final String admin_collegeid, final String admin_department) {
        auth.createUserWithEmailAndPassword(admin_emailid,admin_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String adminid = firebaseUser.getUid();

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin").child(adminid);

                            AdminInformation adminInformation = new AdminInformation();
                            adminInformation.setAdminuid(adminid);
                            adminInformation.setAdminusername(admin_username);
                            adminInformation.setAdminemailid(admin_emailid);
                            adminInformation.setAdminpassword(admin_password);
                            adminInformation.setAdminphonenumber(admin_phonenumber);
                            adminInformation.setAdmincollegeid(admin_collegeid);
                            adminInformation.setAdmindepartment(admin_department);

                            reference.setValue(adminInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(AdminRegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminRegisterActivity.this,AdminActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminRegisterActivity.this, "Registration Failure", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminRegisterActivity.this,AdminActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });

    }
}
