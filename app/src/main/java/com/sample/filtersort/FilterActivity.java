package com.sample.filtersort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sample.filtersort.adapter.FilterAdapter;
import com.sample.filtersort.model.Filter;
import com.sample.filtersort.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterActivity extends AppCompatActivity {


    RecyclerView filterRV;
    RecyclerView filterValuesRV;
    FilterAdapter filterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initView();
        
    }

    private void initView() {

        filterRV = findViewById(R.id.filterRV);
        filterValuesRV = findViewById(R.id.filterValuesRV);
        filterRV.setLayoutManager(new LinearLayoutManager(this));
        filterValuesRV.setLayoutManager(new LinearLayoutManager(this));

        List<String> colors = Arrays.asList("Red", "Green", "Blue", "White");
        if (!Preferences.filters.containsKey(Filter.INDEX_COLOR)) {
            Preferences.filters.put(Filter.INDEX_COLOR, new Filter("Color", colors, new ArrayList()));
        }
        List<String> sizes = Arrays.asList("10", "12", "14", "16", "18", "20");
        if (!Preferences.filters.containsKey(Filter.INDEX_SIZE)) {
            Preferences.filters.put(Filter.INDEX_SIZE, new Filter("Size", sizes, new ArrayList()));
        }
        List<String> prices = Arrays.asList("0-100", "101-200", "201-300");
        if (!Preferences.filters.containsKey(Filter.INDEX_PRICE)) {
            Preferences.filters.put(Filter.INDEX_PRICE, new Filter("Price", prices, new ArrayList()));
        }

        filterAdapter = new FilterAdapter(getApplicationContext(), Preferences.filters, filterValuesRV);
        filterRV.setAdapter(filterAdapter);

        Button clearB = findViewById(R.id.clearB);
        clearB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.filters.get(Filter.INDEX_COLOR).setSelected(new ArrayList());
                Preferences.filters.get(Filter.INDEX_SIZE).setSelected(new ArrayList());
                Preferences.filters.get(Filter.INDEX_PRICE).setSelected(new ArrayList());
                startActivity(new Intent(FilterActivity.this, MainActivity.class));
            }
        });

        Button applyB = findViewById(R.id.applyB);
        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, MainActivity.class));
            }
        });
    }
    }
