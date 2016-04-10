package com.example.john.bookprogress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by john on 2016/4/7.
 */
public class ProgressAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private List<HashMap<String, ProgressStatement>> m_StateList;
    Context m_Context;

    public ProgressAdapter(Context context, List<HashMap<String, ProgressStatement>> m_data)
    {
        inflater = LayoutInflater.from(context);
        m_StateList = m_data;
        m_Context = context;
    }

    @Override
    public int getCount()
    {
        return m_StateList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.progress_adapter_layout, null);
        System.out.println("Alpha CheckPoint");
        //0 is Name, 1 is Current Page, 2 is Total Page
        TextView[] textView = {(TextView) convertView.findViewById(R.id.ProgressAdapter_Name),
                (TextView) convertView.findViewById(R.id.ProgressAdapter_CurrentPage),
                (TextView) convertView.findViewById(R.id.ProgressAdapter_TotalPage)};
        System.out.println("Beta CheckPoint");
        ProgressStatement temp = m_StateList.get(position).get("Statement");
        System.out.println("Gamma CheckPoint");
        String[] str = {m_Context.getString(R.string.Adapter_Name) + temp.Statement_GetName(),
                m_Context.getString(R.string.Adapter_CurrentPage) + Integer.toString(temp.Statement_GetCurrentPage()),
                m_Context.getString(R.string.Adapter_TotalPage) + Integer.toString(temp.Statement_GetTotalPage())};
        System.out.println(str[2]);
        System.out.println("Delta CheckPoint");
        for(int i = 0; i < 3; i++)
            textView[i].setText(str[i]);
        System.out.println("Last CheckPoint");
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        progressBar.setProgress((int) temp.Statement_GetProgress());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return m_StateList.get(position).get(Integer.toString(position));
    }
}
