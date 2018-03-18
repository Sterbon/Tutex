package com.sterbon.tutex;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Utsav on 3/8/2018.
 */

public class DownloadCompleteDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context mContext = getActivity();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext)
                .setTitle("FILE DOWNLOADED")
                .setPositiveButton("OPEN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);

                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        startActivity(i);


                    }
                });


        AlertDialog dialog = mBuilder.create();
        return dialog;
    }


}
