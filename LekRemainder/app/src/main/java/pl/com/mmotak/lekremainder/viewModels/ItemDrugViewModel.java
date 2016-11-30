package pl.com.mmotak.lekremainder.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import pl.com.mmotak.lekremainder.models.Drug;

/**
 * Created by mmotak on 29.11.2016.
 */

public class ItemDrugViewModel {

    private final Drug drug;
    private final Context context;

    public ItemDrugViewModel(Context context, Drug drug) {
        this.drug = drug;
        this.context = context;
    }

    public void onItemClick(View view) {
        Toast.makeText(context, "You clicked on " + drug.toString(), Toast.LENGTH_LONG).show();
    }

    public String getName() {
        return drug.name;
    }

    public String getType() {
        return drug.type;
    }
}











