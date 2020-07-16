package com.mintway_dev.my_own_thermometer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/My Own Thermometer/temperature data.txt";
    String str[][] = new String[1000000][3];
    int cnt = 0;

    ImageView btn1;
    TextView txtRead;
    ArrayList<SampleData> DataList;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        DataActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btn1 = findViewById(R.id.btn_back);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                DataActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
        });
    }

    public void onStart()
    {
        cnt = 0;
        super.onStart();

        String read = ReadTextFile(filePath);

        File files = new File(filePath);
        if(files.exists()) {
            String[] change_target = read.split("\\n");

            for (int i = 0; i < change_target.length; i++) {
                String[] spl = change_target[i].split(",");
                str[i][0] = spl[0];
                str[i][1] = spl[1];
                str[i][2] = spl[2];
                System.out.println(str[i][0]);
                System.out.println(str[i][1]);
                System.out.println(str[i][2]);
                cnt = i;
            }
        }

        this.InitializeData();

        ListView listView = (ListView)findViewById(R.id.list1);
        final MyAdapter myAdapter = new MyAdapter(this,DataList);

        listView.setSelector(R.drawable.list_selector);

        listView.setAdapter(myAdapter);
    }

    public String ReadTextFile(String path){
        StringBuffer strBuffer = new StringBuffer();
        try{
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                strBuffer.append(line+"\n");
            }

            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
    }

    public void InitializeData()
    {
        DataList = new ArrayList<SampleData>();

        System.out.println(cnt);
        DataList.add(new SampleData("",""));
        DataList.add(new SampleData("",""));
        DataList.add(new SampleData("",""));
        DataList.add(new SampleData("",""));
        for (int i = cnt; i >= 0; i--)
        {
            if (((MainActivity)MainActivity.context_main).switchChecked) {
                DataList.add(new SampleData(str[i][1] + "°F", str[i][2]));
            }
            else {
                DataList.add(new SampleData(str[i][0] + "°C", str[i][2]));
            }
        }
        DataList.add(new SampleData("",""));
        DataList.add(new SampleData("",""));
        DataList.add(new SampleData("",""));
        DataList.add(new SampleData("",""));

    }
}