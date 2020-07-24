package com.example.idlegame.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idlegame.data.Building;
import com.example.idlegame.data.Resource;
import com.example.idlegame.databinding.CardBuildingBinding;
import com.example.idlegame.utilities.BuildingViewModel;
import com.example.idlegame.utilities.NumberConverter;

import java.util.List;

public class BuildingCardAdapter extends RecyclerView.Adapter<BuildingCardAdapter.CardViewHolder> {

    private List<Building> mBuildingList;
    private BuildingViewModel viewModel;
    private Activity activity;
    private List<Resource> resources;

    public BuildingCardAdapter(Activity activity, List<Building> buildingList){
        mBuildingList = buildingList;
        this.activity = activity;
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(BuildingViewModel.class);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardBuildingBinding bind = CardBuildingBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        viewModel.getAllResources().observe((LifecycleOwner) activity, resourceList -> {
            if (!resourceList.isEmpty()) resources = resourceList;
        });
        return new CardViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Building currentBuilding = mBuildingList.get(position);
        holder.binding.textViewBuildingCount.setText(String.valueOf(currentBuilding.getBuildingCount()));
        holder.binding.textviewBuildingName.setText(currentBuilding.getBuildingName());
        holder.binding.textViewCostResource1.setText(NumberConverter.toString(currentBuilding.getBuildingCost()[0]));
        holder.binding.textViewCostResource2.setText(NumberConverter.toString(currentBuilding.getBuildingCost()[1]));
        holder.binding.textViewCostResource3.setText(NumberConverter.toString(currentBuilding.getBuildingCost()[2]));
        holder.binding.textViewProductionValue.setText(NumberConverter.toString(currentBuilding.getProductionValue()));

        holder.binding.buttonBuyBuilding.setOnClickListener(event -> {
            updateResourceList(currentBuilding);
            currentBuilding.buyBuilding();
            viewModel.update(currentBuilding);
        });


        viewModel.getAllResources().observe((LifecycleOwner) activity, resources -> {
            if (!resources.isEmpty()){
                if (buyable(currentBuilding, resources)) enableButton(holder.binding.buttonBuyBuilding);
                else disableButton(holder.binding.buttonBuyBuilding);
            }
        });


    }

    private void updateResourceList(Building building) {
        List<Resource> updatedList = resources;
        updatedList.get(0).value -= building.getBaseCostResource1();
        updatedList.get(1).value -= building.getBaseCostResource2();
        updatedList.get(2).value -= building.getBaseCostResource3();
        viewModel.updateResource(updatedList.get(0));
        viewModel.updateResource(updatedList.get(1));
        viewModel.updateResource(updatedList.get(2));
    }

    private boolean buyable(Building building, List<Resource> resourceList){
        for (int i = 0; i < resourceList.size(); i++){
            if (resourceList.get(i).value < building.getBuildingCost()[i]) return false;
        }
        return true;
    }

    private void disableButton(Button button){
        button.setClickable(false);
        button.setAlpha(0.3f);
    }

    private void enableButton(Button button){
        button.setClickable(true);
        button.setAlpha(1.0f);
    }

    @Override
    public int getItemCount() {
        return mBuildingList != null ? mBuildingList.size() : 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        CardBuildingBinding binding;

        public CardViewHolder(CardBuildingBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
