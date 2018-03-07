package com.sterbon.tutex;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Utsav on 1/15/2018.
 */

public class PDFRead extends Activity {

    ArrayList<Long> list = new ArrayList<>();
    BroadcastReceiver onComplete = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfread);
        Bundle message=getIntent().getExtras();
        String get_name = (String) message.get("name");

        if(onComplete!=null) {
            File file = new File(String.valueOf(Environment.getExternalStorageDirectory() + get_name + ".pdf"));
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        }
            onComplete = new BroadcastReceiver() {

                public void onReceive(Context ctxt, Intent intent) {

                    long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                    list.remove(referenceId);

                    if (list.isEmpty()) {
                        Log.e("INSIDE", "" + referenceId);
                        NotificationCompat.Builder mBuilder =
                                null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            //Intent openIntent = mPDFView.;
                            Intent openIntent = new Intent(PDFRead.this, Main.class);
                            mBuilder = new NotificationCompat.Builder(PDFRead.this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("TutEx")
                                    .setContentText("Download completed")
                                    .addAction(new NotificationCompat.Action(0, "OPEN", PendingIntent.getActivity(PDFRead.this, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT)));
                        }

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(455, mBuilder.build());


                    }

                }
            };

            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(onComplete);
    }

}