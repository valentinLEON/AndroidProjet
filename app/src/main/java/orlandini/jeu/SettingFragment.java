package orlandini.jeu;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    View myView;
    int vitesse;

    public SettingFragment() {
        // Required empty public constructor
    }

    public interface OnActionListener {
        public void onAction(int myNumber);
    }

    OnActionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SettingFragment.OnActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnActionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_setting, container, false);

        final SeekBar mySeekBar = (SeekBar)myView.findViewById(R.id.seekBarVitesse);

        /* On get la valeur du numberpicker au changement */
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Toast.makeText(getContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
                vitesse = progress;
                // Toast.makeText(getContext(), Integer.toString(myNumberPicker.getValue()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mListener.onAction(vitesse);
            }
        });
        return myView;
    }

    public void changerVitesse(int myVitesse){
        GameCustomView monGame = new GameCustomView(getContext());
        monGame.setVitesse(myVitesse);
    }

}
