package mt.qik.app;

import android.content.res.Configuration;

/**
 * Generated class from qik.cordova.pip plugin.
 */
public class PIPActivity extends MainActivity
{
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (appView != null) {
            appView.getPluginManager().postMessage("onPictureInPictureModeChanged", isInPictureInPictureMode);
        }
    }
}
