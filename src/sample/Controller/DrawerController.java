package sample.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class DrawerController {
    @FXML
    private JFXButton sortingBtn;

    @FXML
    private JFXButton pathfindingBtn;

    @FXML
    private JFXButton hashingBtn;

    @FXML
    private JFXButton aboutBtn;

    @FXML
    private void drawerBtnOnClick(ActionEvent event) throws IOException {
        Parent root;
        if (event.getSource().equals(sortingBtn)) {
            root = FXMLLoader.load(Main.class.getResource("View/sorting.fxml"));
        } else if (event.getSource().equals(pathfindingBtn)) {
            root = FXMLLoader.load(Main.class.getResource("View/pathfinding.fxml"));
        } else if (event.getSource().equals(hashingBtn)){
            root = FXMLLoader.load(Main.class.getResource("View/hashing.fxml"));
        } else {
            Stage stage = new Stage();
            root = FXMLLoader.load(Main.class.getResource("View/about.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("About AlgoHolic");
            stage.getIcons().add(new Image("file:src/sample/View/icon.png"));
            stage.setResizable(false);
            return;
        }

        Stage stage = (Stage) aboutBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
