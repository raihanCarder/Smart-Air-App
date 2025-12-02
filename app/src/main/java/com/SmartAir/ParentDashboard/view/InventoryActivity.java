package com.SmartAir.ParentDashboard.view;

import static com.SmartAir.ParentDashboard.view.ParentDashboardActivity.childId;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.SmartAir.R;
import com.SmartAir.onboarding.model.BaseUser;
import com.SmartAir.onboarding.model.CurrentUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class InventoryActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    BaseUser user =  CurrentUser.getInstance().getUserProfile();



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_parent);
        Toolbar toolbar = findViewById(R.id.toolbar_inv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.parent_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }
}
