package com.example.dell.mysuperawesomegame.AlertDialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.dell.mysuperawesomegame.R;

/**
 * Created by dell on 8/8/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlertDialog_IndexedTest extends DialogFragment {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Indexed tester")
                .setItems(R.array.indexed_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        final Intent intent = new Intent( "com.badlogic.androidgames.framework.LAYOUT.INDEXEDTEST");
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", which);
                        intent.putExtra("dulieugoi", bundle);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

}
