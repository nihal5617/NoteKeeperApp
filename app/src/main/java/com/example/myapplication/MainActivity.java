package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes;
    static ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
        notes=new ArrayList<>();
        Set<String> set=sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("\tEXAMPLE NOTES");
        }
        else{
            notes= new ArrayList(set);}
        ListView lvnote=findViewById(R.id.lv_notes);
        adapter =new ArrayAdapter<>(this,R.layout.list_item_my_note,notes);
        lvnote.setAdapter(adapter);
        lvnote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("pos",position);
                intent.putExtra("note",notes.get(position));
                startActivity(intent);
            }
        });

        lvnote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
                pos=index;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("ARE YOU SURE?");
                builder.setMessage("DO YOU REALLY WANT TO DELETE THIS NOTE?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        adapter.remove(notes.get(pos));
                        //notes.remove(index);
                        adapter.notifyDataSetChanged();
                        HashSet<String> h=new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes",h).apply();
                        Log.i("size",String.valueOf(notes.size()));

                    }
                });
                builder.setNegativeButton("NO",null);
                AlertDialog dialoge=builder.create();
                dialoge.show();
                return true;
            }
        });
        ImageButton p=findViewById(R.id.ib_add);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add("\tWRITE YOUR NOTES HERE");
                adapter.notifyDataSetChanged();

            }
        });
    }

}



