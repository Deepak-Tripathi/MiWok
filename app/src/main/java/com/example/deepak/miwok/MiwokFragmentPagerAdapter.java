package com.example.deepak.miwok;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;

public class MiwokFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mcontext;

    public MiwokFragmentPagerAdapter(Context context ,FragmentManager fm) {
        super(fm);
        mcontext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1) {
            return new FamilyFragment();
        } else if (position == 2) {
            return new ColorFragment();
        } else {
            return new PhraseFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return mcontext.getString(R.string.category_numbers);
        } else if (position == 1) {
            return mcontext.getString(R.string.category_family);

        } else if (position == 2) {
            return mcontext.getString(R.string.category_colors);

        } else {
            return mcontext.getString(R.string.category_phrases);

        }
    }
}
