package com.SmartAir.dailycheckin.presenter;
import com.SmartAir.dailycheckin.DailyCheckInContract;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DailyCheckInPresenter implements DailyCheckInContract.Presenter {

    private final DailyCheckInContract.View view;

    public DailyCheckInPresenter(DailyCheckInContract.View view){
        this.view = view;
    }
    @Override
    public void submitDailyCheckIn(Boolean isNightWalking, Boolean hasLimitedAbility, Boolean isSick,
                                   List<String> triggers){
        String date = getCurrentDate();
        // add data containing if current user is parent or child

        // implement sending data to database

        System.out.println("working submit btn");
    }

    public String getCurrentDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return now.format(formatter);
    }


}
