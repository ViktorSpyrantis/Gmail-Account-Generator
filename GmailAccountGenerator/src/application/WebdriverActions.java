package application;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class WebdriverActions {
	private static WebDriver dr;
	private static XSSFWorkbook wb;
	private static XSSFWorkbook wb2;
	private static int  accountNum = 1;

	public  void setDriverSpecs(boolean t){
		if(t == false){
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");		//ChromeDriver MUST exist on the C:\ drive 
			WebDriver driver = new ChromeDriver();
			setDriver(driver);
		}
		
	}
	
	public  void createAccount() throws java.text.ParseException, InterruptedException, IOException{
		
		XSSFSheet sheet = ExcelFileActions.getSheet(); 
		XSSFSheet sheet2 = ExcelFileActions.getSheet2(); 
		XSSFWorkbook  workbook = getWorkbook();
		XSSFWorkbook  workbook2 = getWorkbook2();
		
		WebDriver driver;
		driver = getDriver();
        driver.get("https://accounts.google.com/SignUp");
        Row row = sheet.getRow(1);
        Row row2 = sheet2.createRow(sheet2.getLastRowNum() + 1);

        if (row != null) {
           for (int cn=row.getFirstCellNum(); cn<row.getLastCellNum(); cn++) {
         	  
              Cell cell = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
              Cell cell2 = row2.createCell(cn);
              String value = "";
              
              if (cell != null) {
             	 cell.setCellType(Cell.CELL_TYPE_STRING);
	             value = cell.getStringCellValue();
	             System.out.print(cell.getStringCellValue() + "\t");
	             cell2.setCellValue(value);
	                
	             switch(cell.getColumnIndex()){
             		case 0:
             			driver.findElement(By.id("FirstName")).sendKeys(value);
             			break;
             		case 1:
             			driver.findElement(By.id("LastName")).sendKeys(value);
             			break;
             		case 2:
             			driver.findElement(By.id("GmailAddress")).sendKeys(value);
             			
             			driver.findElement(By.id("FirstName")).click();
             			TimeUnit.SECONDS.sleep(1);
             			String s = driver.findElement(By.cssSelector("#errormsg_0_GmailAddress")).getAttribute("innerHTML");
             			if(!s.isEmpty()){
             				value = driver.findElement(By.cssSelector("#username-suggestions > a:nth-child(1)")).getAttribute("innerHTML");
             			}
             			driver.findElement(By.id("GmailAddress")).clear();
             			TimeUnit.SECONDS.sleep(1);
             			driver.findElement(By.id("GmailAddress")).sendKeys(value);
             			break;
             		case 3:
             			driver.findElement(By.id("Passwd")).sendKeys(value);
             			driver.findElement(By.id("PasswdAgain")).sendKeys(value);
             			break;
             		case 04:
             			driver.findElement(By.id("BirthDay")).sendKeys(retrieveDayOrYear(value, 0) +"");
             			driver.findElement(By.id("BirthYear")).sendKeys(retrieveDayOrYear(value, 1) +"");
             			
             			driver.findElement(By.xpath("//*[@id='BirthMonth']/div[1]")).click();
             			driver.findElement(By.xpath("//*[@id=':" + retrieveMonth(value)  + "']")).click();
             			
             			break;
             		case 5:
             			driver.findElement(By.xpath("//*[@id='Gender']/div[1]/div[2]")).click();
             			if(value.length() == 4){
             				driver.findElement(By.xpath("//*[@id=':f']/div")).click();
             			}else{
             				driver.findElement(By.xpath("//*[@id=':e']/div")).click();
             			}
             			break;
             		case 6:
             			driver.findElement(By.id("RecoveryEmailAddress")).sendKeys(value);
             			break;
             			
	                }
	                
              	}
            
           }
	       sheet.shiftRows(1+1, sheet.getLastRowNum(), -1);
	           
	       driver.findElement(By.id("submitbutton")).click(); 
	       WebElement el = driver.findElement(By.cssSelector("#tos-text > div:nth-child(4) > p"));
	           
	       JavascriptExecutor jse = (JavascriptExecutor) driver;
	       jse.executeScript("arguments[0].scrollIntoView(true);", el);
	       driver.findElement(By.id("iagreebutton")).click();
	       System.out.print(accountNum + "         "+sheet.getLastRowNum());
	       addAccountNum();
	           
		   setWorkbook(workbook);
		   setWorkbook2(workbook2);
		}
        System.out.println();
    }
	
	public static int retrieveDayOrYear(String dateInString,int test) throws java.text.ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("m/d/yyyy");
        
        Date date = formatter.parse(dateInString);
        int day = 0;
        int year = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            day = cal.get(Calendar.DAY_OF_MONTH);
            year = cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(test == 0){
        	return day;
        }else{
        	return year;
        }  
	}

	public static String retrieveMonth(String dateInString) throws java.text.ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
        
        Date date = formatter.parse(dateInString);
        int month = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            month = cal.get(Calendar.MONTH);
            System.out.println(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch(month){
        	case 0:
        	case 1:
        	case 2:
        	case 3:
        	case 4:
        	case 5:
        	case 6:
        	case 7:
        	case 8:
        		return month + 1 + "";
        	case 9:
        		return "a";
        	case 10:
        		return "b";
        	case 11:
        		return "c";
        }
        return "";
	}
	
	private static void setDriver(WebDriver d){
		dr = d;
	}
	private static WebDriver getDriver(){
		return dr;
	}
	
	private static void setWorkbook(XSSFWorkbook w){
		wb = w;
	}
	private static XSSFWorkbook getWorkbook(){
		return wb;
	}
	
	private static void setWorkbook2(XSSFWorkbook w){
		wb2 = w;
	}
	private static XSSFWorkbook getWorkbook2(){
		return wb2;
	}
	
	private static void addAccountNum(){
		accountNum++;
	}
	
	public static int getAccountNum(){
		return accountNum;
	}
	
}
