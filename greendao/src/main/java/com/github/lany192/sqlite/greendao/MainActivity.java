package com.github.lany192.sqlite.greendao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.text.DateFormat;
import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private RxDao<Note, Long> noteDao;
    private RxQuery<Note> notesQuery;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        DaoSession daoSession = ((MyApp) getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao().rx();
        notesQuery = daoSession.getNoteDao().queryBuilder().orderAsc(NoteDao.Properties.Text).rx();
        updateNotes();
    }

    private void updateNotes() {
        notesQuery.list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notes -> recyclerView.setAdapter(new NotesAdapter(notes, (position, note) -> {
                    noteDao.deleteByKey(note.getId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aVoid -> {
                                Log.d("DaoExample", "Deleted note, ID: " + note.getId());
                                updateNotes();
                            });
                })));
    }

    protected void setUpViews() {
        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText = findViewById(R.id.editTextNote);
        findViewById(R.id.buttonAdd).setOnClickListener(v -> addNote());
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note(null, noteText, comment, new Date());
        noteDao.insert(note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(note1 -> {
                    Log.d("DaoExample", "Inserted new note, ID: " + note1.getId());
                    updateNotes();
                });
    }
}
