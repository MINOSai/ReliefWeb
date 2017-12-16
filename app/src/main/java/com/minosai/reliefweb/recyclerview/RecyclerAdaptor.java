package com.minosai.reliefweb.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minosai.reliefweb.R;
import com.minosai.reliefweb.dataClasses.ReportList;

import java.util.List;

/**
 * Created by minos.ai on 16/12/17.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> {

    private List<ReportList.Data> data;

    public RecyclerAdaptor(List<ReportList.Data> data) {
        this.data = data;
    }

    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdaptor.ViewHolder holder, int position) {
        holder.textReportTitle.setText(data.get(position).getFields().getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textReportTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            textReportTitle = (TextView) itemView.findViewById(R.id.text_report_title);
        }
    }
}
