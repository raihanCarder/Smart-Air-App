package com.SmartAir.presenter;

import com.SmartAir.view.OneTapView;
import com.SmartAir.view.WelcomeView;

public class OneTapPresenter {
    private final OneTapView view;
    public OneTapPresenter(OneTapView view) { this.view = view;}
    public void redFlagClicked(){
        view.callEmergency();
    }
}
