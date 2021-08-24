package com.infosyialab.digifarm;

public class UserInfo {

    UserInfo(){}

    public String Name;
    public String Email;
    public int Humidity;
    public int Temp;
    public int SOIL;
    public int LIGHT;
    public int PUMP;
    public int Cooler;
    public int Humidifier;
    public String DeviceID;

    public UserInfo(String name, String email, int humidity, int temp, int SOIL, int LIGHT, int PUMP, int cooler, int humidifier, String deviceID) {
        Name = name;
        Email = email;
        Humidity = humidity;
        Temp = temp;
        this.SOIL = SOIL;
        this.LIGHT = LIGHT;
        this.PUMP = PUMP;
        Cooler = cooler;
        Humidifier = humidifier;
        DeviceID = deviceID;
    }




}
