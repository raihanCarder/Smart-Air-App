package com.SmartAir.ParentDashboard.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.SmartAir.ParentDashboard.model.ParentModel;
import com.SmartAir.ParentDashboard.presenter.ParentDashboardPresenter;
import com.SmartAir.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import android.util.Log;

public class ParentDashboardActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ParentDashboardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_dashboard);

        FirebaseApp.initializeApp(this);


        Log.i("TAG", "CREATED PAGE");


        TextView test_text = findViewById(R.id.r6_test);

        Button butt = findViewById(R.id.dash_button);


        dbTest(test_text);


    }


    protected void dbTest(TextView test_text){

        db.collection("Users").
                document(" f7VfCpCK355148sX129J ")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                   if (documentSnapshot.exists()) {
                       ParentModel user = documentSnapshot.toObject((ParentModel.class));
                       String name = documentSnapshot.get("Name").toString();
                       String age = documentSnapshot.get("Role").toString();

                       test_text.setText("Name: " + user.getName() + "Age: " + user.getRole());
                       Log.i("TAG", "DEBUG NAME:" + user.name);
                   }
                })
                .addOnFailureListener(e ->{

                });

    }

}
