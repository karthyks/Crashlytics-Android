
# Crashlytics-Android
[![Gradle](https://img.shields.io/badge/gradle-1.0.1-green.svg)](https://bintray.com/karthik-logs/karthyks/Crashlytics)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Crashlytics-green.svg?style=flat )]( https://android-arsenal.com/details/1/6509 )


Gradle : <br/>
```
compile 'com.github.karthyks:crashlytics:1.0.1'
```

Usage :<br/>
First step is to initialize Crashlytics inside your application class, along with a CustomCrashEvent class instance, as shown below

```java
Crashlytics.init(Application, new CrashHandler());
```
Typical CrashHandler class will look like mentioned below.

```java
// This class runs on a different thread,  UI related tasks should not be executed inside this class.
public class CrashHandler extends CustomCrashEvent {
 @Override
  public void onEventOccurred(List<EventModel> eventModels) throws Exception {
    
  }
}

<br/>
In case of any exception thrown in the onEventOccurred method, then the events are stored locally
 in the db. Once if there are any locally stored events found, then a background service will run
 every 15 minutes by default, which in turn keeps on triggering the onEventOccurred method, until it
 passes. The 15 minutes interval can be tweaked by changing CrashEvent.ALARM_FREQUENCY.
 <br/>
```
If your app has login feature, then the user info can be injected into the Crashlytics, to obtain info about the crashes and events once happened.

```java
Crashlytics.login(company, userid);
```






