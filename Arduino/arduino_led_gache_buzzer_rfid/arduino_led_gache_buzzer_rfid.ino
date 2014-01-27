#include <SoftwareSerial.h>
#include <SeeedRFIDLib.h>
#define RFID_RX_PIN 2
#define RFID_TX_PIN 3
SeeedRFIDLib RFID(RFID_RX_PIN, RFID_TX_PIN);
RFIDTag tag;

int led = 13;
int gache = 10;
int buzzer = 7;

char val ='0'; // 0 ou 1 va allumer etteindre la led
                // 2 ou 3 commander la gâche
                 // 4 déclanche le buzzer

void setup() {
  Serial.begin(57600);
  pinMode(led, OUTPUT);     
  pinMode(gache, OUTPUT);    
  pinMode(buzzer, OUTPUT);
}

void loop() {
  
  if(RFID.isIdAvailable()) {
    tag = RFID.readId();
    Serial.println(tag.raw);
  }
  
  
  if(Serial.available()){
      val = Serial.read();
      Serial.println(val);
        if(val == '0')
          digitalWrite(led, LOW);
        else if(val == '1')
          digitalWrite(led, HIGH);
        else if(val == '2')
        {
          digitalWrite(gache, HIGH);
          digitalWrite(buzzer, HIGH);
          delay(200);
          digitalWrite(buzzer, LOW);
          delay(200);
          digitalWrite(buzzer, HIGH);
          delay(350);
          digitalWrite(buzzer, LOW);
        }
        else if(val == '3')
          digitalWrite(gache, LOW);
        else if(val == '4')
          tone(buzzer, 200, 1200);    
        else 
        {}
  }  
}  


