package com.example.minotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NotesEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);

        EditText editText = findViewById(R.id.editText);
        //get the intent for the noteId from MainActivity
        Intent intent = getIntent();
        int itemId = intent.getIntExtra("noteId", -1);

        if (itemId != -1) {
            editText.setText(MainActivity.notes.get(itemId));
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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}