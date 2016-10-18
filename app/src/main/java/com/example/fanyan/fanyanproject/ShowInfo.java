package com.example.fanyan.fanyanproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowInfo extends AppCompatActivity {

    private TextView etName,etAge,etSkill,etIntro,mThird;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        initView();
    }

    private void initView() {
        etName = (TextView) findViewById(R.id.set_name);
        etAge = (TextView) findViewById(R.id.set_age);
        etSkill = (TextView) findViewById(R.id.set_skill);
        etIntro = (TextView) findViewById(R.id.set_intro);
        mThird = (TextView) findViewById(R.id.third_page);

        mThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getExtras().getString("useName");
                String age = getIntent().getExtras().getString("useAge");
                String skill = getIntent().getExtras().getString("useSkill");
                String intro = getIntent().getExtras().getString("useIntro");

                etName.setText(name);
                etAge.setText(age);
                etSkill.setText(skill);
                etIntro.setText(intro);
            }
        });

    }
}
