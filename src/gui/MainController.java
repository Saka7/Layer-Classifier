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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
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

		// Training Data table
		trainingNumbers.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("number"));
		trainingNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		trainingNumbers.setOnEditCommit(
			    (CellEditEvent<TrainingLayerFeatures, Integer> t) -> {
			        ((TrainingLayerFeatures) t.getTableView().getItems().get(
			            t.getTablePosition().getRow())
			            ).setNumber(t.getNewValue());
			});
		
		trainingAmountOfCarbonate
				.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("amountOfCarbonate"));
		trainingAmountOfClay
				.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("amountOfClay"));
		trainingSponginess.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("sponginess"));
		trainingVPAmplitude.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("vPAmplitude"));
		trainingType.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("type"));

		trainingLayersTable.setItems(trainingLayersFeatures);

		// Real Data table
		realNumbers.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Integer>("number"));
		realAmountOfCarbonate
				.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfCarbonate"));
		realAmountOfClay.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfClay"));
		realSponginess.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("sponginess"));
		realVPAmplitude.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("vPAmplitude"));

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

	// Useful buttons
	public void train() {
		resultText.appendText(neuralNetworkType.getValue() + " : Training\n");
	}

	public void generate() {
		resultText.appendText("Generating\n");
	}

	public void solve() {
		resultText.appendText("Solving\n");
	}

	// File Menu Bar
	public void exit() {
		Platform.exit();
	}

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

	// Help Menu
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Neural Net Predicator v1.0");
		alert.setHeaderText("Додаток реалізує нейромережі\nBackPropagation та Extended Delta Bar Delta");
		alert.setContentText(
				"Видобуток знань на основі набору\nданих для визначення типу\nпласта (коллектор, покришка)\n\n"
				+ "Розробник: ст. групи ПІ-13-2\nСакайлюк Ігор Миколайович");
		alert.showAndWait();
	}

}
