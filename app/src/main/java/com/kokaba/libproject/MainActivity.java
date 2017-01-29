package com.kokaba.libproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kokaba.firebasemiyagi.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseHelper helper = new FirebaseHelper();

        helper.getChildren(SampleData.class, "sample_data")
            .orderByChild("surname")
            .endAt("Kokelj")
            .toRx()
            .subscribe(e -> {
                Log.d("smt", "hoe");
            }, error -> {
                Log.d("smt", "who knows");
            });

        helper.getValues(SampleData.class, "sample_data")
            .toRx()
            .subscribe(e -> {
                Log.d("smt", "hoe");
            }, error -> {
                Log.d("smt", "who knows");
            });


        helper.listenToValues(SampleData.class, "sample_data")
            .orderByChild("surname")
            .endAt("Kokelj")
            .toRx()
            .subscribe(e -> {
                Log.d("smt", "hoe");
            }, error -> {
                Log.d("smt", "who knows");
            });



    }

}
