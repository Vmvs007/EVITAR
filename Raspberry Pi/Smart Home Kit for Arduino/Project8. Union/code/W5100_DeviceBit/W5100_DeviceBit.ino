/*********************************************************************
**   SPI-compatible                                                 **
**   CS - to digital pin 22                                          **
**   CSN - to digital pin 24  (SS pin)                               **
**   SCK - to digital pin 26 (SCK pin)                              **
**   MOSI - to digital pin 28 (MOSI pin)                            **
**   MISO - to digital pin 30 (MISO pin)                            **
**   IRQ - to digital pin 32 (MISO pin)                             **
*********************************************************************/
#include <SPI.h>
#include <Ethernet.h>
#include <DeviceBitTcpClient.h>
#include <EEPROM.h>
#include <dht11.h>
#include "NRF24L01.h"
#include "rfid.h"
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP085_U.h>
#include <avr/interrupt.h>

//Common data types macro definition
#define uint8 unsigned char
#define uint16 unsigned int
#define uint32 unsigned long

//Library configuration
DHT11 DHT11;
RFID rfid;
uchar serNum[5];
Adafruit_BMP085_Unified bmp = Adafruit_BMP085_Unified(10085);


#define TX_ADR_WIDTH    5   // 5 unsigned chars TX(RX) address width
#define TX_PLOAD_WIDTH  3  // 32 unsigned chars TX payload

unsigned char TX_ADDRESS[TX_ADR_WIDTH]  = 
{
  0x34,0x43,0x10,0x10,0x01
}; // Define a static TX address

unsigned char rx_buf[TX_PLOAD_WIDTH] = {0}; // initialize value
uint8 tx_buf[TX_PLOAD_WIDTH] = {0};

#define LW_USERKEY "611442bf3e1344ddac7302fe43187e91" // USEEKEY
#define LW_GATEWAY "01"                               // User gateway Numbers

//byte mac[] = {0xD0,0xC7,0xC0,0xAC,0xC5,0x81};
//IPAddress ip(192,168,0,135);
//IPAddress mydns(223,5,5,5);
//IPAddress gw(192,168,0,1);
//IPAddress subnet(255,255,255,0);
 
DeviceBitTcpClient *client;

int postInterval = 30000;

unsigned long lastConnectionTime = 0;          // last time you connected to the server, in milliseconds
boolean lastConnected = false;                 // state of the connection last time through the main loop
const unsigned long postingInterval = 30*1000; //delay between updates to cosm.com

unsigned long duration;
unsigned long starttime;
unsigned long sampletime_ms = 10000;
unsigned long lowpulseoccupancy = 0;
float ratio = 0;
double concentration = 0;

//各种变量或常量
#define p0 101325     // Pressure at sea level (Pa) p0 = 101325;
#define currentAltitude 8 // current altitude in METERS
const float epressure = p0 * pow((1-currentAltitude/44330), 5.255);  // expected pressure (in Pa) at altitude
#define BMP085_ADDRESS 0x77  // I2C address of BMP085
const unsigned char OSS = 0;  // Oversampling Setting

float pressure = 0;
float temperature = 0;
float altitude = 0;
char weather;

#define DELAYTIME 1000
#define F_CPU 16000000
#define F_DIV 1024
#define TIME 0.1
volatile uint8 count1,count2 = 0;

// Calibration values
int ac1; int ac2; int ac3;       
uint16 ac4; uint16 ac5; uint16 ac6;       
int b1; int b2;     
int mb; int mc; int md;
// b5 is calculated in bmp085GetTemperature(...), this variable is also used in bmp085GetPressure(...)
// so ...Temperature(...) must be called before ...Pressure(...).
long b5;

uint8 pirValue = 0;
boolean pirState = false;
uint8 rfidValue = 0;
boolean rfidState = false;


