package xyz.yankai.react.refresh;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;


/**
 * Created by yankai on 2015/11/13.
 */
public class PullToRefreshView extends android.support.v4.widget.SwipeRefreshLayout implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private ReactContext mReactContext;

    public PullToRefreshView(ReactContext context) {
        super(context);
        mReactContext = context;
        setOnRefreshListener(this);
    }

    public void onRefresh() {
        Log.i("YANKAI", "onRefresh");

        WritableMap event = Arguments.createMap();
        event.putString("message", "MyMessage");

        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "onRefresh",
                event);
    }
}
