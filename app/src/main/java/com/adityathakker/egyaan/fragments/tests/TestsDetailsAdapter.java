package com.adityathakker.egyaan.fragments.tests;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.models.TestsDetailsModel;

import java.util.List;

/**
 * Created by fireion on 30/11/17.
 */

public class TestsDetailsAdapter extends RecyclerView.Adapter<TestsDetailsAdapter.TestDetailsViewHolder> {
    private static final String TAG = TestsDetailsAdapter.class.getSimpleName();
    Context context;
    private List<TestsDetailsModel> testsDetailsModelList;

    public TestsDetailsAdapter(Context testDetailsActivity, List<TestsDetailsModel> testsDetailsModels) {
        this.context = testDetailsActivity;
        this.testsDetailsModelList = testsDetailsModels;
    }

    @Override
    public TestDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tests_details_activity, parent, false);
        return new TestsDetailsAdapter.TestDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestDetailsViewHolder holder, int position) {
        TestsDetailsModel testsDetailsModel = testsDetailsModelList.get(position);

        holder.testDetailsQuestion.setText(testsDetailsModel.getQuestion());
        holder.testDetailsAnswer.setText(testsDetailsModel.getAnswer());

        if (testsDetailsModel.getAnswer().equals(testsDetailsModel.getCorrectAns())) {
            holder.testDetailsResult.setVisibility(View.VISIBLE);
            holder.testDetailsResult.setText("Correct Answer");
        } else {
            holder.testDetailsCorrectAnswer.setVisibility(View.VISIBLE);
            holder.testDetailsResult.setVisibility(View.VISIBLE);
            holder.testDetailsCorrectAnswer.setText(testsDetailsModel.getCorrectAns());
            holder.testDetailsResult.setText("Wrong Answer");
            holder.testDetailsResult.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return testsDetailsModelList.size();
    }

    class TestDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView testDetailsQuestion, testDetailsAnswer, testDetailsCorrectAnswer, testDetailsResult;

        TestDetailsViewHolder(View itemView) {
            super(itemView);
            testDetailsQuestion = itemView.findViewById(R.id.testDetailsQuestions);
            testDetailsAnswer = itemView.findViewById(R.id.testDetailsAnswer);
            testDetailsCorrectAnswer = itemView.findViewById(R.id.testDetailsCorrectAnswer);
            testDetailsResult = itemView.findViewById(R.id.testDetailsResult);
        }
    }
}
