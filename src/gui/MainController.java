package gui;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import beans.RealLayerFeatures;
import beans.TrainingLayerFeatures;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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
import neuralNets.BackPropagationNeuralNet;
import neuralNets.ExtendedDeltaBarDeltaNeuralNet;
import neuralNets.NeuralNet;
import utils.ArtificialValueGenerator;
import utils.CSVDispatcher;

/** Основний контроллер-клас */
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

	private Main mainApp; // Посилання на Main-клас
	private NeuralNet net; // Нейромережа

	/** Ініціалізація форми */
	@FXML
	private void initialize() {
		net = new BackPropagationNeuralNet();

		// Ініціалізація даних в таблиці
		initTables();

		trainingLayersTable.setEditable(true);
		realLayersTable.setEditable(true);
		trainingLayersTable.setTableMenuButtonVisible(true);
		realLayersTable.setTableMenuButtonVisible(true);

		// Додати новий рядок, коли натиснута права кнопка миші
		trainingLayersTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton() == MouseButton.SECONDARY)
				trainingLayersFeatures.add(new TrainingLayerFeatures());
		});

		realLayersTable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton() == MouseButton.SECONDARY)
				realLayersFeatures.add(new RealLayerFeatures());
		});

		// Видалення виділеного рядка із таблиці
		trainingLayersTable.setOnKeyPressed((Event event) -> {
			if (((KeyEvent) event).getCode() == KeyCode.BACK_SPACE)
				trainingLayersFeatures.remove(trainingLayersTable.getSelectionModel().getSelectedIndex());
		});

		realLayersTable.setOnKeyPressed((Event event) -> {
			if (((KeyEvent) event).getCode() == KeyCode.BACK_SPACE)
				realLayersFeatures.remove(realLayersTable.getSelectionModel().getSelectedIndex());
		});

		// Таблиця тренувальних даних
		// Додання можливості редагування рядка - номер об'єкта
		trainingNumbers.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("number"));
		trainingNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		trainingNumbers.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Integer> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setNumber(t.getNewValue());
		});
		
		// Додання можливості редагування рядка - пористість
		trainingSponginess.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("sponginess"));
		trainingSponginess.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingSponginess.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setSponginess(t.getNewValue());
		});

		// Додання можливості редагування рядка - глинистість
		trainingAmountOfClay
				.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("amountOfClay"));
		trainingAmountOfClay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingAmountOfClay.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfClay(t.getNewValue());
		});
		
		// Додання можливості редагування рядка - карбонатність
		trainingAmountOfCarbonate
				.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("amountOfCarbonate"));
		trainingAmountOfCarbonate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingAmountOfCarbonate.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfCarbonate(t.getNewValue());
		});

		// Додання можливості редагування рядка - амплітуда ВП
		trainingVPAmplitude.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Double>("vPAmplitude"));
		trainingVPAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		trainingVPAmplitude.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Double> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setVPAmplitude(t.getNewValue());
		});

		// Додання можливості редагування рядка - тип
		trainingType.setCellValueFactory(new PropertyValueFactory<TrainingLayerFeatures, Integer>("type"));
		trainingType.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		trainingType.setOnEditCommit((CellEditEvent<TrainingLayerFeatures, Integer> t) -> {
			((TrainingLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setType(t.getNewValue());
		});

		trainingLayersTable.setItems(trainingLayersFeatures);

		// Таблиця реальних даних
		// Додання можливості редагування рядка - номер об'єкта
		realNumbers.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Integer>("number"));
		realNumbers.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		realNumbers.setOnEditCommit((CellEditEvent<RealLayerFeatures, Integer> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setNumber(t.getNewValue());
		});
		
		// Додання можливості редагування рядка - пористість
		realSponginess.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("sponginess"));
		realSponginess.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realSponginess.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setSponginess(t.getNewValue());
		});

		// Додання можливості редагування рядка - глинистість
		realAmountOfClay.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfClay"));
		realAmountOfClay.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realAmountOfClay.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfClay(t.getNewValue());
		});
		
		// Додання можливості редагування рядка - карбонатність
		realAmountOfCarbonate
				.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("amountOfCarbonate"));
		realAmountOfCarbonate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realAmountOfCarbonate.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setAmountOfCarbonate(t.getNewValue());
		});

		// Додання можливості редагування рядка - амплітуда ВП
		realVPAmplitude.setCellValueFactory(new PropertyValueFactory<RealLayerFeatures, Double>("vPAmplitude"));
		realVPAmplitude.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		realVPAmplitude.setOnEditCommit((CellEditEvent<RealLayerFeatures, Double> t) -> {
			((RealLayerFeatures) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setVPAmplitude(t.getNewValue());
		});

		realLayersTable.setItems(realLayersFeatures);

		// Запис типів нейромереж у combobox
		neuralNetworkType.getItems().addAll("Back Propagation", "Extended Delta Bar Delta");
		neuralNetworkType.setValue("Back Propagation");
	}

	// Ініціалізація таблиць
	private void initTables() {
		CSVDispatcher.filename = "data_samples/training.csv";
		CSVDispatcher.CSVFile2TList(trainingLayersFeatures);
		CSVDispatcher.filename = "data_samples/tests.csv";
		CSVDispatcher.CSVFile2RList(realLayersFeatures);
	}

	// Ініціалізація основного класу
	public void setMain(Main mainApp) {
		this.mainApp = mainApp;
	}

	// Обробник подій кнопки - тренувати
	public void train() {
		resultText.appendText(neuralNetworkType.getValue() + " : Тренування...\n");
		
		// Вспливаюче меню опцій тренування нейромережі
		final Stage options = new Stage();
		options.initModality(Modality.APPLICATION_MODAL);
		options.initOwner(mainApp.getStage());
		VBox optionsBox = new VBox(5);
		optionsBox.setPadding(new Insets(0, 15, 0, 15));
		optionsBox.setAlignment(Pos.CENTER);
		Button ok = new Button("OK");

		Label learningRateLabel = new Label("Швидкість навчання");
		Label maxIterationsLabel = new Label("Максимальна кількість ітерацій");
		Label maxErrorLabel = new Label("Максимально можлива похибка");

		// Слайдер швидкості навчання 
		final Slider learningRate = new Slider(0.001, 1.0, 0.001);
		learningRate.setShowTickLabels(true);
		learningRate.setShowTickMarks(true);
		learningRate.setMajorTickUnit(0.1);
		learningRate.setMinorTickCount(1);

		// Слайдер максимальної к-сті ітерацій
		final Slider maxIterations = new Slider(1, 1e4, 100);
		maxIterations.setShowTickLabels(true);
		maxIterations.setShowTickMarks(true);
		maxIterations.setMajorTickUnit(1000);
		maxIterations.setMinorTickCount(5);

		// Слайдер максильної помилки
		final Slider maxError = new Slider(.001, 1.0, .001);
		maxError.setShowTickLabels(true);
		maxError.setShowTickMarks(true);
		maxError.setMajorTickUnit(0.1);
		maxError.setMinorTickCount(1);

		// Текстове поле швидкості навчання 
		TextField learningRateValue = new TextField(String.valueOf(learningRate.getValue()));
		learningRateValue.textProperty().bindBidirectional(learningRate.valueProperty(), new NumberStringConverter());
		
		// Текстове поле максимальної к-сті ітерацій
		TextField maxIterationsValue = new TextField(String.valueOf(maxIterations.getValue()));
		maxIterationsValue.textProperty().bindBidirectional(maxIterations.valueProperty(), new NumberStringConverter());

		// Текстове поле максимальної похибки
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

		// Обробник подій кнопки Ok
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
				net = new ExtendedDeltaBarDeltaNeuralNet();
				networkType = "Extended Delta Bar Delta";
			}
			String results = net.train(inputs, expected, learningRate.getValue(), maxError.getValue(),
					Math.round(maxIterations.getValue()));
			resultText.appendText("\n\n" + networkType + " : тренування успішно завершене:\n");
			resultText.appendText("похибка = " + results.split(" ")[1]);
			resultText.appendText("\nна " + results.split(" ")[0] + " ітерації\n\n");

			String[] sweights = net.getWeights().split(",");
			double[] weights = new double[sweights.length];

			resultText.appendText("із такими Вагами:\n\n");
			for (int i = 0; i < weights.length; i++) {
				weights[i] = Double.parseDouble(sweights[i]);
				resultText.appendText(String.format("%.4f", weights[i]));
				if ((i + 1) % 4 == 0)
					resultText.appendText("\n");
				else
					resultText.appendText(",\t");
			}

			options.close();

			// Графік відношення помилки до ітерації
			List<Integer> iterations = net.getIterations();
			List<Double> errors = net.getErrors();

			final Stage chartStage = new Stage();
			chartStage.setTitle("Графік Похибки");
			// Визначення осей
			final NumberAxis xAxis = new NumberAxis();
			final NumberAxis yAxis = new NumberAxis();
			xAxis.setLabel("Ітерація");
			yAxis.setLabel("Похибка");
			// Створення графіку
			final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
			lineChart.setCreateSymbols(false);

			lineChart.setTitle("Графік похибки для " + networkType);
			// Визнаяення даних
			Series<Number, Number> series = new XYChart.Series<>();
			series.setName("Похибка");

			for (int i = 0; i < iterations.size(); i++) {
				series.getData().add(new XYChart.Data(iterations.get(i), errors.get(i)));
			}

			Scene scene = new Scene(lineChart, 800, 600);
			lineChart.getData().add(series);

			chartStage.setScene(scene);
			chartStage.show();

		});
	}

	// Обробник подій для кнопки - згенерувати
	public void generate() {
		// Вспливаюче меню опцій генерування синтетичних даних
		final Stage options = new Stage();
		options.initModality(Modality.APPLICATION_MODAL);
		options.initOwner(mainApp.getStage());
		HBox optionsBox = new HBox(10);
		optionsBox.setPadding(new Insets(10, 15, 10, 15));
		Button ok = new Button("OK");

		Label amountOfRowsLable = new Label("Кількість рядків: ");

		// Слайде для генерування синтетичних даних
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

		Scene optionScene = new Scene(optionsBox, 550, 60);
		options.setResizable(false);
		options.setTitle("Генерація синтетичних даних");
		options.setScene(optionScene);
		options.show();
		resultText.appendText("Генерація...\n");

		// Обробник подій для генерації штучних змінних
		ok.setOnAction(e -> {
			realLayersFeatures.clear();
			for (int i = 0; i < Math.round(amountOfRows.getValue()); i++) {
				double sponginess = ArtificialValueGenerator.getRandom3Sigma();
				double amountOfClay = ArtificialValueGenerator.getRandom3Sigma();
				double amountOfCarbonate = ArtificialValueGenerator.getRandom3Sigma();
				double vPAmplitude = ArtificialValueGenerator.getRandom3Sigma();

				realLayersFeatures
						.add(new RealLayerFeatures(i+1, sponginess, amountOfClay, amountOfCarbonate, vPAmplitude));
				options.close();
			}
		});
	}

	// Обробник подій для кнопки - Обчислити
	public void solve() {
		resultText.appendText("\nОбчислення\n");
		int size = realLayersFeatures.size();
		double[][] inputs = new double[size][4];

		for (int i = 0; i < size; i++) {
			RealLayerFeatures item = realLayersFeatures.get(i);
			inputs[i][0] = item.getSponginess();
			inputs[i][1] = item.getAmountOfClay();
			inputs[i][2] = item.getAmountOfCarbonate();
			inputs[i][3] = item.getVPAmplitude();
		}

		double[] results = new double[inputs.length];
		results = net.solve(inputs);
		
		resultText.appendText("\nРезальтати:\n");
		for (int i = 0; i < results.length; i++) {
			resultText.appendText("\n Об'єкт [" + (i+1) + "] = ");
			if (results[i] < .65)
				resultText.appendText("Покришка");
			else 
				resultText.appendText("Коллектор");
		}
	}

	// Меню - Файл
	// Обробник подій для кнопки - Нові тренувальні дані
	public void newTrainingData() {
		trainingLayersTable.getItems().clear();
		resultText.appendText("\nОчищення тренувальних даних...\n");
	}

	// Обробник подій для кнопки - Завантажити тренувальні дані 
	public void loadTrainingData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("training-data.csv");
		File file = fileChooser.showOpenDialog(mainApp.getStage());
		if (file == null) return;
		resultText.appendText("\nЗавантаження тренувальних даних із : " + file.getAbsolutePath() + "\n");
		CSVDispatcher.filename = file.getAbsolutePath();
		trainingLayersFeatures.clear();
		CSVDispatcher.CSVFile2TList(trainingLayersFeatures);
	}

	// Обробник подій для кнопки - Зберегти тренувальні дані 
	public void saveTrainingData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("training-data.csv");
		File file = fileChooser.showSaveDialog(mainApp.getStage());

		if (file == null)
			return;

		resultText.appendText("\nЗбереження тренувальних даних до : " + file.getAbsolutePath() + "\n");

		CSVDispatcher.filename = file.getAbsolutePath();
		CSVDispatcher.list2CSVFile(trainingLayersFeatures);
	}

	// Обробник подій для кнопки - Нові дані 
	public void newData() {
		realLayersTable.getItems().clear();
		resultText.appendText("\nОчищення тренувальних даних...\n");
	}

	// Обробник подій для кнопки - Завантажити дані
	public void loadData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("data.csv");
		File file = fileChooser.showOpenDialog(mainApp.getStage());
		if (file == null) return;
		resultText.appendText("\nЗавантаження даних із : " + file.getAbsolutePath() + "\n");
		CSVDispatcher.filename = file.getAbsolutePath();
		realLayersFeatures.clear();
		CSVDispatcher.CSVFile2RList(realLayersFeatures);
	}

	// Обробник подій для кнопки - Зберегти дані 
	public void saveData() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("data.csv");
		File file = fileChooser.showSaveDialog(mainApp.getStage());
		if (file == null) return;
		resultText.appendText("\nЗбереження даних до : " + file.getAbsolutePath() + "\n");
		CSVDispatcher.filename = file.getAbsolutePath();
		CSVDispatcher.list2CSVFile(realLayersFeatures);
	}

	// Обробник подій для кнопки - Нові ваги
	public void newWeights() {
		if (net instanceof BackPropagationNeuralNet)
			net = new BackPropagationNeuralNet();
		else if (net instanceof ExtendedDeltaBarDeltaNeuralNet)
			net = new ExtendedDeltaBarDeltaNeuralNet();
		resultText.appendText("\nОчищення ваг для  " + net.getClass().getSimpleName() + " \n");
	}

	// Обробник подій для кнопки - Завантажити ваги
	public void loadWeights() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("weights.eg");
		File file = fileChooser.showOpenDialog(mainApp.getStage());
		if (file == null) return;
		net.loadWeights(file.getAbsolutePath());
		resultText.appendText("\nЗавантаження ваг із: " + file.getAbsolutePath() + "\n");
	}

	// Обробник подій для кнопки - Зберегти ваги
	public void saveWeights() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("weights.eg");
		File file = fileChooser.showSaveDialog(mainApp.getStage());
		if (file == null) return;
		net.saveWeights(file.getAbsolutePath());
		resultText.appendText("\nЗбереження ваг до: " + file.getAbsolutePath() + "\n");
	}

	// Обробник подій для кнопки - Вихід
	public void exit() {
		Platform.exit();
	}

	// Обробник подій для кнопки - Очистити результати
	public void clearResults() {
		resultText.clear();
	}

	// Меню допомога
	// Обробник подій для кнопки - Про додаток
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
		Scene dialogScene = new Scene(dialogVbox, 250, 180);
		dialog.setTitle("Neural Net Classifier v1.0");
		dialog.setScene(dialogScene);
		dialog.show();
	}

	// Обробник подій для кнопки - Швидкі клавіші
	public void hotkeys() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(mainApp.getStage());
		VBox dialogVbox = new VBox(20);
		TextArea text = new TextArea("Додати новий рядок в таблицю \n\t-> [права кнопка миші]\n\n"
				+ "Видалити рядок із таблиці \n\t-> [backspace]");
		text.setEditable(false);
		dialogVbox.getChildren().add(text);
		Scene dialogScene = new Scene(dialogVbox, 250, 150);
		dialog.setTitle("Швидкі клавіші");
		dialog.setScene(dialogScene);
		dialog.show();
	}
}