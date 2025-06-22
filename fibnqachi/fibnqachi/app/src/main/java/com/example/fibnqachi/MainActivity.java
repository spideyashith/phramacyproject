package com.example.fibnqachi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button genBtn = findViewById(R.id.btngen);
        genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText numtxt = findViewById(R.id.editTextText);
                int num = Integer.parseInt(numtxt.getText().toString());
                Intent i = new Intent(MainActivity.this,
                        Second.class);
                i.putExtra("limit",num);
                startActivity(i);
            }
        });

    }
}