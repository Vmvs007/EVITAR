/*****************************************
 * 头文件
 ****************************************/
#include <dht11.h>
#include <SPI.h>
//#include <RFID.h>
#include <Wire.h>
#include <avr/io.h>
#include "nRF24L01.h"
#include "RF24.h"
#include "printf.h"
#include "rfid.h"

#define uchar unsigned char
#define uint unsigned int

/*
  ping-back 接受端
*/
// 设置 nRF24L01+ CE与CSN引脚
RF24 radio(9,10);
// 设置数据通道地址
const uint64_t pipes[2] = { 0xF0F0F0F0E1LL, 0xF0F0F0F0D2LL };
#define  BUFSZ   14

//引用库配置
DHT11 DHT11;
#define BMP085_ADDRESS 0x77  // I2C address of BMP085
const unsigned char OSS = 0;  // Oversampling Setting
//nRF24L01
//#define TX_ADR_WIDTH    5   // 5 unsigned chars TX(RX) address width
//#define TX_PLOAD_WIDTH  14  // 32 unsigned chars TX payload

//引脚分配
#define DHT11PIN 2
#define photocellPin 0
#define mq2Pin 1
//RFID
#define SS_PIN 10
#define RST_PIN 9
//PIR

RFID rfid;
uchar serNum[5];
//RFID rfid(SS_PIN, RST_PIN);

//变量定义
//uint lightValue = 0;
// Add these to the top of your program
const float p0 = 101325;     // Pressure at sea level (Pa)
float altitude;
// Add these to the top of your program
const float currentAltitude = 8; // current altitude in METERS
const float ePressure = p0 * pow((1-currentAltitude/44330), 5.255);  // expected pressure (in Pa) at altitude
float weatherDiff;
// Calibration values
int ac1;
int ac2; 
int ac3; 
unsigned int ac4;
unsigned int ac5;
unsigned int ac6;
int b1; 
int b2;
int mb;
int mc;
int md;
// b5 is calculated in bmp085GetTemperature(...), this variable is also used in bmp085GetPressure(...)
// so ...Temperature(...) must be called before ...Pressure(...).
long b5;
short temperature;
long pressure;
//DHT11
uchar humValue = 0;  //湿度
uchar temValue = 0;  //温度
//photocell
uchar lightValue = 0;  //光照值
//uchar lightValueL = 0;  //光照值低8位
//MQ2
uchar gasValue = 0;  //气体值
//uchar gasValueL = 0;  //气体值低8位
//RFID
uchar rfidValue = 0;  //RFID状态值
//PIR
uchar pirValue = 0;
//BMP085
uchar pressureValueH = 0;  //气压值高8位
//uchar pressureValueM = 0;  //气压值中间8位
uchar pressureValueL = 0;  //气压值低8位
uchar altitudeValueH = 0;
uchar altitudeValueL = 0;
uchar altitudeValueD = 0;
uchar weather = 0;

uchar package[14];

uchar Hun, Ten, Uni;

//初始化
void setup()   
{  
  Serial.begin(9600); 
  //pinMode(pirPin, INPUT);
  DDRC &= ~(1 << 2);  //设置PIR信号引脚为输入PC2即A2
  SPI.begin();
  rfid.init();
  Wire.begin();
  bmp085Calibration();
  nrfInit(); 
  
  rfid.begin(2, 3, 4, 5, 6, 7);
  delay(100);
  rfid.init(); 
  //NRF_init();
}

//主程序
void loop() 
{ 
  dhtPreprocess();
  dataProcess();
  radio.stopListening();
  for(int i = 0; i < BUFSZ; i ++)
  {
    Serial.println(package[i]);  
  }
  bool ok = radio.write(package, BUFSZ);
  if(ok)
  {
    printf("..............Tx 1 set ok !\n");
  }
  else
  {
    printf("Tx 1 set failed !\n");
  }
  radio.startListening();
  for(int i = 0; i < 11; i ++)
  {
    delay(1000);
  }
}

//温湿度传感器预处理程序
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

