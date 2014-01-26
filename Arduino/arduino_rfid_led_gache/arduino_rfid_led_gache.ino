#include <SoftwareSerial.h>
#include <SeeedRFIDLib.h>
#define RFID_RX_PIN 2
#define RFID_TX_PIN 3
SeeedRFIDLib RFID(RFID_RX_PIN, RFID_TX_PIN);
RFIDTag tag;

int led = 13;
int gache = 10;

char val ='0'; // 0 ou 1 va allumer etteindre la led
                // 2 ou 3 commander la gâche

void setup() {
  Serial.begin(57600);
  pinMode(led, OUTPUT);     
  pinMode(gache, OUTPUT);
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
          digitalWrite(gache, HIGH);
        else if(val == '3')
          digitalWrite(gache, LOW);
        else 
          Serial.println("Error");    
    }
}
