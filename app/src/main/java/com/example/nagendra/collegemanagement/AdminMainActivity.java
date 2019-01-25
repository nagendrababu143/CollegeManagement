package com.example.nagendra.collegemanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nagendra.collegemanagement.models.AdminLectureModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

public class AdminMainActivity extends AppCompatActivity {

    MaterialEditText subjectname,chapters,nooftests,noofperiods;
    EditText date;
    Button update;
    private int mYear, mMonth, mDay;
    String datelimit;

    String subjectname_text,chapters_text,nooftests_text,noofperiods_text;

    FirebaseAuth auth;
    FirebaseUser user;
    String currentuserid;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_main_act);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Map Advice");

        auth = FirebaseAuth.getInstance();


        subjectname = (MaterialEditText)findViewById(R.id.subject_name);
        chapters = (MaterialEditText)findViewById(R.id.chapters);
        nooftests = (MaterialEditText)findViewById(R.id.nooftests);
        noofperiods = (MaterialEditText)findViewById(R.id.noofperiods);
        date = (EditText) findViewById(R.id.date);
        update = (Button)findViewById(R.id.updatelecturedetails);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                datelimit = String.valueOf(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                                date.setText(datelimit);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(AdminMainActivity.this);
                progressDialog.setMessage("Updating wait..");
                progressDialog.show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminLecture");

                subjectname_text = subjectname.getText().toString().trim();
                chapters_text = chapters.getText().toString().trim();
                nooftests_text = nooftests.getText().toString().trim();
                noofperiods_text = noofperiods.getText().toString().trim();

                user = auth.getCurrentUser();
                currentuserid = user.getUid();

                AdminLectureModel model = new AdminLectureModel();

                model.setUserid(currentuserid);
                model.setSubjectname(subjectname_text);
                model.setChapters(chapters_text);
                model.setNooftests(nooftests_text);
                model.setNoofperiods(noofperiods_text);
                model.setDate(datelimit);

                databaseReference.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminMainActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                            /*Intent intent = new Intent(AdminRegisterActivity.this,AdminActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);*/
                        }
                    }
                });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout)
        {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminMainActivity.this,MainActivity.class));
            finish();
            return true;
        }
        else if(id == R.id.profile)
        {
            //startActivity(new Intent(AdminMainActivity.this,ProfileActivity.class));
            return true;
        }
        else if(id == R.id.about)
        {
            //startActivity(new Intent(MapsActivity.this,AboutActivity.class));
            return true;
        }


        return false;
    }
}
