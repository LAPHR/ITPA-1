import pandas as pd
import numpy as np
import nltk 
from sklearn.cross_validation import train_test_split
from sklearn import svm
from sklearn.externals import joblib
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support
from sklearn.metrics import accuracy_score
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.model_selection import KFold
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
import string
df=pd.read_csv("finalboth4.csv")
df = df.loc[~df.index.duplicated(keep='first')]
df["class1"].replace('', np.nan)
df.dropna(subset=['class1'])

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
print(df["headWord"])

df['headWord']=df['headWord'].fillna("none")
df['synomums2']=df['synomums2'].fillna("none")
df['synomums1']=df['synomums1'].fillna("none")
df['synomums3']=df['synomums3'].fillna("none")
df["allwords"]=df['synomums1']+" "+df['synomums2']+" "+df['synomums3']
headlist= df['allwords'].values.tolist()
headlist1= df['wh'].values.tolist()

X_cout = count_vect.fit_transform(headlist).toarray()
X_cout1 = count_vect.fit_transform(headlist1).toarray()

df11=pd.DataFrame(X_cout)
df12=pd.DataFrame(X_cout1)

print(df11.shape)
print(df12.shape)

lemmatizer = WordNetLemmatizer()

list3=[]
for index, row in df.iterrows():
	print(row["question"])
	text=row["question"].decode().encode('utf-8')
	text=nltk.word_tokenize(text)
	text = filter(lambda x: x not in string.punctuation, text)
	text=map(lambda x: lemmatizer.lemmatize(x), text);
	text2=""
	for i in text:
		text2 = text2 + " " +i
	list3.append(text2)


questions= list3
word_vectorizer = CountVectorizer(ngram_range=(2,2), analyzer='word')
X_cout5 = word_vectorizer.fit_transform(questions).toarray()
print(X_cout5)
df15=pd.DataFrame(X_cout5)
print("df15")
print(df15.shape)


list2=[]
for index, row in df.iterrows():
	text=nltk.word_tokenize(row["question"])
	text=nltk.pos_tag(text);
	text2=""
	for i in text:
		text2 = text2 + " " +i[1]+"/"+i[0]
	list2.append(text2)


word_vectorizer = CountVectorizer(ngram_range=(1,1), analyzer='word')
X_cout7 = word_vectorizer.fit_transform(list2).toarray()
print(X_cout7)
df17=pd.DataFrame(X_cout7)

df=df[["question","class1"]]#"Location","Person","Organization","Money","Percent","Date","Time","DURATION","MISC","NUMBER","SET","ORDINAL","dbPedia"]]
df=pd.concat([df,df15], axis=1)
#df=pd.concat([df,df16], axis=1)
df=pd.concat([df,df17], axis=1)
df=pd.concat([df,df11], axis=1)
df=pd.concat([df,df12], axis=1)
del df17
del df11
del df12
train, test = train_test_split(df, test_size = 0.1)
Y=train["class1"].astype(int)
X=train.drop('class1', axis=1).drop('question', axis=1)
print("X shape")
print(X.shape)
#print(train.shape)
#test[["question","class1","AllCaps","AllLowerCase","AllDigits","AllLetters","others","Location","Person","Organization","Money","Percent","Date","Time","DURATION","MISC","NUMBER","SET","ORDINAL","dbPedia"]].to_csv('df2.csv')


#print(Y)
#count_vect = CountVectorizer()
#clf = tree.DecisionTreeClassifier()
#clf.fit(X, Y)


clf=svm.LinearSVC() 
	


#clf 15=pd.DataFrame(X_cout5)


#df["dbPedia"]=df["dbPedia"].fillna('none')
#dbpedia= = GaussianNB()
clf.fit(X, Y)
#clf = RandomForestClassifier(n_estimators=13, max_depth=None,min_samples_split=9, random_state=0)
#clf = linear_model.LogisticRegression(C=1e5)
#clf.fit(X, Y)

#joblib.dump(clf, 'filename.pkl') 
xx=test[["question","class1"]]
print(test.shape)
x=test.drop('class1', axis=1).drop('question', axis=1).drop('dbPedia', axis=1)
print(x.shape)
y=test["class1"].astype(int)
print (clf.score(x,y))
yy=clf.predict(x)

print(accuracy_score(y, yy, normalize=False))
print(confusion_matrix(y, yy))
print(precision_recall_fscore_support(y, yy, average='macro'))
df1 = pd.DataFrame(y, columns=['actual'])
df2=pd.DataFrame(yy,columns=['predicted'])
result=xx
sresult["predicted"]=df2["predicted"]
result.to_csv('test2.csv')
#df2.to_csv('df2.csv')



