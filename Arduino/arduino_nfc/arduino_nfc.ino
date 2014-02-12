#include <PN532.h>
#include <SPI.h>
 
/*Chip select pin can be connected to D10 or D9 which is hareware optional*/
/*if you the version of NFC Shield from SeeedStudio is v2.0.*/
#define PN532_CS 10
PN532 nfc(PN532_CS);
 
void setup(void) {
  Serial.begin(57600);
  Serial.println("Connexion");
  nfc.begin();
  uint32_t versiondata = nfc.getFirmwareVersion();
  // configure board to read RFID tags and cards
  nfc.SAMConfig();
}
 
void loop(void) {
  uint32_t id;
  id = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A);
  if (id != 0)
    Serial.print(id);
    
  delay(1000);
}
