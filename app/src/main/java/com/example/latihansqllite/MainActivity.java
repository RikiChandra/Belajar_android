package com.example.latihansqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button tambah_data;
    Cursor cursor;
    biodataTbl biodataTbl;
    Custome_adapter adapter;
    SQLiteDatabase database;
    ArrayList<Objek> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        tambah_data=findViewById(R.id.tambah_data);
        biodataTbl = new biodataTbl(getApplicationContext());
        tambah_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tambah_data.class));
            }
        });

        ambil_data();

    }
    void ambil_data(){
        list = new ArrayList<Objek>();
        cursor=biodataTbl.tampil_data();
        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                String id=cursor.getString(0); //id_data
                String nama=cursor.getString(1); //nama
                String alamat=cursor.getString(2); //alamat
                list.add(new Objek(id,nama,alamat));
            }

            adapter = new Custome_adapter(getApplicationContext(),list, MainActivity.this);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ambil_data();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}


class Custome_adapter extends BaseAdapter{
    biodataTbl biodataTbl;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Objek> model;
    Activity activity;

    Custome_adapter(Context context, ArrayList<Objek> list, Activity activity){
        this.context=context;
        this.model=list;
        this.activity=activity;
        layoutInflater=LayoutInflater.from(context);
        biodataTbl = new biodataTbl(context);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView id,nama,alamat;
    Button edit,hapus;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1= layoutInflater.inflate(R.layout.list_data,parent,false);
        id=view1.findViewById(R.id.id);
        nama=view1.findViewById(R.id.nama);
        alamat=view1.findViewById(R.id.alamat);

        id.setText(model.get(position).getId());
        nama.setText(model.get(position).getNama());
        alamat.setText(model.get(position).getAlamat());

        edit=view1.findViewById(R.id.edit);
        hapus=view1.findViewById(R.id.hapus);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Edit_data.class);
                intent.putExtra("nama",model.get(position).getNama());
                intent.putExtra("alamat", model.get(position).getAlamat());
                intent.putExtra("id", model.get(position).getId());
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                builder.setTitle("Tanya");
                builder.setMessage("Apakah yakin ingin menghapus data ini ? ");
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        biodataTbl.delete(model.get(position).getId());
//                        ((Activity)context).overridePendingTransition(0,0);
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
//                        ((Activity)context).finish();

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view1;
    }
}

