package com.SmartAir.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.SmartAir.R;
import com.SmartAir.presenter.OneTapPresenter;
import com.SmartAir.presenter.RoleSelectionPresenter;
import com.SmartAir.presenter.WelcomePresenter;

public class OneTapActivity extends AppCompatActivity implements OneTapView{
    RadioButton greyLips, blueLips, otherLips, greyNails, blueNails, otherNails,yesChest,noChest, yesSpeak, noSpeak,  noAttempts;
    Button help;
    EditText currentPEF, ifYes;
    private OneTapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_tap_triage);

        presenter = new OneTapPresenter(this);


        greyLips = findViewById(R.id.greyLips);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

        blueLips = findViewById(R.id.blueLips);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

        otherLips = findViewById(R.id.otherLips);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        yesChest = findViewById(R.id.yesChest);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

        noChest = findViewById(R.id.NoChest);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        yesSpeak = findViewById(R.id.yesSpeak);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        noSpeak = findViewById(R.id.noSpeak);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

        greyNails = findViewById(R.id.greyNails);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

        blueNails = findViewById(R.id.blueNails);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

        otherNails = findViewById(R.id.otherNails);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        noAttempts = findViewById(R.id.noAttempts);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        ifYes = findViewById(R.id.ifYes);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        currentPEF = findViewById(R.id.currentPEF);
        greyLips.setOnClickListener(v -> presenter.onContinueClicked());

        help = findViewById(R.id.helpButton);
        greyLips.setOnClickListener(v -> presenter.redFlagClicked());

    }

    @Override
    public void callEmergency() {
        Intent i = new Intent();
        i.setData(Uri.parse("tel:7809648081"));
        startActivity(i);
    }
}
