package com.wcrawler.dao;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class H2Dao extends FilePersistentBase implements Pipeline {
    private String path;
    private String tableName;
    private Map<String,Boolean> wholeMap;
    private int maxLineNum;
    private String jdbcUrl;
    private String embedded  = "jdbc:h2:";
    private String user = "test";
    private String password = "test";
    private String driverClass="org.h2.Driver";
    private String creatTable;



    public H2Dao(String path, String tableName, Map<String,Boolean> wholeMap,Map<String,String> lengthMap, int maxLineNum){
        this.path = path;
        this.tableName = tableName;
        this.wholeMap = wholeMap;
        this.maxLineNum = maxLineNum;
        this.jdbcUrl = embedded +path+"/"+"data";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(jdbcUrl,user,password);
            Statement statement = connection.createStatement();
            statement.execute("drop table "+ tableName +" if"+" exists ");
            stringBuilder.append("create table ").append(tableName).append("(id int primary key, ");
            for (String s:wholeMap.keySet()){
                stringBuilder.append(s).append(" varchar(").append(lengthMap.get(s)).append("),");
            }
            stringBuilder.append(")");
            System.out.println(stringBuilder.toString());
            statement.execute(stringBuilder.toString());

//            // 4、新增
//           statement.executeUpdate("insert into user_info(id,name,age,sex) values(1,'张三',23,'男' )");
//            statement.executeUpdate("insert into user_info(id,name,age,sex) values(2,'李四',25,'男' )");
//            statement.executeUpdate("insert into user_info(id,name,age,sex) values(3,'王五',33,'男' )");
//            statement.executeUpdate("insert into user_info(id,name,age,sex) values(4,'珠帘',23,'女' )");
//            statement.executeUpdate("insert into user_info(id,name,age,sex) values(5,'鲤鱼',20,'女' )");
//            ResultSet rs = statement.executeQuery("select * from user_info");
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + " - " + rs.getString(2)
//                        + " - " + rs.getInt(3)+ " - " + rs.getString(4) );
//            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void process(ResultItems resultItems, Task task) {

    }

    private void add(Map<String,Object> map){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ").append(tableName).append("(id,");
        for (String s:map.keySet()){

        }
    }
}
