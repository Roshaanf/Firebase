package roshaan.firebase.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import roshaan.firebase.R;
import roshaan.firebase.ui.activities.admin.AdminHomeActivity;
import roshaan.firebase.ui.activities.customer.CustomerHomeActivity;
import roshaan.firebase.ui.fragments.LoginFragment;
import roshaan.firebase.ui.fragments.SignupFragment;

public class AuthenticationActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener {

    FragmentManager mng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mng = getSupportFragmentManager();
        mng.beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commit();
    }

    @Override
    public void onLoginFragmentInteraction(int uri) {
        switch (uri) {

            case LoginFragment.OnFragmentInteractionListener.ADMIN_FEEED:
                startActivity(new Intent(this, AdminHomeActivity.class));
                finish();
                break;

            case LoginFragment.OnFragmentInteractionListener.CUSTOMER_FEED:
                startActivity(new Intent(this, CustomerHomeActivity.class));
                finish();
                break;
            case LoginFragment.OnFragmentInteractionListener.SGN_UP:
                mng.beginTransaction()
                        .replace(R.id.container, new SignupFragment())
                        .addToBackStack(null)
                        .commit();

        }
    }

    @Override
    public void onSignupFragmentInteraction(int i) {

        switch (i) {

            case SignupFragment.OnFragmentInteractionListener.ADMIN_HOME:
                startActivity(new Intent(this, AdminHomeActivity.class));
                finish();
                break;

            case SignupFragment.OnFragmentInteractionListener.CUSTOMER_HOME:
                startActivity(new Intent(this, CustomerHomeActivity.class));
                finish();
                break;
    }
    }
}
