import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main extends Application {

    // Data
    private boolean isOnNote;
    private HashMap<String, Note> notes = new HashMap<>();
    private String currentOn;

    // Visual elements
    // Top bar
    private Button newNote;
    private Button delete;
    private Button save;

    private DatePicker datePicker;
    private ToggleButton checkDone;

    // Left bar
    private ToggleButton toCalendar;
    private ToggleButton toNote;

    // Note title buttons
    private ArrayList<ToggleButton> titleButtons = new ArrayList<>();

    // Note area
    private TextArea content = new TextArea();

    private GridPane calendar;

    private HBox topLeft;
    private HBox topMid;
    private HBox topRight;
    private HBox topBar;
    private VBox leftBar;
    private HBox bottomSection;
    private  VBox finalLayout = new VBox();

    Scene scene;
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Initialize
        isOnNote = true;
        readFiles();

        stage = primaryStage;
        stage.setTitle("NoteE");

        // Creation of entities

        newNote = new Button();
        newNote.setId("newNote");
        newNote.setGraphic(new ImageView(new Image(new FileInputStream("Assets/new.png"), 30, 30, false, false)));
        delete = new Button();
        delete.setId("deleteNote");
        delete.setGraphic(new ImageView(new Image(new FileInputStream("Assets/delete.png"), 30, 30, false, false)));
        save = new Button();
        save.setId("save");
        save.setGraphic(new ImageView(new Image(new FileInputStream("Assets/save.png"), 30, 30, false, false)));

        // Date and repeat for particular note
        datePicker = new DatePicker();
        datePicker.setId("datePicker");
        checkDone = new ToggleButton();
        checkDone.setId("checkDone");
        checkDone.setSelected(false);
        checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/uncheck.png"), 30, 30, false, false)));


        // Different theme
        toCalendar = new ToggleButton();
        toCalendar.setSelected(false);
        toCalendar.setId("toCalendar");
        toCalendar.getStyleClass().add("leftBarButton");
        toCalendar.setGraphic(new ImageView(new Image(new FileInputStream("Assets/calendar.png"))));
        toNote = new ToggleButton();
        toNote.setSelected(true);
        toNote.setId("toNote");
        toNote.getStyleClass().add("leftBarButton");
        toNote.setGraphic(new ImageView(new Image(new FileInputStream("Assets/note.png"))));

        // Layout
        topLeft = new HBox();
        topLeft.getChildren().addAll(newNote, delete, save);
        topLeft.setAlignment(Pos.CENTER_LEFT);
        topLeft.setPadding(new Insets(10, 7, 10, 7));
        topMid = new HBox();
        topMid.getChildren().addAll(datePicker);
        topMid.setAlignment(Pos.CENTER);
        topRight = new HBox();
        topRight.getChildren().addAll(checkDone);
        topRight.setAlignment(Pos.CENTER_RIGHT);
        topBar = new HBox(75);
        topBar.getChildren().addAll(topLeft, topMid, topRight);
        topBar.setId("topBar");
        topBar.setAlignment(Pos.CENTER);

        leftBar = new VBox();
        leftBar.getChildren().addAll(toNote, toCalendar);

        bottomSection = new HBox();
        bottomSection.setId("bottomSection");
        bottomSection.getChildren().addAll(leftBar, createTitleButtons(), content);

        finalLayout.getChildren().addAll(topBar, bottomSection);

        // Showing the scene
        scene = new Scene(finalLayout, 1000, 600);
        scene.getStylesheets().add("Style/MainPage.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        alert();

        // functionality
        newNote.setOnAction(e -> {
            try {
                NewNotePopup.createNewNote();
            } catch (Exception allE){

            }
            currentOn = null;
            content.setText("");
            datePicker.setValue(null);
            checkDone.setSelected(false);
            try {
                if (checkDone.isSelected())
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/check.png"), 30, 30, false, false)));
                else
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/unCheck.png"), 30, 30, false, false)));
            } catch (IOException ioe) {

            }
            readFiles();
            try {
                scene = LayoutCreation.layout(topBar, createTitleButtons(), content, leftBar);
            } catch (Exception ex) {

            }
            scene.getStylesheets().add("Style/MainPage.css");
            stage.setScene(scene);
            stage.show();
        });
        delete.setOnAction(e -> {
            File file = new File("Data" + File.separator + currentOn + ".txt");
            file.delete();
            notes.remove(currentOn);
            currentOn = null;
            content.setText("");
            datePicker.setValue(null);
            checkDone.setSelected(false);
            try {
                if (checkDone.isSelected())
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/check.png"), 30, 30, false, false)));
                else
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/unCheck.png"), 30, 30, false, false)));
            } catch (IOException ioe) {

            }
            readFiles();
            try {
                scene = LayoutCreation.layout(topBar, createTitleButtons(), content, leftBar);
            } catch (Exception ex) {

            }
            scene.getStylesheets().add("Style/MainPage.css");
            stage.setScene(scene);
            stage.show();
        });
        save.setOnAction(e -> {
            try {
                File file = new File("Data" + File.separator + currentOn + ".txt");
                file.delete();
                currentOn = null;
                Note temp = new Note(content.getText(), datePicker.getValue(), checkDone.isSelected());
                content.setText("");
                datePicker.setValue(null);
                checkDone.setSelected(false);
                if (checkDone.isSelected())
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/check.png"), 30, 30, false, false)));
                else
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/unCheck.png"), 30, 30, false, false)));
            } catch (IOException ioe) {

            }
            readFiles();
            try {
                scene = LayoutCreation.layout(topBar, createTitleButtons(), content, leftBar);
            } catch (Exception ex) {

            }
            scene.getStylesheets().add("Style/MainPage.css");
            stage.setScene(scene);
            stage.show();
        });
        checkDone.setOnAction(e -> {
            try {
                if (checkDone.isSelected())
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/check.png"), 30, 30, false, false)));
                else
                    checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/unCheck.png"), 30, 30, false, false)));
            } catch (Exception allE) {

            }
        });
        toCalendar.setOnAction(e -> {
            if (isOnNote) {
                toNote.setSelected(false);
                isOnNote = false;
            }
            toCalendar.setSelected(true);
        });
        toNote.setOnAction( e -> {
            if (!isOnNote) {
                toCalendar.setSelected(false);
                isOnNote = true;
            }
            toNote.setSelected(true);
        });

    }

    // refresh the ArrayList to contain all the files
    private void readFiles() {
        notes.clear();
        File folder = new File("Data");
        File[] fileList = folder.listFiles();
        Arrays.sort(fileList);
        for (File file : fileList) {
            try {
                String lastEdited;
                String content = "";
                String date;
                String done;
                if (!file.getName().equals(".DS_Store")) {
                    FileReader fr = new FileReader("Data" + File.separator + file.getName());
                    BufferedReader br = new BufferedReader(fr);
                    lastEdited = br.readLine();
                    String input = br.readLine();
                    while (!input.equals("-")) {
                        content += input + "\n";
                        input = br.readLine();
                    }
                    date = br.readLine();
                    done = br.readLine();
                    br.close();
                    fr.close();
                    notes.put(lastEdited, new Note(lastEdited, content, date, done));
                }
            } catch (IOException ioe) {

            }
        }
    }

    private VBox createTitleButtons() {
        VBox layout = new VBox();
        layout.setMaxWidth(300);
        layout.setMinWidth(300);
        layout.setId("titleColumn");
        titleButtons.clear();
        String[] temp = notes.keySet().toArray(new String[notes.keySet().size()]);
        Arrays.sort(temp, Collections.reverseOrder());
        for (String s : temp) {
            Note n = notes.get(s);
            String title = "";
            for (int i = 0; i < 20; i++) {
                if (i <= 18 && n.getContent().substring(i, i+1).equals("\n"))
                    break;
                title += n.getContent().charAt(i);
            }
            ToggleButton button;
            String date = n.getReminderDate();
            if (!date.equalsIgnoreCase("X")) {
                button = new ToggleButton(date + "\n" + title);
                if (!LocalDate.now().isBefore(LocalDate.parse(date)) && !n.isDone()) {
                    try {
                        button.setGraphic(new ImageView(new Image(new FileInputStream("Assets/notification.png"))));
                    } catch (IOException ioe) {

                    }
                }
            }
            else
                button = new ToggleButton(title);

            button.setAlignment(Pos.CENTER_LEFT);
            button.getStyleClass().add("titleButtons");
            button.setId(n.getLastEdit());
            titleButtons.add(button);
        }
        for (ToggleButton b : titleButtons) {
            b.setOnAction(e -> {
                Note memory = notes.get(b.getId());
                currentOn = memory.getLastEdit();
                content.setText(memory.getContent());
                if (!memory.getReminderDate().equalsIgnoreCase("X"))
                    datePicker.setValue(LocalDate.parse(memory.getReminderDate()));
                else
                    datePicker.setValue(null);
                checkDone.setSelected(memory.isDone());
                try {
                    if (checkDone.isSelected())
                        checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/check.png"), 30, 30, false, false)));
                    else
                        checkDone.setGraphic(new ImageView(new Image(new FileInputStream("Assets/unCheck.png"), 30, 30, false, false)));
                } catch (IOException ioe) {

                }
                for (ToggleButton B : titleButtons)
                    B.setSelected(false);
                b.setSelected(true);
            });
            layout.getChildren().add(b);
        }
        return layout;
    }

    private void alert() throws IOException{
        int counter = 0;
        for (String key : notes.keySet()) {
            if (!notes.get(key).getReminderDate().equalsIgnoreCase("X")) {
                LocalDate timeExtract = LocalDate.parse(notes.get(key).getReminderDate());
                if (!LocalDate.now().isBefore(timeExtract) && !notes.get(key).isDone()) {
                    counter++;
                }
            }
        }
        AlertBox.display(counter);
    }
}