package com.example.nasrf.githubtask.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nasrf.githubtask.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        LinearLayout element = (LinearLayout) findViewById(R.id.test);
        element.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        */
    }

    public void getREPO(View view) {
        Intent in = new Intent(this,ResponseActivity.class);
        startActivity(in);
    }
}
