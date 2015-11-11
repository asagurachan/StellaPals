package com.stella.pals.frontend;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.stella.pals.R;
import com.stella.pals.frontend.adapter.MessageGroupAdapter;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.global.Global;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
        ListView lvMessageGroups = (ListView) findViewById(R.id.lv_message_groups);
        MessageGroupAdapter adapter = new MessageGroupAdapter(this);

        lvMessageGroups.setAdapter(adapter);
        Global.updateMessageGroups(1, adapter);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}