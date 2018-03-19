package roshaan.firebase.ui.activities.admin;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import roshaan.firebase.R;
import roshaan.firebase.databinding.ActivityAdminHomeBinding;

public class AdminHomeActivity extends AppCompatActivity {

    ActivityAdminHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_admin_home);

        setupListeners();
    }

    private void setupListeners(){


    }
}
