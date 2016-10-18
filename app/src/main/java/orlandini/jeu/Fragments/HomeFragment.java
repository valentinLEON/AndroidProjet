package orlandini.jeu.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import orlandini.jeu.FatalityDialogFragment;
import orlandini.jeu.GameActivity;
import orlandini.jeu.MainActivity;
import orlandini.jeu.R;
import orlandini.jeu.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        Button settings = (Button) myView.findViewById(R.id.btnSettings);
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button tryAgain = (Button) myView.findViewById(R.id.btn_TryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);

                FragmentManager fm = getFragmentManager();
                FatalityDialogFragment newFragment = new FatalityDialogFragment();
                newFragment.show(fm, "Fragment_fatality_dialog");
            }
        });

        return myView;
    }

}
