package pl.com.mmotak.lekremainder.viewModels;

import android.content.Context;
import android.view.View;

import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.activities.Henson;
import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 29.11.2016.
 */

public class ItemDrugViewModel {

    private final Drug drug;

    public ItemDrugViewModel(Drug drug) {
        this.drug = drug;
    }

    public void onItemClick(View view) {
        //Toast.makeText(context, "You clicked on ("+drug.getId()+") " + drug.toString(), Toast.LENGTH_LONG).show();

        view.getContext().startActivity(Henson.with(view.getContext())
                .gotoSingleDrugActivity()
                .drugID(drug.getId())
                .build()
        );
    }

    public String getName() {
        return drug.getName();
    }

    public String getType() {
        return drug.getType();
    }

    public int getDosesNo() {
        return drug.getDosesNo();
    }

    public int getDosesEveryH() {
        return drug.getDosesEveryH();
    }
}











