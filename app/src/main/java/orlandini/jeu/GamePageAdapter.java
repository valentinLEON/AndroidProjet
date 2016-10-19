package orlandini.jeu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nicolas ORLANDINI on 10/10/2016.
 */

public class GamePageAdapter extends FragmentStatePagerAdapter  {
    //private Fragment gamesettings;
    private Fragment game;

    public Fragment getGame() {
        return game;
    }

    /*public Fragment getGamesettings() {
        return gamesettings;
    }*/

    public GamePageAdapter(FragmentManager fm)
    {
        super(fm);

        //this.gamesettings = new SettingFragment();
        this.game = new GameFragment();
    }

    //On récupère la vue d'un des fragments
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i){
            case 0 :
                //fragment = getGamesettings();
                break;
            case 1 :
                fragment = getGame();
                break;
        }

        return fragment;
    }

    //Retourne le nombre d'onglet possible dans le tab
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String frag = null;

        switch (position){
            case 0:
                frag = "Game settings";
                break;
            case 1:
                frag = "Jeu";
                break;
        }
        return frag;
    }
}
