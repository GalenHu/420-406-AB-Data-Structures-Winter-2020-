package com.company;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // Constructors
    public Log(String line) throws Exception{
        String[] data = line.split("\\s+");

        try {
            ipAddress = new IPAddress(data[0]);
        }
        catch (Exception e){
            throw new Exception("Error: Bad IP address" + ipAddress.toString());
        }
        serviceName = data[1];
        String time = data[2].concat(" "+data[3]);
        ParsePosition position = new ParsePosition(0);
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time, position);
        }
        catch (Exception e){
            throw new Exception("Error: Bad Date" + timestamp.toString());
        }
        length = Integer.parseInt(data[4]);
    }


    // Getters and setters
    // TODO
    public Date getDate(){
        return timestamp;
    }

    public IPAddress getIpAddress(){
        return ipAddress;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getLength() {
        return length;
    }

    @Override
    public int compareTo(Log rhs) {
        if(this.ipAddress.compareTo(rhs.ipAddress) <= -1)
            return -1;
        else if(this.serviceName.compareTo(rhs.serviceName) <= -1)
            return -1;
        else if(this.timestamp.compareTo(rhs.timestamp) < 0)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        System.out.println(ipAddress + "\t" + serviceName + " \t" + dateFormat.format(timestamp) +"\t"+ length);
        return ipAddress + "\t" + serviceName + " \t" + dateFormat.format(timestamp) +"\t"+ length;
    }
}
