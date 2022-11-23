package com.example.inztagram.fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.yalantis.ucrop.UCrop;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.inztagram.Models.UserRegisterRequest;
import com.example.inztagram.R;
import com.example.inztagram.utility.EndpointBuilder;
import com.example.inztagram.viewModels.ProfileViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.UUID;

public class ProfileFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View view;

    private Button logoutButton;
    private FloatingActionButton editProfilePicButton;
    private ImageView profileImageView;
    private EditText userNameEditText;
    private EditText fullNameEditText;
    private EditText emailIdEditText;

    private ActivityResultLauncher<String> mGetContent;

    private ProfileViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.view = view;
        setElements(view);
        initViewModel();
        this.setOnClickListeners();
        viewModel.getUserDetails();
        return view;
    }

    private void initViewModel() {
        this.viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        this.addViewModelObservers();
    }

    private void setElements(View view) {
        logoutButton = view.findViewById(R.id.button_logout);
        editProfilePicButton = view.findViewById(R.id.button_edit);
        profileImageView = view.findViewById(R.id.profile_image);
        userNameEditText = view.findViewById(R.id.editText_UserName);
        fullNameEditText = view.findViewById(R.id.editText_FullName);
        emailIdEditText = view.findViewById(R.id.editText_EmailID);
    }

    private void addViewModelObservers() {
        viewModel.getProfileLiveData().observe(getViewLifecycleOwner(), new Observer<UserRegisterRequest>() {
            @Override
            public void onChanged(UserRegisterRequest userRegisterRequest) {
                populateScreen(userRegisterRequest);
            }
        });
    }

    private void populateScreen(UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) { return; }
        emailIdEditText.setText(userRegisterRequest.getEmailId());
        userNameEditText.setText(userRegisterRequest.getUserName());
        fullNameEditText.setText(userRegisterRequest.getFullName());
        Glide.with(view.getContext())
                .asBitmap()
                .load(EndpointBuilder.getProfileImageUrlForUserName(userRegisterRequest.getUserName()))
                .optionalCircleCrop()
                .into(profileImageView);
    }

    private void setOnClickListeners() {
        handleImageSelected();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout();
            }
        });
        editProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditProfilePic();
            }
        });
    }

    private void handleLogout() {

    }

    private void handleEditProfilePic() {
        showImagePicker();
    }

    private void handleImageSelected() {
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                String destinationUri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
                UCrop.Options options = new UCrop.Options();
                options.setBrightnessEnabled(true);
                options.setContrastEnabled(true);
                options.setSharpnessEnabled(true);
                options.setSaturationEnabled(true);
                UCrop.of(result, Uri.fromFile(new File(getActivity().getCacheDir(), destinationUri)))
                        .withOptions(options)
                        .withAspectRatio(0,0)
                        .withMaxResultSize(1000, 1000)
                        .start(getActivity());
            }
        });
    }

    private void showImagePicker() {
        mGetContent.launch("image/*");
    }
}