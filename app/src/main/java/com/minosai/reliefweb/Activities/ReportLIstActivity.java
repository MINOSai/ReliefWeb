package com.minosai.reliefweb.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.minosai.reliefweb.R;

public class ReportLIstActivity extends AppCompatActivity {

    RecyclerView recyclerReportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        recyclerReportList = (RecyclerView) findViewById(R.id.recycler_repost_list);
    }
}