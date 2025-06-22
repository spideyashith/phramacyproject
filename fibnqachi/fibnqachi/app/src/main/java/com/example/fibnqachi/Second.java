package com.example.fibnqachi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Second extends AppCompatActivity {
    int [] series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        int limit1= getIntent().getIntExtra("limit",1);
        series = new int[limit1];
        series[0]=0;
        series[1]=1;
        for(int i=2;i<limit1;i++){
            series[i]=series[i-1]+series[i-2];
        }

        List<Integer> fiblist = new ArrayList<>();

        for(int i=0;i<series.length;i++){
            fiblist.add(series[i]);
        }

        ListView listView=findViewById(R.id.iv_fibonachi);
        ArrayAdapter<Integer> adapter=new ArrayAdapter (this, android.R.layout.simple_list_item_1,fiblist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(Second.this, "The Position is "+position, Toast.LENGTH_SHORT).show();

            }
        });



    }

}