# Kotlin bandit algorithms examples

## Prerequisites to running the example

* download Maven directly from the [Apache Maven homepage](http://maven.apache.org/download.html)
* install and configure your system as described in [the installation section](http://maven.apache.org/download.html#Installation)

## Compiling/Testing/Running the example

If you have maven on your path, simple type:

```cmd
mvn test
```

It will compile all files then run tests, and finally run the main Kotlin class.

## Only running the example

Once you compiled the sources with previous 'mvn test' command, you can run the application by typing:

```cmd
mvn exec:java
```

## Using commandline arguments

If you want to modify the main method in order to use commandline arguments, you can specify them on commandline as:

```cmd
mvn exec:java -Dexec.args="argument1"
```
