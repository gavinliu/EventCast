## EventCast
A new EventCast for Android

* Faster
* Support Multi-Parameter

## Usage

### Including

Last version is 0.2

Gradle:

```gradle
compile 'cn.gavinliu.android.lib:EventCast:VERSION'
```

### Register & unregister

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    EventCast.getInstance().register(this);
}

@Override
protected void onDestroy() {
    super.onDestroy();
    EventCast.getInstance().unregister(this);
}
```

### Define Receiver

+ Use ``@Receiver`` define a Receiver method
+ Tow kinds of Receiver
  * Receiver has TAG : @Receiver(tag = "xx")
  * Receiver no  TAG : @Receiver
+ @Receiver(posterType = PosterType.MAIN) define poster thread

### Post Event

1. ``eventCast.post(tag, data...)``
2. ``eventCast.post(class, method, data...)``

* ``Receiver has TAG`` only use 1
* ``Receiver no  TAG`` only use 2

### Simple demo

Support Multi-Parameter

* has tag

``` java
@Receiver(tag = "xx")
public void postByTag() {
    Toast.makeText(this, "PostByTag", Toast.LENGTH_SHORT).show();
}

... other code ...

EventCast.getInstance().post("xx");

```

``` java
@Receiver(tag = "xx", posterType = PosterType.POST)
public void postByTagAndParam(int x, int y) {
    Toast.makeText(this, "PostByTagAndParam: (" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
}

... other code ...

EventCast.getInstance().post("xx", 1, 2);

```

* without tag

``` java
@Receiver
public void postByMethod() {
    Toast.makeText(this, "postByMethod:", Toast.LENGTH_SHORT).show();
}

... other code ...

EventCast.getInstance().post(MainActivity.class, "postByMethod");
```

``` java
@Receiver
public void postByMethodAndParam(String x) {
    Toast.makeText(this, "postByMethodAndParam:" + x, Toast.LENGTH_SHORT).show();
}

... other code ...

EventCast.getInstance().post(MainActivity.class, "postByMethodAndParam", "Hello~");
```

* Use Generics Type

```java
@Receiver(tag = "xx", posterType = PosterType.POST)
public void testGenericsType(List<String> s) {
    Toast.makeText(this, "testGenericsType: ", Toast.LENGTH_SHORT).show();
}

... other code ...

EventCast.getInstance().post("xx", new ArrayList<String>());
```

## Trap (important!)

* If Receiver method has only one parameter, EventCast will use widely match policy. Your parameter type can be generics type.
* Else EventCast will use strict match policy, so your parameter type can not be generics type.

## License

The MIT License (MIT)

Copyright (c) 2015 Liu Yunlong(gavin6liu@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

