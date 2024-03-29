<h2>Android Studio</h2>

- On set up New Project:
  - Minimum SDK: `API 21 ("Lolipop"; Android 5.0)`
- At design layout page, you can click the `Pixel` on the menu bar to choose which device frame to display.

  - You can also open `Layout Validation` view which is presented at the column menu on the right edge.

- Switch to another activity:

```java
Intent intent = new Intent(this, TargetActivity.class);
intent.putExtra("key", "value");
startActivity(intent);
```

<h2>Notes</h2>

- From directory `app/manifests/AndroidManifest.xml`, you can decide which activity to be a MAIN.

```xml
<activity
    ...
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

- File `build.gradle.kts`: add dependencies.
- Config data binding feature.

```kts
android {
    ...
    buildFeatures {
        dataBinding = true
    }
}
```
