package xyz.yankai.react.refresh;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import java.util.Map;
import javax.annotation.Nullable;


/**
 * Created by yankai on 2015/11/13.
 */
public class PullToRefreshViewManager extends ViewGroupManager<PullToRefreshView> {

    private static  final int END_REFRESH = 1;

    @Override
    public String getName() {
        return "yankai-react-native-pull-to-refresh-view";
    }

    @Override
    protected PullToRefreshView createViewInstance(ThemedReactContext reactContext) {
        return new PullToRefreshView(reactContext);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        //super.getExportedCustomDirectEventTypeConstants();
        return MapBuilder.<String, Object>builder()
                .put(
                        "onRefresh",
                        MapBuilder.of("registrationName", "onRefresh"))
                .build();
    }

    @Override
    public @Nullable Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("endRefresh", END_REFRESH);
    }

    @Override
    public void receiveCommand(
            PullToRefreshView view,
            int commandId,
            @Nullable ReadableArray args) {
        switch (commandId) {
            case END_REFRESH:
                Log.i("YANKAI", "end refresh");
                view.setRefreshing(false);
                break;
        }
    }

    @ReactProp(name ="size" )
    public void setSize(PullToRefreshView view,String size) {
        int sizeValue = PullToRefreshView.DEFAULT;
        switch (size) {
            case "large":
                sizeValue = PullToRefreshView.LARGE;
                break;
            default:
                sizeValue = PullToRefreshView.DEFAULT;
        }
        view.setSize(sizeValue);
    }

    @ReactProp(name ="colors" )
    public void setColors(PullToRefreshView view,String colors) {
        String[] arr = colors.split(",");
        if (arr.length > 0) {
            int[] values = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                values[i] = Color.parseColor(arr[i]);
            }
            view.setColorSchemeColors(values);
        }
    }

    @Override
    public void addView(PullToRefreshView parent, View child, int index) {
        super.addView(parent, child, index);
    }
}
