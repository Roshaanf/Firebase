package roshaan.firebase.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import roshaan.firebase.Constants;
import roshaan.firebase.R;
import roshaan.firebase.databinding.FragmentLoginBinding;
import roshaan.firebase.models.UserModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    FragmentLoginBinding binding;
    private OnFragmentInteractionListener mListener;
    FirebaseAuth mAuth;
    DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);

        mAuth=FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference(Constants.USERS);

        setUpListeners();

        return binding.getRoot();
    }


    private void setUpListeners(){

        binding.login.setOnClickListener(this);
        binding.signup.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        if(view==binding.login){

            if(binding.email.length()>0&&
                    binding.password.length()>0){

                login(binding.email.getText().toString(),binding.password.getText().toString());
            }
            else{
                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view==binding.signup){
            mListener.onLoginFragmentInteraction(OnFragmentInteractionListener.SGN_UP);
        }
    }

    private  void login(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            //checking user type
                            mRef.orderByChild("userID")
                                    .equalTo(mAuth.getCurrentUser().getUid())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.exists()){

                                                Iterable<DataSnapshot> child=dataSnapshot.getChildren();
                                                UserModel user=null;
                                                for(DataSnapshot ch:child)
                                                    user=ch.getValue(UserModel.class);

                                                System.out.println(dataSnapshot.getValue());

                                                if(user.getUserType().equals(Constants.ADMIN)){
                                                    mListener.onLoginFragmentInteraction(OnFragmentInteractionListener.ADMIN_FEEED);
                                                }
                                                else{
                                                    mListener.onLoginFragmentInteraction(OnFragmentInteractionListener.CUSTOMER_FEED);
                                                }
                                            }
                                            else {
                                                Toast.makeText(getContext(), "User doesnot exist", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                        else{
                            Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        int ADMIN_FEEED=0,CUSTOMER_FEED=1,SGN_UP=2;
        void onLoginFragmentInteraction(int uri);
    }
}
