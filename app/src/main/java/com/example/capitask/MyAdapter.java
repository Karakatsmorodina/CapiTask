package com.example.capitask;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    RealmResults<Note> notesList;
    private OnNoteDeleteListener deleteListener;

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;
        TextView costOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            costOutput = itemView.findViewById(R.id.costoutput);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }


    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    public interface OnNoteDeleteListener {
        void onNoteDeleted(int cost,String title);
    }
    public void setOnNoteDeleteListener(OnNoteDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());
        holder.costOutput.setText(Integer.toString(note.getCost()));
        holder.timeOutput.setText(DateFormat.getDateTimeInstance().format(note.getCreatedTime()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DONE");

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DONE")){
                            if (deleteListener != null) {
                                deleteListener.onNoteDeleted(note.getCost(),note.getTitle());
                            }
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                        }
                        return true;
                    }
                });

                menu.show();
                return true;
            }
        });

    }


}
