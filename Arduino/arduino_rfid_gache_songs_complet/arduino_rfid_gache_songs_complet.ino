#include <SoftwareSerial.h>
#include <SeeedRFIDLib.h>
#define RFID_RX_PIN 5
#define RFID_TX_PIN 6
SeeedRFIDLib RFID(RFID_RX_PIN, RFID_TX_PIN);
RFIDTag tag;
#include <pitches.h>


#define NOTE_B0  31
#define NOTE_C1  33
#define NOTE_CS1 35
#define NOTE_D1  37
#define NOTE_DS1 39
#define NOTE_E1  41
#define NOTE_F1  44
#define NOTE_FS1 46
#define NOTE_G1  49
#define NOTE_GS1 52
#define NOTE_A1  55
#define NOTE_AS1 58
#define NOTE_B1  62
#define NOTE_C2  65
#define NOTE_CS2 69
#define NOTE_D2  73
#define NOTE_DS2 78
#define NOTE_E2  82
#define NOTE_F2  87
#define NOTE_FS2 93
#define NOTE_G2  98
#define NOTE_GS2 104
#define NOTE_A2  110
#define NOTE_AS2 117
#define NOTE_B2  123
#define NOTE_C3  131
#define NOTE_CS3 139
#define NOTE_D3  147
#define NOTE_DS3 156
#define NOTE_E3  165
#define NOTE_F3  175
#define NOTE_FS3 185
#define NOTE_G3  196
#define NOTE_GS3 208
#define NOTE_A3  220
#define NOTE_AS3 233
#define NOTE_B3  247
#define NOTE_C4  262
#define NOTE_CS4 277
#define NOTE_D4  294
#define NOTE_DS4 311
#define NOTE_E4  330
#define NOTE_F4  349
#define NOTE_FS4 370
#define NOTE_G4  392
#define NOTE_GS4 415
#define NOTE_A4  440
#define NOTE_AS4 466
#define NOTE_B4  494
#define NOTE_C5  523
#define NOTE_CS5 554
#define NOTE_D5  587
#define NOTE_DS5 622
#define NOTE_E5  659
#define NOTE_F5  698
#define NOTE_FS5 740
#define NOTE_G5  784
#define NOTE_GS5 831
#define NOTE_A5  880
#define NOTE_AS5 932
#define NOTE_B5  988
#define NOTE_C6  1047
#define NOTE_CS6 1109
#define NOTE_D6  1175
#define NOTE_DS6 1245
#define NOTE_E6  1319
#define NOTE_F6  1397
#define NOTE_FS6 1480
#define NOTE_G6  1568
#define NOTE_GS6 1661
#define NOTE_A6  1760
#define NOTE_AS6 1865
#define NOTE_B6  1976
#define NOTE_C7  2093
#define NOTE_CS7 2217
#define NOTE_D7  2349
#define NOTE_DS7 2489
#define NOTE_E7  2637
#define NOTE_F7  2794
#define NOTE_FS7 2960
#define NOTE_G7  3136
#define NOTE_GS7 3322
#define NOTE_A7  3520
#define NOTE_AS7 3729
#define NOTE_B7  3951
#define NOTE_C8  4186
#define NOTE_CS8 4435
#define NOTE_D8  4699
#define NOTE_DS8 4978
const int c = 261;
const int d = 294;
const int e = 329;
const int f = 349;
const int g = 391;
const int gS = 415;
const int a = 440;
const int aS = 455;
const int b = 466;
const int cH = 523;
const int cSH = 554;
const int dH = 587;
const int dSH = 622;
const int eH = 659;
const int fH = 698;
const int fSH = 740;
const int gH = 784;
const int gSH = 830;
const int aH = 880;

//Mario main theme melody
int melody[] = {
  NOTE_E7, NOTE_E7, 0, NOTE_E7, 
  0, NOTE_C7, NOTE_E7, 0,
  NOTE_G7, 0, 0,  0,
  NOTE_G6, 0, 0, 0, 

  NOTE_C7, 0, 0, NOTE_G6, 
  0, 0, NOTE_E6, 0, 
  0, NOTE_A6, 0, NOTE_B6, 
  0, NOTE_AS6, NOTE_A6, 0, 

};
//Mario main them tempo
int tempo[] = {
  12, 12, 12, 12, 
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12, 

  12, 12, 12, 12,
  12, 12, 12, 12, 
  12, 12, 12, 12, 
  12, 12, 12, 12, 

  9, 9, 9,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,

  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,

  9, 9, 9,
  12, 12, 12, 12,
  12, 12, 12, 12,
  12, 12, 12, 12,
};
int underworld_melody[] = {
  NOTE_C4, NOTE_C5, NOTE_A3, NOTE_A4, 
  NOTE_AS3, NOTE_AS4, 0,
  0,
  NOTE_C4, NOTE_C5, NOTE_A3, NOTE_A4, 
  NOTE_AS3, NOTE_AS4, 0,
  0,
  NOTE_F3, NOTE_F4, NOTE_D3, NOTE_D4,
  NOTE_DS3, NOTE_DS4, 0,
  0,
  NOTE_F3, NOTE_F4, NOTE_D3, NOTE_D4,
  NOTE_DS3, NOTE_DS4, 0,
  0, NOTE_DS4, NOTE_CS4, NOTE_D4,
  NOTE_CS4, NOTE_DS4, 
  NOTE_DS4, NOTE_GS3,
  NOTE_G3, NOTE_CS4,
  NOTE_C4, NOTE_FS4,NOTE_F4, NOTE_E3, NOTE_AS4, NOTE_A4,
  NOTE_GS4, NOTE_DS4, NOTE_B3,
  NOTE_AS3, NOTE_A3, NOTE_GS3,
  0, 0, 0
};
//Underwolrd tempo
int underworld_tempo[] = {
  12, 12, 12, 12, 
  12, 12, 6,
  3,
  12, 12, 12, 12, 
  12, 12, 6,
  3,
  12, 12, 12, 12, 
  12, 12, 6,
  3,
  12, 12, 12, 12, 
  12, 12, 6,
  6, 18, 18, 18,
  6, 6,
  6, 6,
  6, 6,
  18, 18, 18,18, 18, 18,
  10, 10, 10,
  10, 10, 10,
  3, 3, 3
};
int counter = 0;

