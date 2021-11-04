package com.lany.greendao;

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
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private RxDao<Note, Long> noteDao;
    private RxQuery<Note> notesQuery;
    private NotesAdapter notesAdapter;

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
                .subscribe(new Action1<List<Note>>() {

                    @Override
                    public void call(List<Note> notes) {
                        notesAdapter.setNotes(notes);
                    }
                });
    }

    protected void setUpViews() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);
        editText = findViewById(R.id.editTextNote);
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note(null, noteText, comment, new Date(), NoteType.TEXT);
        noteDao.insert(note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Note>() {
                    @Override
                    public void call(Note note) {
                        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
                        updateNotes();
                    }
                });
    }

    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {
            Note note = notesAdapter.getNote(position);
            final Long noteId = note.getId();

            noteDao.deleteByKey(noteId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            Log.d("DaoExample", "Deleted note, ID: " + noteId);
                            updateNotes();
                        }
                    });
        }
    };
}
