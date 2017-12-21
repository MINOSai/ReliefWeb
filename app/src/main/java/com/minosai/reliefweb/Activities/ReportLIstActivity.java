package com.minosai.reliefweb.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minosai.reliefweb.R;
import com.minosai.reliefweb.api.ApiClient;
import com.minosai.reliefweb.api.ApiInterface;
import com.minosai.reliefweb.dataClasses.ReportList;
import com.minosai.reliefweb.recyclerview.RecyclerAdaptor;
import com.minosai.reliefweb.recyclerview.RecyclerItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportLIstActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;

    RecyclerView recyclerReportList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdaptor recyclerAdaptor;
    private SwipeRefreshLayout refreshReportList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    ApiInterface apiInterface;

    ReportList reportList;
    List<ReportList.Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        initializeVars();

        attachMethods();

    }

    public void initializeVars(){
        recyclerReportList = (RecyclerView) findViewById(R.id.recycler_repost_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerReportList.setLayoutManager(layoutManager);

        refreshReportList = (SwipeRefreshLayout) findViewById(R.id.refresh_report);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("report_list");
    }

    private void attachMethods() {
        recyclerReportList.addOnItemTouchListener(
                new RecyclerItemClickListener(ReportLIstActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ReportLIstActivity.this, ReportInfoActivity.class);
                        intent.putExtra(ReportInfoActivity.REPORT_ID, data.get(position).getId());
                        startActivity(intent);
                    }
                })
        );

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null){
                    onSignedInInit();
                }else{
                    onSignedOutCleaner();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,
                                            AuthUI.GOOGLE_PROVIDER)
                            .setTheme(R.style.LoginTheme)
                            .build(),
                            RC_SIGN_IN
                    );
                }

            }
        };

        refreshReportList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchReportList();
            }
        });
    }

    public void fetchReportList() {
        refreshReportList.setRefreshing(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportList> call = apiInterface.getReportList();
        call.enqueue(new Callback<ReportList>() {
            @Override
            public void onResponse(Call<ReportList> call, Response<ReportList> response) {

                if(response.isSuccessful()) {
                    reportList = response.body();
                    databaseReference.child("response").setValue(reportList);
                } else {
                    Toast.makeText(ReportLIstActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReportList> call, Throwable t) {
                Toast.makeText(ReportLIstActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                refreshReportList.setRefreshing(false);
            }
        });

        if(refreshReportList.isRefreshing()) {
            refreshReportList.setRefreshing(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
        detachDatabaseReadListener();
        reportList = null;
    }

    @Override
    protected void onResume(){
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void onSignedInInit(){
        fetchReportList();
        attachDatabaseReadListener();
    }

    private void onSignedOutCleaner(){
        detachDatabaseReadListener();
    }

    private void attachDatabaseReadListener(){
        if(childEventListener==null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    ReportList reportListFirebase = dataSnapshot.getValue(ReportList.class);
                    data = reportListFirebase.getData();
                    recyclerAdaptor = new RecyclerAdaptor(data);
                    recyclerReportList.setAdapter(recyclerAdaptor);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    ReportList reportListFirebase = dataSnapshot.getValue(ReportList.class);
                    data = reportListFirebase.getData();
                    recyclerAdaptor = new RecyclerAdaptor(data);
                    recyclerReportList.setAdapter(recyclerAdaptor);

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            databaseReference.addChildEventListener(childEventListener);
        }
    }

    private void detachDatabaseReadListener(){
        if(childEventListener!=null) {
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signout:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
