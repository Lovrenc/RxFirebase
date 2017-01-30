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

Add repository url to your gradle build file:

```gradle
maven {
    url 'https://dl.bintray.com/lovrenc/maven/'
}
```

Add dependency:

```gradle
dependencies {
    compile 'com.kokaba.rxfirebase:database:0.1.0-alpha'
}
```
