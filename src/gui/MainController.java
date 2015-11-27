package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import beans.RealLayerFeatures;
import beans.RealLayerFeaturesData;
import beans.TrainingLayerFeatures;
import beans.TrainingLayerFeaturesData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import utils.CSVDispatcher;

public class MainController {

	@FXML
	Button trainButton;
	@FXML
	Button generateButton;
	@FXML
	Button solveButton;
	@FXML
	TextArea resultText;
	@FXML
	ComboBox<String> neuralNetworkType;

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

	private List<TrainingLayerFeaturesData> trainingLayersFeaturesData = new ArrayList<>();

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

	private List<RealLayerFeaturesData> realLayersFeaturesData = new ArrayList<>();

	private Main mainApp;

	public MainController() {
	}

	@FXML
	private void initialize() {
		// Initialize table's data
		initTables();

		// Set table editable
		trainingLayersTable.setEditable(true);
		realLayersTable.setEditable(true);
		trainingLayersTable.setTableMenuButtonVisible(true);
		realLayersTable.setTableMenuButtonVisible(true);

		// Adding new row when right mouse button is clicked
		trainingLayersTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton() == MouseButton.SECONDARY)
				trainingLayersFeatures.add(new TrainingLayerFeatures());
		});

		realLayersTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton() == MouseButton.SECONDARY)
				realLayersFeatures.add(new RealLayerFeatures());
		});

		// Deleting selected row from tables
		trainingLayersTable.setOnKeyPressed((Event event) -> {
			if (((KeyEvent) event).getCode() == KeyCode.BACK_SPACE)
				trainingLayersFeatures.remove(trainingLayersTable.getSelectionModel().getSelectedIndex());
		});

		realLayersTable.setOnKeyPressed((Event event) -> {
			if (((KeyEvent) event).getCode() == KeyCode.BACK_SPACE)
				realLayersFeatures.remove(realLayersTable.getSelectionModel().getSelectedIndex());
		});

		// Training Data table
		// Number cell
		trainingNumbers.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("number"));
		trainingNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		trainingNumbers.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Integer> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setNumber(t.getNewValue());
		});

		// Amount of Carbonate cell
		trainingAmountOfCarbonate
				.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("amountOfCarbonate"));
		trainingAmountOfCarbonate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingAmountOfCarbonate.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfCarbonate(t.getNewValue());
		});

		// Amount of Clay cell
		trainingAmountOfClay
				.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("amountOfClay"));
		trainingAmountOfClay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingAmountOfClay.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfClay(t.getNewValue());
		});

		// Sponginess cell
		trainingSponginess.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("sponginess"));
		trainingSponginess.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingSponginess.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setSponginess(t.getNewValue());
		});

		// VPAmplitude cell
		trainingVPAmplitude.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("vPAmplitude"));
		trainingVPAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingVPAmplitude.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setVPAmplitude(t.getNewValue());
		});

		// Type cell
		trainingType.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("type"));
		trainingType.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		trainingType.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Integer> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setType(t.getNewValue());
		});

		// Adding items to table
		trainingLayersTable.setItems(trainingLayersFeatures);

		// Real Data table
		// Number cell
		realNumbers.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Integer>("number"));
		realNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		realNumbers.setOnEditCommit((CellEditEvent<RealLayerFeatures, Integer> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setNumber(t.getNewValue());
		});

		// Amount Of Carbonate cell
		realAmountOfCarbonate
				.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfCarbonate"));
		realAmountOfCarbonate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realAmountOfCarbonate.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfCarbonate(t.getNewValue());
		});

		// Amount of Clay cell
		realAmountOfClay.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfClay"));
		realAmountOfClay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realAmountOfClay.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfClay(t.getNewValue());
		});

		// Sponginess cell
		realSponginess.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("sponginess"));
		realSponginess.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realSponginess.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setSponginess(t.getNewValue());
		});

		// VPAmplitude cell
		realVPAmplitude.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("vPAmplitude"));
		realVPAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realVPAmplitude.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setVPAmplitude(t.getNewValue());
		});

		// Adding items to table
		realLayersTable.setItems(realLayersFeatures);

		// Set values to combobox
		neuralNetworkType.getItems().addAll("Back Propagation", "Extended Delta Bar Delta");
		neuralNetworkType.setValue("Back Propagation");
	}

	// Initialize tables with random values
	private void initTables() {
		for (int i = 0; i < 20; i++) {
			trainingLayersFeatures
					.add(new TrainingLayerFeatures(i, Math.random(), Math.random(), Math.random(), Math.random(), 0));
			realLayersFeatures
					.add(new RealLayerFeatures(i, Math.random(), Math.random(), Math.random(), Math.random()));
		}
	}

	public void setMain(Main mainApp) {
		this.mainApp = mainApp;
	}

	// Train neural nets with parameters
	public void train() {
		final Stage options = new Stage();
		options.initModality(Modality.APPLICATION_MODAL);
		options.initOwner(mainApp.getStage());
		VBox optionsBox = new VBox(5);
		optionsBox.setPadding(new Insets(0, 15, 0, 15));
		Button ok = new Button("OK");

		Label learningRateLabel = new Label("Learning Rate");
		Label maxIterationsLabel = new Label("Max Iteration");
		Label maxErrorLabel = new Label("Max Error");

		final Slider learningRate = new Slider(0.001, 1.0, 0.001);
		learningRate.setShowTickLabels(true);
		learningRate.setShowTickMarks(true);
		learningRate.setMajorTickUnit(50);
		learningRate.setMinorTickCount(5);

		final Slider maxIterations = new Slider(1, 1000, 1);
		maxIterations.setShowTickLabels(true);
		maxIterations.setShowTickMarks(true);
		maxIterations.setMajorTickUnit(50);
		maxIterations.setMinorTickCount(5);

		final Slider maxError = new Slider(.001, 1.0, .001);
		maxError.setShowTickLabels(true);
		maxError.setShowTickMarks(true);
		maxError.setMajorTickUnit(50);
		maxError.setMinorTickCount(5);

		TextField learningRateValue = new TextField(String.valueOf(learningRate.getValue()));
		learningRateValue.textProperty().bindBidirectional(learningRate.valueProperty(), new NumberStringConverter());

		TextField maxIterationsValue = new TextField(String.valueOf(maxIterations.getValue()));
		maxIterationsValue.textProperty().bindBidirectional(maxIterations.valueProperty(), new NumberStringConverter());

		TextField maxErrorValue = new TextField(String.valueOf(maxError.getValue()));
		maxErrorValue.textProperty().bindBidirectional(maxError.valueProperty(), new NumberStringConverter());

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
		resultText.appendText(neuralNetworkType.getValue() + " : Training\n");
	}

	// Generating test table's rows
	public void generate() {
		final Stage options = new Stage();
		options.initModality(Modality.APPLICATION_MODAL);
		options.initOwner(mainApp.getStage());
		HBox optionsBox = new HBox(10);
		optionsBox.setPadding(new Insets(10, 15, 10, 15));
		Button ok = new Button("OK");

		Label amountOfRowsLable = new Label("Amount of rows: ");

		final Slider amountOfRows = new Slider(1, 500, 1);
		amountOfRows.setShowTickLabels(true);
		amountOfRows.setShowTickMarks(true);
		amountOfRows.setMajorTickUnit(50);
		amountOfRows.setMinorTickCount(5);

		TextField amountOfRowsValue = new TextField(String.valueOf(amountOfRows.getValue()));
		amountOfRowsValue.textProperty().bindBidirectional(amountOfRows.valueProperty(), new NumberStringConverter());

		optionsBox.getChildren().add(amountOfRowsLable);
		optionsBox.getChildren().add(amountOfRows);
		optionsBox.getChildren().add(amountOfRowsValue);

		optionsBox.getChildren().add(ok);

		Scene optionScene = new Scene(optionsBox, 520, 60);
		options.setResizable(false);
		options.setTitle("Artificial value generating");
		options.setScene(optionScene);
		options.show();
		resultText.appendText(neuralNetworkType.getValue() + " : Training\n");
		resultText.appendText("Generating\n");
	}

	public void solve() {
		resultText.appendText("Solving\n");
	}

	// File Menu Bar
	public void newTrainingData() {
		trainingLayersTable.getItems().clear();
		resultText.appendText("\nClearing training data...\n");
	}

	public void loadTrainingData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("training-data.csv");
		File file = fileChooser.showOpenDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nLoading training data from : " + file.getAbsolutePath() + "\n");

		CSVDispatcher.filename = file.getAbsolutePath();
		trainingLayersFeatures.clear();
		CSVDispatcher.CSVFile2TList(trainingLayersFeatures);

	}

	public void saveTrainingData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("training-data.csv");
		File file = fileChooser.showSaveDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nSaving training data to : " + file.getAbsolutePath() + "\n");

		CSVDispatcher.filename = file.getAbsolutePath();
		CSVDispatcher.list2CSVFile(trainingLayersFeatures);
	}

	public void newData() {
		realLayersTable.getItems().clear();
		resultText.appendText("\nClearing data...\n");
	}

	public void loadData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("data.csv");
		File file = fileChooser.showOpenDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nLoading data from : " + file.getAbsolutePath() + "\n");
		CSVDispatcher.filename = file.getAbsolutePath();
		realLayersFeatures.clear();
		CSVDispatcher.CSVFile2RList(realLayersFeatures);
	}

	public void saveData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("data.csv");
		File file = fileChooser.showSaveDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nSaving data to : " + file.getAbsolutePath() + "\n");

		CSVDispatcher.filename = file.getAbsolutePath();
		CSVDispatcher.list2CSVFile(realLayersFeatures);
	}

	public void newWeights() {
		// TODO Implement clearing weights
	}

	public void loadWeights() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("weights.csv");
		File file = fileChooser.showOpenDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nLoading weights from : " + file.getAbsolutePath() + "\n");
	}

	public void saveWeights() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("weights.csv");
		File file = fileChooser.showSaveDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nSaving weights to: " + file.getAbsolutePath() + "\n");
	}

	public void exit() {
		Platform.exit();
	}

	public void clearResults() {
		resultText.clear();
	}

	// Help Menu
	public void about() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(mainApp.getStage());
		VBox dialogVbox = new VBox(20);
		TextArea text = new TextArea("Додаток реалізує нейромережі\n" + "BackPropagation\nта Extended Delta Bar Delta\n"
				+ "Видобуток знань на основі набору\n" + "даних для визначення типу\n"
				+ "nпласта (коллектор, покришка)\n\n" + "Розробник: ст. групи ПІ-13-2\nСакайлюк Ігор Миколайович");
		text.setEditable(false);
		dialogVbox.getChildren().add(text);
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setTitle("Neural Net Predicator v1.0");
		dialog.setScene(dialogScene);
		dialog.show();
	}

	public void hotkeys() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(mainApp.getStage());
		VBox dialogVbox = new VBox(20);
		TextArea text = new TextArea("Додати новий рядок в таблицю \n\t-> [права кнопка миші]\n\n"
				+ "Видалити рядок із таблиці \n\t-> [backspace]");
		text.setEditable(false);
		dialogVbox.getChildren().add(text);
		Scene dialogScene = new Scene(dialogVbox, 300, 150);
		dialog.setTitle("Швидкі клавіші");
		dialog.setScene(dialogScene);
		dialog.show();
	}

}