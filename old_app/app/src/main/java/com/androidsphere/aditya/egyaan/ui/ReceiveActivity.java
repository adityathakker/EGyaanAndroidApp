package com.androidsphere.aditya.egyaan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.androidsphere.aditya.egyaan.R;

import org.json.JSONException;
import org.json.JSONObject;


public class ReceiveActivity extends AppCompatActivity {
    private static final String TAG = ReceiveActivity.class.getSimpleName();
    TextView message;
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Intent intent = getIntent();

        message = (TextView) findViewById(R.id.message);
        String messageString = intent.getExtras().getString("message");
        Log.d(TAG, "onCreate: " + messageString);
        try {
            json = new JSONObject(messageString);
            String stime = json.getString("message");
            message.setText("Message: " + stime);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
