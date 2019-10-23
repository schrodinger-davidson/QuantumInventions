# QuantumInventions
Test Assignment

The assignment is based on the android speech to text api. Candiate has to fetch a dictionary json from API and show on UI. On Speak button 
click it takes speech input and matches with existing dictinary word and if any match happens it incresed the frequency count of the word and saves it in shared preferences.


Basic Modules of the project :

1. Retrofit: Retrofit is used as API client for this project. Retrofit gives good performance in terms of speed and accessibity(GSON POJO class modelling)

2. Android Speech API: This is android's build in speech to text api, it takes speech input, sends data to cloud server and gets text response in terms of list of arrays where 0th position text indicates the most appropriate guess.

3. RecyclerView : Recyclerview is used to show the dicionary's words and frequencies in a tabular manner. The reason behind choosing recylerview instead of GridView or tableview is for simplicity and future scope of application. It can show dynamic dictionary and it 
immune to changes made in api.

4. SharedPrerence: It is used to store upated data after frequency changes. Futher we can update server database based on this. 
The reason behind choosing SharedPreference instead of SQLite is speed of access and simple getter setter property of Shared Preference.
Sqlite queries are heavy to run on UI thread.

5. Unit Testing: Here Espresso is used as a unit testing library. The online major test written for the requirement of sorted dictionary based on ascending order of frequency.


Scope of futher works, progress & Enhancement:

1. The android speech api is not able to provide difference between number in digit and number in words. So in application there are some hardcoded values for changes number from digit to words and vice-versa.

2. The achirecture of the application could be in Android Observer pattern or it could be in a true MVX architecture.

3. For data storing in sharedpreferences, String class is used abundantly. There is a good scope of improvement after using StringBuffer and charArray kind of data structures.

4. The apk is not using any proguard or keep rules that can futhrer improve the size of apk.
