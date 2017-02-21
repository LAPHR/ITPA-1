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
import matplotlib
df=pd.read_csv("finalsecondclass1.csv")
df["class1"].replace('', np.nan)
df["class1"].dropna()
df = df.loc[~df.index.duplicated(keep='first')]
df["class1"][df["class1"]=="Sights"]=0
df["class1"][df["class1"]=="FOD\n"]=1
df["class1"][df["class1"]=="TGU\n"]=2
df["class1"][df["class1"]=="ACM\n"]=3
df["class1"][df["class1"]=="TRS\n"]=4
df["class1"][df["class1"]=="ENT\n"]=5
df["class1"][df["class1"]=="WTH\n"]=6
df["class1"][df["class1"]=="TTD\n"]=0
df["class1"][df["class1"]=="\nENT"]=5
df["class1"][df["class1"]=="Other"]=2
df["class1"][df["class1"]=="TTD "]=0


df["class1"][df["class1"]=="TTD"]=0
df["class1"][df["class1"]=="FOD"]=1
df["class1"][df["class1"]=="TGU"]=2
df["class1"][df["class1"]=="TRU"]=2
df["class1"][df["class1"]=="ACM"]=3
df["class1"][df["class1"]=="TRS"]=4
df["class1"][df["class1"]=="ENT"]=5
df["class1"][df["class1"]=="WTH"]=6
list=df["class2"].unique()
i=0
for tag in list:
	df["class2"][df["class2"]==tag]=i
	i=i+1
	print(tag)
	print(i)
print(df["class2"].unique())

