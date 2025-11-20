package com.SmartAir.dailycheckin;

import java.util.List;

public interface DailyCheckInContract {

    interface View {
        public abstract void showSubmitSuccess();
        public abstract void showSubmitFailure();
    }

    interface Presenter {
        public abstract void submitDailyCheckIn(Boolean isNightWalking, Boolean hasLimitedAbility,
                                                Boolean isSick, List<String> triggers);
    }
}
