
import random
import itertools

number_of_items = int(raw_input('Enter Number of items:'))

while((number_of_items  < 0 )):
	number_of_items = int(raw_input('Enter positive int number of item:'))

file_name="random_entry_with_"+str(number_of_items)+"_items.txt"
file = open(file_name,"w")
escape_line = "\n"


item = "1,-1,1,-1,"
item_type = ["1","2","3","4"]
for x in range(number_of_items):
	type = random.sample(item_type,1)
	file.write(item+type[0]+",0"+escape_line)


print "se genero el archivo: "+file_name