import random
import itertools


number_of_streets = int(raw_input('Enter Number of streets:'))
number_of_levels = int(raw_input('Enter Number of levels:'))
number_of_sublevels = int(raw_input('Enter Number of sublevels:'))

porcent_of_poblation = -1

while((porcent_of_poblation  < 0 or porcent_of_poblation > 100)):
	porcent_of_poblation = int(raw_input('Enter Porcent int number between 1 - 99 of poblation:'))

print number_of_streets
print number_of_levels
print number_of_sublevels
print porcent_of_poblation

posible_streets = range(0,number_of_streets)
posible_levels= range(0,number_of_levels)
posible_sublevels = range(0,number_of_sublevels)
total_of_poblation = number_of_streets*number_of_sublevels*number_of_levels

total_posible_places =  [ item for item in itertools.product(posible_streets,posible_levels,posible_sublevels)]
total_to_take = int(total_of_poblation*(porcent_of_poblation/100.0))

print "se van a tomar_"+str(total_to_take)
file_name = "random_"+str(porcent_of_poblation)+"%_"+str(number_of_streets)+"x"+str(number_of_levels)+"x"+str(number_of_sublevels)+".txt"
file = open(file_name,"w")
escape_line = "\n"
file.write(str(number_of_streets)+","+str(number_of_levels)+","+str(number_of_sublevels)+escape_line)
item = "1,-1,1,-1,"
item_type = ["1","2","3","4"]
for e in  random.sample(total_posible_places,total_to_take):
	type = random.sample(item_type,1)
	(street,level,sublevel) = e
	file.write(item+type[0]+",0,"+str(street)+","+str(level)+","+str(sublevel)+escape_line)

print "se genero el archivo: "+file_name