//引脚分配
#define photocellPin 0
#define mq2Pin 1
#define light1Pin 8  //Set the control operation of I/o port
#define light2Pin 9  
#define pirPin 36
#define DHT11PIN 34
#define rfidLED 38
#define pirLED 40

void setup() 
{
    Serial.begin(9600);
    if (!bmp.begin()) 
    {
	Serial.println("Could not find a valid BMP085 sensor, check wiring!");
	while (1);
    }
    //Wire.begin();
    //calibration();
    rfid.begin(2, 3, 4, 5, 6, 7);
    delay(100);
    rfid.init();
    pinMode(pirPin, INPUT);
    pinMode(rfidLED, OUTPUT);
    pinMode(pirLED, OUTPUT);
    
    pinMode(CE,  OUTPUT);
    pinMode(SCK_PIN, OUTPUT);
    pinMode(CSN, OUTPUT);
    pinMode(MOSI_PIN,  OUTPUT);
    pinMode(MISO_PIN, INPUT);
    pinMode(IRQ, INPUT);
    init_io();                        // Initialize IO port
    unsigned char status=SPI_Read(STATUS);
    Serial.print("status = ");    
    Serial.println(status,HEX);     // There is read the mode’s status register, the default value should be ‘E’
    Serial.println("*******************TX_Mode Start****************************");
    TX_Mode();
    
    TIMER1_init();

    client = new DeviceBitTcpClient(LW_USERKEY, LW_GATEWAY);
    //client = new LeweiTcpClient(LW_USERKEY, LW_GATEWAY,mac,ip,mydns,gw,subnet);
    UserSwitch us1(switchLight1,"light1",1);
    client->addUserSwitch(us1);
    UserSwitch us2(switchLight2,"light2",1);
    client->addUserSwitch(us2);
    UserSwitch us3(switchLight3,"light3",1);
    client->addUserSwitch(us3);
    UserSwitch us4(switchLight4,"light4",1);
    client->addUserSwitch(us4);
    starttime = millis();
    pinMode(light1Pin,OUTPUT);
    pinMode(light2Pin,OUTPUT);
}


void loop() 
{
    //long pressure = 0;
    //float altitude = 0;
    
  client->keepOnline();
  if ((millis()-starttime) > postInterval)
  {
      //humiture
      dhtPreprocess();
      client -> appendSensorValue("Humidity", DHT11.humidity); 
      delay(DELAYTIME);
      client -> appendSensorValue("Temperature", DHT11.temperature);
      delay(DELAYTIME);
      //illumination intensity
      client -> appendSensorValue("Light",analogRead(photocellPin));    
      delay(DELAYTIME);
      //gas sensor
      client -> appendSensorValue("Gas",analogRead(mq2Pin));  
      delay(DELAYTIME);
      //long pressure = 0;
      //float altitude = 0;
      //getBmpData(&pressure, &altitude);
      //int pressureInt = (int)pressure;
      readBmp();
      client -> appendSensorValue("Pressure", pressure);
      delay(DELAYTIME);
      client -> appendSensorValue("Altitude",weather);
      delay(DELAYTIME);  
      //人体红外感应      
      client->appendSensorValue("PIR",pirValue);         
      delay(DELAYTIME);
      //RFID
      client->sendSensorValue("RFID",rfidValue); 
      delay(DELAYTIME);      
      Serial.println("repeat lwc->send  ...Completed.");
      starttime = millis();
  }
}  

void stateChange1()
{
  if(digitalRead(light1Pin)==HIGH)
  {
    switchLight1("0");
  }
  else
  {
    switchLight1("1");
  }
}
void stateChange2()
{
  if(digitalRead(light2Pin)==HIGH)
  {
    switchLight2("0");
  }
  else
  {
    switchLight2("1");
  }
}

