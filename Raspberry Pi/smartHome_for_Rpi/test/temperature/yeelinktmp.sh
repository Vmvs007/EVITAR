#sudo python /home/smartHome/test/temperature/temp.py
curl --request POST --data-binary @"/home/smartHome/test/temperature/datafile.txt" --header "U-ApiKey:7f0df7d7e5b94d5cc1e17314849e73e1" http://api.yeelink.net/v1.0/device/12403/sensor/20168/datapoints

