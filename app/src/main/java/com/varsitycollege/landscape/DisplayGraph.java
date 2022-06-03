package com.varsitycollege.landscape;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayGraph extends Fragment {
    private PieChart graph;
    private ArrayList<CategoryInfo> list = new ArrayList<>();

    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_display_graph, container, false);

        graph = rootView.findViewById(R.id.pieChart);

        setupPieChart();
        loadPieChartData();

        return rootView;
    }

    //https://www.youtube.com/watch?v=S3zqxVoIUig - ref
    private void setupPieChart() {
       graph.setDrawHoleEnabled(true);
        graph.setUsePercentValues(true);
        graph.setEntryLabelTextSize(12);
        graph.setEntryLabelColor(Color.BLACK);
        graph.setCenterText("Category Percentages");
        graph.setCenterTextSize(24);
        graph.getDescription().setEnabled(false);

        Legend l = graph.getLegend();
        l.setTextSize(12);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList <PieEntry> entry = new ArrayList();

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseDatabase db = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference root  = db.getReference().child(userId).child("Categories");
        DatabaseReference root2  = db.getReference().child(userId).child("Listings");
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        CategoryInfo info = new CategoryInfo();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoryInfo cat = dataSnapshot.getValue(CategoryInfo.class);
                    String name = cat.getCategoryName();

                    /*root2.orderByChild("category").equalTo(name).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            cat.setCatCount((int) snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                    //must show the number of categories in each item on chart (cat.getCatCount())
                    float percent = Float.parseFloat((cat.getCategoryGoal()));
                    entry.add(new PieEntry(percent, name));
                }

                ArrayList<Integer> colors = new ArrayList<>();
                for (int color : ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color);
                }

                for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color);
                }

                PieDataSet dataSet = new PieDataSet(entry, "Categories");
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter(graph));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                graph.setData(data);
                graph.invalidate();

                graph.animateY(1400, Easing.EaseInOutQuad);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
