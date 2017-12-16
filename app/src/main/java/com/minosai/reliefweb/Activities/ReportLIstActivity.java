package com.minosai.reliefweb.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.minosai.reliefweb.R;
import com.minosai.reliefweb.api.ApiClient;
import com.minosai.reliefweb.api.ApiInterface;
import com.minosai.reliefweb.dataClasses.RecyclerAdaptor;
import com.minosai.reliefweb.dataClasses.ReportInfo;
import com.minosai.reliefweb.dataClasses.ReportList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportLIstActivity extends AppCompatActivity {

    RecyclerView recyclerReportList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdaptor recyclerAdaptor;
    private SwipeRefreshLayout refreshReportList;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        initializeVars();

        fetchReportList();


    }

    public void initializeVars(){
        recyclerReportList = (RecyclerView) findViewById(R.id.recycler_repost_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerReportList.setLayoutManager(layoutManager);

        refreshReportList = (SwipeRefreshLayout) findViewById(R.id.refresh_report);
        refreshReportList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchReportList();
            }
        });
    }

    public void fetchReportList() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportList> call = apiInterface.getReportList();
        call.enqueue(new Callback<ReportList>() {
            @Override
            public void onResponse(Call<ReportList> call, Response<ReportList> response) {
                refreshReportList.setRefreshing(true);
                ReportList reportList = response.body();
                Log.i("api-response",reportList.data.get(0).getFields().getTitle());
                recyclerAdaptor = new RecyclerAdaptor(reportList.getData());
                recyclerReportList.setAdapter(recyclerAdaptor);
                refreshReportList.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ReportList> call, Throwable t) {
                Toast.makeText(ReportLIstActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                refreshReportList.setRefreshing(false);
            }
        });
    }
}
