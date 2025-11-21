package com.SmartAir.ParentDashboard.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
        TextView box1 = findViewById(R.id.myText);
        TextView box2 = findViewById(R.id.myText3);
        TextView box3 = findViewById(R.id.myText4);

        Spinner spinner = findViewById(R.id.mySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.options_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Log.i("Spinner", "Selected: " + selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        dbTest(box1);



    }



    @SuppressLint("SetTextI18n")
    protected void dbTest(TextView test_text){
        Log.i("TAGGGG", "function initilize");

        db.collection("Users").
                document("f7VfCpCK355148sX129J")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                   if (documentSnapshot.exists()) {
                       ParentModel user = documentSnapshot.toObject((ParentModel.class));
                       String name = documentSnapshot.getString("name");
                       String age = documentSnapshot.getString("role");

                       assert user != null;
                       test_text.setText("Name: " + user.getName() + "Age: " + user.getRole());

                       Log.i("TAG", "DEBUG NAME:" + name + "   " + age);
                   }
                })
                .addOnFailureListener(e ->{

                });

    }

}
