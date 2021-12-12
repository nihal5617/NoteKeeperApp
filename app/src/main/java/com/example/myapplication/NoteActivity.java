package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashSet;

public class NoteActivity extends AppCompatActivity {
    int pos=0;
    EditText ednote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Intent intent = getIntent();
        final String notes=intent.getStringExtra("note");
        pos = intent.getIntExtra("pos", -1);
        if (MainActivity.notes.size() == 0) {
            MainActivity.notes.add(" ");

        } else {
            ednote = findViewById(R.id.ed_note);
            ednote.setText("Write");
            CheckBox cbnote=findViewById(R.id.cb_save);
            cbnote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ednote.getText().toString().equals(notes)){

                        MainActivity.adapter.remove(notes);
                        MainActivity.notes.add(pos, ednote.getText().toString());
                        MainActivity.adapter.notifyDataSetChanged();
                        SharedPreferences s=getApplicationContext().getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
                        HashSet<String> h=new HashSet<>(MainActivity.notes);
                        s.edit().putStringSet("notes",h).apply();

                    }
                    else{
                        MainActivity.adapter.notifyDataSetChanged();
                    }
                    Toast.makeText(NoteActivity.this, "SAVED SUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}