void switchLight1(char* p1)
{
    Serial.println("switch1 test");  
  //  stateChange1();
    if(String(p1).equals("0"))
    {
        digitalWrite(light1Pin,HIGH);
        tx_buf[0] = 0x55;
        tx_buf[1] = 0xAA;
        tx_buf[2] = 0x11;
        unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
        if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
        {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
        }
        if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
        {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
        }
        SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
        //delay(1000);
    }
    else
    {
        digitalWrite(light1Pin,LOW);
        tx_buf[0] = 0x55;
        tx_buf[1] = 0xAA;
        tx_buf[2] = 0x10;
        unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
        if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
        {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
        }
        if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
        {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
        }
        SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
        //delay(1000);
    }
    client->checkFreeMem();
}

void switchLight2(char* p1)
{
  Serial.println("switch2 test"); 
//  stateChange2();
  if(String(p1).equals("0"))
  {
      digitalWrite(light2Pin,HIGH);
      tx_buf[0] = 0x55;
      tx_buf[1] = 0xAA;
      tx_buf[2] = 0x21;
      unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
      if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
      {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
       }
       if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
       {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
       }
       SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
        //delay(1000);
  }
  else
  {
      digitalWrite(light2Pin,LOW);
      tx_buf[0] = 0x55;
      tx_buf[1] = 0xAA;
      tx_buf[2] = 0x20;
      unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
      if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
      {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
      }
      if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
      {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
      }
      SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
        //delay(1000);
  }
  client->checkFreeMem();
}

void switchLight3(char* p1)
{
    Serial.println("switch1 test");  
  //  stateChange1();
    if(String(p1).equals("0"))
    {
        //digitalWrite(light3Pin,HIGH);
        tx_buf[0] = 0x55;
        tx_buf[1] = 0xAA;
        tx_buf[2] = 0x31;
        unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
        if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
        {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
        }
        if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
        {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
        }
        SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
    }
    else
    {
        //digitalWrite(light3Pin,LOW);
        tx_buf[0] = 0x55;
        tx_buf[1] = 0xAA;
        tx_buf[2] = 0x30;
        unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
        if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
        {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
        }
        if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
        {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
        }
        SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
    }
    client->checkFreeMem();
}

void switchLight4(char* p1)
{
  Serial.println("switch2 test"); 
//  stateChange2();
  if(String(p1).equals("0"))
  {
      //digitalWrite(light4Pin,HIGH);
      tx_buf[0] = 0x55;
      tx_buf[1] = 0xAA;
      tx_buf[2] = 0x41;
      unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
      if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
      {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
       }
       if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
       {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
       }
       SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
  }
  else
  {
      //digitalWrite(light4Pin,LOW);
      tx_buf[0] = 0x55;
      tx_buf[1] = 0xAA;
      tx_buf[2] = 0x40;
      unsigned char status = SPI_Read(STATUS);                   // read register STATUS's value
      if(status&TX_DS)                                           // if receive data ready (TX_DS) interrupt
      {
          SPI_RW_Reg(FLUSH_TX,0);                                  
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);       // write playload to TX_FIFO
      }
      if(status&MAX_RT)                                         // if receive data ready (MAX_RT) interrupt, this is retransmit than  SETUP_RETR                          
      {
          SPI_RW_Reg(FLUSH_TX,0);
          SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);      // disable standy-mode
      }
      SPI_RW_Reg(WRITE_REG+STATUS,status);                     // clear RX_DR or TX_DS or MAX_RT interrupt flag
  }
  client->checkFreeMem();
}

//**************************************************
// Function: init_io();
// Description:
// flash led one time,chip enable(ready to TX or RX Mode),
// Spi disable,Spi clock line init high
//**************************************************
void init_io(void)
{
  digitalWrite(IRQ, 0);
  digitalWrite(CE, 0);			// chip enable
  digitalWrite(CSN, 1);                 // Spi disable	
}

/**************************************************
 * Function: SPI_RW();
 * 
 * Description:
 * Writes one unsigned char to nRF24L01, and return the unsigned char read
 * from nRF24L01 during write, according to SPI protocol
 **************************************************/
