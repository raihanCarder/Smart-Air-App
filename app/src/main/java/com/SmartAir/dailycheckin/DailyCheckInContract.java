package com.SmartAir.dailycheckin;

import com.SmartAir.dailycheckin.presenter.DailyCheckInDataModel;

import java.util.List;

public interface DailyCheckInContract {

    interface View {
        public abstract void showSubmitSuccess();
        public abstract void showSubmitFailure();
        public abstract void showSpinnerData(List<String> childrenNames);
        public abstract void showError(String message);
    }

    interface Presenter {
        public abstract void submitDailyCheckIn(String role, String childName, String parentId,
                                                Boolean isNightWalking, Boolean hasLimitedAbility,
                                                Boolean isSick, List<String> triggers);
        public abstract void loadChildren(String parentId);
    }

    interface Repository {
        interface SaveCallback {
            public abstract void onSuccess();
            public abstract void onFailure(Exception e);
        }

        interface LoadChildrenCallback{
            public abstract void onChildrenLoaded(List<String> childNames);
            public abstract void onError(Exception e);
        }
        public abstract void loadChildren(String parentId, LoadChildrenCallback callback);

        public abstract void sendDataToDatabase(DailyCheckInDataModel data, SaveCallback callback);
    }
}
