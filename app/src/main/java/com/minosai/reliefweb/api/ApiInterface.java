package com.minosai.reliefweb.api;

import com.minosai.reliefweb.dataClasses.ReportList;
import com.minosai.reliefweb.dataClasses.ReportInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by minos.ai on 16/12/17.
 */

public interface ApiInterface {

    @GET("reports?appname=apidoc&limit=10")
    public Call<ReportList> getReportList();

    @POST("reports/{reportId}")
    public Call<ReportInfo> getReportInfo(@Path("reportId") String reportid);
}
