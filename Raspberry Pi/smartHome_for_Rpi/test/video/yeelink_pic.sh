sudo fswebcam -d /dev/video0 -r 640x480 --bottom-banner --title "Sunfounder @ Video" --no-timestamp /home/smartHome/test/video/yeelink.jpg
curl --request POST --data-binary @"/home/smartHome/test/video/yeelink.jpg" --header "U-ApiKey: 7f0df7d7e5b94d5cc1e17314849e73e1" http://api.yeelink.net/v1.0/device/12842/sensor/20896/photos
