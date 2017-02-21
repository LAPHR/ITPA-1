import csv
import re

with open("../Untitled Folder 9/test2.csv") as f:
    r = csv.DictReader(f)
    theids = set(row['question'] for row in r)
print(theids)

with open("finalboth4.csv") as f:
	with open("trainset.csv", 'w') as train:
		with open("testset.csv", 'w') as test:
    			r = csv.DictReader(f)
   			w = csv.DictWriter(train, r.fieldnames)
    			w2=csv.DictWriter(test, r.fieldnames)
    			for row in r:
				 s=row['question']
       				 if s.strip() in theids:
            				w2.writerow(row)
				 else:
	    				w.writerow(row)

