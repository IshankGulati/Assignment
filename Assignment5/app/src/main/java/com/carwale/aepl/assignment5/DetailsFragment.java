package com.carwale.aepl.assignment5;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by aepl on 27/6/16.
 */
public class DetailsFragment extends Fragment {
    private int currentIndex;
    ArrayList<State> states = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        currentIndex = bundle.getInt("index");
        states = (ArrayList<State>) bundle.getSerializable("States");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

//        if(savedInstanceState != null){
//            currentIndex = savedInstanceState.getInt("ind");
//        }

        ScrollView view = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        view.addView(text);
        ArrayList<String> cities= new ArrayList<String>();
        for (State state : states){
            String name = "";
            for (City city: state.cities){
                name += city.name + " ";
            }
            cities.add(name);
        }
        text.setText(cities.get(currentIndex));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("ind", currentIndex);
    }

}
