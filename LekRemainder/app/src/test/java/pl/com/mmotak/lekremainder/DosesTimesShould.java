package pl.com.mmotak.lekremainder;

/**
 * Created by mmotak on 08.12.2016.
 */

import org.joda.time.LocalTime;
import org.junit.Test;

import pl.com.mmotak.lekremainder.models.DosesTimes;

import static org.assertj.core.api.Assertions.*;

public class DosesTimesShould {

    private LocalTime startTime = new LocalTime(8,0,0);

    @Test
    public void createStartTimeForSingleDoses() {
        DosesTimes dosesTimes = DosesTimes.Build(startTime,1,0);

        assertThat(dosesTimes.getList()).hasSize(1);
        assertThat(dosesTimes.getList().get(0)).isEqualTo(startTime);
    }

    @Test
    public void createTwoItemsForTwoDosesAndZeroH() {
        DosesTimes dosesTimes = DosesTimes.Build(startTime,2,0);

        assertThat(dosesTimes.getList()).hasSize(2);
        assertThat(dosesTimes.getList().get(0)).isEqualTo(startTime);
        assertThat(dosesTimes.getList().get(1)).isEqualTo(startTime.plusHours(12));
    }

    @Test
    public void createThreeItemsForThreeDosesAndZeroH() {
        DosesTimes dosesTimes = DosesTimes.Build(startTime,3,0);

        assertThat(dosesTimes.getList()).hasSize(3);
        assertThat(dosesTimes.getList().get(0)).isEqualTo(startTime);
        assertThat(dosesTimes.getList().get(1)).isEqualTo(startTime.plusHours(6));
        assertThat(dosesTimes.getList().get(2)).isEqualTo(startTime.plusHours(12));
    }

    @Test
    public void createThreeItemsForThreeDosesAndFourH() {
        DosesTimes dosesTimes = DosesTimes.Build(startTime,3,4);

        assertThat(dosesTimes.getList()).hasSize(3);
        assertThat(dosesTimes.getList().get(0)).isEqualTo(startTime);
        assertThat(dosesTimes.getList().get(1)).isEqualTo(startTime.plusHours(4));
        assertThat(dosesTimes.getList().get(2)).isEqualTo(startTime.plusHours(8));
    }
}
