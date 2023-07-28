package com.example.capitask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

public class NoteActivity extends AppCompatActivity implements MyAdapter.OnNoteDeleteListener {
    int score = 0;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        SharedPreferences sp = this.getSharedPreferences("Myscore", Context.MODE_PRIVATE);
        score = sp.getInt("score",0);

        MaterialButton addNoteBtn = findViewById(R.id.addnewnotebtn);
        MaterialButton goHomeBtn = findViewById(R.id.gohomebnt);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this,AddNoteActivity.class));
            }
        });
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this,MainActivity.class));
            }
        });



        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> notesList = realm.where(Note.class).findAllSorted("createdTime", Sort.DESCENDING);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),notesList);

        myAdapter.setOnNoteDeleteListener((MyAdapter.OnNoteDeleteListener)this);
        recyclerView.setAdapter(myAdapter);
        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });


    }
    public void onNoteDeleted(int cost,String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(title.equals("goal")) {
                    score += cost;
                    SharedPreferences sp = getSharedPreferences("Myscore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("score", score);
                    editor.apply();
                    animationView = findViewById(R.id.coin);
                    animationView.playAnimation();
                }
                else if(title.equals("reward")) {
                    score -= cost;
                    SharedPreferences sp = getSharedPreferences("Myscore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("score", score);
                    editor.apply();
                    animationView = findViewById(R.id.congrats);
                    animationView.playAnimation();
                }
            }
        });
    }
}

