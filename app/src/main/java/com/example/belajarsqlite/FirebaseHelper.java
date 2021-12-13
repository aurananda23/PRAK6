package com.example.belajarsqlite;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    FirebaseDatabase db;
    DatabaseReference dbref;

    public FirebaseHelper() {

        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("mhstb");

    }

    public void simpan(MhsModel mm){
        dbref.push().setValue(mm);
    }
}