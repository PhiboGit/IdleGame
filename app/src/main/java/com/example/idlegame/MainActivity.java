package com.example.idlegame;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.idlegame.adapter.NavigationFragmentAdapter;
import com.example.idlegame.data.Resource;
import com.example.idlegame.utilities.BuildingViewModel;
import com.example.idlegame.utilities.DataCollector;
import com.example.idlegame.utilities.PeriodicTask;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements DataCollector {

    NavigationFragmentAdapter mNavAdapter;
    ViewPager2 mViewPager;
    TabLayout mTabLayout;
    BuildingViewModel viewModel;
    boolean taskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(BuildingViewModel.class);

        //Setup TabLayout with Tabs and Drag-Click-Listener
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.viewpager);
        mNavAdapter = new NavigationFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        addFragmentToTab(new BuildingFragment(), "Buildings");
        addFragmentToTab(new UpgradesFragment(), "Upgrades");
        mViewPager.setAdapter(mNavAdapter);
        setListenerOnPageDrag();
        setListenerOnTabSelected();

        //setup TextViews
        TextView textViewResource1 = findViewById(R.id.textViewCostResource1);
        TextView textViewResource2 = findViewById(R.id.textViewCostResource2);
        TextView textViewResource3 = findViewById(R.id.textViewCostResource3);


        viewModel.getAllResources().observe(this, resources -> {
            if (!resources.isEmpty()){
                textViewResource1.setText(resources.get(0).getValue());
                textViewResource2.setText(resources.get(1).getValue());
                textViewResource3.setText(resources.get(2).getValue());
                startPeriodicTask(resources);
            }
        });
    }



    private void startPeriodicTask(List<Resource> resources) {
        if (!taskRunning){
            taskRunning = true;
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleAtFixedRate(new PeriodicTask(MainActivity.this, resources),0,1000, TimeUnit.MILLISECONDS);
        }
    }

    private void setListenerOnPageDrag(){
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
    }

    private void setListenerOnTabSelected(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void addFragmentToTab(Fragment fragment, String fragmentName){
        mTabLayout.addTab(mTabLayout.newTab().setText(fragmentName));
        mNavAdapter.addFragment(fragment);
    }

    @Override
    public void resources(List<Resource> resourceList) {
        viewModel.updateResource(resourceList.get(0));
        viewModel.updateResource(resourceList.get(1));
        viewModel.updateResource(resourceList.get(2));
    }
}