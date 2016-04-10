package com.example.john.bookprogress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ProgressListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProgressStatement Statement = ProgressStatementList.get(position).get("Statement");
                Intent intent = new Intent(MainActivity.this, StatementDetails.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("New State", Statement);
                intent.putExtras(bundle);
                intent.putExtra("Item Position", position);
                startActivityForResult(intent, REQUEST_CODE_DETAIL_ACTIVITY);
            }
        });
        //Read Data from File to Buffer -> String
        String str = "";
        try {
            FileInputStream fin = openFileInput("DATABASE");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer, 0, length);
            fin.close();
            str = new String(buffer, "UTF-8");
            System.out.println(str);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //Separate String to Name, Current Page and Total Page
        //Add to Progress Statement List
        int i = 0;
        while (i < str.length()) {
            int j = 0;
            String[] temp = {"", "", ""};
            while (str.charAt(i) != '#') {
                if (str.charAt(i) == '\n') {
                    i++;
                    j++;
                    continue;
                }
                temp[j] += str.charAt(i);
                i++;
            }
            HashMap<String, ProgressStatement> newHashMap = new HashMap<>();
            newHashMap.put("Statement",
                    new ProgressStatement(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
            ProgressStatementList.add(newHashMap);
            i++;
        }
    }

    public void AddNewStatement(View view)
    {
        Intent intent = new Intent(this,AddStateActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_ACTIVITY);
    }
    public void RefreshList(View view)
    {
        ProgressAdapter progressAdapter = new ProgressAdapter(this, ProgressStatementList);
        listView.setAdapter(progressAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Result Received.");

        if(requestCode == REQUEST_CODE_ADD_ACTIVITY && resultCode == 1) {
            //Acquire Statement & Add to List
            ProgressStatement newProgressStatement = data.getParcelableExtra("New State");
            int len = ProgressStatementList.size();
            HashMap<String, ProgressStatement> newHashMap = new HashMap<>();
            System.out.println("HashMap<String, ProgressStatement> Created.");
            newHashMap.put("Statement", newProgressStatement);
            ProgressStatementList.add(newHashMap);
            //Write to File
            try {
                FileOutputStream fout = openFileOutput("DATABASE", Context.MODE_APPEND);
                fout.write(ProgressStatementList.get(len).get("Statement").convertToBytes());
                fout.close();
            }
            catch(FileNotFoundException Exception) {
                Exception.printStackTrace();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
        if(requestCode == REQUEST_CODE_DETAIL_ACTIVITY
                && resultCode == StatementDetails.DELETE_STATEMENT_SUCCEED) {
            int position = data.getIntExtra("Item Position", 0);
            System.out.println(position);
            ProgressStatementList.remove(position);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private ListView listView;
    private List<HashMap<String, ProgressStatement>> ProgressStatementList =
            new LinkedList<>();

    public static final int REQUEST_CODE_ADD_ACTIVITY = 1;
    public static final int REQUEST_CODE_DETAIL_ACTIVITY = 2;
}
