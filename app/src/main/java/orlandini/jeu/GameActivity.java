package orlandini.jeu;

import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements ActionBar.TabListener{

    ViewPager mViewPager;
    GamePageAdapter mGamePagerAdapter;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        /*mGamePagerAdapter = new GamePageAdapter(getSupportFragmentManager());
        actionBar = getSupportActionBar();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mGamePagerAdapter);

        // On affiche les tabs dans l'actionbar
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
         */

    }

    //Toggle l'icone hamburger
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void onAction(int myNumber) {
        Toast.makeText(getApplicationContext(), String.valueOf(myNumber),Toast.LENGTH_LONG).show();

        ((GameFragment)mGamePagerAdapter.getGame()).changerVitesse(myNumber);
    }
}
