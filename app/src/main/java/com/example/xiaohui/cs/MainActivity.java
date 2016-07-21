package com.example.xiaohui.cs;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.xiaohui.cs.fragments.CategoryFragment;
import com.example.xiaohui.cs.fragments.SkinFragment;
import com.example.xiaohui.cs.fragments.SkinInfoFragment;
import com.example.xiaohui.cs.fragments.WeaponFragment;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategorySelectedListener
        , WeaponFragment.OnWeaponSelectedListener
        , SkinFragment.OnSkinSelectedListener {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] drawItems = {"Browse", "Currently Tracking"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_category) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the main activity layout
            CategoryFragment categoryFragment = new CategoryFragment();

            // Add the fragment to the 'fragment_category' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_category, categoryFragment).commit();
        }


        //instantiate the draw
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, drawItems));

    }

    @Override
    public void onCategorySelected(String category) {
        WeaponFragment weaponFragment = new WeaponFragment();
        Bundle args = new Bundle();
        args.putString(WeaponFragment.CAT_SELECTED_KEY, category);
        weaponFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_category, weaponFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onWeaponSelected(String weapon) {
        SkinFragment skinFragment = new SkinFragment();
        Bundle args = new Bundle();
        args.putString(SkinFragment.WEAP_SELECTED_KEY, weapon);
        skinFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_category, skinFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onSkinSelected(String skinSelected, String weaponSelected){
        SkinInfoFragment skinInfoFragment = new SkinInfoFragment();
        Bundle args = new Bundle();
        args.putString(SkinInfoFragment.WEAP_SELECTED_KEY, weaponSelected);
        args.putString(SkinInfoFragment.SKIN_SELECTED_KEY, skinSelected);

        skinInfoFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_category, skinInfoFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
