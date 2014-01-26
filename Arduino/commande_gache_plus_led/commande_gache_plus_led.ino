int led = 13;
int gache = 10;

char val ='0'; // 0 ou 1 va allumer etteindre la led
                // 2 ou 3 commander la gâche

// the setup routine runs once when you press reset:
void setup() {                
  // initialize the digital pin as an output.
  Serial.begin(9600);
  pinMode(led, OUTPUT);     
  pinMode(gache, OUTPUT);
}

// the loop routine runs over and over again forever:
void loop() {

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
          Serial.println("Error");    // turn the LED off by making the voltage LOW
    }
  }

