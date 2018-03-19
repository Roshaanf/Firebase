package roshaan.firebase.ui.fragments.Admin;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import roshaan.firebase.Constants;
import roshaan.firebase.R;
import roshaan.firebase.databinding.FragmentEditProfileBinding;
import roshaan.firebase.models.UserModel;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {

    FragmentEditProfileBinding binding;
    private OnFragmentInteractionListener mListener;
    Uri profileImagePath;
    StorageReference mStorage;
    DatabaseReference mRef;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);

        mRef = FirebaseDatabase.getInstance().getReference(Constants.USERS);
        mStorage = FirebaseStorage.getInstance().getReference("Photos");
        mAuth = FirebaseAuth.getInstance();

        setupListeners();
        // Inflate the layout for this fragment
        return binding.getRoot();

    }

    private void setupListeners() {

        binding.image.setOnClickListener(this);
        binding.edit.setOnClickListener(this);
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

        if (view == binding.edit) {

            if (binding.contact.length() > 0 &&
                    binding.name.length() > 0 &&
                    profileImagePath != null) {

                update(binding.name.getText().toString(),
                        binding.contact.getText().toString());


            } else {
                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == binding.image) {

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i, 200);
        }
    }


    private void update(final String userName, final String contact) {

        //uploading pic
        mStorage.child(profileImagePath.getLastPathSegment()).putFile(profileImagePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        profileImagePath = taskSnapshot.getDownloadUrl();

                        System.out.println("yes");

                        //updating in db
                        final Query query = mRef.orderByChild("userID").equalTo(mAuth.getCurrentUser().getUid());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                System.out.println("yes");
                                String key = null;

                                Iterable<DataSnapshot> child = dataSnapshot.getChildren();
                                UserModel user=null;
                                for (DataSnapshot ch : child) {
                                    key = ch.getKey();
                                    user=ch.getValue(UserModel.class);
                                }

                                user.setImageUrl(profileImagePath.toString());
                                user.setContact(contact);
                                user.setUserName(userName);


                                Map<String, Object> map = new HashMap<>();
                                map.put("contact", contact);
                                map.put("imageUrl", profileImagePath.toString());
                                map.put("userName", userName);

                                mRef.child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        profileImagePath=null;
                                        mListener.onEditProfileFragmentInteraction(OnFragmentInteractionListener.EDIT);
                                    }
                                });

                                Toast.makeText(getContext(), key, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });


    }

    public interface OnFragmentInteractionListener {
        int EDIT = 0;

        void onEditProfileFragmentInteraction(int i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK) {
            Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            binding.image.setImageURI(uri);

            profileImagePath = uri;
        } else {
            Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
        }
    }
}
