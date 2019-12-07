#define pirPin 36  //PIR attach to
#define ledPin 38  //LED attach to

#define uint8 unsigned char
#define uint16 unsigned int
#define uint32 unsigned long

uint8 pirValue = 0;  //store the PIR state

void setup() 
{
    Serial.begin(9600);
    pinMode(pirPin, INPUT);
    pinMode(ledPin, OUTPUT);
}

void loop()
{
    pirValue = digitalRead(pirPin);
    Serial.println("Pir State: ");
    Serial.println(pirValue);
    digitalWrite(ledPin, pirValue);
    delay(500);
}
