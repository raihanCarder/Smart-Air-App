package com.SmartAir.presenter;

import com.SmartAir.view.PEFEntryView;

public class PEFPresenter {
    private final PEFEntryView view;
    public PEFPresenter(PEFEntryView view) {
        this.view = view;
    }
    public void onPEFClicked() {
        view.popOut();
    }

}
