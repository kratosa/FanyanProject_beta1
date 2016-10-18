package com.example.fanyan.fanyanproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddInfo extends AppCompatActivity {

    private EditText setName,setAge,setSkill,setIntro;
    private Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        initView();
    }

    private void initView() {
        //文本框字段
        setName = (EditText) findViewById(R.id.use_name);
        setAge = (EditText) findViewById(R.id.use_age);
        setSkill = (EditText) findViewById(R.id.use_skill);
        setIntro = (EditText) findViewById(R.id.use_intro);
        btnAdd = (Button) findViewById(R.id.success_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = setName.getText().toString();
                String age = setAge.getText().toString();
                String skill = setSkill.getText().toString();
                String intro = setIntro.getText().toString();

                Bundle b = new Bundle();
                b.putString("useName",name);
                b.putString("useAge",age);
                b.putString("useSkill",skill);
                b.putString("useIntro",intro);
                Intent intent = new Intent(AddInfo.this,ShowInfo.class);
                intent.putExtras(b);
                finish();
            }
        });
    }
}
