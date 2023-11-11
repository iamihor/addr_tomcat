/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class AppDb {

  public static Connection getConnection() throws Exception {
    AppUtility Util=new AppUtility();
    String drvClassName = Util.getProp("jdbc.My.driver");
    String url = Util.getProp("jdbc.My.url");
    String password = Util.getProp("jdbc.My.password");
    String user = Util.getProp("jdbc.My.user");
    try {
      Class.forName(drvClassName);
      Connection con = DriverManager.getConnection(url, user, password);
      return con;
    }
    catch (SQLException ex1) {
      throw new Exception("Could not create connection : " + ex1.getMessage()+url+user);
    }
  }

 
}

