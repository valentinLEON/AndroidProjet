package orlandini.jeu;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements ActionBar.TabListener, SettingFragment.OnActionListener {

    ViewPager mViewPager;
    GamePageAdapter mGamePagerAdapter;
    int mData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGamePagerAdapter = new GamePageAdapter(getSupportFragmentManager());
        final ActionBar actionBar = getSupportActionBar();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mGamePagerAdapter);

         /* On affiche les tabs dans l'actionbar */
        actionBar.addTab(actionBar.newTab().setText("Settings").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Jeu").setTabListener(this));

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Un listener quand on change de page
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

    }

    /* Navigation dans les tabs */
    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onAction(int myNumber) {
        //mData = myNumber;
        //((SettingFragment)mGamePagerAdapter.getItem(1)).changerVitesse(mData);
        Toast.makeText(getApplicationContext(), String.valueOf(myNumber),Toast.LENGTH_LONG).show();
    }

}
