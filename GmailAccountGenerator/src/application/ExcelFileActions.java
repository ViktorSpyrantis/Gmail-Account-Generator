package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileActions {
	private static XSSFSheet sh;
	private static XSSFSheet sh2;
	private static XSSFWorkbook wb;
	private static XSSFWorkbook wb2;
	
	//private ImportWindowController iwcObj = new ImportWindowController();
	private String inFilePath = ImportWindowController.getFilePath();
    private File file1 = new File(inFilePath);
    private String fileFolder = file1.getParent();
	File file2;
	private String outFilePath = fileFolder + "ready.xlsx";
	
	//@SuppressWarnings("resource")
	public void setFiles() throws IOException{
		try {
			System.out.println("pathhhh" + inFilePath);
	        FileInputStream fileIn = new FileInputStream(new File(inFilePath));
	        
	        //Creates Workbook instance holding reference to the .xlsx file of the account data to be imported
	        XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
	        XSSFSheet sheet = workbook.getSheetAt(0);
	        
	        //Creates Workbook instance holding reference to the .xlsx file of the created accounts data
	      
			XSSFWorkbook workbook2 = new XSSFWorkbook();
	        XSSFSheet sheet2 = workbook2.createSheet("profiles");;
	        

	        file2 = new File(fileFolder + "\\ready.xlsx");
	        System.out.println(file2.getPath());
	        //Creates an xlsx file where each created account's data will be stored if it doesn't already exist
	        if(!file2.exists()){
	        	file2.createNewFile();
	        }else{
	        	workbook2 = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file2));
	        	//FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
	        	sheet2 = workbook2.getSheetAt(0);
	        }

	        setSheet(sheet);
	        setSheet2(sheet2);
	        setWorkbook(workbook);
	        setWorkbook2(workbook2);
	        fileIn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void outputData() throws IOException{
		XSSFWorkbook  workbook = getWorkbook();
		XSSFWorkbook  workbook2 = getWorkbook2();
		FileOutputStream fileOut = new FileOutputStream(new File(inFilePath));
		FileOutputStream fileOut2 = new FileOutputStream(new File(outFilePath));
		workbook.write(fileOut);
		workbook2.write(fileOut2);
		
	    fileOut.flush();
	    fileOut.close();
	    fileOut2.flush();
	    fileOut2.close();

		workbook.close();
		workbook2.close();
	}
	
	
	private static void setSheet(XSSFSheet s ){
		sh = s;
	}
	public static XSSFSheet getSheet(){
		return sh;
	}
	
	private static void setSheet2(XSSFSheet s ){
		sh2 = s;
	}
	public static XSSFSheet getSheet2(){
		return sh2;
	}
	
	private static void setWorkbook(XSSFWorkbook w){
		wb = w;
	}
	public static XSSFWorkbook getWorkbook(){
		return wb;
	}
	
	private static void setWorkbook2(XSSFWorkbook w){
		wb2 = w;
	}
	public static XSSFWorkbook getWorkbook2(){
		return wb2;
	}
	
	

	
}