/********************************************
 * 函数名:   读取温度值
 * 功能描述: 读取DHT11温度值
 * 返回：    十进制温度值
 *******************************************/
uchar readTem(void)
{
  return DHT11.temperature;
}
/********************************************
 * 函数名:   读取湿度值
 * 功能描述: 读取DHT11湿度值
 * 返回：    十进制湿度值
 *******************************************/
uchar readHum(void)
{
  return DHT11.humidity;
}
/********************************************
 * 函数名:   读取光照强度
 * 功能描述: 读取十次，求平均值
 * 返回：    十进制AD值
 *******************************************/
uint readLight(void)
{
  uchar i;
  uint lightVal = 0;
  for(i = 0; i < 10; i ++)
  {
    lightVal = lightVal + analogRead(photocellPin);
  }
  return ( lightVal/10 );
}
/********************************************
 * 函数名:   读取光照强度
 * 功能描述: 读取十次，求平均值
 * 返回：    十进制AD值
 *******************************************/
uchar readPir(void)
{
  if(PINC&(1 << 2))
    return 1;
  else 
    return 0;
}
/********************************************
 * 函数名:   读取气体值
 * 功能描述: 读取气体传感器MQ2的值
 * 返回：    十进制AD值
 *******************************************/
uint readGas(void)
{
  return analogRead(mq2Pin);
}
/********************************************
 * 函数名:   读取RFID值
 * 功能描述: 读取RFID
 * 返回：    0/1,检测到标签返回1，否则返回0
 *******************************************/
uchar readRfid(void)
{
  uchar status;
  uchar str[MAX_LEN];
  status = rfid.request(PICC_REQIDL, str);
  if (status != MI_OK)
  {
    return 0;
  }
  
  rfid.showCardType(str);
  status = rfid.anticoll(str);
  
  if (status == MI_OK)
  {
    Serial.print("The card's number is: ");
    memcpy(serNum, str, 5);
    rfid.showCardID(serNum);
    
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
      //Serial.println("Hello SUNFOUNDER!");
      //digitalWrite(8, HIGH);
      return 1;
    }
    else
    {
      //Serial.println("Hello unkown guy!");
      return 0;
    }
  }
  //delay(1000);
  //digitalWrite(8, LOW);
  return 0;
   
  rfid.halt(); //command the card into sleep mode 
}
/********************************************
 * 函数名:   BMP085校准
 * 功能描述: 校准BMP085,读取出厂设定值
 * 返回：    none
 *******************************************/
