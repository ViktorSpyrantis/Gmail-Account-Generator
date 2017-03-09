package application;


import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ImportWindowController {
	@FXML
	private Button importButton;
	private static String  filePath ;
	
	public void importButtonClick() throws IOException{
		JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file == null) {
                return;
            }

            filePath = chooser.getSelectedFile().getAbsolutePath();
            setFilePath(filePath);
            System.out.println(filePath);
        }
        goToMainWindow();
        Stage stage =  (Stage)importButton.getScene().getWindow();
        stage.close();
	}
	
	private void goToMainWindow(){
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			root.getStyleClass().clear();
			root.getStyleClass().add("application.css");
			stage.setTitle("Profile Generator");
			stage.setScene(new Scene(root,500,300));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setFilePath(String filePath){
		ImportWindowController.filePath = filePath;
	}
	
	public static String getFilePath(){
		return filePath;
	}

}
