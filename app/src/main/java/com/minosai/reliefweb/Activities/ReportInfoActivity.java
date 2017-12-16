package com.minosai.reliefweb.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.minosai.reliefweb.R;
import com.minosai.reliefweb.api.ApiClient;
import com.minosai.reliefweb.api.ApiInterface;
import com.minosai.reliefweb.dataClasses.ReportInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportInfoActivity extends AppCompatActivity {

    public static final String REPORT_ID = "com.minosai.reliefweb.Activities.REPORT_ID";

    private ReportInfo reportInfo;
    private TextView textInfoTitle;
    private TextView textInfoBody;
    private String reportId;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);

        initializeViews();
        fetchData();
    }

    private void fetchData() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportInfo> call = apiInterface.getReportInfo(reportId);
        call.enqueue(new Callback<ReportInfo>() {
            @Override
            public void onResponse(Call<ReportInfo> call, Response<ReportInfo> response) {
                if(response.isSuccessful()) {
                    reportInfo = response.body();
                } else {
                    Toast.makeText(ReportInfoActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
//                List<ReportInfo.Data> data = reportInfo.getData();
//                textInfoTitle.setText(reportInfo.getData().get(0).getFields().get(0).getTitle());
//                textInfoBody.setText(reportInfo.getData().get(0).getFields().get(0).getBody());
            }

            @Override
            public void onFailure(Call<ReportInfo> call, Throwable t) {

            }
        });
    }

    private void initializeViews() {
        textInfoTitle = (TextView) findViewById(R.id.text_info_title);
        textInfoBody = (TextView) findViewById(R.id.text_info_body);

        Intent intent = getIntent();
        reportId = intent.getStringExtra(REPORT_ID);
    }
}
