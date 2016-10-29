package orlandini.jeu;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import orlandini.jeu.Fragments.AProposFragment;
import orlandini.jeu.Fragments.HelpFragment;
import orlandini.jeu.Fragments.HomeFragment;
import orlandini.jeu.Fragments.LeaderboardFragment;

/**
 * Activité principale, contient le navigation drawer
 * ainsi que le fragment sélectionné par l'utilisateur depuis
 * le navigation drawer.
 *
 * @author Nicolas Orlandini
 * @version 2016.0.37
 *
 * Date de création : 09/10/2016
 * Dernière modification : 27/10/2016
 */

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private LinearLayout navHeader;
    private ActionBarDrawerToggle drawerToggle;
    public static ScoreDataBase scoreDataBase;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        scoreDataBase = new ScoreDataBase(getBaseContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(changerCouleur());
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        appliquerThemeNavigationDrawer();
        setupDrawerContent(nvDrawer);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(changerCouleur()));
        }
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        //TextView myAwesomeTextView = (TextView)findViewById(R.id.nom_joueur);
        //myAwesomeTextView.setText(prefs.getString("id_joueur", "@string/pref_title_display_name"));

        Toast.makeText(this, prefs.getString("id_joueur", "NIL"), Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_Content, new HomeFragment()).commit();
    }

    //Toggle l'icone hamburger
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        navHeader = (LinearLayout) findViewById(R.id.nav_header);
                        navHeader.setBackgroundColor(changerCouleur());
                        TextView myAwesomeTextView = (TextView)findViewById(R.id.nom_joueur);
                        myAwesomeTextView.setText(prefs.getString("id_joueur", "@string/pref_title_display_name"));

                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    /**
     * Spécifie les actions a effectuer suivant l'item sélectionné par l'utilisateur dans le navigation drawer.
     * @param menuItem item sélectionné
     */
    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;
        Class fragmentClass;

        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_leaderboard:
                fragmentClass = LeaderboardFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingFragment.class;
                break;
            case R.id.nav_a_propos:
                fragmentClass = AProposFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Remplacer le fragment
        transaction.replace(R.id.main_Content, fragment).commit();

        // Surligner l'item sélectionné
        menuItem.setChecked(true);
        // Applique le titre de l'actionBar
        setTitle(menuItem.getTitle());
        //setTitle(Html.fromHtml("<font color='"+changerCouleur()+"'>"+menuItem.getTitle()+"</font>"));

        mDrawer.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Défini les actions a exécuter suivant l'item de l'action bar sélectionné par l'utilisateur.
     * @param item item sélectionné par l'utilisateur
     * @return booléen
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.help:
                Class fragmentClass =  HelpFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_Content, fragment).commit();
                break;

            default:
                break;
        }
        return  super.onOptionsItemSelected(item);
    }


    /**
     * Méthode exécutée lorsque l'utilisateur sélectionne l'image du navigation drawer header
     * @param v vue
     */
    public void easterEgg(View v) {
        Toast.makeText(getApplicationContext(), "Bonjour, je suis un easter egg", Toast.LENGTH_LONG).show();
    }


    private int changerCouleur() {
        // Récupération des préférences
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String color = prefs.getString("pref_theme", "#00AFF0");
        return Color.parseColor(color);
    }

    private void appliquerThemeNavigationDrawer(){
        int[][] state = new int[][] {
                new int [] {android.R.attr.state_pressed},
                new int [] {android.R.attr.state_focused},
                new int [] {android.R.attr.state_checked},
                new int [] {}
        };

        int[] color = new int[] {
                changerCouleur(),
                changerCouleur(),
                changerCouleur(),
                Color.DKGRAY
        };

        ColorStateList colorStateList = new ColorStateList(state, color);
        nvDrawer.setItemTextColor(colorStateList);
        nvDrawer.setItemIconTintList(colorStateList);
    }
}
