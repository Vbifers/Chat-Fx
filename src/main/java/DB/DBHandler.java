package DB;


import org.example.User;

import java.sql.*;
import java.util.HashMap;

public class DBHandler extends DBConfigs {
     Connection dbConnection;
     public Connection getDbConnection() throws ClassNotFoundException, SQLException{
          String connectionString = "jdbc:mysql://"+host+":"+port+"/"+dbName+
          "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
          dbConnection = DriverManager.getConnection(connectionString, user, password);
          return dbConnection;
     }
     public void signUpUser(User user){
          String insertQuery = "INSERT INTO "+DBFields.USER_TABLE+" "+
                  "("+DBFields.USERS_FIRSTNAME+","+DBFields.USERS_LASTNAME+","+
                  DBFields.USERS_USERNAME+","+DBFields.USERS_PASSWORD+","+
                  DBFields.USERS_GENDER+","+DBFields.USERS_LOCATION+")"
                  +" VALUES(?,?,?,?,?,?)";
          try {
               PreparedStatement preparedStatement= getDbConnection().prepareStatement(insertQuery);
               preparedStatement.setString(1, user.getFirstname());
               preparedStatement.setString(2, user.getLastname());
               preparedStatement.setString(3, user.getUsername());
               preparedStatement.setString(4, user.getPassword());
               preparedStatement.setString(5, user.getGender());
               preparedStatement.setString(6, user.getLocation());

               preparedStatement.executeUpdate();
          } catch (SQLException e) {
               e.printStackTrace();
          } catch (ClassNotFoundException e) {
               e.printStackTrace();
          }

     }
     public ResultSet getUser(User user){
          ResultSet resultSet = null;
          String selectQuery = "SELECT "+DBFields.USERS_USERNAME+", "+DBFields.USERS_PASSWORD+" "
          + "FROM "+DBFields.USER_TABLE+" WHERE "+DBFields.USERS_USERNAME+"=? AND "+DBFields.USERS_PASSWORD+
                  "=?";
          try {
               PreparedStatement preparedStatement= getDbConnection().prepareStatement(selectQuery);
               preparedStatement.setString(1, user.getUsername());
               preparedStatement.setString(2, user.getPassword());
               resultSet = preparedStatement.executeQuery();
          } catch (SQLException e) {
               e.printStackTrace();
          } catch (ClassNotFoundException e) {
               e.printStackTrace();
          }
          return resultSet;
     }

}
