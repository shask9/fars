package com.shahsk0901.fars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private TextView textView;
    private EditText netID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button) findViewById(R.id.register);
        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netID = (EditText) findViewById(R.id.netID);
                if(TextUtils.isEmpty(netID.getText().toString()))
                    netID.setError("Can't be empty");
            }
        };

        register.setOnClickListener(registerListener);
    }
}
