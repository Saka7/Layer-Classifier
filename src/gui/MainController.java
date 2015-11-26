package gui;

import beans.TrainingLayerFeatures;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

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
	private TableView<TrainingLayerFeatures> layersTable;
	@FXML
	private TableColumn<TrainingLayerFeatures, Integer> numbers;
	@FXML
	private TableColumn<TrainingLayerFeatures, Double> sponginess;
	@FXML
	private TableColumn<TrainingLayerFeatures, Double> amountOfClay;
	@FXML
	private TableColumn<TrainingLayerFeatures, Double> amountOfCarbonate;
	@FXML
	private TableColumn<TrainingLayerFeatures, Double> vPAmplitude;

	@FXML
	private Label numberLabel;
	@FXML
	private Label sponginessLabel;
	@FXML
	private Label amountOfClayLabel;
	@FXML
	private Label amountOfCarbonateLabel;
	@FXML
	private Label vPAmplitudeLabel;

	private Main mainApp;

	public MainController() {
	}

	@FXML
	private void initialize() {
		neuralNetworkType.getItems().addAll("Back Propagation", "Extended Delta Bar Delta");
		neuralNetworkType.setValue("Back Propagation");
		/*
		 * numbers.setCellValueFactory(cellData ->
		 * cellData.getValue().getNumberProperty().asObject());
		 * sponginess.setCellValueFactory(cellData ->
		 * cellData.getValue().getSponginessProperty().asObject());
		 * amountOfClay.setCellValueFactory(cellData ->
		 * cellData.getValue().getAmountOfClayProperty().asObject());
		 * amountOfCarbonate.setCellValueFactory(cellData ->
		 * cellData.getValue().getAmountOfCarbonateProperty().asObject());
		 * vPAmplitude.setCellValueFactory(cellData ->
		 * cellData.getValue().getvPAmplitudeProperty().asObject());
		 */}

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
