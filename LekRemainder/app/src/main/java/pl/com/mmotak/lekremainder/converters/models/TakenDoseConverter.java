package pl.com.mmotak.lekremainder.converters.models;

import pl.com.mmotak.lekremainder.entities.DbTakeDose;
import pl.com.mmotak.lekremainder.entities.IDbTakeDose;
import pl.com.mmotak.lekremainder.models.TakeDose;

/**
 * Created by mmotak on 19.12.2016.
 */
public class TakenDoseConverter {
    public static TakeDose toTakeDose(DbTakeDose dbTakeDose) {
        if (dbTakeDose == null) {
            return null;
        } else {
            TakeDose takeDose = new TakeDose(
                    dbTakeDose.getId(),
                    dbTakeDose.getTime(),
                    dbTakeDose.getShiftInSeconds(),
                    dbTakeDose.isTaken());

            return takeDose;
        }
    }

    public static IDbTakeDose toDbTakeDose(TakeDose takeDose) {
        DbTakeDose dbTakeDose = new DbTakeDose();
        dbTakeDose.setTaken(takeDose.isTaken());
        dbTakeDose.setTime(takeDose.getTime());
        dbTakeDose.setShiftInSeconds(takeDose.getShiftInSeconds());

        return dbTakeDose;
    }
}
