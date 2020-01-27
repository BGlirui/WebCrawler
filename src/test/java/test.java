import com.wcrawler.dao.GetJsonToExcel;
import com.wcrawler.dao.H2Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.minidev.json.JSONArray;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {

    @Test
    public void test(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\李睿\\Desktop\\111.xlsx");
            fileOutputStream.close();
            System.out.println(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPoiRemoveRow(){
        FileInputStream fileInputStream = null;
        FileOutputStream outputStream = null;
        try {
//            fileInputStream = new FileInputStream("C:\\Users\\李睿\\Desktop\\111.xlsx");
////            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
////            XSSFSheet  xssfSheet = xssfWorkbook.getSheet("Sheet1");
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            xssfWorkbook.createSheet("Sheet1");

            System.out.println(xssfWorkbook.getSheet("Sheet1").getLastRowNum());





            outputStream = new FileOutputStream("C:\\Users\\李睿\\Desktop\\111.xlsx");
            xssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void hash(){
        String a = "asndasdaksjdalksdla";
        System.out.println(a.hashCode());
    }
    @Test
    public void date(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
    }

    @Test
    public void getTest(){
//        File file = new File("data");
//        File[] files = file.listFiles();
//        System.out.println(files.length);
//        for (int i=0;i<files.length;i++){
//            try {
//               FileInputStream fileInputStream = new FileInputStream(files[i].getPath());
//                System.out.println(files[i].getName()+".."+ JSONArray.);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
    }


    @Test
    public void testH2(){
        Map<String,Boolean> map = new HashMap<String, Boolean>();
        map.put("aaa",true);
        map.put("ddd",true);
        map.put("ccc",true);
        Map<String,String> lengthMap = new HashMap<String, String>();
        lengthMap.put("aaa","10");
        lengthMap.put("ddd","50");
        lengthMap.put("ccc","10");
        System.out.println(map.toString());
        //H2Dao h2Dao = new H2Dao("C:\\Users\\李睿\\Desktop\\teas","abc",map,lengthMap,10);
    }

    @Test
    public void testJson(){
        GetJsonToExcel getJsonToExcel = new GetJsonToExcel();
        getJsonToExcel.input("C:\\Users\\李睿\\Desktop\\teas\\");
        //getJsonToExcel.toExcel("data\\拉拉-17-54-40");
        for(String s:getJsonToExcel.getFileMap().keySet()){
            System.out.println("生成了"+s);
            getJsonToExcel.toExcel(s,"C:\\Users\\李睿\\Desktop\\teas\\"+s+".xlsx");
        }
    }

    @Test
    public void testList(){
        File file = new File("hhh.java");
        System.out.println(file.getName().substring(0,file.getName().lastIndexOf(".")));
    }


}
