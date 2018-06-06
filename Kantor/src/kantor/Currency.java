package kantor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Currency {

	public int Id;
	public String nazwa;
	public String ImageURL;
	public String Kod;
	
	public String toJSONString()
	{
		String entry = "{\"Id\": %d, \"nazwa\": \"%s\", \"Obrazek\": \"%s\", \"Kod\": \"%s\"}";
		return String.format(entry, Id, nazwa, ImageURL, Kod);
	}
	
	public void updateCurrency(boolean buy, float newamount)
	{
		if (Id > 0)
		{
			
		}
	}
	
	public static Currency getCurrencyByCode(String code, Connection conn)
	{
		if (conn == null)
		{
			conn = DB.GetConnection();
		}
		
		try
		{
			Statement select = conn.createStatement();
			String query = "SELECT * FROM Waluta WHERE Kod = '%s'";
			query = String.format(query, code);
			
			ResultSet result = select.executeQuery(query);
			
			if (result.next())
			{
				Currency waluta = new Currency();
				waluta.Id = result.getInt(1);
				waluta.nazwa = result.getString(2);
				waluta.ImageURL = result.getString(3);
				waluta.Kod = result.getString(4);
				
				return waluta;
			}
			
			return null;
		}
		catch (SQLException ex)
		{
			return null;
		}
			
	}
	
	public static String getJSONStringFromArray(Currency waluty[])
	{
		String json = "[";
		
		int i = 0;
		
		for (; i<waluty.length-1; ++i)
		{
			json = json.concat(waluty[i].toJSONString()).concat(",");
		}
		
		json = json.concat(waluty[i].toJSONString());
		
		json = json.concat("]");
		
		return json;
	}
	
	public static Currency[] GetWaluty()
	{
		Connection conn = DB.GetConnection();
		
		if (conn != null)
		{
			try {
				Statement select = conn.createStatement();
				ResultSet result = select.executeQuery("SELECT * From Waluta");
				
				List<Currency> waluty = new ArrayList<Currency>();
				
				while (result.next())
				{
					Currency temp = new Currency();
					temp.Id = result.getInt(1);
					temp.nazwa = result.getString(2);
					temp.ImageURL = result.getString(3);
					temp.Kod = result.getString(4);
					waluty.add(temp);
				}
				
				conn.close();
				return waluty.toArray(new Currency[] {});
			}
			catch (Exception ex)
			{
				
			}
		}
		
		return null;
	}
}
