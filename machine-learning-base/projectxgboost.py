import pandas as pd
import numpy as np
import optunity
import optunity.metrics
import nltk 
import xgboost as xgb
from sklearn import tree
from sklearn.cross_validation import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn import svm
from sklearn.externals import joblib
from sklearn import preprocessing
from sklearn.metrics import confusion_matrix
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.ensemble import RandomForestClassifier
from sklearn import linear_model
import matplotlib
dftrain=pd.read_csv("yourfilenew2.csv")
dftest=pd.read_csv("yourfilenew2.csv")
def preprocessDataframe(df):
 df["class1"].replace('', np.nan)
 df["class1"].dropna()
 df = df.loc[~df.index.duplicated(keep='first')]
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
 df["class1"][df["class1"]=='other']='Other'
 #df["class1"][df["class1"]==np.nan]="Other"
 df["class1"][df["class1"]=="Accommodation"]=0
 df["class1"][df["class1"]=="Weather"]=1

 df["class1"][df["class1"]=='Weather\n']=1
 df["class1"][df["class1"]=='weather']=1

 df["class1"][df["class1"]=="Other\n"]='Other'

 df["class1"][df["class1"]=="Neighborhood"]=0
 df["class1"][df["class1"]=="Things to do"]=2
 df["class1"][df["class1"]=="Food"]=3
 df["class1"][df["class1"]=="Travel guide"]=4
 df["class1"][df["class1"]=="Other"]=5
 df["class1"][df["class1"]=="Transport"]=6
 df["class1"][df["class1"]=="Entertainment"]=7
 df["class1"][df["class1"]=="entertainment"]=7
 df["class1"][df["class1"]=="Landmarks"]=8
 df["AllCaps"][df["AllCaps"]==True]=1
 df["AllCaps"][df["AllCaps"]==False]=0
 df["AllLowerCase"][df["AllLowerCase"]==True]=1
 df["AllLowerCase"][df["AllLowerCase"]==False]=0
 df["AllDigits"][df["AllDigits"]==True]=1
 df["AllDigits"][df["AllDigits"]==False]=0
 df["AllLetters"][df["AllLetters"]==True]=1
 df["AllLetters"][df["AllLetters"]==False]=0
 df["AllLetters"][df["AllLetters"]=="true"]=1
 df["AllLetters"][df["AllLetters"]=="false"]=0
 df["AllLetters"][df["AllLetters"]=="flase"]=0
 df["AllLetters"][df["AllLetters"]==False]=0
 df["others"][df["others"]==True]=1
 df["others"][df["others"]=="flase"]=0
 return df
dftrain=preprocessDataframe(dftrain)
dftest=preprocessDataframe(dftest)
print (dftarin)
print (dftarin["class1"].unique())
print (dftest["class1"].unique())

count_vect = CountVectorizer(min_df=0., max_df=1.0)
print(df["headWord"])

def createHeadWord(df):
 df['headWord']=df['headWord'].fillna("none")
 df['synomums2']=df['synomums2'].fillna("none")
 df['synomums1']=df['synomums1'].fillna("none")
 df['synomums3']=df['synomums3'].fillna("none")
 df["allwords"]=df['synomums1']+" "+df['synomums2']+" "+df['synomums3']+" "+df['headWord']
 headlist= df['allwords'].values.tolist()
 headlist1= df['wh'].values.tolist()

 X_cout = count_vect.fit_transform(headlist).toarray()
 X_cout1 = count_vect.fit_transform(headlist1).toarray()

 df11=pd.DataFrame(X_cout)
 df12=pd.DataFrame(X_cout1)

 return df11
def createWh(df):
 
questions= df['question'].values.tolist()
word_vectorizer = CountVectorizer(ngram_range=(1,2), analyzer='word')
X_cout5 = word_vectorizer.fit_transform(questions).toarray()
print(X_cout5)
df15=pd.DataFrame(X_cout5)


#df["dbPedia"]=df["dbPedia"].fillna('none')
#dbpedia= df['dbPedia'].values.tolist()
#word_vectorizer = CountVectorizer()
#X_cout = word_vectorizer.fit_transform(dbpedia).toarray()
#print(X_cout)
#df16=pd.DataFrame(X_cout)

list2=[]
for index, row in df.iterrows():
	text=nltk.word_tokenize(row["question"])
	text=nltk.pos_tag(text);
	text2=""
	for i in text:
		text2=text2+" "+i[1]
	list2.append(text2)


word_vectorizer = CountVectorizer(ngram_range=(1,1), analyzer='word')
X_cout7 = word_vectorizer.fit_transform(list2).toarray()
print(X_cout7)
df17=pd.DataFrame(X_cout7)

df=df[["class1","AllCaps","AllLowerCase","AllDigits","AllLetters","others","Location","Person","Organization","Money","Percent","Date","Time","DURATION","MISC","NUMBER","SET","ORDINAL"]]
df=pd.concat([df,df15], axis=1)
#df=pd.concat([df,df16], axis=1)
del df15
#del df16
df=pd.concat([df,df17], axis=1)
del df17

df=pd.concat([df,df11], axis=1)
df=pd.concat([df,df12], axis=1)
X=df.drop('class1', axis=1)
X.replace('', np.nan)
X.dropna()
print(X)
print(df["class1"].isnull().any().any())
dtrain = xgb.DMatrix(X.values, label=df["class1"].values)


params = {}
params['objective'] = "multi:softmax"
params['eta'] = 0.1
params['max_depth'] = 10
params['subsample'] = 1.2
params['colsample_bytree'] = 0.6
params['silent'] = False
params["num_class"]=9
num_round = 84

xgb.cv(params, dtrain, num_round, nfold=2,maximize=False,
       metrics={'merror'}, seed = 0,
       callbacks=[xgb.callback.print_evaluation(show_stdv=True)])




