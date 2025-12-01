package com.SmartAir.history;

import com.SmartAir.history.presenter.FilterDataModel;
import com.SmartAir.history.presenter.HistoryItem;

import java.util.List;

/**
 * Contract interfaces defining the communication between the View, Presenter, Repository,
 * Adapter, and FilterDialog within the History feature.
 */
public interface HistoryContract {

    interface View {
        public abstract void showHistory(List<HistoryItem> items);
        public abstract void showLoadError(String message);
        public abstract void showLoading();
        public abstract void hideLoading();
    }

    interface FilterDialog{
        public abstract void show();
    }

    interface Presenter{
        public abstract void loadData(FilterDataModel filter);
        public abstract List<HistoryItem> getLastQuery();
    }

    interface Repository{
        interface LoadCallback {
            public abstract void onSuccess(List<HistoryItem> items);
            public abstract void onFailure(Exception e);
        }

        public abstract void getData(FilterDataModel filter, LoadCallback callback);
    }

    interface Export{
        public abstract void exportHistoryToPdf(List<HistoryItem> items);
    }

    interface Adapter {
        public abstract void setItems(List<HistoryItem> newItems);
    }
}
