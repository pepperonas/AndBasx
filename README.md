# AndBasx
This tiny Android library provides common code for Android.


## How to use
### 1. Import
Add JitPack to your repositories:

```
   repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
```

and add the library to your dependencies:

```
    compile 'com.github.pepperonas:andbasx:0.2.7'
```

This library may require the following permissions:

```
    ACCESS_WIFI_STATE, CHANGE_WIFI_STATE, READ_SYNC_SETTINGS
```


## ProGuard
```
-keep class com.pepperonas.jbasx.** { *; }
-dontwarn com.pepperonas.jbasx.**
-keep class com.pepperonas.andbasx.** { *; }
-dontwarn com.pepperonas.andbasx.**
```


## Developed By

* Martin Pfeffer - https://celox.io - <martin.pfeffer@celox.io>


## License

    Copyright 2017 Martin Pfeffer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


