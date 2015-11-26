package gui;

import beans.RealLayerFeatures;
import beans.TrainingLayerFeatures;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

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

	private Main mainApp;

	public MainController() {
	}

	@FXML
	private void initialize() {
		// Initialize table's data
		initTables();

		// Training Data table
		trainingNumbers.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("number"));
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
		realAmountOfClay
				.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfClay"));
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
			trainingLayersFeatures.add(new TrainingLayerFeatures(i, Math.random(), Math.random(), Math.random(), Math.random(), 0));
			realLayersFeatures.add(new RealLayerFeatures(i, Math.random(), Math.random(), Math.random(), Math.random()));
		}
	}

	public void setMain(Main mainApp) {
		// this.mainApp = mainApp;

		// layersTable.setItems(mainApp.getLayerFeatures());
	}

	public void train() {
		resultText.appendText(neuralNetworkType.getValue() + " : Training\n");
	}

	public void generate() {
		resultText.appendText("Generating\n");
	}

	public void solve() {
		resultText.appendText("Solving");
	}

}
