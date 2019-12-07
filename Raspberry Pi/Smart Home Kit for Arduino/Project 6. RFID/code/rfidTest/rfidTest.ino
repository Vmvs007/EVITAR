#include "rfid.h"

RFID rfid;

uchar serNum[5];

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
    
    // Check people associated with card ID
    uchar* id = serNum;
    if( id[0]==0x4B && id[1]==0xE6 && id[2]==0xD1 && id[3]==0x3B ) 
    {
      Serial.println("Hello Mary!");
    } 
    else if(id[0]==0x3B && id[1]==0xE6 && id[2]==0xD1 && id[3]==0x3B) 
    {
      Serial.println("Hello Greg!");
    }
    else if(id[0] == 0x15 && id[1] == 0x6F && id[2] == 0x9F && id[3] == 0xAF)
    {
      Serial.println("Collect!");
      Serial.println();
    }
    else
    {
      Serial.println("Hello unkown guy!");
    }
  }
  delay(500); 
  rfid.halt(); //command the card into sleep mode 
}
