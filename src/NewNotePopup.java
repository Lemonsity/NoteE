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

public class NewNotePopup {

    private static Note newNote;
    static Label label;
    static Button addNewNote;
    static TextArea content;
    static DatePicker datePicker;
    static VBox datesRelated;
    static VBox rightSide;
    static HBox finalLayout;

    public static void createNewNote() throws Exception{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Note");
        window.setMinWidth(250);
        window.setResizable(false);

        label = new Label("Please enter the new note");
        addNewNote = new Button();
        addNewNote.setGraphic(new ImageView(new Image(new FileInputStream("Assets/note2.png"))));
        content = new TextArea();
        datePicker = new DatePicker();
        datesRelated = new VBox(10);
        datesRelated.getChildren().addAll(datePicker);
        rightSide = new VBox(325);
        rightSide.getChildren().addAll(datesRelated, addNewNote);

        addNewNote.setOnAction(e -> { // Creation of new note
            try {
                if (content.getText().length() != 0) {
                    if (datePicker.getValue() == null)
                        newNote = new Note(content.getText());
                    else {
                        newNote = new Note(content.getText(), datePicker.getValue());
                    }
                    window.close();
                }
            } catch (Exception ioe) {
                System.out.print("error");
            }
        });
        finalLayout = new HBox(10);
        finalLayout.setPadding(new Insets(10, 10, 10, 10));
        finalLayout.getChildren().addAll(content, rightSide);
        Scene scene = new Scene(finalLayout);
        scene.getStylesheets().add("Style/NewNote.css");
        window.setScene(scene);
        window.showAndWait();
    }

}
