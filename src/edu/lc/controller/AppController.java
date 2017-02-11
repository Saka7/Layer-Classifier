package edu.lc.controller;

import edu.lc.App;
import edu.lc.beans.RealLayerFeatures;
import edu.lc.beans.TrainingLayerFeatures;
import edu.lc.neuralNets.BackPropagationNeuralNet;
import edu.lc.neuralNets.NeuralNet;
import edu.lc.neuralNets.ResilientPropagationNeuralNet;
import edu.lc.utils.ArtificialValueGenerator;
import edu.lc.utils.CSVDispatcher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** Main Controller, which handle user Interactions */
public class AppController {

  @FXML
  private TextArea resultText;

  @FXML
  private ComboBox<String> neuralNetworkType;

  @FXML
  private TableView<TrainingLayerFeatures> trainingLayersTable;
  
  @FXML
  private TableColumn<TrainingLayerFeatures, Integer> trainingNumbers;
  
  @FXML
  private TableColumn<TrainingLayerFeatures, Double> trainingSponginess;
  
  @FXML
  private TableColumn<TrainingLayerFeatures, Double> trainingAmountOfClay;
  
  @FXML
  private TableColumn<TrainingLayerFeatures, Double> trainingAmountOfCarbonate;
  
  @FXML
  private TableColumn<TrainingLayerFeatures, Integer> trainingType;
  
  @FXML
  private TableColumn<TrainingLayerFeatures, Double> trainingVPAmplitude;
  
  private ObservableList<TrainingLayerFeatures> trainingLayersFeatures = FXCollections.observableArrayList();

  @FXML
  private TableView<RealLayerFeatures> realLayersTable;
  
  @FXML
  private TableColumn<RealLayerFeatures, Integer> realNumbers;
  
  @FXML
  private TableColumn<RealLayerFeatures, Double> realSponginess;
  
  @FXML
  private TableColumn<RealLayerFeatures, Double> realAmountOfClay;
  
  @FXML
  private TableColumn<RealLayerFeatures, Double> realAmountOfCarbonate;
  
  @FXML
  private TableColumn<RealLayerFeatures, Double> realVPAmplitude;
  
  private ObservableList<RealLayerFeatures> realLayersFeatures = FXCollections.observableArrayList();

  private App mainApp;
  
  private NeuralNet net;
  
  @FXML
  private void initialize() {
    net = new BackPropagationNeuralNet();

    initTables();

    trainingLayersTable.setEditable(true);
    realLayersTable.setEditable(true);
    trainingLayersTable.setTableMenuButtonVisible(true);
    realLayersTable.setTableMenuButtonVisible(true);

    // Adding new row when right mouse button has been clicked
    trainingLayersTable.setOnMouseClicked((MouseEvent event) -> {
      if (event.getButton() == MouseButton.SECONDARY)
        trainingLayersFeatures.add(new TrainingLayerFeatures());
    });

    realLayersTable.setOnMouseClicked((MouseEvent event) -> {
      if (event.getButton() == MouseButton.SECONDARY)
        realLayersFeatures.add(new RealLayerFeatures());
    });

    // Deleting row from table
    trainingLayersTable.setOnKeyPressed((KeyEvent event) -> {
      if (event.getCode() == KeyCode.BACK_SPACE && trainingLayersFeatures.size() > 0) {
        trainingLayersFeatures.remove(trainingLayersTable.getSelectionModel().getSelectedIndex());
      }
    });

    realLayersTable.setOnKeyPressed((KeyEvent event) -> {
      if (event.getCode() == KeyCode.BACK_SPACE && realLayersFeatures.size() > 0) {
        realLayersFeatures.remove(realLayersTable.getSelectionModel().getSelectedIndex());
      }
    });

    // Training Data table 
    // Adding ability to Edit row - Object Number
    trainingNumbers.setCellValueFactory(new PropertyValueFactory<>("number"));
    trainingNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    trainingNumbers.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setNumber(t.getNewValue()));
    
