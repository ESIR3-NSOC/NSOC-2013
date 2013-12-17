#include <SoftwareSerial.h>
#include <SeeedRFIDLib.h>
#define RFID_RX_PIN 2
#define RFID_TX_PIN 3
SeeedRFIDLib RFID(RFID_RX_PIN, RFID_TX_PIN);
RFIDTag tag;

void setup() {
  Serial.begin(57600);
}

void loop() {
  if(RFID.isIdAvailable()) {
    tag = RFID.readId();
    Serial.print(tag.raw);
  }
}
