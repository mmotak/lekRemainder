package pl.com.mmotak.lekremainder.viewModels;

public class ItemLogViewModel {
    private final String message;
    private final int position;

    public ItemLogViewModel(int position, String message) {
        this.position = position;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
