import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Note {

    // content
    private String content;
    private String lastEdit;

    // Date related
    private String reminderDate;
    private boolean done;

    public Note(String content) throws IOException{ // Only content
        this.lastEdit = currentTimeToString();
        this.content = content;
        this.reminderDate = "X";
        this.done = false;
        creationOfFile();
    }
    public Note(String content, LocalDate date) throws IOException{ // One time events
        this.lastEdit = currentTimeToString();
        this.content = content;
        this.reminderDate = date.getYear() + "-";
        if (date.getMonthValue() < 10)
            this.reminderDate += "0";
        this.reminderDate += date.getMonthValue() + "-";
        if (date.getDayOfMonth() < 10)
            this.reminderDate += "0";
        this.reminderDate += date.getDayOfMonth() + "";
        this.done = false;
        creationOfFile();
    }
    public Note(String content, LocalDate date, boolean done) throws IOException{
        this.lastEdit = currentTimeToString();
        this.content = content;
        if (date != null) {
            this.reminderDate = date.getYear() + "-";
            if (date.getMonthValue() < 10)
                this.reminderDate += "0";
            this.reminderDate += date.getMonthValue() + "-";
            if (date.getDayOfMonth() < 10)
                this.reminderDate += "0";
            this.reminderDate += date.getDayOfMonth() + "";
        }
        else {
            this.reminderDate = "X";
        }
        this.done = done;
        creationOfFile();
    }
    public Note(String lastEdit, String content, String date, String done) {
        this.lastEdit = lastEdit;
        this.content = content;
        this.reminderDate = date;
        if (done.equals("true"))
            this.done = true;
        else
            this.done = false;
    }

    private void creationOfFile() throws IOException{
        String fileLocationAndName = "Data" + File.separator;
        fileLocationAndName += lastEdit;
        FileWriter fw = new FileWriter(fileLocationAndName + ".txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(this.lastEdit + "\n");
        bw.write(this.content + "\n");
        bw.write("-\n");
        if (this.reminderDate != "X")
            bw.write(this.reminderDate + "\n");
        else
            bw.write("X\n");
        bw.write(this.done + "");
        bw.close();
    }

    public String currentTimeToString() {
        String temp = "";
        String time = LocalDateTime.now().toString();
        for (int i = 0; i < time.length(); i++) {
            char character = time.charAt(i);
            if (character == 'T')
                temp += " ";
            else if (character == ':')
                temp += "-";
            else
                temp += character + "";
        }
        return temp;
    }

    public String getLastEdit() { return lastEdit; }
    public String getContent() { return content; }
    public String getReminderDate() { return reminderDate; }
    public boolean isDone() { return done; }
}
