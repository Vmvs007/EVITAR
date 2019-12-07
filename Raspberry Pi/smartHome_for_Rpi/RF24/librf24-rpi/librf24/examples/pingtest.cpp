#include <stdio.h>
#include <cstdlib>
#include <iostream>
#include <time.h>
#include <string.h>

#include "../RF24.h"

RF24 radio("/dev/spidev0.0", 8000000 , 8); 

const uint64_t pipes[2] = { 0xF0F0F0F0E1LL, 0xF0F0F0F0D2LL };

#define        BUFSZ         14

char Sensor_Rx_Buf[BUFSZ] = {0};

#if 1   //debug
void ch_dat(void)
{
	srand(time(NULL));	
	Sensor_Rx_Buf[0] = 20 + rand() % 20;
	Sensor_Rx_Buf[1] = 30 + rand() % 40;
	Sensor_Rx_Buf[2] = 35 + rand() % 45;
	Sensor_Rx_Buf[3] = 14 + rand() % 25;
	Sensor_Rx_Buf[4] = 42 + rand() % 32;
	Sensor_Rx_Buf[5] = 35 + rand() % 28;
	Sensor_Rx_Buf[6] = 43 + rand() % 48;
	Sensor_Rx_Buf[7] = 30 + rand() % 40;
	Sensor_Rx_Buf[8] = 35 + rand() % 76;
	Sensor_Rx_Buf[9] = 29 + rand() % 20;
	Sensor_Rx_Buf[10] = 30 + rand() % 25;
	Sensor_Rx_Buf[11] = 44 + rand() % 45;
	Sensor_Rx_Buf[12] = 58 + rand() % 16;
	Sensor_Rx_Buf[13] = 21 + rand() % 56;
}
#endif

void setup(void) 
{
	radio.begin();
	radio.enableDynamicPayloads();
	radio.setDataRate(RF24_1MBPS);
	radio.setPALevel(RF24_PA_HIGH);
	radio.setChannel(110);
	radio.setCRCLength(RF24_CRC_16);
	radio.openWritingPipe(pipes[0]);
	radio.openReadingPipe(1,pipes[1]);
	radio.startListening();
	radio.printDetails();
}

static void head_WR(FILE *fp)
{
	const char head = '[';

	fprintf(fp, "%c", head);
}

static void comma_WR(FILE *fp)  // , fen ge fu
{
	const char comma = ',';

	fprintf(fp, "%c", comma);
}

static void dot_WR(FILE *fp)  // , fen ge fu
{
	const char dot = '.';

	fprintf(fp, "%c", dot);
}

static void newline_WR(FILE *fp)
{
	const char newline = '\n';

	fprintf(fp, "%c", newline);
}

static void tail_WR(FILE *fp)
{
	const char tail = ']';

	fprintf(fp, "%c", tail);

	fclose(fp);
}

static void one_dat_name_WR(FILE *fp, const char *name, int len)
{
	int i;
	const char headBuf[] = {'{','"','N','a','m','e','"',':','"'};
	
	for(i = 0; i < sizeof(headBuf); i++){
		fprintf(fp, "%c", headBuf[i]);
	}

	for(i = 0; i < len; i++){
		fprintf(fp, "%c", *(name + i));	
	}
	fprintf(fp, "%c", '"');

	comma_WR(fp);  // ,
}

static void one_dat_val_WR(FILE *fp, const unsigned char val_i, const unsigned char val_d )
{
	unsigned int i;
	const char headBuf[] = {'"','V','a','l','u','e','"',':','"'};
		
	for(i = 0; i < sizeof(headBuf); i++){
		fprintf(fp, "%c", headBuf[i]);
	}

	fprintf(fp, "%d", val_i);	
	dot_WR(fp);		
	fprintf(fp, "%d", val_d);	
	fprintf(fp, "%c", '"');
	fprintf(fp, "%c", '}');
}

