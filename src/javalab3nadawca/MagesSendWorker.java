package javalab3nadawca;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javafx.concurrent.Task;
import javafx.scene.layout.HBox;

public class MagesSendWorker extends Task<Void> {

    private File file; 
    private HBox hbox;
    private FXMLDocumentController doc;
       
    public MagesSendWorker(File file, HBox hbox, FXMLDocumentController doc ) {
        this.file = file;       
        this.hbox = hbox;
        this.doc = doc;
    }
        
    @Override
    protected Void call() throws Exception {
        
            Socket socket = new Socket("localhost", 9876);
            OutputStream os = socket.getOutputStream();

            try (ObjectOutputStream fos = new ObjectOutputStream(os)) {
                
                              
                fos.writeObject(file.getName());

                int count = 0;
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffor = new byte[100];
                    int dataSize;
                    while((dataSize = fis.read(buffor)) > -1 ) {
                        fos.write(buffor, 0, dataSize);

                        count += dataSize;
                        updateProgress(count, file.length());
                        updateMessage("Sending " + count + " bytes of data");               
                        Thread.sleep(8);
                    }

                } catch (Exception ex) {
                    System.out.println("Błąd otwarcie pliku!");
                }

            } catch (Exception ex) {
                System.out.println("Błąd otwarcie pliku!");
            }
       
    updateMessage("Done!");
    
    
    
    return null;
    }
    
   @Override protected void succeeded() {
             super.succeeded();
             doc.getFlowPane().getChildren().remove(hbox);
             doc.setNumberOfEndedFiles(doc.getNumberOfEndedFiles() + 1);
             doc.setProgressBar((double)doc.getNumberOfEndedFiles()/(double)doc.getNumberOfFiles());
             doc.getLabel().textProperty().setValue(null);            
        }
    
}
