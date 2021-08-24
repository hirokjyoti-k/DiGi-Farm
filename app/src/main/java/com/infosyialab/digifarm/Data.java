package com.infosyialab.digifarm;

public class Data {

    public Data(){}

    String Name;
    String Humidity;
    String Time;
    String Image;
    String Soil;
    String Temperature;

    public Data(String name, String humidity, String time, String image, String soil, String temperature) {
        Name = name;
        Humidity = humidity;
        Time = time;
        Image = image;
        Soil = soil;
        Temperature = temperature;
    }

    public String getName() {
        return Name;
    }

    public String getHumidity() {
        return Humidity;
    }

    public String getTime() {
        return Time;
    }

    public String getImage() {
        return Image;
    }

    public String getSoil() {
        return Soil;
    }

    public String  getTemperature() {
        return Temperature;
    }
}
