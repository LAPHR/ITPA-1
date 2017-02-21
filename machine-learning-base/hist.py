import pandas as pd
import numpy as np
import optunity
import optunity.metrics
import nltk 
from sklearn.cross_validation import train_test_split
from sklearn import svm
from sklearn.externals import joblib
from sklearn import preprocessing
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support
from sklearn.metrics import accuracy_score
from sklearn.feature_extraction.text import CountVectorizer
from nltk.stem.porter import PorterStemmer
from sklearn import linear_model
import matplotlib.pyplot as plt
df=pd.read_csv("yourfilenew2.csv")
df["class1"].replace('', np.nan)
df["class1"].dropna()
df["class1"][df["class1"]=="Accomodation"]="Accommodation"
df["class1"][df["class1"]=="Accomodaton"]="Accommodation"
df["class1"][df["class1"]=="Accomadation"]="Accommodation"
df["class1"][df["class1"]=="Accomadation"]="Accommodation"
df["class1"][df["class1"]=="Accomdaton"]="Accommodation"
df["class1"][df["class1"]==' Accommodation']="Accommodation"
df["class1"][df["class1"]=="Accommodation\n"]="Accommodation"
df["class1"][df["class1"]=='accomodation']="Accommodation"
df["class1"][df["class1"]=="Trasporation"]="Transport"
df["class1"][df["class1"]=="Hotel"]="Accommodation"
df["class1"][df["class1"]=="Tranportation"]="Transport"
df["class1"][df["class1"]=="Trasport"]="Transport"
df["class1"][df["class1"]=="Tranport"]="Transport"
df["class1"][df["class1"]=="Transport\n"]="Transport"
df["class1"][df["class1"]=="transport"]="Transport"
df["class1"][df["class1"]=="Transportation "]="Transport"

df["class1"][df["class1"]=="Transportation"]="Transport"
df["class1"][df["class1"]=="Transportation"]="Transport"
df["class1"][df["class1"]=="Neighborhood"]="Neighborhood"
df["class1"][df["class1"]=="Neibhourhood"]="Neighborhood"
df["class1"][df["class1"]=="Neighborhood"]="Neighborhood"
df["class1"][df["class1"]=='Neighbourhood']="Neighborhood"
df["class1"][df["class1"]=='Neigbourhood']="Neighborhood"
df["class1"][df["class1"]=="Things to dos"]="Things to do"
df["class1"][df["class1"]=="Sports & Adventurous Activities"]="Things to do"
df["class1"][df["class1"]=="Thingd to do"]="Things to do"
df["class1"][df["class1"]=="Thinhgs to do"]="Things to do"

df["class1"][df["class1"]=="Things to do\n"]="Things to do"
df["class1"][df["class1"]=="\nThings to do"]="Things to do"
df["class1"][df["class1"]=='Things  to do']="Things to do"

df["class1"][df["class1"]=='things to do' ]="Things to do"
df["class1"][df["class1"]=='Things  to do ']="Things to do"
df["class1"][df["class1"]=='things to do ']="Things to do"

df["class1"][df["class1"]=='Things to do ']="Things to do"

df["class1"][df["class1"]=="food"]="Food"
df["class1"][df["class1"]=="Fodd"]="Food"
df["class1"][df["class1"]=="Resturant"]="Food"
df["class1"][df["class1"]=="Returant"]="Food"
df["class1"][df["class1"]=="Travel Guide Tour Operators & Comapnies"]="Travel guide"
df["class1"][df["class1"]=="Travel Guide"]="Travel guide"

df["class1"][df["class1"]=="Travel Guide\n"]="Travel guide"
df["class1"][df["class1"]=='Travel Guide ' ]="Travel guide"
df["class1"][df["class1"]=='travel guide' ]="Travel guide"

#df["class1"][df["class1"]==np.nan]="Other"
df["class1"][df["class1"]=="Accommodation"]=0
df["class1"][df["class1"]=="Weather"]=1

df["class1"][df["class1"]=='Weather\n']=1
df["class1"][df["class1"]=='weather']=1

df["class1"][df["class1"]=="Other\n"]='Other'

df["class1"][df["class1"]=="Neighborhood"]=0
df["class1"][df["class1"]=="Things to do"]=3
df["class1"][df["class1"]=="Food"]=4
df["class1"][df["class1"]=="Travel guide"]=5
df["class1"][df["class1"]=="Other"]=5
df["class1"][df["class1"]=="Transport"]=7
df["class1"][df["class1"]=="Entertainment"]=8
df["class1"][df["class1"]=="entertainment"]=8
df["class1"][df["class1"]=="Landmarks"]=9
print(df["class1"].value_counts()/len(df["class1"]))

