package pl.com.mmotak.lekremainder.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.entities.DbDrug;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 29.11.2016.
 */

public class ItemDrugViewModel {

    private final DbDrug drug;
    private final Context context;
    private final String dateTimeFormat;

    public ItemDrugViewModel(Context context, DbDrug drug) {
        this.drug = drug;
        this.context = context;
        this.dateTimeFormat = context.getString(R.string.date_format);
    }

    public void onItemClick(View view) {
        Toast.makeText(context, "You clicked on " + drug.toString(), Toast.LENGTH_LONG).show();
    }

    public String getName() {
        return drug.getName();
    }

    public String getType() {
        return drug.getType();
    }

    public boolean isStartDate() {
        return drug.isStartDateEnable();
    }

    public String getStartDateFormatted() {
        return getDateTimeFormat(drug.getStartDate());
    }

    public boolean isEndDate() {
        return drug.isEndDateEnable();
    }

    public String getEndDateFormatted() {
        return getDateTimeFormat(drug.getEndDate());
    }

    public int getDosesNo() {
        return drug.getDosesNo();
    }

    public int getDosesEveryH() {
        return drug.getDosesEveryH();
    }

    private String getDateTimeFormat(DateTime dateTime) {
        return dateTime == null ? "" : dateTime.toString(dateTimeFormat);
    }
}











