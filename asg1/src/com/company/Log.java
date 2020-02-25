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

        //If wrong ip, throw error
        try {
            ipAddress = new IPAddress(data[0]);
        }
        catch (Exception e){
            throw new Exception("Error: bad Ip address" + ipAddress.toString());
        }
        serviceName = data[1];
        //if there is no service name, throw error
        if (serviceName.length() == 0)
            throw new Exception("Error: Service name is empty");
        String time = data[2].concat(" "+data[3]);
        ParsePosition position = new ParsePosition(0);
        //if the number of character to describe the time is exactly 23, it work, otherwise, bad timestamp
        if (time.length() != 23)
            throw new Exception("Error: Bad date format" + timestamp.toString());
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time, position);
        length = Integer.parseInt(data[4]);
        //if there is no length, length is missing
        if (length == 0)
            throw new Exception("Error: Length is missing");
    }


    // Getters and setters
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
        return ipAddress + "\t" + serviceName + " \t" + dateFormat.format(timestamp) +"\t"+ length;
    }
}
