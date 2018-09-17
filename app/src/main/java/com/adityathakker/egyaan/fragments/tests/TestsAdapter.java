package com.adityathakker.egyaan.fragments.tests;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.TestsModel;
import com.adityathakker.egyaan.ui.activities.TestsDetailsActivity;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;

import java.util.List;

/**
 * Created by fireion on 30/11/17.
 */

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestsViewHolder> {
    private static final String TAG = TestsAdapter.class.getSimpleName();
    Context context;
    private List<TestsModel> testsModelList;

    public TestsAdapter(Context testsActivity, List<TestsModel> testsModels) {
        this.context = testsActivity;
        this.testsModelList = testsModels;
    }

    @Override
    public TestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tests_activity, parent, false);
        return new TestsAdapter.TestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestsViewHolder holder, int position) {
        final TestsModel testsModel = testsModelList.get(position);

        holder.textViewTestTitle.setText(testsModel.getTitle());
        holder.textViewTestDate.setText(testsModel.getDateOfTest());

        if (testsModel.getMarks().equals("-")) {
            holder.textViewTestMarks.setVisibility(View.GONE);
            holder.textViewTestTotalMarks.setVisibility(View.GONE);
            holder.textViewTestAttendance.setVisibility(View.VISIBLE);
            holder.textViewTestAttendance.setText("Absent");
            holder.textViewTestAttendance.setTextColor(Color.RED);
        } else {
            holder.textViewTestMarks.setText(testsModel.getMarks());
            holder.textViewTestTotalMarks.setText("/" + testsModel.getTotalMarks());
        }

        if (testsModel.getType().equals("O")) {
            holder.imageViewOnlineTest.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CommonTasks.isDataOn(context).equals(AppConst.Extras.WIFI) || CommonTasks.isDataOn(context).equals(AppConst.Extras.MOBILE)) {
                        Intent intentTestDetails = new Intent(context, TestsDetailsActivity.class);
                        intentTestDetails.putExtra("testId", testsModel.getTestId());
                        context.startActivity(intentTestDetails);
                    } else {
//                        Toast.makeText(context, AppConst.Messages.NO_INTERNET, Toast.LENGTH_SHORT).show();
                        CommonTasks.showMessage(view, AppConst.Messages.NO_INTERNET);
                    }
                }
            });
        } else {
            holder.imageViewOnlineTest.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return testsModelList.size();
    }

    class TestsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTestTitle, textViewTestDate, textViewTestMarks, textViewTestTotalMarks, textViewTestAttendance;
        ImageView imageViewOnlineTest;

        TestsViewHolder(View itemView) {
            super(itemView);
            textViewTestTitle = itemView.findViewById(R.id.testTitle);
            textViewTestDate = itemView.findViewById(R.id.testDate);
            textViewTestMarks = itemView.findViewById(R.id.testMarks);
            textViewTestTotalMarks = itemView.findViewById(R.id.testTotalMarks);
            textViewTestAttendance = itemView.findViewById(R.id.testAttendance);

            imageViewOnlineTest = itemView.findViewById(R.id.imageOnlineTest);
        }
    }
}
