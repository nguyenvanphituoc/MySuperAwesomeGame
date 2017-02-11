package com.example.dell.mysuperawesomegame.AlertDialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.dell.mysuperawesomegame.R;

/**
 * Created by dell on 8/7/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlertDialog_TriangleScreenTest extends DialogFragment {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title)
                .setItems(R.array.triangle_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        final Intent intent = new Intent( "com.badlogic.androidgames.framework.LAYOUT.FIRSTTRIANGLETEST");
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", which);
                        intent.putExtra("dulieugoi", bundle);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

}
