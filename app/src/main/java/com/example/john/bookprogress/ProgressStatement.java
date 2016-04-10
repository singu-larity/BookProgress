package com.example.john.bookprogress;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by john on 2016/4/5.
 */
public class ProgressStatement implements Parcelable
{
    private String m_Name = "NULL";
    private double m_Progress = 0;
    private int m_CurrentPage = 0;
    private int m_TotalPage = 0;

    private static final int PERCENTAGE = 100;

    ProgressStatement(String Name, int CurrentPage, int TotalPage)
    {
        m_Name = Name;
        m_CurrentPage = CurrentPage;
        m_TotalPage = TotalPage;
        m_Progress = (double)m_CurrentPage / (double)m_TotalPage * PERCENTAGE;
    }
    ProgressStatement(Parcel source)
    {
        m_Name = source.readString();
        int[] array = {0, 0};
        source.readIntArray(array);
        m_CurrentPage = array[0];
        m_TotalPage = array[1];
        m_Progress = source.readDouble();
    }

    public void Statement_SetName(String Name)
    {
        m_Name = Name;
    }
    public void Statement_SetCurrentPage(int Page) {
        m_CurrentPage = Page;
        m_Progress = (double) m_CurrentPage / (double) m_TotalPage * PERCENTAGE;
    }
    public void Statement_SetTotalPage(int Page)
    {
        m_TotalPage = Page;
        m_Progress = (double)m_CurrentPage / (double)m_TotalPage * PERCENTAGE;
    }

    public String Statement_GetName() {return m_Name;}
    public int Statement_GetCurrentPage() {return m_CurrentPage;}
    public int Statement_GetTotalPage() {return m_TotalPage;}
    public double Statement_GetProgress() {return m_Progress;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int[] array = {m_CurrentPage, m_TotalPage};
        dest.writeString(m_Name);
        dest.writeIntArray(array);
        dest.writeDouble(m_Progress);
    }
    public static final Parcelable.Creator<ProgressStatement> CREATOR = new Creator<ProgressStatement>()
    {
        @Override
        public ProgressStatement createFromParcel(Parcel source) {
            return new ProgressStatement(source);
        }

        @Override
        public ProgressStatement[] newArray(int size) {
            return new ProgressStatement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] convertToBytes()
    {
        String str = m_Name + "\n" + Integer.toString(m_CurrentPage) + "\n"
                + Integer.toString(m_TotalPage) + "\n" + "#";
        try {
            return str.getBytes("UTF-8");
        }
        catch(UnsupportedEncodingException Exception) {

        }
        return new byte[1];
    }
}
