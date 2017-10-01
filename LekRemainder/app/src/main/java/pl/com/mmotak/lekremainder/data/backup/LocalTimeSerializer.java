package pl.com.mmotak.lekremainder.data.backup;

import android.support.annotation.Nullable;

import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by Maciej on 2017-09-30.
 */

class LocalTimeSerializer extends com.squareup.moshi.JsonAdapter<LocalTime> {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");

    @Nullable
    @Override
    public LocalTime fromJson(JsonReader reader) throws IOException {
        String string = reader.nextString();
        return formatter.parseLocalTime(string);
    }

    @Override
    public void toJson(JsonWriter writer, LocalTime value) throws IOException {
        String string = formatter.print(value);
        writer.value(string);
    }
}
