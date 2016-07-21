package com.example.xiaohui.cs.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
public class WeaponFragment extends Fragment {

    public final static String CAT_SELECTED_KEY = "position";

    private ListView weaponList;

    OnWeaponSelectedListener mCallback;

    public interface OnWeaponSelectedListener {
        public void onWeaponSelected(String weapon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment_view, container, false);
        this.weaponList = (ListView) view.findViewById(R.id.listView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.open();
            List<String> quotes = databaseAccess.getGunsByType(args.getString(CAT_SELECTED_KEY));
            databaseAccess.close();

            weaponList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String weaponSelected = ((TextView)view).getText().toString();
                    //Deliver the category selected to the parent activity
                    mCallback.onWeaponSelected(weaponSelected);
                }
            });

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                    quotes);
            weaponList.setAdapter(adapter);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity a;

        if(context instanceof Activity) {
            a = (Activity) context;
            mCallback = (OnWeaponSelectedListener) a;
        }

    }

}
