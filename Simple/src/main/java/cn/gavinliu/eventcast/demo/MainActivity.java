package cn.gavinliu.eventcast.demo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cn.gavinliu.eventcast.EventCast;
import cn.gavinliu.eventcast.annotation.Receiver;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventCast.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCast.getInstance().unRegister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Receiver(mode = "1")
    public void a() {
        Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
    }

    @Receiver(tag = "xx", mode = "1")
    public void b(int x, int y) {
        Toast.makeText(this, "b: (" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
    }

    public void a(View v) {
        EventCast.getInstance().post(MainActivity.class, "a");
    }

    public void b(View v) {
        EventCast.getInstance().post("xx", 1, 2);
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
