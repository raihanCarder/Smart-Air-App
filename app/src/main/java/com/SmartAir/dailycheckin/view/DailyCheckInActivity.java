package com.SmartAir.dailycheckin.view;
import com.SmartAir.R; // R obj that contains all info regarding layout and obj inside them
import androidx.appcompat.app.AppCompatActivity; // base class for android screens
import android.widget.Button;
import android.widget.CheckBox;
import com.SmartAir.dailycheckin.DailyCheckInContract;
import com.SmartAir.dailycheckin.presenter.DailyCheckInPresenter;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

public class DailyCheckInActivity extends AppCompatActivity implements DailyCheckInContract.View {

    private Button submitBtn;
    private Button exitBtn;

    private TextView outputLog;
    private CheckBox nightWakingCheckBox;
    private CheckBox limitedAbilityCheckBox;
    private CheckBox sickCheckBox;
    private ChipGroup triggerChipGroup;

    DailyCheckInContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // normal activity setup
        setContentView(R.layout.activity_daily_check_in); // this draws whatever is in that xml.

        // TODO: Based upon user if Parent can choose which child to submit daily activiy
        // TODO: Cannot submit more then one daily activity per day per child

        presenter = new DailyCheckInPresenter(this);

        exitBtn = findViewById(R.id.dailyCheckInExitBtn);
        submitBtn = findViewById(R.id.dailyCheckInSubmitBtn);
        nightWakingCheckBox = findViewById(R.id.dailyCheckInNightWakingChkBox);
        limitedAbilityCheckBox = findViewById(R.id.dailyCheckInLimitActivityChkBox);
        sickCheckBox = findViewById(R.id.dailyCheckInSickChkBox);
        triggerChipGroup = findViewById(R.id.dailyCheckInTriggerChipGroup);
        outputLog = findViewById(R.id.ouputResults); // TODO: Delete later


        submitBtn.setOnClickListener(v -> {
            SubmitDataToPresenter();
        });

        exitBtn.setOnClickListener(v ->{
            exit();
        });
    }

    public void SubmitDataToPresenter(){
        boolean isNightWalking, hasLimitedAbility, isSick;
        String content;

        isNightWalking = nightWakingCheckBox.isChecked();
        hasLimitedAbility = limitedAbilityCheckBox.isChecked();
        isSick = sickCheckBox.isChecked();

        ArrayList<String> triggers = new ArrayList<>();

        for (int id: triggerChipGroup.getCheckedChipIds()) {
            Chip chip = triggerChipGroup.findViewById(id);

            if (chip == null) {
                continue;
            }

            content = chip.getText().toString();

            if (!content.isBlank()) {
                triggers.add(chip.getText().toString());
            }

        }

        presenter.submitDailyCheckIn(isNightWalking,hasLimitedAbility,isSick,triggers);
    }

    public void exit(){
        // TODO: go back to either Parent or Child Dashboards depending on User
    }

    @Override
    public void showSubmitSuccess(){
        outputLog.setText("Submit Success");
        // TODO : Navigate back to home after
    }

    @Override
    public void showSubmitFailure(){
        outputLog.setText("Submit Fail");
    }

}
