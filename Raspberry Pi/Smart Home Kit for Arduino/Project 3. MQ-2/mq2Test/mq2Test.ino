#define mq2Pin 1  // MQ-2 module attach to A1 on SUNFOUNDER MEGA2560

void setup()
{
    Serial.begin(9600);
}

void loop()
{
    Serial.print("MQ-2 Value: ");
    Serial.println(analogRead(mq2Pin));
    delay(500);
}
