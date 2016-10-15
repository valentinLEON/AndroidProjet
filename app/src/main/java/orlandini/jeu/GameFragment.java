package orlandini.jeu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class GameFragment extends Fragment {

    private int vitesse = 1000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent game = new Intent(getContext(), GameCustomView.class);
        game.putExtra("vitesse", vitesse);
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    public void changerVitesse(int myVitesse){
        //TextView myTextView = (TextView) myView.findViewById(R.id.);
        //myTextView.setText(Integer.toString(myNumberPicker));

        //GameCustomView gameView = new GameCustomView(this.getContext());
        //gameView.changeTextNumber(myVitesse);

        //((GameCustomView)getView()).setVitesse(myVitesse);

        Intent game = new Intent(getContext(), GameCustomView.class);
        game.putExtra("vitesse", myVitesse);
    }
}