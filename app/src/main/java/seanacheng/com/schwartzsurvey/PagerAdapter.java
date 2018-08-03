package seanacheng.com.schwartzsurvey;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    Fragment fragment;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                fragment = new WelcomeFragment();
                break;
            case 1:
                fragment = new InfoFragment();
                break;
            case 2:
                fragment = new AboutFragment();
                break;
            case 3:
                fragment = new InstructionsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
