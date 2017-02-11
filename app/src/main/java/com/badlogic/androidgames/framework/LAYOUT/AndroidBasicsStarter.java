package com.badlogic.androidgames.framework.LAYOUT;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dell.mysuperawesomegame.AlertDialog.AlertDialog_IndexedTest;
import com.example.dell.mysuperawesomegame.AlertDialog.AlertDialog_TriangleScreenTest;

public class AndroidBasicsStarter extends ListActivity {
    public final static String ACTION_UPDATE = "actionUpdate";
    private BroadcastReceiver updateReceiver;
    private Bundle bundle;
    String tests[] = { "LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
            "KeyTest", "AccelerometerTest", "AssetsTest",
            "ExternalStorageTest", "SoundPoolTest", "MediaPlayerTest",
            "FullScreenTest", "RenderViewTest", "ShapeTest", "BitmapTest",
            "FontTest", "SurfaceViewTest", "GLSurfaceViewTest", "GLGameTest",
            "FirstTriangleTest", "IndexedTest", "AnimationTest"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tests));
        PowerManager powerManager =
                (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
      /*  updateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // viet them cac doan code cap nhat du lieu tren MainActivity ở đây
                // de khai thac du lieu truyen tu Fragment ve thi có thể sử dụng các phương thức: intent.getStringExtra(), intent.getIntExtra().....
                bundle = new Bundle();
                bundle = intent.getBundleExtra("dulieugoi");
            }
        };

        registerReceiver(updateReceiver, new IntentFilter(ACTION_UPDATE));*/
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onListItemClick(ListView list, View view, int position,
                                   long id) {
        super.onListItemClick(list, view, position, id);
        String testName = tests[position];
        try {
            /*
            Class.forName("com.example.dell.mysuperawesomegame.MainActivity");*/
            Class clazz = Class
                    .forName("com.badlogic.androidgames.framework.LAYOUT." + testName);
            final Intent intent = new Intent(this, clazz);
            if(testName.equals("FirstTriangleTest")) {
                AlertDialog_TriangleScreenTest alertDialogTriangleScreenTest = new AlertDialog_TriangleScreenTest();
                alertDialogTriangleScreenTest.show(getFragmentManager(), "Choose Screen");

            }
            else
            if(testName.equals("IndexedTest")){
                AlertDialog_IndexedTest alertDialog_indexedTest = new AlertDialog_IndexedTest();
                alertDialog_indexedTest.show(getFragmentManager(), "Choose Screen");
            }
            else
                startActivity(intent);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateReceiver);
    }*/
}
