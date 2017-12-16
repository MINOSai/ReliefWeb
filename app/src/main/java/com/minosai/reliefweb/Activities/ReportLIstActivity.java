package com.minosai.reliefweb.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.minosai.reliefweb.R;
import com.minosai.reliefweb.api.ApiClient;
import com.minosai.reliefweb.api.ApiInterface;
import com.minosai.reliefweb.dataClasses.ReportInfo;
import com.minosai.reliefweb.dataClasses.ReportList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportLIstActivity extends AppCompatActivity {

    RecyclerView recyclerReportList;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        recyclerReportList = (RecyclerView) findViewById(R.id.recycler_repost_list);

        fetchReportList();


    }

    public void fetchReportList() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportList> call = apiInterface.getReportList();
        call.enqueue(new Callback<ReportList>() {
            @Override
            public void onResponse(Call<ReportList> call, Response<ReportList> response) {
                Toast.makeText(ReportLIstActivity.this, "Success", Toast.LENGTH_SHORT).show();
                ReportList reportList = response.body();
                Log.i("api-response",reportList.data.get(0).getFields().getTitle());
            }

            @Override
            public void onFailure(Call<ReportList> call, Throwable t) {
                Toast.makeText(ReportLIstActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
