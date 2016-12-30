package pl.com.mmotak.lekremainder.settings;

import org.joda.time.DateTime;

/**
 * Created by Maciej on 2016-12-30.
 */

public class SavedSettings {

    //TODO: move to settings

    public static DateTime getNextRestartDateTime() {
        return DateTime.now().plusDays(1).withTime(6, 0, 0, 0); // set tomorrow at 6 am
    }
}
