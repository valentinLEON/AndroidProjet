package orlandini.jeu.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import orlandini.jeu.R;

/**
 * Dialg affiché lorsqu'une partie est terminée
 * L'utilisateur peut alors revenir au menu principal ou rejouer.
 *
 * @author Valentin Leon
 * @version 2016.0.39
 *
 * Date de création : 09/10/2016
 * Dernière modification : 29/10/2016
 */

public class HelpDialogFragment extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String texte = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        texte += getResources().getString(R.string.title_text_helper);
        texte += "\n\n" + getResources().getString(R.string.text_helper);
        builder.setTitle("Comment jouer ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setMessage(texte);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public HelpDialogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fatality_dialog, container, false);
    }

}
