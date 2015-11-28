``` Bash
    _   __                      __      _   __     __        ________                _ _____          
   / | / /__  __  ___________ _/ /     / | / /__  / /_      / ____/ /___ ___________(_) __(_)__  _____
  /  |/ / _ \/ / / / ___/ __ `/ /_____/  |/ / _ \/ __/_____/ /   / / __ `/ ___/ ___/ / /_/ / _ \/ ___/
 / /|  /  __/ /_/ / /  / /_/ / /_____/ /|  /  __/ /_/_____/ /___/ / /_/ (__  |__  ) / __/ /  __/ /    
/_/ |_/\___/\__,_/_/   \__,_/_/     /_/ |_/\___/\__/      \____/_/\__,_/____/____/_/_/ /_/\___/_/     
                                                                                                      
```
# Neural-Net-Classifier
###EN:
 App for extraction of knowledge from the data set to determine the type of layer (collector, tire) using **Back Propagation** and **Extended Delta Bar Delta** Neural Nets

###UA:
 Додаток для вилучення знань з набору даних для визначення типу пласта (колектора, покришка) даних з використанням нейромереж
 **Back Propagation** та **Extended Delta Bar Delta**

###Project Structure / Структура проекту

``` Bash
 .
├── bin
│   ├── beans
│   │   ├── RealLayerFeatures.class
│   │   └── TrainingLayerFeatures.class
│   ├── gui
│   │   ├── Main.class
│   │   ├── MainController.class
│   │   ├── main.css
│   │   └── neural-nets-main.fxml
│   ├── neuralNets
│   │   ├── BackPropagationNeuralNet.class
│   │   ├── ExtendedDeltaBarDeltaNeuralNet.class
│   │   └── NeuralNet.class
│   └── utils
│       ├── ArtificialValueGenerator.class
│       └── CSVDispatcher.class
├── build.fxbuild
├── build.gradle
├── data_samples
│   ├── tests.csv
│   └── training.csv
├── README.md
├── src
   ├── beans
   │   ├── RealLayerFeatures.java
   │   └── TrainingLayerFeatures.java
   ├── gui
   │   ├── MainController.java
   │   ├── main.css
   │   ├── Main.java
   │   └── neural-nets-main.fxml
   ├── neuralNets
   │   ├── BackPropagationNeuralNet.java
   │   ├── ExtendedDeltaBarDeltaNeuralNet.java
   │   └── NeuralNet.java
   └── utils
       ├── ArtificialValueGenerator.java
       └── CSVDispatcher.java
```