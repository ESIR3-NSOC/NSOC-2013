int led = 13;
char  val = 0;

// the setup routine runs once when you press reset:
void setup() {                
  // initialize the digital pin as an output.
  Serial.begin(9600);
  pinMode(led, OUTPUT);     
}

// the loop routine runs over and over again forever:
void loop() {

  if(Serial.available()){
      val = Serial.read();
      Serial.println(val);
        if(val == '1'){
          digitalWrite(led, HIGH);
       Serial.println("okai");   // turn the LED on (HIGH is the voltage level)  
           }
        else{
          digitalWrite(led, LOW);    // turn the LED off by making the voltage LOW
           
  }
  }
}