    // Adding ability to Edit row - Sponginess
    trainingSponginess.setCellValueFactory(new PropertyValueFactory<>("sponginess"));
    trainingSponginess.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    trainingSponginess.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setSponginess(t.getNewValue()));

    // Adding ability to Edit row - Amount of Clay
    trainingAmountOfClay.setCellValueFactory(new PropertyValueFactory<>("amountOfClay"));
    trainingAmountOfClay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    trainingAmountOfClay.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setAmountOfClay(t.getNewValue()));
    
    // Adding ability to Edit row - Amount of Carbonate
    trainingAmountOfCarbonate.setCellValueFactory(new PropertyValueFactory<>("amountOfCarbonate"));
    trainingAmountOfCarbonate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    trainingAmountOfCarbonate.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setAmountOfCarbonate(t.getNewValue()));

    // // Adding ability to Edit row - VP Amplitude
    trainingVPAmplitude.setCellValueFactory(new PropertyValueFactory<>("vPAmplitude"));
    trainingVPAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    trainingVPAmplitude.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setVPAmplitude(t.getNewValue()));

    // // Adding ability to Edit row - Type
    trainingType.setCellValueFactory(new PropertyValueFactory<>("type"));
    trainingType.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    trainingType.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setType(t.getNewValue()));

    trainingLayersTable.setItems(trainingLayersFeatures);

    // Real Data Table
    // Adding ability to Edit row - Number
    realNumbers.setCellValueFactory(new PropertyValueFactory<>("number"));
    realNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    realNumbers.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setNumber(t.getNewValue()));
    
    // Adding ability to Edit row - Sponginess
    realSponginess.setCellValueFactory(new PropertyValueFactory<>("sponginess"));
    realSponginess.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    realSponginess.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setSponginess(t.getNewValue()));

    // Adding ability to Edit row - Amount of clay
    realAmountOfClay.setCellValueFactory(new PropertyValueFactory<>("amountOfClay"));
    realAmountOfClay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    realAmountOfClay.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setAmountOfClay(t.getNewValue()));
    
    // Adding ability to Edit row - Amount of carbonate
    realAmountOfCarbonate.setCellValueFactory(new PropertyValueFactory<>("amountOfCarbonate"));
    realAmountOfCarbonate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    realAmountOfCarbonate.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setAmountOfCarbonate(t.getNewValue()));

    // Adding ability to Edit row - VP Amplitude
    realVPAmplitude.setCellValueFactory(new PropertyValueFactory<>("vPAmplitude"));
    realVPAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    realVPAmplitude.setOnEditCommit(t -> t.getTableView()
          .getItems().get(t.getTablePosition().getRow())
          .setVPAmplitude(t.getNewValue()));

    realLayersTable.setItems(realLayersFeatures);

    // Adding neural nets to combo-box
    neuralNetworkType.getItems().addAll("Back Propagation", "Resilient Propagation");
    neuralNetworkType.setValue("Back Propagation");
  }

  /** 
   * Main Class Initialization
   * @param mainApp
   */
  public void setMain(App mainApp) {
    this.mainApp = mainApp;
  }

  /** Train Button Event Handler */
  @FXML public void train() {
    resultText.appendText(neuralNetworkType.getValue() + " : Training...\n");
    
    // Popup menu of neural net training options
    final Stage options = new Stage();
    options.initModality(Modality.APPLICATION_MODAL);
    options.initOwner(mainApp.getStage());
    VBox optionsBox = new VBox(5);
    optionsBox.setPadding(new Insets(0, 15, 0, 15));
    optionsBox.setAlignment(Pos.CENTER);
    Button ok = new Button("OK");

    Label learningRateLabel = new Label("Learning Speed");
    Label maxIterationsLabel = new Label("Max Iterations");
    Label maxErrorLabel = new Label("Max Error");

    // Learning Speed slider
    final Slider learningRate = new Slider(0, 1.0, 0.001);
    learningRate.setShowTickLabels(true);
    learningRate.setShowTickMarks(true);
    learningRate.setMajorTickUnit(0.1);
    learningRate.setMinorTickCount(1);

    // Max Iterations Slider
    final Slider maxIterations = new Slider(0, 1e4, 100);
    maxIterations
        .valueProperty()
        .addListener((obs, oldVal, newVal) -> maxIterations.setValue(newVal.intValue()));

    maxIterations.setShowTickLabels(true);
    maxIterations.setShowTickMarks(true);
    maxIterations.setMajorTickUnit(1000);
    maxIterations.setMinorTickCount(5);

    // Max Error Slider
    final Slider maxError = new Slider(0, 1.0, 0.001);
    maxError.setShowTickLabels(true);
    maxError.setShowTickMarks(true);
    maxError.setMajorTickUnit(0.1);
    maxError.setMinorTickCount(1);

    // learning speed text field data binding
    TextField learningRateValue = new TextField(
        String.valueOf(learningRate.getValue()));
    learningRateValue.textProperty().bindBidirectional(
        learningRate.valueProperty(), new NumberStringConverter());
    
    // Max Iterations text field data binding
    TextField maxIterationsValue = new TextField(
        String.valueOf(maxIterations.getValue()));
    maxIterationsValue.textProperty().bindBidirectional(
        maxIterations.valueProperty(), new NumberStringConverter());

    // Max Error text field data binding
    TextField maxErrorValue = new TextField(
        String.valueOf(maxError.getValue()));
    maxErrorValue.textProperty().bindBidirectional(
        maxError.valueProperty(), new NumberStringConverter());

    optionsBox.getChildren().add(learningRateLabel);
    optionsBox.getChildren().add(learningRate);
    optionsBox.getChildren().add(learningRateValue);

    optionsBox.getChildren().add(maxIterationsLabel);
    optionsBox.getChildren().add(maxIterations);
    optionsBox.getChildren().add(maxIterationsValue);

    optionsBox.getChildren().add(maxErrorLabel);
    optionsBox.getChildren().add(maxError);
    optionsBox.getChildren().add(maxErrorValue);

    optionsBox.getChildren().add(ok);

    Scene optionScene = new Scene(optionsBox, 300, 340);
    options.setResizable(false);
    options.setTitle(neuralNetworkType.getValue());
    options.setScene(optionScene);
    options.show();

    // Ok Button Event Handler
    ok.setOnAction(e -> {
      int size = trainingLayersFeatures.size();
      double[][] inputs = new double[size][4];
      double[][] expected = new double[size][1];

      for (int i = 0; i < size; i++) {
        TrainingLayerFeatures item = trainingLayersFeatures.get(i);
        inputs[i][0] = item.getSponginess();
        inputs[i][1] = item.getAmountOfClay();
        inputs[i][2] = item.getAmountOfCarbonate();
        inputs[i][3] = item.getVPAmplitude();
        expected[i][0] = item.getType();
      }

      String networkType = "";
      if (neuralNetworkType.getValue().equals("Back Propagation")) {
        net = new BackPropagationNeuralNet();
        networkType = "Back Propagation";
      } else {
        net = new ResilientPropagationNeuralNet();
        networkType = "Resilient Propagation";
      }
      
      String results = net.train(inputs, expected, learningRate.getValue(), maxError.getValue(),
          Math.round(maxIterations.getValue()));
      
      resultText.appendText("\n\n" + networkType 
          + " : training has been successfully completed:\n");
      resultText.appendText("error = " + results.split(" ")[1]);
      resultText.appendText("\non" + results.split(" ")[0] + " iteration\n\n");

      String[] sweights = net.getWeights().split(",");
      double[] weights = new double[sweights.length];

      resultText.appendText("with weights:\n\n");
      for (int i = 0; i < weights.length; i++) {
        weights[i] = Double.parseDouble(sweights[i]);
        resultText.appendText(String.format("%.4f", weights[i]));
        if ((i + 1) % 4 == 0)
          resultText.appendText("\n");
        else
          resultText.appendText(",\t");
      }

      options.close();

      // Error/Iteration Chart
      List<Integer> iterations = net.getIterations();
      List<Double> errors = net.getErrors();

      final Stage chartStage = new Stage();
      chartStage.setTitle("Error Chart");
      
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Iteration");
      yAxis.setLabel("Error");
      
      final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
      lineChart.setCreateSymbols(false);

      lineChart.setTitle("Error chart for " + networkType);
      
      Series<Number, Number> series = new XYChart.Series<>();
      series.setName("Error");

      for (int i = 0; i < iterations.size(); i++) {
        series.getData().add(new XYChart.Data(iterations.get(i), errors.get(i)));
      }

      Scene scene = new Scene(lineChart, 800, 600);
      lineChart.getData().add(series);

      chartStage.setScene(scene);
      chartStage.show();

    });
  }

  /** Generate button event handler */
  @FXML public void generate() {
    // Artificial Value Generation pop-up menu
    final Stage options = new Stage();
    options.initModality(Modality.APPLICATION_MODAL);
    options.initOwner(mainApp.getStage());
    HBox optionsBox = new HBox(10);
    optionsBox.setPadding(new Insets(10, 15, 10, 15));
    Button ok = new Button("OK");

    Label amountOfRowsLabel = new Label("Amount of row: ");

    // Artificial Value Generation Slider
    final Slider amountOfRows = new Slider(0, 500, 1);
    amountOfRows
        .valueProperty()
        .addListener((obs, oldVal, newVal) -> amountOfRows.setValue(newVal.intValue()));
    amountOfRows.setShowTickLabels(true);
    amountOfRows.setShowTickMarks(true);
    amountOfRows.setMajorTickUnit(50);
    amountOfRows.setMinorTickCount(5);

    TextField amountOfRowsValue = new TextField(String.valueOf(amountOfRows.getValue()));
    amountOfRowsValue.textProperty().bindBidirectional(amountOfRows.valueProperty(), 
        new NumberStringConverter());

    optionsBox.getChildren().add(amountOfRowsLabel);
    optionsBox.getChildren().add(amountOfRows);
    optionsBox.getChildren().add(amountOfRowsValue);

    optionsBox.getChildren().add(ok);

    Scene optionScene = new Scene(optionsBox, 600, 60);
    options.setResizable(false);
    options.setTitle("Artificial Value Generation");
    options.setScene(optionScene);
    options.show();
    resultText.appendText("Artificial values have been generated\n");

    // Artificial Value Generation Event Handler
    ok.setOnAction(e -> {
      realLayersFeatures.clear();
      for (int i = 0; i < Math.round(amountOfRows.getValue()); i++) {
        double sponginess = ArtificialValueGenerator.getRandom3Sigma();
        double amountOfClay = ArtificialValueGenerator.getRandom3Sigma();
        double amountOfCarbonate = ArtificialValueGenerator.getRandom3Sigma();
        double vPAmplitude = ArtificialValueGenerator.getRandom3Sigma();

        realLayersFeatures.add(new RealLayerFeatures(i+1, sponginess, 
            amountOfClay, amountOfCarbonate, vPAmplitude));
        options.close();
      }
    });
  }

  /** Solve Button event handler */
  @FXML public void solve() {
    resultText.appendText("\nSolving\n");
    int size = realLayersFeatures.size();
    double[][] inputs = new double[size][4];

    for (int i = 0; i < size; i++) {
      RealLayerFeatures item = realLayersFeatures.get(i);
      inputs[i][0] = item.getSponginess();
      inputs[i][1] = item.getAmountOfClay();
      inputs[i][2] = item.getAmountOfCarbonate();
      inputs[i][3] = item.getVPAmplitude();
    }

    double[] results = net.solve(inputs);
    resultText.appendText("\nResults:\n");

    for (int i = 0; i < results.length; i++) {
      resultText.appendText("\n Object [" + (i+1) + "] = ");
      if (results[i] < 0.65)
        resultText.appendText("Tire");
      else 
        resultText.appendText("Collector");
    }
  }

  /** New Training Data Button Event Handler */
  @FXML public void newTrainingData() {
    trainingLayersTable.getItems().clear();
    resultText.appendText("\nTraining Data Clearing...\n");
  }

  /** Load Training Data Button Event Handler */
  @FXML public void loadTrainingData() {
    File file = openLoadFromFileDialog("training-data.csv");
    if (file != null) {
        resultText.appendText("\nloading training data from : " + file.getAbsolutePath() + "\n");
        CSVDispatcher.filename = file.getAbsolutePath();
        trainingLayersFeatures.clear();
        CSVDispatcher.CSVFile2TList(trainingLayersFeatures);
    }
  }

  /** Save Training Data Button Event Handler */
  @FXML public void saveTrainingData() {
    File file = openSaveToFileDialog("training-data.csv");
    if (file != null) {
        resultText.appendText("\nSaving training data to : " + file.getAbsolutePath() + "\n");
        CSVDispatcher.filename = file.getAbsolutePath();
        CSVDispatcher.list2CSVFile(trainingLayersFeatures);
    }
  }

  /** New Data Button Event Handler */
  @FXML public void newData() {
    realLayersTable.getItems().clear();
    resultText.appendText("\nTraining data clearing...\n");
  }

  /** Load Data Button Event Handler */
  @FXML public void loadData() {
    File file = openLoadFromFileDialog("data.csv");
    if (file != null) {
        resultText.appendText("\nLoading data from : " + file.getAbsolutePath() + "\n");
        CSVDispatcher.filename = file.getAbsolutePath();
        realLayersFeatures.clear();
        CSVDispatcher.CSVFile2RList(realLayersFeatures);
    }
  }

  /** Save Data Button Event Handler */
  @FXML public void saveData() {
    File file = openSaveToFileDialog("data.csv");
    if (file != null) {
        resultText.appendText("\nSaving Data to : " + file.getAbsolutePath() + "\n");
        CSVDispatcher.filename = file.getAbsolutePath();
        CSVDispatcher.list2CSVFile(realLayersFeatures);
    }
  }

  /** New Weights Button Event Handler */
  @FXML public void newWeights() {
    if (net.getClass().equals(BackPropagationNeuralNet.class)) {
      net = new BackPropagationNeuralNet();
    }
    else if (net.getClass().equals(ResilientPropagationNeuralNet.class)) {
      net = new ResilientPropagationNeuralNet();
    }
    resultText.appendText("\nWeights clearing for  " + net.getClass().getSimpleName() + " \n");
  }

  /** Load Weights Button Event Handler */
  @FXML public void loadWeights() {
    File file = openLoadFromFileDialog("weights.eg");
    if (file != null) {
        net.loadWeights(file.getAbsolutePath());
        resultText.appendText("\nLoading Data from: " + file.getAbsolutePath() + "\n");
    }
  }

  /** Save Weights Button Event Handler */
  @FXML public void saveWeights() {
    File file = openSaveToFileDialog("weights.eg");
    if (file != null) {
        net.saveWeights(file.getAbsolutePath());
        resultText.appendText("\nSaving weights to : " + file.getAbsolutePath() + "\n");
    }
  }

  /** Exit Button Event Handler */
  @FXML public void exit() {
    Platform.exit();
  }

  /** Clear Results Button Event Handler */
  @FXML public void clearResults() {
    resultText.clear();
  }

  /** About Menu Item Event Handler */
  @FXML public void about() {
    final Stage about = new Stage();
    about.initModality(Modality.APPLICATION_MODAL);
    about.initOwner(mainApp.getStage());
    VBox box= null;
    try {
      box = FXMLLoader.load(App.class.getResource("resources/about.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(box);
    scene.getStylesheets().add(App.class.getResource("resources/style.css").toExternalForm());
    
    about.setTitle("About");
    about.setScene(scene);
    about.show();
  }

  /** Shortcuts Menu Item Event Handler */
  @FXML public void hotKeys() {
    final Stage shortcuts = new Stage();
    shortcuts.initModality(Modality.APPLICATION_MODAL);
    shortcuts.initOwner(mainApp.getStage());
    VBox box = null;
    try {
      box = FXMLLoader.load(App.class.getResource("resources/shortcuts.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    Scene scene = new Scene(box);
    scene.getStylesheets().add(App.class.getResource("resources/style.css").toExternalForm());

    shortcuts.setTitle("Shortcuts");
    shortcuts.setScene(scene);
    shortcuts.show();
  }

  // Table Initialization
  private void initTables() {
    CSVDispatcher.filename = "data_samples/training.csv";
    CSVDispatcher.CSVFile2TList(trainingLayersFeatures);
    CSVDispatcher.filename = "data_samples/tests.csv";
    CSVDispatcher.CSVFile2RList(realLayersFeatures);
  }

  private File openLoadFromFileDialog(String initialFileName) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialFileName(initialFileName);
    return fileChooser.showOpenDialog(mainApp.getStage());
  }

  private File openSaveToFileDialog(String initialFileName) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialFileName(initialFileName);
    return fileChooser.showSaveDialog(mainApp.getStage());
  }

}