unsigned char SPI_RW(unsigned char Byte)
{
  unsigned char i;
  for(i=0;i<8;i++)                      // output 8-bit
  {
    if(Byte&0x80)
    {
      digitalWrite(MOSI_PIN, 1);
    }
    else
    {
      digitalWrite(MOSI_PIN, 0);
    }
    digitalWrite(SCK_PIN, 1);
    Byte <<= 1;                         // shift next bit into MSB..
    if(digitalRead(MISO_PIN) == 1)
    {
      Byte |= 1;       	                // capture current MISO bit
    }
    digitalWrite(SCK_PIN, 0);
  }
  return(Byte);           	        // return read unsigned char
}
/**************************************************/

/**************************************************
 * Function: SPI_RW_Reg();
 * 
 * Description:
 * Writes value 'value' to register 'reg'
/**************************************************/
unsigned char SPI_RW_Reg(unsigned char reg, unsigned char value)
{
  unsigned char status;

  digitalWrite(CSN, 0);                   // CSN low, init SPI transaction
  status = SPI_RW(reg);                   // select register
  SPI_RW(value);                          // ..and write value to it..
  digitalWrite(CSN, 1);                   // CSN high again

  return(status);                   // return nRF24L01 status unsigned char
}
/**************************************************/

/**************************************************
 * Function: SPI_Read();
 * 
 * Description:
 * Read one unsigned char from nRF24L01 register, 'reg'
/**************************************************/
unsigned char SPI_Read(unsigned char reg)
{
  unsigned char reg_val;

  digitalWrite(CSN, 0);           // CSN low, initialize SPI communication...
  SPI_RW(reg);                   // Select register to read from..
  reg_val = SPI_RW(0);           // ..then read register value
  digitalWrite(CSN, 1);          // CSN high, terminate SPI communication
  
  return(reg_val);               // return register value
}
/**************************************************/

/**************************************************
 * Function: SPI_Read_Buf();
 * 
 * Description:
 * Reads 'unsigned chars' #of unsigned chars from register 'reg'
 * Typically used to read RX payload, Rx/Tx address
/**************************************************/
unsigned char SPI_Read_Buf(unsigned char reg, unsigned char *pBuf, unsigned char bytes)
{
  unsigned char status,i;

  digitalWrite(CSN, 0);                  // Set CSN low, init SPI tranaction
  status = SPI_RW(reg);       	    // Select register to write to and read status unsigned char

  for(i=0;i<bytes;i++)
  {
    pBuf[i] = SPI_RW(0);    // Perform SPI_RW to read unsigned char from nRF24L01
  }

  digitalWrite(CSN, 1);                   // Set CSN high again

  return(status);                  // return nRF24L01 status unsigned char
}
/**************************************************/

/**************************************************
 * Function: SPI_Write_Buf();
 * 
 * Description:
 * Writes contents of buffer '*pBuf' to nRF24L01
 * Typically used to write TX payload, Rx/Tx address
/**************************************************/
unsigned char SPI_Write_Buf(unsigned char reg, unsigned char *pBuf, unsigned char bytes)
{
  unsigned char status,i;

  digitalWrite(CSN, 0);                  // Set CSN low, init SPI tranaction
  status = SPI_RW(reg);             // Select register to write to and read status unsigned char
  for(i=0;i<bytes; i++)             // then write all unsigned char in buffer(*pBuf)
  {
    SPI_RW(*pBuf++);
  }
  digitalWrite(CSN, 1);                   // Set CSN high again
  return(status);                  // return nRF24L01 status unsigned char
}
/**************************************************/

