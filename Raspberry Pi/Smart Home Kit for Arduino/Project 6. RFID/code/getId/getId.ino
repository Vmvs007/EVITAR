#include "rfid.h"

RFID rfid;

uchar serNum[5];  // array to store your ID

void setup()
{
  Serial.begin(9600);
  rfid.begin(2, 3, 4, 5, 6, 7);  
  delay(100);
  rfid.init();
}
void loop()
{
  uchar status;
  uchar str[MAX_LEN];
  status = rfid.request(PICC_REQIDL, str);
  if (status != MI_OK)
  {
    return;
  }
  
  rfid.showCardType(str);
  status = rfid.anticoll(str);
  
  if (status == MI_OK)
  {
    Serial.print("The card's number is: ");
    memcpy(serNum, str, 5);
    rfid.showCardID(serNum);
    Serial.println();
    Serial.println();
  }
  delay(500);
   
  rfid.halt(); //command the card into sleep mode 
}
