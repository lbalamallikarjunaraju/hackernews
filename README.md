# HackerNews Reader

#### Pre Requisites:
* Java - JDK 1.8
   > https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
* Git
  > https://www.atlassian.com/git/tutorials/install-git
* Docker
  > https://docs.docker.com/docker-for-windows/install/
           
  > https://docs.docker.com/install/linux/docker-ce/ubuntu/
                                                                                                                              
  > https://docs.docker.com/v17.12/docker-for-mac/install/#install-and-run-docker-for-mac

## Usage
#### How to download:
* Open command prompt and run this command:
    * git clone https://github.com/lbalamallikarjunaraju/hackernews.git

#### How to run:
###### Run as a docker:

* Open terminal and navigate to the project folder. e.g. cd path/to/the/project/folder
* ./gradlew clean build
* docker build -t hackerorg/hackernews .
* docker run -e HACKERNEWS="hackernews --posts #noOfNews" hackerorg/hackernews
    * replace '#noOfNews' with any integer value
    * e.g.  ``docker run -e HACKERNEWS="hackernews --posts 1" hackerorg/hackernews``
    
###### Run from command prompt:
* `Linux/Mac:` 
    * Open terminal and navigate to the project folder. e.g. cd path/to/the/project/folder
    * ./gradlew clean build
    * ./gradlew build && java -jar build/libs/hackernews-docker-0.1.0.jar hackernews --posts #noOfNews
        * replace '#noOfNews' with any integer value
        * e.g. ``./gradlew build && java -jar build/libs/hackernews-docker-0.1.0.jar hackernews --posts 1``
* `Windows:`
    * Open command-prompt and navigate to the project folder. e.g. cd path/to/the/project/folder
    * gradlew.bat clean build
    * gradlew.bat build && java -jar build/libs/hackernews-docker-0.1.0.jar hackernews --posts #noOfNews
        * replace '#noOfNews' with any integer value
        * e.g. ``gradlew.bat build && java -jar build/libs/hackernews-docker-0.1.0.jar hackernews --posts 1``

##### Sample Output looks like:
```$json
$ ./gradlew build && java -jar build/libs/hackernews-docker-0.1.0.jar hackernews --posts 1

> Task :run
[
  {
    "title": "Joplin – a note taking and to-do application with synchronization capabilities",
    "uri": "https://github.com/laurent22/joplin",
    "author": "adulau",
    "points": 221,
    "comments": 84,
    "rank": 1
  },
  {
    "title": "Tinyland, a Small Dynamicland",
    "uri": "https://emmasmith.me/tinyland/",
    "author": "luu",
    "points": 111,
    "comments": 7,
    "rank": 2
  },
  {
    "title": "Hacking Neural Networks: A Short Introduction",
    "uri": "https://github.com/Kayzaks/HackingNeuralNetworks",
    "author": "jonbaer",
    "points": 16,
    "comments": 1,
    "rank": 3
  }
]
```
#### Validations:
* Invalid Arguments:
    > * error-message: "arguments format should be: hackernews --posts #noOfRecords"
* Runtime Exceptions will be thrown,for invalid - 
    > * Rank: "Rank should be positive and non--zero integer"
    > * URL: "Invalid URL"
    > * Title: "Invalid Title"
    > * Points: "Points should be positive and non--zero integer"
    > * Comments: "Comments should be positive and non--zero integer"

## Additional information:
##### Dependencies:
* JSoup:
    * jsoup is a Java library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.
* JUnit:
    * Java unit test framework.
* Gson:
    * Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.
* Lombok:
    * Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
      Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

##### Plugins:
* Spring-Boot gradle plugin:
    * For dependency management in docker
* Palantir gradle plugin:
    * Docker plugin for gradle
