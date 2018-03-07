package com.sterbon.tutex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Utsav on 12/19/2017.
 */

public class Login extends Activity {

    String mail = null;
    TextView phoneHead;
    EditText phone;
    Button verifyBt;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        phoneHead = (TextView) findViewById(R.id.phonehd);
        phone = (EditText) findViewById(R.id.phone);
        verifyBt = (Button) findViewById(R.id.verifybt);
        Button phoneLogin = (Button) findViewById(R.id.phonelogin);

        phoneHead.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);
        verifyBt.setVisibility(View.INVISIBLE);


        phoneLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            phoneHead.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            verifyBt.setVisibility(View.VISIBLE);

            verifyBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Login.this,PhoneVerify.class);
                    String phoneNumber = String.valueOf(phone.getText());
                    Bundle bundle = new Bundle();
                    bundle.putString("number", phoneNumber);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    });
}

}
