# FirebaseHelperLib #

This small library simplifies (some) of the basic Firebase database queries.

# Usage

FirebaseHelper class gives you several simple different listener functions. Here is an example of usage of one of them:


```java

FirebaseHelper helper = new FirebaseHelper();

    helper.getChildren(SampleData.class, "sample_data") //Get children of sample_data. SampleData.class - what we want to map data to
        .orderByChild("surname") //We want this data orderer
        .endAt("Kokelj") //Filter out everything that comes after 
        .toRx() //Give me the Source (in this case it will be Single)
        .subscribe(e -> {
            //Do smt
        }, error -> {
            //Handle error
        });   
```


The library enables you to simply read what is in database (example above), listen to events, read values of children, sorting and filtering.


# Installation

Library is hosted on JCenter.

Add dependency:

```gradle
dependencies {
    compile 'com.kokaba.rxfirebase:database:0.1.0-alpha'
}
```



# License

```
Copyright 2017 Lovrenc Gregorčič

Permission is hereby granted, free of charge, to any person obtaining a copy of this
software and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE.
```
