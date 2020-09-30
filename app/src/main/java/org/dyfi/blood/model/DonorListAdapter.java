package org.dyfi.blood.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.dyfi.blood.R;

import java.util.List;

public class DonorListAdapter extends RecyclerView.Adapter<DonorViewHolder>{
    private List<Donor> donorList;
    private LayoutInflater inflater;
    private Context mCtx;

    public DonorListAdapter(Context context, List<Donor> donorListUList) {
        this.donorList = donorListUList;
        this.mCtx = context;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout.row_item,parent,false);
        DonorViewHolder holder = new DonorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder donorViewHolder, int i) {
        donorViewHolder.setDetails(mCtx,donorList.get(i));
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }
}
