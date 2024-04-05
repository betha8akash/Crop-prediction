package com.example.farmwise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.Recyclervh> {
    List<ViewModel> mviewlist;
    ViewModel viewModel;
    private Context mContext;

    public ViewAdapter(Context c, List<ViewModel> viewModels) {
        this.mContext = c;
        this.mviewlist = viewModels;
    }

    @NonNull
    public Recyclervh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View v = LayoutInflater.from(mContext).inflate(R.layout.viewrecycler, parent, false);
        return new Recyclervh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclervh holder, int position) {
        viewModel = mviewlist.get(position);
        holder.mPeriod.setText("Month & Year : "+ viewModel.getPeriod());
        holder.mCrop_type.setText("Crop Type : "+ viewModel.getCrop_type());
        holder.mProduction.setText("Approx Production : "+ viewModel.getProduction());
        holder.mRegion.setText("Region : "+ viewModel.getRegion());
        holder.mArea.setText("Cultivated Area : "+ viewModel.getArea());

        holder.mPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("Delete Item")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // User clicked OK, so remove the corresponding ViewModel object from the data list
                                    mviewlist.remove(position);
                                    // Notify the RecyclerView adapter about the change
                                    notifyItemRemoved(position);
                                }
                            })
                            .setNegativeButton("No", null)
                            .create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mviewlist.size();
    }

    public class Recyclervh extends RecyclerView.ViewHolder {
        TextView mPeriod, mCrop_type, mProduction, mRegion, mArea;
        public Recyclervh(View itemview) {
            super(itemview);
            mPeriod = (TextView) itemview.findViewById(R.id.pid);
            mCrop_type = (TextView) itemview.findViewById(R.id.cid);
            mProduction = (TextView) itemview.findViewById(R.id.prid);
            mRegion = (TextView) itemview.findViewById(R.id.rid);
            mArea = (TextView) itemview.findViewById(R.id.aid);
        }
    }
}