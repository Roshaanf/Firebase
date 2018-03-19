package roshaan.firebase.ui.activities.customer;

import android.databinding.DataBindingUtil;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import roshaan.firebase.R;
import roshaan.firebase.databinding.ActivityCustomerHomeBinding;
import roshaan.firebase.ui.fragments.Admin.EditProfileFragment;

public class CustomerHomeActivity extends AppCompatActivity implements View.OnClickListener,
        EditProfileFragment.OnFragmentInteractionListener {

    ActivityCustomerHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_home);

        setupListeners();
    }

    private void setupListeners() {
        binding.updateProfile.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == binding.updateProfile) {

            binding.dl.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, new EditProfileFragment())
                    .commit();
        }
    }

    @Override
    public void onEditProfileFragmentInteraction(int i) {

        if (i == EditProfileFragment.OnFragmentInteractionListener.EDIT)
            System.out.println("edit");
    }
}
