package pl.com.mmotak.lekremainder.logger;

public abstract class AbstractLogger implements ILogger {
    private final String tag;
    private final String className;

    public AbstractLogger(String tag, String className) {
        valid(tag, "TAG");
        valid(className, "className");
        this.tag = tag.trim();
        this.className = className.trim();
    }

    private void valid(String text, String what) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(what + " cannot be null or empty!");
        }
    }

    protected String createMessage(String message) {
        return "[" + className + "]: " + message;
    }

    @Override
    public void d(String message) {
        logd(tag, createMessage(message));
    }

    @Override
    public void e(String message) {
        loge(tag, createMessage(message));
    }


    @Override
    public void e(String message, Throwable throwable) {
        loge(tag, createMessage(message), throwable);
    }

    @Override
    public void w(String message) {
        logw(tag, createMessage(message));
    }

    protected abstract void logd(String tag, String message);

    protected abstract void loge(String tag, String message);

    protected abstract void loge(String tag, String message, Throwable throwable);

    protected abstract void logw(String tag, String message);
}
