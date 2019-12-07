#include <wiringPiI2C.h>  
#include <stdio.h>  
#include <stdlib.h>  
#include <errno.h>  

#define  DevAddr  0x23  //device address  

int main(void)  
{  
	int fd;  
	int tmp;

	fd = wiringPiI2CSetup(DevAddr);  
	if(-1 == fd){  
		perror("I2C device setup error");     
	}  

	while(1){ 
		wiringPiI2CWrite(fd, 99);
//		tmp = wiringPiI2CRead(fd);
		printf("tmp = %c | %d\n", (char)tmp, tmp);
		delay(500);  
	}  

	return 0;  
}  
