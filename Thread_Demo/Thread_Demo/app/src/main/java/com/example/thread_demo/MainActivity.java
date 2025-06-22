package com.example.thread_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageLogo;
    private TextView tvProgress;
    private Button btnDownload;

    // Sample Base64 image string (replace with your own if needed)
    private final String base64Image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA"
            + "AAAFCAYAAACNbyblAAAAHElEQVQI12P4"
            + "//8/w38GIAXDIBKE0DHxgljNBAAO"
            + "9TXL0Y4OHwAAAABJRU5ErkJggg==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageLogo = findViewById(R.id.image_logo);
        tvProgress = findViewById(R.id.tvProgress);
        btnDownload = findViewById(R.id.btnDownload);
    }

    // Method linked to button via android:onClick="startDownload"
    public void startDownload(View view) {
        new DecodeImageTask().execute(base64Image);
    }

    private class DecodeImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvProgress.setText("Decoding image...");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                String base64Str = params[0];
                if (base64Str.contains(",")) {
                    base64Str = base64Str.split(",")[1]; // Remove data prefix
                }
                byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageLogo.setImageBitmap(bitmap);
                tvProgress.setText("Image decoded successfully.");
            } else {
                tvProgress.setText("Failed to decode image.");
            }
        }
    }
}
