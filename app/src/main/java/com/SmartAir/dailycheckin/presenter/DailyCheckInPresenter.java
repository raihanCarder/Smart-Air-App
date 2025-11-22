package com.SmartAir.dailycheckin.presenter;
import com.SmartAir.dailycheckin.DailyCheckInContract;
import com.SmartAir.dailycheckin.model.DailyCheckInRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DailyCheckInPresenter implements DailyCheckInContract.Presenter {

    private final DailyCheckInContract.View view;
    private final DailyCheckInContract.Repository repository;

    private String tempUser = "Child"; // TODO: Delete later when we know what user logged in
    private String tempChildName = "Raihan"; // TODO: delete later when child selection functionality added

    public DailyCheckInPresenter(DailyCheckInContract.View view){
        this.view = view;
        this.repository = new DailyCheckInRepository();
    }
    @Override
    public void submitDailyCheckIn(Boolean isNightWalking, Boolean hasLimitedAbility,
                                   Boolean isSick, List<String> triggers){
        String date = getCurrentDate();

        // TODO: Get rid of temp data and replace with user data

        DailyCheckInDataModel data = new DailyCheckInDataModel(date, tempUser, tempChildName,
                isNightWalking, hasLimitedAbility, isSick, triggers);
        repository.sendDataToDatabase(data, new DailyCheckInContract.Repository.SaveCallback() {
            @Override
            public void onSuccess() {
                view.showSubmitSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                view.showSubmitFailure();
            }
        });
    }

    public String getCurrentDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return now.format(formatter);
    }


}
