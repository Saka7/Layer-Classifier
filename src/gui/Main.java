package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage = new Stage();
	private VBox rootPane;
		
	@Override
	public void start(Stage primaryStage) {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("neural-nets-main.fxml"));
			rootPane = (VBox) loader.load();
			
			MainController controller = loader.getController();
			controller.setMain(this);
			
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootPane);
			scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Neural Nets Predicator");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}