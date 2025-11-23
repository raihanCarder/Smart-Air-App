package com.SmartAir.dailycheckin.view;
import com.SmartAir.R;
import androidx.appcompat.app.AppCompatActivity;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_check_in);

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
            finish();
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

    @Override
    public void showSubmitSuccess(){
        outputLog.setText("Submit Success"); // TODO: delete later
        finish();
    }

    @Override
    public void showSubmitFailure(){
        outputLog.setText("Submit Fail"); // TODO: delete later
    }

}
