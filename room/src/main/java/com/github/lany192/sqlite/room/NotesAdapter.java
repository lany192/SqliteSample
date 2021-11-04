package com.github.lany192.sqlite.room;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.sqlite.greendao.R;

import java.util.List;

public class NotesAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    private final NoteClickListener clickListener;

    public NotesAdapter(List<User> data, NoteClickListener clickListener) {
        super(R.layout.item_note, data);
        this.clickListener = clickListener;
    }

    @Override
    protected void convert(BaseViewHolder holder, User note) {
        holder.setText(R.id.textViewNoteText, note.getText());
        holder.setText(R.id.textViewNoteComment, note.getComment());
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onNoteClick(holder.getBindingAdapterPosition(),note);
            }
        });
    }

    public interface NoteClickListener {

        void onNoteClick(int position, User note);
    }
}