/**************************************************
 * Function: TX_Mode();
 * 
 * Description:
 * This function initializes one nRF24L01 device to
 * TX mode, set TX address, set RX address for auto.ack,
 * fill TX payload, select RF channel, datarate & TX pwr.
 * PWR_UP is set, CRC(2 unsigned chars) is enabled, & PRIM:TX.
 * 
 * ToDo: One high pulse(>10us) on CE will now send this
 * packet and expext an acknowledgment from the RX device.
 **************************************************/
void TX_Mode(void)
{
  digitalWrite(CE, 0);

  SPI_Write_Buf(WRITE_REG + TX_ADDR, TX_ADDRESS, TX_ADR_WIDTH);    // Writes TX_Address to nRF24L01
  SPI_Write_Buf(WRITE_REG + RX_ADDR_P0, TX_ADDRESS, TX_ADR_WIDTH); // RX_Addr0 same as TX_Adr for Auto.Ack

  SPI_RW_Reg(WRITE_REG + EN_AA, 0x01);      // Enable Auto.Ack:Pipe0
  SPI_RW_Reg(WRITE_REG + EN_RXADDR, 0x01);  // Enable Pipe0
  SPI_RW_Reg(WRITE_REG + SETUP_RETR, 0x1a); // 500us + 86us, 10 retrans...
  SPI_RW_Reg(WRITE_REG + RF_CH, 40);        // Select RF channel 40
  SPI_RW_Reg(WRITE_REG + RF_SETUP, 0x07);   // TX_PWR:0dBm, Datarate:2Mbps, LNA:HCURR
  SPI_RW_Reg(WRITE_REG + CONFIG, 0x0e);     // Set PWR_UP bit, enable CRC(2 unsigned chars) & Prim:TX. MAX_RT & TX_DS enabled..
  SPI_Write_Buf(WR_TX_PLOAD,tx_buf,TX_PLOAD_WIDTH);

  digitalWrite(CE, 1);
}

 //Temperature and humidity sensor preprocessor
void dhtPreprocess(void)
{
  int chk = DHT11.read(DHT11PIN);
  switch (chk)
  {
    case DHTLIB_OK:  
		break;
    case DHTLIB_ERROR_CHECKSUM: 
		break;
    case DHTLIB_ERROR_TIMEOUT:  
		break;
    default:  
		break;
  }
}

void getBmpData(long *pre, float *alt)
{
    *pre = getPressure(readUP());
    *alt = (float)44330 * (1 - pow(((float) (*pre)/p0), 0.190295));
}

long getPressure(uint32 up)
{
  long x1, x2, x3, b3, b6, p;
  uint32 b4, b7;
  
  b6 = b5 - 4000;
  // Calculate B3
  x1 = (b2 * (b6 * b6)>>12)>>11;
  x2 = (ac2 * b6)>>11;
  x3 = x1 + x2;
  b3 = (((((long)ac1)*4 + x3)<<OSS) + 2)>>2;
  
  // Calculate B4
  x1 = (ac3 * b6)>>13;
  x2 = (b1 * ((b6 * b6)>>12))>>16;
  x3 = ((x1 + x2) + 2)>>2;
  b4 = (ac4 * (unsigned long)(x3 + 32768))>>15;
  
  b7 = ((unsigned long)(up - b3) * (50000>>OSS));
  if (b7 < 0x80000000)
    p = (b7<<1)/b4;
  else
    p = (b7/b4)<<1;
    
  x1 = (p>>8) * (p>>8);
  x1 = (x1 * 3038)>>16;
  x2 = (-7357 * p)>>16;
  p += (x1 + x2 + 3791)>>4;
  
  return p;
}

