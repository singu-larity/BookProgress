package com.example.john.bookprogress;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Context;

/**
 * Created by john on 2016/4/12.
 */
public class ModifyDetailsFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.StatementDetails_Modify);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.modify_details, null);

        //Set Spinner View, Set Listener For Every Item in Spinner
        ArrayAdapter<CharSequence> stringArrayAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.ModifyDetails, android.R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) view.findViewById(R.id.ModifyDetails_Spinner);
        final EditText editText = (EditText) view.findViewById(R.id.ModifyDetails_EditText);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);break;
                    case 1:editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);break;
                    case 2:editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);break;
                }
                setPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(stringArrayAdapter);

        builder.setView(view)
                .setPositiveButton(R.string.ModifyDetails_Confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                str = editText.getText().toString();
                ((StatementDetails)getActivity()).ModifyDetailsFragmentCallBack(itemPosition, str);
            }
        }).setNegativeButton(R.string.ModifyDetails_Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    //Callback Function
    //Get Information when Click
    private void setPosition(int position) {
        itemPosition = position;
    }

    Context context;
    private int itemPosition = 0;
    private String str = "";
}