// Stores all of the bmp085's calibration values into global variables
// Calibration values are required to calculate temp and pressure
// This function should be called at the beginning of the program
void bmp085Calibration(void)
{
  ac1 = bmp085ReadInt(0xAA);
  ac2 = bmp085ReadInt(0xAC);
  ac3 = bmp085ReadInt(0xAE);
  ac4 = bmp085ReadInt(0xB0);
  ac5 = bmp085ReadInt(0xB2);
  ac6 = bmp085ReadInt(0xB4);
  b1 = bmp085ReadInt(0xB6);
  b2 = bmp085ReadInt(0xB8);
  mb = bmp085ReadInt(0xBA);
  mc = bmp085ReadInt(0xBC);
  md = bmp085ReadInt(0xBE);
}
// Calculate temperature given ut.
// Value returned will be in units of 0.1 deg C
short bmp085GetTemperature(unsigned int ut)
{
  long x1, x2;
  
  x1 = (((long)ut - (long)ac6)*(long)ac5) >> 15;
  x2 = ((long)mc << 11)/(x1 + md);
  b5 = x1 + x2;

  return ((b5 + 8)>>4);  
}
// Calculate pressure given up
// calibration values must be known
// b5 is also required so bmp085GetTemperature(...) must be called first.
// Value returned will be pressure in units of Pa.
long bmp085GetPressure(unsigned long up)
{
  long x1, x2, x3, b3, b6, p;
  unsigned long b4, b7;
  
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
// Read 1 byte from the BMP085 at 'address'
char bmp085Read(unsigned char address)
{
  unsigned char data;
  
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(address);
  Wire.endTransmission();
  
  Wire.requestFrom(BMP085_ADDRESS, 1);
  while(!Wire.available())
    ;
    
  return Wire.read();
}
// Read 2 bytes from the BMP085
// First byte will be from 'address'
// Second byte will be from 'address'+1
int bmp085ReadInt(unsigned char address)
{
  unsigned char msb, lsb;
  
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
// Read the uncompensated temperature value
unsigned int bmp085ReadUT()
{
  unsigned int ut;
  
  // Write 0x2E into Register 0xF4
  // This requests a temperature reading
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF4);
  Wire.write(0x2E);
  Wire.endTransmission();
  
  // Wait at least 4.5ms
  delay(5);
  
  // Read two bytes from registers 0xF6 and 0xF7
  ut = bmp085ReadInt(0xF6);
  return ut;
}
// Read the uncompensated pressure value
unsigned long bmp085ReadUP()
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

/**************************************************/
void dataProcess(void)
{
  humValue = readHum();
  temValue = readTem();
  lightValue = map(readLight(), 0, 675, 0, 100);
  //lightValueH = readLight() / 256;
  //lightValueL = readLight() % 256;
  gasValue = map(readGas(), 0, 1023, 0, 100);
  //gasValueH = readGas() / 256;
  //gasValueL = readGas() % 256;
  rfidValue = readRfid();
  pirValue = readPir();
  pressure = bmp085GetPressure(bmp085ReadUP()) / 100;
  pressureValueH = pressure / 256;  
  //pressureValueM = pressure % 65536 / 256;
  pressureValueL = pressure % 256;
  altitude = (float)44330 * (1 - pow(((float) pressure/p0), 0.190295));
  altitudeValueH = altitude / 256;
  altitudeValueL = (int)altitude % 256;
  altitudeValueD = (int)(altitude * 100) % 100;
  weatherDiff = pressure - ePressure;
  if(weatherDiff > 250)
    weather = 0;
  else if ((weatherDiff <= 250) || (weatherDiff >= -250))
    weather = 1;
  else if (weatherDiff > -250)
    weather = 2;
  
  package[0] = 0xfc;
  package[1] = humValue;
  package[2] = temValue;
  package[3] = lightValue;
  //package[4] = lightValueL;
  package[4] = gasValue;
  //package[6] = gasValueL;
  package[5] = rfidValue;
  package[6] = pirValue;
  package[7] = pressureValueH;
  //package[8] = pressureValueM;
  package[8] = pressureValueL;
  package[9] = altitudeValueH;
  package[10] = altitudeValueL;
  package[11] = altitudeValueD;
  package[12] = weather;
  package[13] = 0xFD;
}

void nrfInit(void)
{
  printf_begin();
    printf("\n\rRF24/examples/pingpair/\n\r");
    printf("ROLE: Pong back\n\r");
    //
    // 设置rf模块
    //
    radio.begin();

    // 开启动态有效信息长度
    radio.enableDynamicPayloads();

    // 设置重传次数以及每次重传的延迟
    //radio.setRetries(15,15);

    // 设置传输速率
    radio.setDataRate(RF24_1MBPS);

    // 设置功放级别，有四种级别：
    // RF24_PA_MIN=-18dBm
    // RF24_PA_LOW=-12dBm
    // RF24_PA_MED=-6dBM
    // RF24_PA_HIGH=0dBm
    radio.setPALevel(RF24_PA_HIGH);

    // 设置信道(0-127)
    radio.setChannel(110);

    // 设置crc校验长度
    // 两种 8位RF24_CRC_8 和 16位RF24_CRC_16
    radio.setCRCLength(RF24_CRC_16);
    // 打开两个通道用于两个设备进行来回的通信

    // 打开本端的通道用来写消息
    radio.openWritingPipe(pipes[1]);

    // 打开对方的通道用来读消息
    radio.openReadingPipe(1,pipes[0]);
    //
    // 开始监听
    //
    radio.startListening();
    //
    // 打印配置信息
    //
    radio.printDetails();
}
