package com.example.nagendra.collegemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AdminMainActivity extends AppCompatActivity {

    MaterialEditText subjectname,chapters,nooftests,noofperiods,date;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_main_act);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Map Advice");

        subjectname = (MaterialEditText)findViewById(R.id.subject_name);
        chapters = (MaterialEditText)findViewById(R.id.chapters);
        nooftests = (MaterialEditText)findViewById(R.id.nooftests);
        noofperiods = (MaterialEditText)findViewById(R.id.noofperiods);
        date = (MaterialEditText)findViewById(R.id.date);
        update = (Button)findViewById(R.id.updatelecturedetails);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
