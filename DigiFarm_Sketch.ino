#include <ESP8266WiFi.h>
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
#include <FirebaseArduino.h>
#include <DHTesp.h>

#define FIREBASE_HOST "digi-farm.firebaseio.com"
#define FIREBASE_AUTH "RcCOJrXWfo1z0HSKURgY1uGseG9V6ZCceSuRY6i0"

DHTesp dht;
WiFiManager wifiManager;

int pump, fan, humidifier, light;

void setup() {

  if (Firebase.failed()){
      Serial.print("setting number failed:");
      Serial.println(Firebase.error());
      return;
  }
  
    Serial.begin(9600);
    wifiManager.autoConnect("DIGI FIRM");   
    Serial.println("connected... :)");
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    dht.setup(D0, DHTesp::DHT11);

     pinMode(D2, OUTPUT);
     pinMode(D3, OUTPUT);
     pinMode(D4, OUTPUT);
     pinMode(D5, OUTPUT);

}

void loop() {

  //DHT sensor
  int temp = dht.getTemperature();
  float humidity = dht.getHumidity();
  Firebase.setFloat("UserDetails/DFRM001/Humidity",humidity);
  Firebase.setInt("UserDetails/DFRM001/Temp",temp);

  //Moisture Sensor
  int sensorValue = analogRead(A0);
  // print out the value you read:
  //Serial.println(sensorValue);
  Firebase.setInt("UserDetails/DFRM001/SOIL",sensorValue);
  delay(100);

  //pump
  pump = Firebase.getInt("UserDetails/DFRM001/PUMP");
  if(pump == 0){
    digitalWrite(D2, HIGH);
    Serial.print("PUMP OFF    ");
  }else{
    digitalWrite(D2, LOW);
    Serial.print("PUMP ON     ");
  }

  //fan
  fan = Firebase.getInt("UserDetails/DFRM001/Cooler");
  if(fan == 0){
    digitalWrite(D3, HIGH);
    Serial.print("FAN OFF   ");
  }else{
    digitalWrite(D3, LOW);
    Serial.print("FAN ON    ");
  }

  //light
  int light = Firebase.getInt("UserDetails/DFRM001/LIGHT");
  if(light == 0){
    digitalWrite(D4, HIGH);
    Serial.print("LIGHT OFF   ");
  }else{
    digitalWrite(D4, LOW);
    Serial.print("LIGHT ON    ");
  }


  //humidifier
  humidifier = Firebase.getInt("UserDetails/DFRM001/Humidifier");
  if(humidifier == 0){
    digitalWrite(D5, HIGH);
    Serial.println("HUMIDIFIER OFF    ");
  }else{
    digitalWrite(D5, LOW);
    Serial.println("HUMIDIFIER ON     ");
  }

}
