package com.github.lany192.sqlite.greendao;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class NotesAdapter extends BaseQuickAdapter<Note, BaseViewHolder> {
    private final NoteClickListener clickListener;

    public NotesAdapter(List<Note> data, NoteClickListener clickListener) {
        super(R.layout.item_note, data);
        this.clickListener = clickListener;
    }

    @Override
    protected void convert(BaseViewHolder holder, Note note) {
        holder.setText(R.id.textViewNoteText, note.getText());
        holder.setText(R.id.textViewNoteComment, note.getComment());
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onNoteClick(holder.getBindingAdapterPosition(),note);
            }
        });
    }

    public interface NoteClickListener {

        void onNoteClick(int position, Note note);
    }
}
