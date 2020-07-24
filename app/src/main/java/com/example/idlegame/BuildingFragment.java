package com.example.idlegame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.idlegame.adapter.BuildingCardAdapter;
import com.example.idlegame.databinding.FragmentBuildingBinding;
import com.example.idlegame.utilities.BuildingViewModel;

public class BuildingFragment extends Fragment {

    private BuildingCardAdapter mBuildingAdapter;
    private FragmentBuildingBinding mBinding;
    private BuildingViewModel buildingViewModel;

    public BuildingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentBuildingBinding.inflate(inflater, container, false);
        buildingViewModel = new ViewModelProvider(this).get(BuildingViewModel.class);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        buildingViewModel.getAllBuilding().observe(getActivity(), buildings -> {
            if (buildings != null){
                mBinding.recyclerView.setAdapter(new BuildingCardAdapter(getActivity(),buildings));
            }
        });


        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        buildingViewModel = null;
        mBinding = null;
        mBuildingAdapter = null;
        super.onDestroy();
    }


}