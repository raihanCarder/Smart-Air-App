package com.SmartAir.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

import com.SmartAir.R;
import com.SmartAir.presenter.PEFPresenter;

import java.util.Objects;

public class PEFEntryActivity extends Activity implements PEFEntryView{
    private PEFPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pef);
        presenter = new PEFPresenter(this);

        Button pefButton = findViewById(R.id.pef_button);
        pefButton.setOnClickListener(v -> presenter.onPEFClicked());
    }
    public void popOut(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        dialog.show();
    }
}
