package com.adityathakker.egyaan.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.DatabaseHandler;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fireion on 29/6/17.
 */

public class StudentInformationActivity extends AppCompatActivity {


    DatabaseHandler databaseHandlerStudentInformation;
    Details details;
    SharedPreferences sharedPreferences;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.profile_image)
    ImageView imageViewProfileImage;
    @BindView(R.id.list_item_genre_email)
    TextView textViewStudentEmail;
    @BindView(R.id.list_item_genre_phone)
    TextView textViewStudentPhone;
    @BindView(R.id.list_item_genre_gender)
    TextView textViewGender;
    @BindView(R.id.list_item_genre_parent_name)
    TextView textViewParentName;
    @BindView(R.id.list_item_genre_parent_email)
    TextView textViewParentEmail;
    @BindView(R.id.list_item_genre_parent_phone)
    TextView textViewParentPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
        ButterKnife.bind(this);

        databaseHandlerStudentInformation = new DatabaseHandler(this);
        sharedPreferences = getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE);
        details = databaseHandlerStudentInformation.getStudent(sharedPreferences.getString(AppConst.Extras.USERNAME, null));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        collapsingToolbarLayout.setTitle(details.getFirstname() + " " + details.getLastname());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Glide.with(this).load(AppConst.URLs.IMAGE_URL + "" + details.getStudentProfilePhoto())
                .into(imageViewProfileImage);

        textViewStudentEmail.setText(details.getEmail());
        textViewStudentPhone.setText("+91 " + details.getMobile());

        if (details.getGender().equals("M")) {
            textViewGender.setText("Male");
        } else if (details.getGender().equals("F")) {
            textViewGender.setText("Female");
        } else {
            textViewGender.setText("TransGender");
        }

        textViewParentName.setText(details.getParentName());
        textViewParentEmail.setText(details.getParentEmail());
        textViewParentPhone.setText("+91 " + details.getParentMobile());
    }
}
