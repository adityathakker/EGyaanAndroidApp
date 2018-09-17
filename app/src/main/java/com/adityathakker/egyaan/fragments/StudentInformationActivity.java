package com.adityathakker.egyaan.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.DatabaseHandler;
import com.adityathakker.egyaan.utils.ProgressBar;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by fireion on 29/6/17.
 */

public class StudentInformationActivity extends Fragment {

    private static final String TAG = StudentInformationActivity.class.getSimpleName();
    DatabaseHandler databaseHandlerStudentInformation;
    Details details;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    @BindView(R.id.studentProfileImage)
    ImageView imageViewProfileImage;
    @BindView(R.id.studentEmail)
    TextView textViewStudentEmail;
    @BindView(R.id.studentPhoneNumber)
    TextView textViewStudentPhone;
    @BindView(R.id.studentGender)
    TextView textViewGender;
    @BindView(R.id.parentName)
    TextView textViewParentName;
    @BindView(R.id.parentEmail)
    TextView textViewParentEmail;
    @BindView(R.id.parentPhoneNumber)
    TextView textViewParentPhone;

    public StudentInformationActivity() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_information, container, false);
        ButterKnife.bind(this, view);
        progressBar = new ProgressBar();

        progressBar.showProgressBar(getActivity());

        databaseHandlerStudentInformation = new DatabaseHandler(getActivity());
        sharedPreferences = getActivity().getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = databaseHandlerStudentInformation.getStudent(sharedPreferences.getString(AppConst.Extras.USERNAME, null));

        Glide.with(this).load(AppConst.URLs.IMAGE_URL + "" + details.getStudentProfilePhoto())
                .into(imageViewProfileImage);

        textViewStudentEmail.setText(details.getEmail());
        textViewStudentPhone.setText("+91 " + details.getMobile());

        switch (details.getGender()) {
            case "M":
                textViewGender.setText("Male");
                break;
            case "F":
                textViewGender.setText("Female");
                break;
            default:
                textViewGender.setText("TransGender");
                break;
        }

        textViewParentName.setText(details.getParentName());
        textViewParentEmail.setText(details.getParentEmail());
        textViewParentPhone.setText("+91 " + details.getParentMobile());

        progressBar.dismissProgressBar();

        return view;
    }
}
