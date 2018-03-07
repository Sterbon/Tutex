package com.sterbon.tutex;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Utsav on 8/26/2017.
 */

public class AlertDialogFrag extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context mcontext= getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext)
                                      .setTitle("Network Error")
                                      .setMessage("Please connect to a Network")
                                      .setPositiveButton("OPEN SETTINGS", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              Intent a = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                              a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                              startActivity(a);

                                          }
                                      })
                                      .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              Intent i = new Intent(Intent.ACTION_MAIN);
                                              i.addCategory(Intent.CATEGORY_HOME);
                                              startActivity(i);


                                          }
                                      });


        AlertDialog dialog = builder.create();
        return dialog;
    }


}
