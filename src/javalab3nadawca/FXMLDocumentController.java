package javalab3nadawca;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class FXMLDocumentController implements Initializable {
      
    @FXML
    private Label statusLabel;
    
    @FXML
    private ProgressBar progressBar;
        
    @FXML
    private FlowPane  flowPane ;    
        
    private int numberOfFiles = 0;
    private Integer numberOfEndedFiles = 0;
    private ArrayList<ProgressBar> barList = new ArrayList<>();
    private ArrayList<Label> labelList = new ArrayList<>();
    
    public Label getLabel() {
        return this.statusLabel;
    }
    
    public FlowPane getFlowPane() {
        return this.flowPane;
    }
    
    public int getNumberOfFiles() {
        return this.numberOfFiles;
    }
    
    public void setNumberOfFiles(int a) {
        this.numberOfFiles = a;
    }
   
    public int getNumberOfEndedFiles() {
        return this.numberOfEndedFiles;
    }
    
    public void setNumberOfEndedFiles(int a) {
        this.numberOfEndedFiles = a;
    }
    
    public ProgressBar getProgressBar() {
        return this.progressBar;
    }
    
    public void setProgressBar(double progressBar) {
        this.progressBar.setProgress(progressBar);
    }
    
    @FXML
    private void sendFileAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        
        HBox hbox = new HBox();
        madeNewProgressBar(hbox, file.getName());
       
        
        MagesSendWorker worker = new MagesSendWorker(file, hbox, this);
        
        barList.get(numberOfFiles).progressProperty().bind(worker.progressProperty());
        labelList.get(numberOfFiles).textProperty().bind(worker.messageProperty());
        numberOfFiles++;

        statusLabel.textProperty().setValue(null);
     
        
        Thread th = new Thread(worker);
        th.start();
        
    }
    
    
     protected void madeNewProgressBar(HBox hbox, String fileName) {         
        
        hbox.setSpacing(10);
         
        Label labelName = new Label(fileName);    
        ProgressBar loclaProgressBar = new ProgressBar(0.0);
        Label labelProgres = new Label();
         
        hbox.getChildren().add(labelName);
        hbox.getChildren().add(loclaProgressBar);
        barList.add(loclaProgressBar);
        hbox.getChildren().add(labelProgres);
        labelList.add(labelProgres);
          
        flowPane.getChildren().add(hbox);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        statusLabel.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                statusLabel.textProperty().setValue("Liczba plików " +
                        String.valueOf(numberOfEndedFiles) + " na " + String.valueOf(numberOfFiles));

            }
        });                
    }   
    
}
