package pl.com.mmotak.lekremainder.data.backup;

import android.support.annotation.Nullable;

import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by Maciej on 2017-09-30.
 */

class DateTimeSerializer extends com.squareup.moshi.JsonAdapter<DateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

    @Nullable
    @Override
    public DateTime fromJson(JsonReader reader) throws IOException {
        String string = reader.nextString();
        return DATE_TIME_FORMATTER.parseDateTime(string);
    }

    @Override
    public void toJson(JsonWriter writer, DateTime value) throws IOException {
        String string = DATE_TIME_FORMATTER.print(value);
        writer.value(string);
    }
}
