package com.example.john.bookprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;

public class AddStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_state);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void SaveStatement(View view)
    {
        //0 is Name, 1 is Current Page, 2 is Total Page
        EditText[] editText = {(EditText)findViewById(R.id.EditText_Name),
                (EditText)findViewById(R.id.EditText_CurrentPage),
                (EditText)findViewById(R.id.EditText_TotalPage)};
        //To String
        String[] Information = {editText[0].getText().toString(),
                editText[1].getText().toString(),
                editText[2].getText().toString()};
        //Check whether Information[i] is Empty
        for(int i = 0; i < 3; i++)
            if(Information[i].isEmpty())
                return;
        //Create New Statement
        ProgressStatement newObject = new ProgressStatement(Information[0], Integer.parseInt(Information[1]), Integer.parseInt(Information[2]));
        //Send Statement Back
        Bundle bundle = new Bundle();
        bundle.putParcelable("New State", newObject);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        AddStateActivity.this.setResult(PROGRESS_STATEMENT_CREATE_SUCCEED, intent);
        super.finish();
    }
    public void Cancel(View view) {this.setResult(PROGRESS_STATEMENT_CREATE_CANCEL); super.finish();}

    private static final int PROGRESS_STATEMENT_CREATE_SUCCEED = 1;
    private static final int PROGRESS_STATEMENT_CREATE_CANCEL = -1;
}
