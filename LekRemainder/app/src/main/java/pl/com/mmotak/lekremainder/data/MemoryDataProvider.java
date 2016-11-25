package pl.com.mmotak.lekremainder.data;

/**
 * Created by mmotak on 25.11.2016.
 */

public class MemoryDataProvider implements IDataProvider {
    @Override
    public String getName() {
        return MemoryDataProvider.class.getName();
    }
}
