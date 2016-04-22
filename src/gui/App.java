package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class, which launch the application
 */
public class App extends Application {

	private Stage primaryStage = new Stage();
	private VBox rootPane;
	
	public Stage getStage() {
		return primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("main-layout.fxml"));
			rootPane = (VBox) loader.load();
			
			AppController controller = loader.getController();
			controller.setMain(this);
			
			Scene scene = new Scene(rootPane);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Neural Net Classifier");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}