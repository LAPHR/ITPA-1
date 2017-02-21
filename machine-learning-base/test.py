import pandas as pd
import numpy as np
from sklearn.cross_validation import train_test_split
from sklearn import svm
from sklearn.externals import joblib
from sklearn import preprocessing
from sklearn.metrics import confusion_matrix
from sklearn.feature_extraction.text import CountVectorizer
import matplotlib.pyplot as plt
df=pd.read_csv("yourfile.csv")
df["class1"].replace('', np.nan)
df["class1"].dropna()
df["class1"][df["class1"]=="Accomodation"]="Accommodation"
df["class1"][df["class1"]=="Accomodaton"]="Accommodation"
df["class1"][df["class1"]=="Accomadation"]="Accommodation"
df["class1"][df["class1"]=="Accomadation"]="Accommodation"
df["class1"][df["class1"]=="Accomdaton"]="Accommodation"
df["class1"][df["class1"]=="Trasporation"]="Transport"
df["class1"][df["class1"]=="Hotel"]="Transport"
df["class1"][df["class1"]=="Tranportation"]="Transport"
df["class1"][df["class1"]=="Trasport"]="Transport"
df["class1"][df["class1"]=="Tranport"]="Transport"
df["class1"][df["class1"]=="Transportation"]="Transport"
df["class1"][df["class1"]=="Neighborhood"]="Neighborhood"
df["class1"][df["class1"]=="Neibhourhood"]="Neighborhood"
df["class1"][df["class1"]=="Neighborhood"]="Neighborhood"
df["class1"][df["class1"]=='Neighbourhood']="Neighborhood"
df["class1"][df["class1"]=="Things to dos"]="Things to do"
df["class1"][df["class1"]=="Sports & Adventurous Activities"]="Things to do"
df["class1"][df["class1"]=="Thingd to do"]="Things to do"
df["class1"][df["class1"]=="Thinhgs to do"]="Things to do"
df["class1"][df["class1"]=="food"]="Food"
df["class1"][df["class1"]=="Fodd"]="Food"
df["class1"][df["class1"]=="Resturant"]="Food"
df["class1"][df["class1"]=="Returant"]="Food"
df["class1"][df["class1"]=="Travel Guide Tour Operators & Comapnies"]="Travel guide"
df["class1"][df["class1"]=="Travel Guide"]="Travel guide"
df["class1"][df["class1"]==np.nan]="Other"
df["class1"][df["class1"]=="Accommodation"]=0
df["class1"][df["class1"]=="Weather"]=1
df["class1"][df["class1"]=="Neighborhood"]=2
df["class1"][df["class1"]=="Things to do"]=3
df["class1"][df["class1"]=="Food"]=4
df["class1"][df["class1"]=="Travel guide"]=5
df["class1"][df["class1"]=="Other"]=6
df["class1"][df["class1"]=="Transport"]=7
df["class1"][df["class1"]=="Entertainment"]=8
df["class1"][df["class1"]=="Landmarks"]=9
df["AllCaps"][df["AllCaps"]==True]=1
df["AllCaps"][df["AllCaps"]==False]=0
df["AllLowerCase"][df["AllLowerCase"]==True]=1
df["AllLowerCase"][df["AllLowerCase"]==False]=0
df["AllDigits"][df["AllDigits"]==True]=1
df["AllDigits"][df["AllDigits"]==False]=0
df["AllLetters"][df["AllLetters"]==True]=1
df["AllLetters"][df["AllLetters"]==False]=0
df["others"][df["others"]==True]=1
df["others"][df["others"]=="flase"]=0
#df=pd.DataFrame({col: df[col].astype('category').cat.codes for col in df}, index=df.index)
print (df)
print (df["class1"].unique())
count_vect = CountVectorizer(min_df=0., max_df=1.0)
print("ssssssssssssssssssssssssssssssss")
print(df["headWord"])

print(df)
plt.xlim(xmin=50)
df["headWord"].value_counts().plot(kind="bar")
plt.show()
