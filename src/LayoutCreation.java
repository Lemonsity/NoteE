import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.io.IOException;

public class LayoutCreation {

    public static Scene layout(HBox topBar, VBox titleButtons, TextArea content, VBox leftBar) throws Exception{
        HBox bottom = new HBox();
        bottom.getChildren().addAll(leftBar, titleButtons, content);
        VBox finalLayout = new VBox();
        finalLayout.getChildren().addAll(topBar, bottom);
        Scene scene = new Scene(finalLayout, 1000, 600);
        scene.getStylesheets().add("Style/MainPage.css");
        return scene;
    }

}
