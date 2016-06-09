![Neural-Net-Classifier Logo](src/edu/nnc/resources/logo.png)
# Neural-Net-Classifier
------
 App for extraction of knowledge from the data set to determine the type of layer (collector, tire) using **Back Propagation** and **Resilient Propagation** Neural Nets Learning Algorithms

## Build and Run
To build this application you need gradle build tool
[Download Gradle](http://gradle.org/gradle-download/)
To run this application you need Java SE 1.8.0_40+
[Download Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Navigate to project parent folder and run: 
``` Bash 
gradle build
```
To build the project. And
``` Bash
 java -jar build/libs/Neural-Net-Classifier[version].jar
```
To run it.

## Main Window
Main window contains two tables with real and training data, selectbox with neural net types and text area for results logging.
To start training you should first fill training data table.
![Main Window](/screenshots/main-window.png)

## Training Settings
When you click on train button - training settings popup menu shows up. In this menu you can customize learning speed, max iterations and max available error of neural net.
![Training Settings](/screenshots/training-settings.png)

## Error decreasing Graphs
![Charts](/screenshots/charts.png)
After training, error decreasing chart shows up. This chart represent value of error on each iteration.

## Artificial Value Generation
![AVG](/screenshots/avg.png)
You can write real data by hand or generate artificial values to test neural net. To generate artificial values just click on generate button and then write amount of rows which should be generated.

## Classifying
![Classifying](/screenshots/classifying.png)
When you press solve button neural net classifies layers on tire or collector (Don't forget to train neural net before run classification).

