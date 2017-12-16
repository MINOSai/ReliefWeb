package com.minosai.reliefweb.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.minosai.reliefweb.R;
import com.minosai.reliefweb.api.ApiClient;
import com.minosai.reliefweb.api.ApiInterface;
import com.minosai.reliefweb.dataClasses.ReportInfo;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportInfoActivity extends AppCompatActivity {

    public static final String REPORT_ID = "com.minosai.reliefweb.Activities.REPORT_ID";

    private ReportInfo reportInfo = null;

    private TextView textInfoTitle;
    private TextView textInfoBody;
    private TextView textInfoSource;
    private TextView textInfoDate;
    private String reportId;
    private FloatingActionButton fab;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);

        initializeViews();
        fetchData();
    }

    private void initializeViews() {
        textInfoTitle = (TextView) findViewById(R.id.text_info_title);
        textInfoBody = (TextView) findViewById(R.id.text_info_body);
        textInfoSource = (TextView) findViewById(R.id.text_info_source);
        textInfoDate = (TextView) findViewById(R.id.text_info_date);

        Intent intent = getIntent();
        reportId = intent.getStringExtra(REPORT_ID);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reportInfo != null)
                    openUrl(reportInfo.getData().get(0).getFields().getOrigin());
            }
        });
    }

    private void fetchData() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportInfo> call = apiInterface.getReportInfo(reportId);
        call.enqueue(new Callback<ReportInfo>() {
            @Override
            public void onResponse(Call<ReportInfo> call, Response<ReportInfo> response) {
                if(response.isSuccessful()) {
                    reportInfo = response.body();
                    populateData();
                } else {
                    Toast.makeText(ReportInfoActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReportInfo> call, Throwable t) {
                Toast.makeText(ReportInfoActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateData() {
        if(reportInfo != null) {
            textInfoTitle.setText(reportInfo.getData().get(0).getFields().getTitle());
            textInfoBody.setText(reportInfo.getData().get(0).getFields().getBody());
            textInfoSource.setText(reportInfo.getData().get(0).getFields().getSource().get(0).getShortname());
            textInfoDate.setText(reportInfo.getData().get(0).getFields().getDate().getOriginal().substring(0, 10));
        }
    }

    public void openUrl(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.download:
                if(reportInfo != null)
                    openUrl(reportInfo.getData().get(0).getFields().getFile().get(0).getUrl());
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
