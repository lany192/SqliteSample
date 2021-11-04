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
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        db.userDao().getAll()

        notesQuery.list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notes -> recyclerView.setAdapter(new NotesAdapter(notes, (position, note) -> {
                    final Long noteId = note.getId();
                    noteDao.deleteByKey(noteId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aVoid -> {
                                Log.d("DaoExample", "Deleted note, ID: " + noteId);
                                updateNotes();
                            });
                })));
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        User note = new User(null, noteText, comment, new Date(), NoteType.TEXT);
        noteDao.insert(note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(note1 -> {
                    Log.d("DaoExample", "Inserted new note, ID: " + note1.getId());
                    updateNotes();
                });
    }
}
