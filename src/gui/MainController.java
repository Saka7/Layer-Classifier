package gui;

import beans.TrainingLayerFeatures;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
		resultText.appendText("Training\n");
	}

	public void generate() {
		resultText.appendText("Generating\n");
	}

	public void solve() {
		resultText.appendText("Solving");
	}

}
