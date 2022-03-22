package com.sample.filtersort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.sample.filtersort.adapter.ItemAdapter;
import com.sample.filtersort.model.Filter;
import com.sample.filtersort.model.Item;
import com.sample.filtersort.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> items = new ArrayList<Item>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        items.add(new Item("Item 1", "Red", 10, 100.00));
        items.add(new Item("Item 2", "Red", 12, 100.00));
        items.add(new Item("Item 3", "Red", 14, 100.00));
        items.add(new Item("Item 4", "Red", 16, 150.00));
        items.add(new Item("Item 5", "Red", 18, 170.00));
        items.add(new Item("Item 6", "Green", 20, 190.00));
        items.add(new Item("Item 7", "Green", 10, 100.00));
        items.add(new Item("Item 8", "Green", 12, 200.00));
        items.add(new Item("Item 9", "Green", 14, 210.00));
        items.add(new Item("Item 10", "Green", 16, 240.00));
        items.add(new Item("Item 11", "Blue", 18, 250.00));
        items.add(new Item("Item 12", "Blue", 20, 280.00));
        items.add(new Item("Item 13", "Blue", 10, 300.00));
        items.add(new Item("Item 14", "Blue", 12, 150.00));
        items.add(new Item("Item 15", "White", 10, 170.00));
        items.add(new Item("Item 16", "White", 16, 370.00));

        if (!Preferences.filters.isEmpty()) {
            ArrayList<Item> filteredItems = new ArrayList<Item>();
            List<String> colors = Preferences.filters.get(Filter.INDEX_COLOR).getSelected();
            List<String> sizes = Preferences.filters.get(Filter.INDEX_SIZE).getSelected();
            List<String> prices = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();
            for (Item item : items) {
                boolean colorMatched = true;
                if (colors.size() > 0 && !colors.contains(item.getColor())) {
                    colorMatched = false;
                }
                boolean sizeMatched = true;
                if (sizes.size() > 0 && !sizes.contains(item.getSize().toString())) {
                    sizeMatched = false;
                }
                boolean priceMatched = true;
                if (prices.size() > 0 && !priceContains(prices, item.getPrice())) {
                    priceMatched = false;
                }
                if (colorMatched && sizeMatched && priceMatched) {
                    filteredItems.add(item);
                }
            }
            items = filteredItems;
        }

        mAdapter = new ItemAdapter(getApplicationContext(), items);
        mRecyclerView.setAdapter(mAdapter);

        final LinearLayout sortContainerBackLL = findViewById(R.id.sortContainerBackLL);
        final LinearLayout sortContainerLL = findViewById(R.id.sortContainerLL);
        //sortContainerLL.setTranslationY(-sortContainerLL.getHeight());
        sortContainerBackLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortContainerLL.animate()
                        .translationY(-sortContainerLL.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                sortContainerLL.animate().setListener(null);
                                sortContainerBackLL.setVisibility(View.GONE);
                            }
                        });
            }
        });
        Button sortB = findViewById(R.id.sortB);
        sortB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortContainerBackLL.setVisibility(View.VISIBLE);
                sortContainerLL.animate().translationY(0);
            }
        });
        Button filterB = findViewById(R.id.filterB);
        filterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });

        RadioGroup sortRG = findViewById(R.id.sortRG);
        sortRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.sortByPriceAsc:
                        Item[] itemsArr2 = new Item[items.size()];
                        itemsArr2 = items.toArray(itemsArr2);
                        Arrays.sort(itemsArr2, Item.ascpriceComparator);
                        items = Arrays.asList(itemsArr2);
                        mAdapter = new ItemAdapter(getApplicationContext(), items);
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case R.id.sortByPriceDec:
                        Item[] itemsArr3 = new Item[items.size()];
                        itemsArr3 = items.toArray(itemsArr3);
                        Arrays.sort(itemsArr3, Item.despriceComparator);
                        items = Arrays.asList(itemsArr3);
                        mAdapter = new ItemAdapter(getApplicationContext(), items);
                        mRecyclerView.setAdapter(mAdapter);
                        break;

                }
                sortContainerLL.animate()
                        .translationY(-sortContainerLL.getHeight())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                sortContainerLL.animate().setListener(null);
                                sortContainerBackLL.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
    private boolean priceContains(List<String> prices, Double price) {
        boolean flag = false;
        for (String p : prices) {
            String tmpPrices[] = p.split("-");
            if (price >= Double.valueOf(tmpPrices[0]) && price <= Double.valueOf(tmpPrices[1])) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}