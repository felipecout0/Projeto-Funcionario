package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/view/FuncionarioView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Cadastro de Funcionários");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            //primaryStage.getIcons().add(new Image("/view/icon.png")); // Opcional: ícone da janela
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
