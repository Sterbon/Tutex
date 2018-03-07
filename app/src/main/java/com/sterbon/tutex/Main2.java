package com.sterbon.tutex;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Utsav on 12/30/2017.
 */

public class Main2 extends AppCompatActivity {

    private Files files;
    private TextView F_name;

    HashMap<String, String> file_id;
    ListAdapter file_adapter;
    String getdl;
    Toolbar mToolbar;
    JSONArray FilesjsonArray;
    ArrayList<HashMap<String, String>> files_List;
    ListView lv;
    Files info = null;
    String list_folder;
    String file_idl;

    private static final String[] REQUIRED_PERMISSIONS =
            new String[] {
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };

    private static final int REQUEST_CODE_REQUIRED_PERMISSIONS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        F_name = (TextView) findViewById(R.id.out);
        files_List = new ArrayList<>();

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(mToolbar);

        Bundle message = getIntent().getExtras();
        String folder_id = (String) message.get("message");

        lv = (ListView) findViewById(R.id.recycler);
        getfile_fol(folder_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onStart() {
        super.onStart();

        if (!hasPermissions(this, REQUIRED_PERMISSIONS)) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_REQUIRED_PERMISSIONS);
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public void getfile_fol(String folder_id) {
        if (connection()) {
            list_folder = "https://api.openload.co/1/file/listfolder?login=9e3a3d716e964a44&key=Mp-fKIzt&folder=" + folder_id;

            OkHttpClient mClient = new OkHttpClient();
            Request mrequest = new Request.Builder()
                    .url(list_folder)
                    .build();

            Call mCall = mClient.newCall(mrequest);
            mCall.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String data = response.body().string();
                    try {
                        if (response.isSuccessful()) {

                            {
                                files = getFileContent(data);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateFileDisplay();

                                    }
                                });
                            }


                        } else {
                            alert();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            alert();
        }
    }

    private Files getFileContent(String data) throws JSONException {
        {
            JSONObject filesn = new JSONObject(data);
            JSONObject obj_name = filesn.getJSONObject("result");
            FilesjsonArray = obj_name.optJSONArray("files");
            info = new Files();

            Log.v("Files JSON ARRAY length", FilesjsonArray.length() + "");
            for (int i = 0; i < FilesjsonArray.length(); i++) {
                JSONObject jsonObject = FilesjsonArray.getJSONObject(i);
                info.setName(jsonObject.getString("name"));
                info.setContent(jsonObject.getString("content_type"));
                info.setLink(jsonObject.getString("link"));
                info.setDown_count(jsonObject.getInt("download_count"));
                info.setFile_id(jsonObject.getString("linkextid"));

                HashMap<String, String> file_info = new HashMap<>();

                file_info.put("name", info.getName());
                file_info.put("content_type", info.getContent());
                file_info.put("download_count", String.valueOf(info.getDown_count()));
                file_info.put("file_id",info.getFile_id());

                files_List.add(file_info);
            }
            return info;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater minfater = getMenuInflater();
        minfater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.backbt:
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
        }
        return true;
    }



    private void alert() {
        AlertDialogFrag dialog = new AlertDialogFrag();
        dialog.show(getFragmentManager(), "Error");
    }


    private boolean connection() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            return true;
        } else
            return false;
    }

    private void updateFileDisplay() {

        file_adapter = new SimpleAdapter(
                Main2.this, files_List,
                R.layout.filelist_item, new String[]{"name", "content_type",
                "download_count"}, new int[]{R.id.name,
                R.id.content_type, R.id.download_count});
        lv.setAdapter(file_adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                file_id= files_List.get(position);
                file_idl=file_id.get("file_id");
              //  Toast.makeText(getBaseContext(),file_idl,Toast.LENGTH_SHORT).show();
                String getfile = "https://api.openload.co/1/file/dlticket?file=" + file_idl + "&login=9e3a3d716e964a44&key=Mp-fKIzt";

                OkHttpClient mClient = new OkHttpClient();
                Request mrequest = new Request.Builder()
                        .url(getfile)
                        .build();

                Call mCall = mClient.newCall(mrequest);
                mCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        Toast.makeText(Main2.this, "SERVER ERROR", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String data = response.body().string();
                        try {
                            if (response.isSuccessful()) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run(){
                                        try {
                                            updateCaptchaDisplay(data);
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    public void updateCaptchaDisplay(String captcha_s) throws JSONException {

        JSONObject captchares =new JSONObject(captcha_s);
        JSONObject captcharesJSONObject = captchares.getJSONObject("result");
        Uri captcha_url= Uri.parse(captcharesJSONObject.getString("captcha_url"));
        final String ticket = captcharesJSONObject.getString("ticket");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2.this);
        View mView = getLayoutInflater().inflate(R.layout.captcha, null);
        final EditText captcha = (EditText) mView.findViewById(R.id.captcha);
        WebView captchaView = (WebView) mView.findViewById(R.id.captchaView);
        captchaView.loadUrl(String.valueOf(captcha_url));
        Button proceed = (Button) mView.findViewById(R.id.proceed);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getdl="https://api.openload.co/1/file/dl?file="+file_idl+"&ticket="+ticket+"&captcha_response="+captcha.getText();
                getFileLink();
                Toast.makeText(getApplicationContext(),"Downloading!Please Wait!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFileLink(){

        OkHttpClient mClient = new OkHttpClient();
        Request mrequest = new Request.Builder()
                .url(getdl)
                .build();

        Call mCall = mClient.newCall(mrequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Toast.makeText(getApplicationContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                final String data = response.body().string();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    updateFileLink(data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
            }
        });

    }

    private void updateFileLink(String data) throws JSONException {

        JSONObject dfile  =new JSONObject(data);
        String f_name = file_id.get("name");
        JSONObject dlink = dfile.getJSONObject("result");
        Uri down_link= Uri.parse(dlink.getString("url"));
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        ArrayList<Long> list = new ArrayList<>();

        DownloadManager.Request request = new DownloadManager.Request(down_link);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setDestinationInExternalPublicDir(String.valueOf(Environment.getExternalStorageDirectory()),f_name+".pdf");
        request.setTitle(f_name);
        request.setDescription("Downloading Requested File!!");
        long refid = downloadManager.enqueue(request);
        list.add(refid);
    }

}