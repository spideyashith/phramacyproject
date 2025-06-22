package com.example.externalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "exampleExternal.txt";
    EditText editTextExternal;
    File myFile, folder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextExternal = findViewById(R.id.edit_text_external);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 123);
        }
    }

    public void saveDataExternal(View view) {
        folder = new File(
                Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath()+"/AIMIT");
        //folder = new File(Environment.getExternalStorageDirectory().getPath()+"/AIMIT");
        if(!folder.exists()){
            folder.mkdir();
        }
        myFile =new File(folder, FILE_NAME);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(myFile);
            OutputStreamWriter writer = new
                    OutputStreamWriter(outputStream);
            writer.write(editTextExternal.getText().toString());
            writer.close();
            editTextExternal.getText().clear();
            Toast.makeText(this,
                    "File Written successfully",
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataExternal(View view) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(myFile);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);
            BufferedReader reader =
                    new BufferedReader(inputStreamReader);
            String temp="";
            StringBuilder text= new StringBuilder();
            while ((temp=reader.readLine())!=null){
                text.append(temp);
            }
            reader.close();
            editTextExternal.setText(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}