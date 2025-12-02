package com.SmartAir.view;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.SmartAir.R;
import com.SmartAir.onboarding.presenter.PEFPresenter;
import com.SmartAir.onboarding.view.PEFEntryView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class PEFEntryActivity extends Activity implements PEFEntryView {
    private PEFPresenter presenter;
    FirebaseFirestore firestore;

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
        Button submit = dialog.findViewById(R.id.Submit_Button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();

                EditText pefVal = dialog.findViewById(R.id.PEFNumber);
                String value= pefVal.getText().toString();
                Toast.makeText(getApplicationContext(), "got value", Toast.LENGTH_LONG).show();

                firestore = FirebaseFirestore.getInstance();
                HashMap<Object, Object> ved_test = new HashMap<>();
                ved_test.put("parentId", "parent1");
                ved_test.put("childId", "child1");
                ved_test.put("timestamp", "2025-02-21T07:45:00Z");
                ved_test.put("pef", Integer.parseInt(value));
                ved_test.put("zone", "yellow");
                Toast.makeText(getApplicationContext(), "About to log", Toast.LENGTH_LONG).show();

                firestore.collection("pefLogs").add(ved_test).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Succcess", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "FAILURE", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        dialog.show();
    }
}
