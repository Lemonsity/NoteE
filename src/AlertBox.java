import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.FileInputStream;
import java.io.IOException;

public class AlertBox {
    public static void display(int counter) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Notification");
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label;
        if (counter != 0 && counter > 1)
            label = new Label("There are " + counter + " things due");
        else if (counter == 1)
            label = new Label("There is " + counter + " thing due");
        else
            label = new Label("There is nothing due");
        label.setPadding(new Insets(10, 10, 10, 10));
        Button okay = new Button();
        okay.setGraphic(new ImageView(new Image(new FileInputStream("Assets/okay.png"))));
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, okay);
        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add("Style/AlertBox.css");
        stage.setScene(scene);
        stage.show();

        okay.setOnAction(e -> {
            stage.close();
        });
    }
}
