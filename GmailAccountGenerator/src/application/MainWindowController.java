package application;

import java.io.IOException;
import java.text.ParseException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainWindowController {
	public VBox layout;
	public Button submitButt;
	public Button quitButt;
	@FXML
	public Label mainLabel;
	
	static int  accountNum;
	private boolean test = false; 
	private ExcelFileActions efxObj = new ExcelFileActions();
	private WebdriverActions wdaObj = new WebdriverActions();
	
	public void onSubmitButtClicked() throws IOException, ParseException, InterruptedException {
		efxObj.setFiles();
		wdaObj.setDriverSpecs(test);
		test = true;
		wdaObj.createAccount();
		efxObj.outputData();
		mainLabel.setText( WebdriverActions.getAccountNum() - 1 + " accounts have been created");
	}
	
	public void quitButtClicked(){
		Platform.exit();
	    System.exit(0);
	}
	
	


}
