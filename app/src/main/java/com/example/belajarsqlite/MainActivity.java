package com.example.belajarsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MhsModel> mhsList ;
    MhsModel mm ;
    DbHelper db ;

    FirebaseHelper firebaseHelper;

    boolean isEdit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edNama = (EditText) findViewById(R.id.edNama);
        EditText edNim = (EditText) findViewById(R.id.edNim);
        EditText edNoHp = (EditText) findViewById(R.id.edNoHp);

        Button btnSimpan = (Button) findViewById(R.id.btnSimpan);

        mhsList = new ArrayList<>();

        isEdit = false ;

        Intent intent_main = getIntent();
        if(intent_main.hasExtra("mhsData")){
            mm = intent_main.getExtras().getParcelable("mhsData");
            edNama.setText(mm.getNama());
            edNim.setText(mm.getNim());
            edNoHp.setText(mm.getNoHp());

            isEdit = true;

            btnSimpan.setBackgroundColor(Color.GREEN);
            btnSimpan.setText("Edit");
        }

        db = new DbHelper(getApplicationContext());
//        FirebaseHelper = new firebaseHelper();
firebaseHelper = new FirebaseHelper();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isian_nama = edNama.getText().toString();
                String isian_nim = edNim.getText().toString();
                String isian_hoHp = edNoHp.getText().toString();

                if(isian_nama.isEmpty() || isian_nim.isEmpty() || isian_hoHp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Isian masih kosong", Toast.LENGTH_SHORT).show();
                }else{
                    // mhsList.add(new MhsModel(-1, isian_nama, isian_nim, isian_hoHp));



                    boolean stts = true;

                    if(!isEdit){
                        mm = new MhsModel(" ", isian_nama, isian_nim, isian_hoHp);
                        //stts = db.simpan(mm);
                        firebaseHelper.simpan(mm);


                        edNama.setText("");
                        edNim.setText("");
                        edNoHp.setText("");

                    }else{
                        mm = new MhsModel(mm.getKey(), isian_nama, isian_nim, isian_hoHp);
                        stts = db.ubah(mm);
                    }


                    if(stts){

                        Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                    }



                    // intent_list.putParcelableArrayListExtra("mhsList", mhsList);
                    // startActivity(intent_list);

                }
            }
        });

        Button btnLihat = (Button) findViewById(R.id.btnLihat);
        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mhsList = db.list();

                if(mhsList.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Belum ada data", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent_list = new Intent(MainActivity.this, ListMhsActivity.class);
                    intent_list.putParcelableArrayListExtra("mhsList", mhsList);
                    startActivity(intent_list);
                }
            }
        });

    }
}