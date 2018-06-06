package kantor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	public static Connection GetConnection()
	{
		String userName = "kantor";
		String password = "kantor";

		String url = "jdbc:sqlserver://localhost;";
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, userName, password);	
		}
		catch (Exception ex)
		{
			return null;
		}
		
		return conn;
	}
	
	public static boolean login(String username, String pass)
	{
		Connection conn = GetConnection();
		String query_pattern = "SELECT Id From Users where Login = '%s' AND Password = HASHBYTES('SHA2_256', '%s')";
		String query = String.format(query_pattern, username, pass);
		try
		{
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			boolean loggedIn = result.next();			
			conn.close();
			return loggedIn;
		}
		catch (SQLException ex)
		{
			return false;
		}
	}
	
	public static int getNextIdForTable(Connection conn, String table)
	{
		if (conn == null)
		{
			conn = GetConnection();
		}
		
		try
		{
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT MAX(Id) FROM "+table);
			
			if (result.next())
			{
				return result.getInt(1) + 1;
			}
			
			return 0;
		}
		catch (SQLException ex)
		{
			return 0;
		}
	}
	
}
