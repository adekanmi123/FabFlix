AWS Instances:
Original (Instance 1) IP: 35.184.60.142
Master (Instance 2) IP: 54.153.115.152
Slave (Instance 3) IP: 54.219.190.244

Google Cloud Instance:



CalcAvg.py — calculating TS and TJ
Can be found at the root of .war file, as well as the root of this folder. 

How to use:
Place CalcAvy.py in the home directory of the ubuntu instance: /home/ubuntu.
Run CalcAvg.py like you do with any python script: python3 CalcAvg.py
CalcAvg.py opens the files TJlog.txt and TSlog.txt (generated by FabFlix and placed in /home/ubuntu) and adds up all the numbers and divides it by the total number of measurements.

Output: 

TS (time in nanoseconds) (number of measurements)
TJ (time in nanoseconds) (number of measurements)