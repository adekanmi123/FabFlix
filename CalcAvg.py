total = 0
count = 0
avg = 0

with open('TSlog.txt') as f:
	for line in f:
		num = float(line)
		total += num
		count += 1
	avg = total/count
	print('{} {} {}'.format("TS", avg, count))

total = 0
count = 0
avg = 0

with open('TJlog.txt') as f:
        for line in f:
                num = float(line)
                total += num
                count += 1
        avg = total/count
        print('{} {} {}'.format("TJ", avg, count))
