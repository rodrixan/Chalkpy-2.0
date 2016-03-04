package es.uam.eps.tfg.cas.android.examples.criminalintent.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

import es.uam.eps.tfg.cas.android.examples.criminalintent.R;
import es.uam.eps.tfg.cas.android.examples.criminalintent.controller.fragments.CrimeFragment;
import es.uam.eps.tfg.cas.android.examples.criminalintent.model.Crime;
import es.uam.eps.tfg.cas.android.examples.criminalintent.model.services.CrimeLabImp;
import es.uam.eps.tfg.cas.android.examples.criminalintent.utils.Utils;


public class CrimePagerActivity extends FragmentActivity {

    private static final String EXTRA_CRIME_ID = Utils.APP_PATH + ".crime_id";
    private static final String EXTRA_CRIME_SHOWN = Utils.APP_PATH + ".crime_shown";
    private ViewPager mViewPager;
    private List<Crime> mCrimeList;


    public static Intent newIntent(final Context packageContext, final UUID crimeID) {
        final Intent i = new Intent(packageContext, CrimePagerActivity.class);
        i.putExtra(EXTRA_CRIME_ID, crimeID);
        return i;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        final UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        wireComponents();
        mCrimeList = CrimeLabImp.getCrimeLab(this).getCrimes();
        setAdapter();

        setCurrentItem(crimeId);
    }

    private void wireComponents() {
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
    }

    private void setAdapter() {
        final FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(final int position) {
                final Crime c = mCrimeList.get(position);
                return CrimeFragment.newInstance(c.getId());
            }

            @Override
            public int getCount() {
                return mCrimeList.size();
            }
        });
    }

    private void setCurrentItem(final UUID crimeId) {
        for (int i = 0; i < mCrimeList.size(); i++) {
            if (mCrimeList.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}