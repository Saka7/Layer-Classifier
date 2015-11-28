package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main-клас, для підвантаження стректури та стилів і запуску додатку
 */
public class Main extends Application {

	private Stage primaryStage = new Stage();
	private VBox rootPane;
	
	public Stage getStage() {
		return primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Завантаження розташування із FXML-файлу
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("neural-nets-main.fxml"));
			rootPane = (VBox) loader.load();
			
			// Завантаження основного контроллера
			MainController controller = loader.getController();
			controller.setMain(this);
			
			Scene scene = new Scene(rootPane);
			// Завантаження стилів
			scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Neural Nets Classifier");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}