package com.SmartAir.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.SmartAir.R;
import com.SmartAir.presenter.startTriagePresenter;
import com.google.firebase.firestore.FirebaseFirestore;

public class startTriageActivity extends AppCompatActivity implements startTriageView {

    private startTriagePresenter presenter;
    Handler h = new Handler(Looper.myLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_for_triage);

        presenter = new startTriagePresenter(this);

        Button startButton = findViewById(R.id.Start_Triage);
        startButton.setOnClickListener(v -> presenter.onStartClicked());
    }

    @Override
    public void openForm() {

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            h.postDelayed(runnable, 600000);
        }
    };

}
