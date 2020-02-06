package ca.qc.johnabbott.cs616.asg1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A log entry for the logfile in Asg #1
 * @author YOU!
 */
public class Log implements Comparable<Log> {
 
    // Fields
    private Date timestamp;
    private IPAddress ipAddress;
    private String serviceName;
    private int length;

    // Constructors
    // TODO

    public Log(Date timestamp, IPAddress ipAddress, String serviceName, int length) {
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.serviceName = serviceName;
        this.length = length;
    }


    // Getters and setters
    // TODO

    @Override
    public int compareTo(Log rhs) {
        // TODO
        return -1;
    }
}
