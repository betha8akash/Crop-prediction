package com.example.farmwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HarvestDetailsAdapter extends RecyclerView.Adapter<HarvestDetailsAdapter.HarvestDetailViewHolder> {
    private List<HarvestDetail> harvestDetailsList;

    public HarvestDetailsAdapter(List<HarvestDetail> harvestDetailsList) {
        this.harvestDetailsList = harvestDetailsList;
    }

    @Override
    public HarvestDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_harvest_detail, parent, false);
        return new HarvestDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HarvestDetailViewHolder holder, int position) {
        HarvestDetail harvestDetail = harvestDetailsList.get(position);
        holder.monthAndYearTextView.setText(harvestDetail.getMonthAndYear());
        holder.cropNameTextView.setText(harvestDetail.getCropName());
        holder.approxProductionTextView.setText(harvestDetail.getApproxProduction());
        holder.regionTextView.setText(harvestDetail.getRegion());
        holder.areaUnderCultivationTextView.setText(harvestDetail.getAreaUnderCultivation());
    }

    @Override
    public int getItemCount() {
        return harvestDetailsList.size();
    }

    public static class HarvestDetailViewHolder extends RecyclerView.ViewHolder {
        public TextView monthAndYearTextView;
        public TextView cropNameTextView;
        public TextView approxProductionTextView;
        public TextView regionTextView;
        public TextView areaUnderCultivationTextView;

        public HarvestDetailViewHolder(View itemView) {
            super(itemView);
            monthAndYearTextView = itemView.findViewById(R.id.month_and_year_text_view);
            cropNameTextView = itemView.findViewById(R.id.crop_name_text_view);
            approxProductionTextView = itemView.findViewById(R.id.approx_production_text_view);
            regionTextView = itemView.findViewById(R.id.region_text_view);
            areaUnderCultivationTextView = itemView.findViewById(R.id.area_under_cultivation_text_view);
        }
    }
}