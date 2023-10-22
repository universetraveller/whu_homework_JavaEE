package cn.edu.whu;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLogger {
    private String LOG_FILE;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SimpleLogger(String fileName){
	    LOG_FILE = fileName;
    }
    public static void main(String[] args) {
	    // nothing
    }

    public enum LogLevel {
        INFO, WARNING, SEVERE
    }

    public void log(String message) {
        this.log(message, LogLevel.INFO);
    }
    public void severe(String message) {
	    this.log(message, LogLevel.SEVERE);
    }
    public void warning(String message) {
	   this.log(message, LogLevel.WARNING);
    }

    public void log(String message, LogLevel level) {
        String logEntry = dateFormat.format(new Date()) + " [" + level + "] " + message;
        writeLog(logEntry);
    }

    private void writeLog(String logEntry) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true)) {
            fileWriter.write(logEntry + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

