package com.example.xiaohui.cs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiaohui.cs.DatabaseAccess;
import com.example.xiaohui.cs.R;

import java.util.List;

/**
 * Created by Xiaohui on 7/20/2016.
 */
public class CategoryFragment extends Fragment {

    OnCategorySelectedListener mCallback;

    public interface OnCategorySelectedListener {
        public void onCategorySelected(String category);
    }

    private ListView categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment_view, container, false);
        this.categoryList = (ListView) view.findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.open();
        List<String> quotes = databaseAccess.getTypes();
        databaseAccess.close();

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categorySelected = ((TextView)view).getText().toString();
                //Deliver the category selected to the parent activity
                mCallback.onCategorySelected(categorySelected);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                quotes);
        categoryList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity a;

        if(context instanceof Activity) {
            a = (Activity) context;
            mCallback = (OnCategorySelectedListener) a;
        }

    }

}
