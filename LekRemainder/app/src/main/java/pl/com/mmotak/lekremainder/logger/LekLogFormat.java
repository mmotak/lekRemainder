package pl.com.mmotak.lekremainder.logger;

import android.content.Context;

import com.hypertrack.hyperlog.LogFormat;

import pl.com.mmotak.lekremainder.BuildConfig;

public class LekLogFormat extends LogFormat {
    public LekLogFormat(Context context) {
        super(context);
    }

    @Override
    public String getFormattedLogMessage(String logLevelName, String tag, String message, String timeStamp, String senderName, String osVersion, String deviceUUID) {
        return super.getFormattedLogMessage(logLevelName, tag, message, timeStamp, BuildConfig.VERSION_NAME, osVersion, deviceUUID);
    }
}
