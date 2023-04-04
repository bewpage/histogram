package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
  private static final String DB_URL = "jdbc:mysql://localhost:3306";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "alamakota";
  private static final String HISTOGRAM_DB = "hist_ex";

  public static Connection connect(String databaseName) throws SQLException {
    String url = DB_URL + "/" + databaseName;
    return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
  }

  public static Connection connectHistDB() throws SQLException {
    return connect(HISTOGRAM_DB);
  }
}
