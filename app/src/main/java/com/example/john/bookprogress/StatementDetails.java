package com.example.john.bookprogress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StatementDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_details);
        Intent GetIntent = getIntent();
        ProgressStatement oldProgressStatement = GetIntent.getParcelableExtra("New State");
        position = GetIntent.getIntExtra("Item Position", 0);

        TextView[] textViews = {(TextView) findViewById(R.id.Statement_Details_Name),
                (TextView) findViewById(R.id.Statement_Details_Current_Page),
                (TextView) findViewById(R.id.Statement_Details_Total_Page)};
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.Statement_Details_ProgressBar);

        String[] str = {getString(R.string.StatementDetails_Name) + " " + oldProgressStatement.Statement_GetName(),
                getString(R.string.StatementDetails_CurrentPage) + " " + Integer.toString(oldProgressStatement.Statement_GetCurrentPage()),
                getString(R.string.StatementDetails_TotalPage) + " " + Integer.toString(oldProgressStatement.Statement_GetTotalPage())};
        for(int i = 0; i < 3; i++)
            textViews[i].setText(str[i]);
        progressBar.setProgress((int)oldProgressStatement.Statement_GetProgress());
    }

    public void ModifyDetails(View view) {

    }
    public void DeleteStatement(View view) {
        try {
            FileInputStream fin = openFileInput("DATABASE");
            byte[] oldBuffer = new byte [fin.available()];
            fin.read(oldBuffer);
            fin.close();
            String str = new String(oldBuffer, "UTF-8");

            int str_start = 0, str_end = str.indexOf("#"), temp_position = position;
            while(temp_position-- != 0) {
                str_start = str_end + 1;
                str_end = str.indexOf("#", str_start);
            }
            String newString = str.substring(0, str_start) + str.substring(str_end + 1);
            byte[] newBuffer = newString.getBytes("UTF-8");

            FileOutputStream fout = openFileOutput("DATABASE", Context.MODE_PRIVATE);
            fout.write(newBuffer);
            fout.close();
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }

        Intent intent = new Intent();
        intent.putExtra("Item Position", position);
        this.setResult(DELETE_STATEMENT_SUCCEED, intent);
        super.finish();
    }

    private static int position = 0;
    public static final int MODIFY_STATEMENT_SUCCEED = 1;
    public static final int DELETE_STATEMENT_SUCCEED = 2;
}
