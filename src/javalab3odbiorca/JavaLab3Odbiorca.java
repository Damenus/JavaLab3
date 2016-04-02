package javalab3odbiorca;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLab3Odbiorca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
              
        try {
             ServerSocket server = new ServerSocket(9876);
             
             while(true) {
                 Socket socket = server.accept();
                 
                 Runnable runnable = new Runnable() {
                     @Override
                     public void run() {
                                                 
                         ObjectInputStream ois = null; 
                         try {
                             byte[] buffor = new byte[100];
                             int dataSize;
                             ois = new ObjectInputStream(socket.getInputStream()); 
                             
                             try(FileOutputStream fos = new FileOutputStream(ois.readObject().toString())) { //is.toString())
                                    while ((dataSize = ois.read(buffor)) > -1) {
                                        fos.write(buffor, 0, dataSize);
                                    }
                                    System.out.println("Zapisano plik!");
                                 } catch (Exception ex) {
                                     System.out.print("Błąd zapisu!");
                                 }                                                           
                             
                         } catch (FileNotFoundException ex) {
                             Logger.getLogger(JavaLab3Odbiorca.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (IOException ex) {
                             Logger.getLogger(JavaLab3Odbiorca.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (Exception ex) {
                             System.out.print("Błąd serwara!");
                         } finally {
                             if (ois != null) {
                                 try {
                                     ois.close();
                                 } catch (Exception ex) {
                                     System.out.print("Błąd serwara!");
                                 }
                             }
                         }                   
                         
                     }                     
                 };
                 
                 Thread thread = new Thread(runnable);
                 thread.start();
                 
             }
             
        } catch (Exception ex) {
            System.out.print("Błąd serwara!");
        }
    }
    
}