// Read the uncompensated pressure value
unsigned long readUP(void)
{
  unsigned char msb, lsb, xlsb;
  unsigned long up = 0;
  
  // Write 0x34+(OSS<<6) into register 0xF4
  // Request a pressure reading w/ oversampling setting
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF4);
  Wire.write(0x34 + (OSS<<6));
  Wire.endTransmission();
  
  // Wait for conversion, delay time dependent on OSS
  delay(2 + (3<<OSS));
  
  // Read register 0xF6 (MSB), 0xF7 (LSB), and 0xF8 (XLSB)
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF6);
  Wire.endTransmission();
  Wire.requestFrom(BMP085_ADDRESS, 3);
  
  // Wait for data to become available
  while(Wire.available() < 3)
    ;
  msb = Wire.read();
  lsb = Wire.read();
  xlsb = Wire.read();
  
  up = (((unsigned long) msb << 16) | ((unsigned long) lsb << 8) | (unsigned long) xlsb) >> (8-OSS);
  
  return up;
}

void calibration(void)
{
    ac1 = readInt(0xAA);
    ac2 = readInt(0xAC);
    ac3 = readInt(0xAE);
    ac4 = readInt(0xB0);
    ac5 = readInt(0xB2);
    ac6 = readInt(0xB4);
    b1  = readInt(0xB6);
    b2  = readInt(0xB8);
    mb  = readInt(0xBA);
    mc  = readInt(0xBC);
    md  = readInt(0xBE);
}

// Read 2 bytes from the BMP085
// First byte will be from 'address'
// Second byte will be from 'address'+1
int readInt(uint8 address)
{
  uint8 msb, lsb;
  
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(address);
  Wire.endTransmission();
  
  Wire.requestFrom(BMP085_ADDRESS, 2);
  while(Wire.available()<2)
    ;
  msb = Wire.read();
  lsb = Wire.read();
  
  return (int) msb<<8 | lsb;
}

void TIMER1_init(void)
{
    //cli();
    TCCR1A=0;  //Shielding PWM output of timer1
    TCCR1B = 0x05;
    TCNT1 = 65536 - F_CPU / F_DIV * TIME;  
    TIMSK1 |= _BV(TOIE1);  //Open the timer1 interrupt
    SREG |= _BV(7);  
}

ISR(TIMER1_OVF_vect)
{
    TCNT1 = 65536 - F_CPU / F_DIV * TIME; 
    count1 ++;
    if(count1 == 5)
    {
        count1 = 0;   
        if(readRfid())
        {
            rfidValue ++;
            rfidState = ~rfidState;
            digitalWrite(rfidLED, rfidState);
         }         
    }
    if(digitalRead(pirPin))
    {
        while(digitalRead(pirPin));         
        pirValue ++;
        pirState = ~pirState;
        digitalWrite(pirLED, pirState);
     }      
}

uint8 readRfid(void)
{
  uint8 status;
  uint8 str[MAX_LEN];
  status = rfid.request(PICC_REQIDL, str);
  if (status != MI_OK)
  {
    return 0;
  }
  status = rfid.anticoll(str);
  
  if (status == MI_OK)
  {
    memcpy(serNum, str, 5);
    
    // Check people associated with card ID
    uchar* id = serNum;

    if(id[0] == 0x15 && id[1] == 0x6F && id[2] == 0x9F && id[3] == 0xAF)
    {
        return 1;
    }
  }
  return 0;
   
  rfid.halt(); //command the card into sleep mode 
}

void readBmp(void)
{
    /* Get a new sensor event */ 
    sensors_event_t event;
    bmp.getEvent(&event);
    
    /* Display the results (barometric pressure is measure in hPa) */
    if (event.pressure)
    {
        pressure = event.pressure;
        bmp.getTemperature(&temperature);
        if((pressure - epressure) > 250)
        {
            weather = 1;
        }
        else if(((pressure - epressure) <= 250) && ((pressure - epressure) >= -250))
        {
            weather = 0;
        }
        else if((pressure - epressure) < -250)
        {
            weather = -1;
        }
        float seaLevelPressure = SENSORS_PRESSURE_SEALEVELHPA;
        altitude = bmp.pressureToAltitude(seaLevelPressure,event.pressure,temperature);
    }
    else
    {
        Serial.println("Sensor error");
    }
}

 


