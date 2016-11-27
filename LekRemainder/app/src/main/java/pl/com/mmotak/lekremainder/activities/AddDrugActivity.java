package pl.com.mmotak.lekremainder.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.mmotak.lekremainder.R;
import pl.com.mmotak.lekremainder.lekapp.LekRemainderApplication;

public class AddDrugActivity extends BaseNavDrawerActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);

        ButterKnife.bind(this);
        ((LekRemainderApplication) getApplication()).getDiComponent().inject(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show());
    }

}