int led = 8;
int led_bicolore_rouge = 12;
int gache = 13;
int melodyPin = 9;

int button = 2;

char val ='0'; // 0 ou 1 va allumer etteindre la led
                // 2 ou 3 commander la gâche
                 // 4 déclanche le buzzer

void setup() {
  Serial.begin(57600);
  pinMode(led, OUTPUT);     
  pinMode(gache, OUTPUT);    
  pinMode(led_bicolore_rouge, OUTPUT);
  pinMode(melodyPin, OUTPUT);
  pinMode(button, INPUT);
}

void loop() {
String programmation="";
  
  // si appuie sur bouton == true 
  int buttonstate = digitalRead(button);
  if(buttonstate == HIGH) 
  {
    programmation="programmation";
    digitalWrite(led_bicolore_rouge, HIGH);
  }
  else 
  {
    programmation="";
    digitalWrite(led_bicolore_rouge, LOW);
  }
  
  if(RFID.isIdAvailable()) {
    tag = RFID.readId();
    Serial.println(programmation+tag.raw);
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
          sing(1);
        }
        else if(val == '3')
          digitalWrite(gache, LOW);
        else if(val == '4')
        {
          firstSection();
          digitalWrite(led_bicolore_rouge, LOW);
          digitalWrite(led, LOW);      
        }     
        else 
        {}
  }  
}  

void beep(int note, int duration)
{
  //Play tone on buzzerPin
  tone(melodyPin, note, duration);
 
  //Play different LED depending on value of 'counter'
  if(counter % 2 == 0)
  {
   // digitalWrite(ledPin1, HIGH);
    delay(duration);
   // digitalWrite(ledPin1, LOW);
  }else
  {
    //digitalWrite(ledPin2, HIGH);
    delay(duration);
    //digitalWrite(ledPin2, LOW);
  }
 
  //Stop tone on buzzerPin
  noTone(melodyPin);
 
  delay(50);
 
  //Increment counter
  counter++;
}
void firstSection()
{
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(a, 500);
  digitalWrite(led_bicolore_rouge, HIGH);
  digitalWrite(led, LOW);
  beep(a, 500);    
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(a, 500);
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(f, 350);
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(cH, 150);  
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(a, 500);
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(f, 350);
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(cH, 150);
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(a, 650);
  
  
  delay(500);
 
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(eH, 500);
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(eH, 500);
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(eH, 500);  
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(fH, 350);
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(cH, 150);
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(gS, 500);
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(f, 350);
  digitalWrite(led, HIGH);
  digitalWrite(led_bicolore_rouge, LOW); 
  beep(cH, 150);
  digitalWrite(led, LOW);
  digitalWrite(led_bicolore_rouge, HIGH);
  beep(a, 650);
 
  delay(500);
}

int song = 0;

void sing(int s){      
   // iterate over the notes of the melody:
   song = s;

     //Serial.println(" 'Mario Theme'");
     int size = sizeof(melody) / sizeof(int);
     for (int thisNote = 0; thisNote < size; thisNote++) {

       // to calculate the note duration, take one second
       // divided by the note type.
       //e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
       int noteDuration = 1000/tempo[thisNote];

       buzz(melodyPin, melody[thisNote],noteDuration);

       // to distinguish the notes, set a minimum time between them.
       // the note's duration + 30% seems to work well:
       int pauseBetweenNotes = noteDuration * 1.30;
       delay(pauseBetweenNotes);

       // stop the tone playing:
       buzz(melodyPin, 0,noteDuration);  
  }
}

void buzz(int targetPin, long frequency, long length) {
  //digitalWrite(3,HIGH);
  long delayValue = 1000000/frequency/2; // calculate the delay value between transitions
  //// 1 second's worth of microseconds, divided by the frequency, then split in half since
  //// there are two phases to each cycle
  long numCycles = frequency * length/ 1000; // calculate the number of cycles for proper timing
  //// multiply frequency, which is really cycles per second, by the number of seconds to 
  //// get the total number of cycles to produce
  for (long i=0; i < numCycles; i++){ // for the calculated length of time...
    digitalWrite(targetPin,HIGH); // write the buzzer pin high to push out the diaphram
    delayMicroseconds(delayValue); // wait for the calculated delay value
    digitalWrite(targetPin,LOW); // write the buzzer pin low to pull back the diaphram
    delayMicroseconds(delayValue); // wait again or the calculated delay value
  }
  //digitalWrite(13,LOW);

}

