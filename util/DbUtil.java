package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

		private String dbDriver = "com.mysql.jdbc.Driver";
		private String dbUrl = "jdbc:mysql://localhost/db_student";
		private String dbUser = "root";
		private String dbPassword = "wolfer";

		public Connection getCon() throws Exception {
			Class.forName(dbDriver);
			Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			return conn;
		}

		public void closeCon(Connection conn) throws Exception {
			if (conn != null)
				conn.close();
		}

}