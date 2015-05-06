package cn.gavinliu.eventcast.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import cn.gavinliu.android.lib.eventcast.EventCast;
import cn.gavinliu.android.lib.eventcast.annotation.Receiver;
import cn.gavinliu.android.lib.eventcast.poster.PosterType;

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

    @Receiver
    public void postByMethod() {
        Toast.makeText(this, "PostByMethod", Toast.LENGTH_SHORT).show();
    }

    public void postByMethod(View v) {
        EventCast.getInstance().post(MainActivity.class, "postByMethod");
    }

    @Receiver
    public void postByMethodAndParam(String x) {
        Toast.makeText(this, "postByMethodAndParam:" + x, Toast.LENGTH_SHORT).show();
    }

    public void postByMethodAndParam(View v) {
        EventCast.getInstance().post(MainActivity.class, "postByMethodAndParam", "Hello~");
    }

    @Receiver(tag = "xx", posterType = PosterType.POST)
    public void postByTag() {
        Toast.makeText(this, "PostByTag", Toast.LENGTH_SHORT).show();
    }

    public void postByTag(View v) {
        // post tag
        EventCast.getInstance().post("xx");
    }

    @Receiver(tag = "xx", posterType = PosterType.POST)
    public void postByTagAndParam(int x, int y) {
        Toast.makeText(this, "PostByTagAndParam: (" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
    }

    public void postByTagAndParam(View v) {
        // post tag and param
        EventCast.getInstance().post("xx", 1, 2);
    }

}
