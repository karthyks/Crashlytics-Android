
# Crashlytics-Android
[![Gradle](https://img.shields.io/badge/gradle-2.0.0--beta1-green.svg)](https://bintray.com/karthik-logs/karthyks/Crashlytics)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Crashlytics-green.svg?style=flat )]( https://android-arsenal.com/details/1/6509 )


Gradle : <br/>
```
compile 'com.github.karthyks:crashlytics:2.0.0-beta1'
```

Usage :<br/>
First step is to initialize Crashlytics inside your application class, along with a CustomCrashEvent class instance, as shown below

```java
Crashlytics.init(Application, new EventListener() {
    new EventListener() {
          @Override
          public void onEventOccurred(List<Event> events) throws Exception {
            // Log to your Cloud DB for future analytics.
            Log.d(TAG, "onEventOccurred: " + events.size());
          }
});

<br/>
If your app has login feature, then the user info can be injected into the Crashlytics, to obtain info about the crashes and events once happened.

```java
Crashlytics.login(company, userid);
```
<br/>
<br/>
In case of any exception thrown in the onEventOccurred method, then the events are stored locally
 in the db. Once if there are any locally stored events found, then a background worker will run
 every 15 minutes by default, which in turn keeps on triggering the onEventOccurred method, until it
 passes.
 <br/> <br/>







