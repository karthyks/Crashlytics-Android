
# Crashlytics-Android
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
```
If your app has login feature, then the user info can be injected into the Crashlytics, to obtain info about the crashes and events once happened.

```java
Crashlytics.login(company, userid);
```






