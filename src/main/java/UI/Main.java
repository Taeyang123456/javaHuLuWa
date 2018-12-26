package UI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("HuLuWa Battle");
        Scene scene = new Scene(root, 1000, 750);

        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        scene.setOnKeyPressed(controller.new KeyEventHandle());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event-> System.exit(0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