static void one_dat_WR(FILE *fp, const char * sensor_name, int len, const char val_i, const char val_d)  //fp, snesor_name, zhengshu, xiaoshu
{
	one_dat_name_WR(fp, sensor_name, len);
	one_dat_val_WR(fp, val_i, val_d);
}

static void w_sensorDat2files(void)
{
	FILE *fp = NULL;
	
	printf("Starting write data to file---------->\n");

	fp = fopen("/home/smartHome_for_Rpi/RF24-master/librf24-rpi/librf24/examples/sensorDat/sensorDat.txt", "w");
	if(fp == NULL){
		perror("open sensorDat.txt failed !");
	}
	
	head_WR(fp);          //write packet head
	newline_WR(fp);       //newline
	one_dat_WR(fp, "temp", 4, Sensor_Rx_Buf[0], Sensor_Rx_Buf[1]);  //write temp sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "humi", 4, Sensor_Rx_Buf[2], Sensor_Rx_Buf[3]);  //write humi sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "illum", 5, Sensor_Rx_Buf[4], Sensor_Rx_Buf[5]);  //write illum sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "airPressure", 11, Sensor_Rx_Buf[6], Sensor_Rx_Buf[7]);  //write airpressure sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "gas", 3, Sensor_Rx_Buf[8], Sensor_Rx_Buf[9]);  //write gas sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "altitude", 8, Sensor_Rx_Buf[10], Sensor_Rx_Buf[11]);  //write altitude sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "pir", 3, Sensor_Rx_Buf[12], Sensor_Rx_Buf[13]);  //write altitude sensor data
	comma_WR(fp);         //write comma
	newline_WR(fp);       //newline
	
	one_dat_WR(fp, "RFID", 4, Sensor_Rx_Buf[8], Sensor_Rx_Buf[6]);  //write altitude sensor data
	
	newline_WR(fp);       //newline
	tail_WR(fp);          //write packet tail
	
	printf("---------------------------------------------------Write data to file success !\n");
	
#if 0
	int tmp0 = 256 * Sensor_Rx_Buf[7] + Sensor_Rx_Buf[8];
	w_dat2file_whead(fp_s6);
	w_dat2file_wdat(fp_s6, tmp0, 0);
	w_dat2file_wtail(fp_s6);

	fp_s7 = fopen("./sensorDat/altitude.txt", "w");
	if(fp_s5 == NULL){
		perror("open ./sensorDat/altitude.txt failed !");
	}
	int tmp1 = 256 * Sensor_Rx_Buf[9] + Sensor_Rx_Buf[10];
	w_dat2file_whead(fp_s7);
	w_dat2file_wdat(fp_s7, tmp1, Sensor_Rx_Buf[11]);
	w_dat2file_wtail(fp_s7);
#endif
}

void loop(void) 
{
	int i;
#if 0	
	//radio.stopListening();
	bool done = false;

	if(radio.available()){
		printf("Debug radio.available\n");
		while(!done){
			done = radio.read(Sensor_Rx_Buf, BUFSZ);	
			while(!((Sensor_Rx_Buf[0] == 0xfc) && (Sensor_Rx_Buf[13] == 0xfd)));
#endif
			ch_dat();
			printf("Sensor_Rx_Buf : ");	
			for(i = 0; i < BUFSZ; i++){
				printf("%02d ", Sensor_Rx_Buf[i]);	
			}
			printf("\n");
			w_sensorDat2files();
			memset(Sensor_Rx_Buf, 0, 14);
			printf("Start upload data to deviceBit ------------>\n");
			system("/home/smartHome_for_Rpi/RF24-master/librf24-rpi/librf24/examples/deviceBit/upload.sh");
			printf("----------------Update data to deviceBit platform success !\n\n");
			sleep(10);    //sleep 10s
#if 0
		}	
	}
#endif
}

int main(int argc, char * argv[]) 
{
	setup();

	while(1){
		loop();
	}

	return 0;
}
