#define photocellPin 0  // photocell attach to A0 on SUNFOUNDER MEGA2560

void setup()
{
    Serial.begin(9600);
}

void loop()
{
    Serial.print("Photocell Value: ");
    Serial.println(analogRead(photocellPin));
    delay(500);
}
