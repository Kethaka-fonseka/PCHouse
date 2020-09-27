package com.example.pchouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Entering extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(Entering.this, Register.class); startActivity(i);
                finish(); } }, 3000);
    }
}