package roshaan.firebase.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import roshaan.firebase.Constants;
import roshaan.firebase.R;
import roshaan.firebase.models.UserModel;
import roshaan.firebase.ui.activities.admin.AdminHomeActivity;
import roshaan.firebase.ui.activities.customer.CustomerHomeActivity;

public class StartActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    mAuth=FirebaseAuth.getInstance();
    mRef=FirebaseDatabase.getInstance().getReference(Constants.USERS);

    if(mAuth.getCurrentUser()==null){

        startActivity(new Intent(this, AuthenticationActivity.class));
        finish();
    }
    else{

        mRef.orderByChild("userID").equalTo(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            Iterable<DataSnapshot> child=dataSnapshot.getChildren();
//                            System.out.println(user);

                            UserModel user=null;
                            for(DataSnapshot ch:child)
                                user=ch.getValue(UserModel.class);

                            System.out.println(dataSnapshot.getValue());
                            if(user.getUserType().equals(Constants.ADMIN)){
                                startActivity(new Intent(StartActivity.this, AdminHomeActivity.class));
                                finish();
                            }
                            else if(user.getUserType().equals(Constants.CUSTOMER)){
                                startActivity(new Intent(StartActivity.this, CustomerHomeActivity.class));
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(StartActivity.this, "User doesnt exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    }
}
