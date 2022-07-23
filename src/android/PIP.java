package qik.cordova.pip;
import android.content.Intent;
import android.app.Activity;
import android.app.PictureInPictureParams;
import android.util.Rational;
import android.util.Log;
import android.os.Build;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

public class PIP extends CordovaPlugin {
    private PictureInPictureParams.Builder pictureInPictureParamsBuilder = null;
    private CallbackContext modeChangeCallback = null;
    private CallbackContext dismissCallback = null;
	private final String TAG = "PIP";
	private boolean hasPIPMode = false;
	private boolean isInBackground = false;
	
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        hasPIPMode = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
		if (hasPIPMode) {
			try {
                pictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
            } catch(Exception e) {
				hasPIPMode = false;
			}
		}
    }
    
    @Override    
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "enter":
                Double width = args.getDouble(0);
                Double height = args.getDouble(1);
                cordova.getThreadPool().execute(() -> enterPip(width, height, callbackContext));
                return true;
            case "isPip":
                this.isPip(callbackContext);
                return true;
            case "onPipModeChanged":
                modeChangeCallback = callbackContext;
                return true;
            case "isPipModeSupported":
                this.isPipModeSupported(callbackContext);
                return true;
            case "onUserDismissed":
                dismissCallback = callbackContext;
                return true;
            case "reset":
                modeChangeCallback = null;
                dismissCallback = null;
                return true;
        }
        return false;
    }

    @Override
    public Object onMessage(String id, Object data) {
        if (id.equals("onPictureInPictureModeChanged")) {
            boolean active = (Boolean) data;
            Log.d(TAG, "onPictureInPictureModeChanged: " + active);
            if (modeChangeCallback != null) {
                PluginResult result = new PluginResult(PluginResult.Status.OK, active);
                result.setKeepCallback(true);
                modeChangeCallback.sendPluginResult(result);
            }

            // User drag to close PIP or click in the close button
            if (dismissCallback != null && isInBackground && !active) {
                Log.d(TAG, "User dismissed PIP mode");
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
                pluginResult.setKeepCallback(true);
                dismissCallback.sendPluginResult(pluginResult);
            }
            return null;
        }
        return super.onMessage(id, data);
    }

    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        isInBackground = true;
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        isInBackground = false;
    }

    private void enterPip(Double width, Double height, CallbackContext callbackContext) {
        if (!hasPIPMode) {
            callbackContext.error("Picture-in-picture unavailable.");
            return;
        }
        try {
            Activity activity = cordova.getActivity();
            boolean active = activity.isInPictureInPictureMode();
            Log.d(TAG, "enterPip " + active);
            if (active) {
                callbackContext.success("Already in picture-in-picture mode.");
            } else {
                if(width != null && width > 0 && height != null && height > 0){
                    Intent openMainActivity = new Intent(activity.getApplicationContext(), activity.getClass());
                    openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    activity.startActivityIfNeeded(openMainActivity, 0);
                    Rational aspectRatio = new Rational(width.intValue(), height.intValue());
                    pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
                    activity.enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
                    callbackContext.success("Scaled picture-in-picture mode started.");
                } else {
                    activity.enterPictureInPictureMode();
                    callbackContext.success("Default picture-in-picture mode started.");
                }
            }
        } catch(Exception e) {
            String stackTrace = Log.getStackTraceString(e);
			Log.d(TAG, "enterPip ERR " + stackTrace);
            callbackContext.error(stackTrace);
        }             
    }
    
    private void isPipModeSupported(CallbackContext callbackContext) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, hasPIPMode);
        callbackContext.sendPluginResult(pluginResult);
    }

    public void isPip(CallbackContext callbackContext) {
		boolean active = false;
		if (hasPIPMode) {
            active = cordova.getActivity().isInPictureInPictureMode();
		}
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, active);
        callbackContext.sendPluginResult(pluginResult);
    }
}