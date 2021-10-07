package com.example.minotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intialize the shared preferences
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.minotes", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("NOTES", null);

        if (set == null) {
            notes.add("First Note: ");
        } else {
            notes = new ArrayList(set);
        }

        ListView listView = findViewById(R.id.listView);


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotesEditActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            final int itemToDelete = position;

            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.warning_delete)
                    .setTitle("Are you sure!")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        notes.remove(itemToDelete);
                        arrayAdapter.notifyDataSetChanged();

                        HashSet<String> set1 = new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("NOTES", set1).apply();

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }

    //Create the add note menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(getApplicationContext(), NotesEditActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}