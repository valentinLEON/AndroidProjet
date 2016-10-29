package orlandini.jeu;


import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;

/**
 * Dialg affiché lorsqu'une partie est terminée
 * L'utilisateur peut alors revenir au menu principal ou rejouer.
 *
 * @author Nicolas Orlandini
 * @version 2016.0.34
 *
 * Date de création : 09/10/2016
 * Dernière modification : 24/10/2016
 */

public class FatalityDialogFragment extends DialogFragment {

    private MediaPlayer mMediaPlayerRejouer;
    private MediaPlayer mMediaPlayerFatality;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        mMediaPlayerFatality = MediaPlayer.create(this.getContext(), R.raw.fatality);
        mMediaPlayerFatality.start();
        mMediaPlayerRejouer = MediaPlayer.create(this.getContext(), R.raw.excellent);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_fatality)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mMediaPlayerRejouer.start();
                    }
                })
                //TODO: voir pourquoi ça kill l'application
                /*.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                })*/
        .setMessage("Votre score : " + GameCustomView.getScore());
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public FatalityDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fatality_dialog, container, false);
    }

}
