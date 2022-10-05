package com.example.latihansqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_data extends AppCompatActivity {

    EditText nama,alamat;
    biodataTbl biodataTbl;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data);
        nama=findViewById(R.id.nama);
        alamat=findViewById(R.id.alamat);
        update=findViewById(R.id.update_data);
        getSupportActionBar().setTitle("Edit Data "+getIntent().getStringExtra("nama"));
        biodataTbl = new biodataTbl(getApplicationContext());

        //Mengambil data dari database

        nama.setText(getIntent().getStringExtra("nama"));
        alamat.setText(getIntent().getStringExtra("alamat"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biodataTbl.update_data(
                        getIntent().getStringExtra("id"),
                        nama.getText().toString(),
                        alamat.getText().toString()
                );
                finish();
            }
        });
    }
}