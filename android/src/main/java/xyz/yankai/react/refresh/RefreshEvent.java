package xyz.yankai.react.refresh;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by yankai on 2015/11/19.
 */
public class RefreshEvent extends Event<RefreshEvent> {

    public static final String EVENT_NAME = "topRefresh";

    protected RefreshEvent(int viewTag, long timestampMs) {
        super(viewTag, timestampMs);
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public boolean canCoalesce() {
        return false;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap event = Arguments.createMap();
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), event);
    }


}
