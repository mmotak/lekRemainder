package pl.com.mmotak.lekremainder.logger;

public interface ILogger {
    void d(String message);
    void e(String message);
    void e(String message, Throwable throwable);
    void w(String message);
}
