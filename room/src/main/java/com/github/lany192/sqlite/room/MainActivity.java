package com.github.lany192.sqlite.room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText = findViewById(R.id.editTextNote);
        findViewById(R.id.buttonAdd).setOnClickListener(v -> addNote());
        updateNotes();
    }

    private void updateNotes() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        List<User> items = db.userDao().getAll();
        recyclerView.setAdapter(new UserAdapter(items, (position, note) -> {
            db.userDao().delete(note);
            Log.d("DaoExample", "Deleted note, ID: " + note.getUid());
            updateNotes();
        }));
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        User note = new User(new Random().nextInt(), noteText, comment);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        db.userDao().insertAll(note);
        updateNotes();
    }
}
