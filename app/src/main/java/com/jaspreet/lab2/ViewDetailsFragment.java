package com.jaspreet.lab2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jaspreet.firebaselogin.R;

public class ViewDetailsFragment extends Fragment {

    TextView tv_fragment_text;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = DatabaseHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_details,container,false);

        tv_fragment_text = view.findViewById(R.id.tv_fragment_text);
        tv_fragment_text.setText("Fragment Showing All Food Orders");

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ElementListAdapter(databaseHelper.getUserData()));
        return view;
    }
}