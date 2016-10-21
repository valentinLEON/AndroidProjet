package orlandini.jeu;


import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FatalityDialogFragment extends DialogFragment {

    private MediaPlayer mMediaPlayer;
    private MediaPlayer mMediaPlayer2;
    private MediaPlayer mMediaPlayer3;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.excellent);
        mMediaPlayer2 = MediaPlayer.create(this.getContext(), R.raw.shutdown_windows);
        mMediaPlayer3 = MediaPlayer.create(this.getContext(), R.raw.fatality);
        mMediaPlayer3.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_fatality)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mMediaPlayer.start();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mMediaPlayer2.start();
                        System.exit(0);
                    }
                })
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
