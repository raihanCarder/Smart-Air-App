package com.SmartAir.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.SmartAir.R;
import com.SmartAir.presenter.WelcomePresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    private WelcomePresenter presenter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        firestore = FirebaseFirestore.getInstance();
        HashMap<Object, Object> ved_test = new HashMap<>();
        ved_test.put("firstName", "EASY");
        ved_test.put("lastName", "PEASY");
        firestore.collection("ved_test").add(ved_test).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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




        presenter = new WelcomePresenter(this);

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> presenter.onContinueClicked());
    }

    @Override
    public void navigateNext() {
        startActivity(new Intent(this, RoleSelectionActivity.class));
    }
}
