package com.example.john.bookprogress;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class StatementDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_details);
        Intent GetIntent = getIntent();
        oldProgressStatement = GetIntent.getParcelableExtra("New State");
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

    public void ConfirmDetails(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("New ProgressStatement", oldProgressStatement);
        intent.putExtras(bundle);
        intent.putExtra("Item Position", position);
        this.setResult(MODIFY_STATEMENT_SUCCEED, intent);
        super.finish();
    }
    public void ModifyDetails(View view) {
        ModifyDetailsFragment dialogFragment = new ModifyDetailsFragment();
        dialogFragment.show(getFragmentManager(), "Modify Dialog");
    }
    public void DeleteStatement(View view) {
        Intent intent = new Intent();
        intent.putExtra("Item Position", position);
        this.setResult(DELETE_STATEMENT_SUCCEED, intent);
        super.finish();
    }

    public void ModifyDetailsFragmentCallBack(int itemPosition, String str) {
        System.out.println("CheckPoint A" + itemPosition);
        switch(itemPosition) {
            case 0:oldProgressStatement.Statement_SetName(str);break;
            case 1:oldProgressStatement.Statement_SetCurrentPage(Integer.parseInt(str));break;
            case 2:oldProgressStatement.Statement_SetTotalPage(Integer.parseInt(str));break;
        }
    }

    private static int position = 0;
    private ProgressStatement oldProgressStatement;
    public static final int MODIFY_STATEMENT_SUCCEED = 1;
    public static final int DELETE_STATEMENT_SUCCEED = 2;
}
