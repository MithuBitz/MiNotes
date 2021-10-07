package com.example.minotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesEditActivity extends AppCompatActivity {

    int itemId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.minotes", Context.MODE_PRIVATE);

        EditText editText = findViewById(R.id.editText);
        //get the intent for the noteId from MainActivity
        Intent intent = getIntent();
        itemId = intent.getIntExtra("noteId", -1);

        if (itemId != -1) {
            editText.setText(MainActivity.notes.get(itemId));
        } else {
            MainActivity.notes.add("");
            itemId = MainActivity.notes.size() -1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Set the new string on edittext and also set it in notes on MainActivity
                MainActivity.notes.set(itemId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                //Store the notes on a Hash and store this hash on sharedPreferences
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("NOTES", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}