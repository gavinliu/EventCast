package cn.gavinliu.eventcast.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

}
