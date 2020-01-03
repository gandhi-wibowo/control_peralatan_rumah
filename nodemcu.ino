#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

const char* ssid     = "MR3020";  
const char* password = "jangandonlot";

const char* host     = "www.kuliketik.id";
String saklar        = "/API_rental/Saklar";  
const int d0   = 16;
const int d1   = 5;
const int d2   = 4;
const int d3   = 0;
const int d4   = 2;
void setup() {  
    pinMode(d0, OUTPUT); 
    pinMode(d0, HIGH);
    pinMode(d1, OUTPUT); 
    pinMode(d1, HIGH);
    pinMode(d2, OUTPUT); 
    pinMode(d2, HIGH);
    pinMode(d3, OUTPUT); 
    pinMode(d3, HIGH);
    pinMode(d4, OUTPUT); 
    pinMode(d4, HIGH);
  Serial.begin(115200);

  delay(10);
  WiFi.begin(ssid, password);
}

void loop(){
  WiFiClient Saklar;
  const int httpPort = 80;
  if (!Saklar.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }  

  Saklar.print(String("GET ") + saklar + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: keep-alive\r\n\r\n");          

  delay(500);


  while(Saklar.available()){
    String sak = Saklar.readStringUntil('\r');
    DynamicJsonBuffer jsonBuffer;
    JsonArray& root = jsonBuffer.parseArray(sak);
    String id1 = root[0]["id_saklar"];
    String id2 = root[1]["id_saklar"];
    String id3 = root[2]["id_saklar"];
    String id4 = root[3]["id_saklar"];
    String stts1 = root[0]["status_saklar"];
    String stts2 = root[1]["status_saklar"];
    String stts3 = root[2]["status_saklar"];
    String stts4 = root[3]["status_saklar"];

    if(id1 == "1"){
      if(stts1 == "mati"){
        digitalWrite(d0, HIGH);
      }
      else{
        digitalWrite(d0, LOW);
      }
    }
    if(id2 == "2"){
      if(stts2 == "mati"){
        digitalWrite(d1, HIGH);
      }
      else{
        digitalWrite(d1, LOW);
      }
    }    
    if(id3 == "3"){
      if(stts3 == "mati"){
        digitalWrite(d2, HIGH);
      }
      else{
        digitalWrite(d2, LOW);
      }
    }
    if(id4 == "4"){
      if(stts4 == "mati"){
        digitalWrite(d4, HIGH);
      }
      else{        
        
        digitalWrite(d4, LOW);
      }
    }
  }

}





