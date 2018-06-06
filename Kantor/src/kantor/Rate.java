package kantor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Rate {

	public int Id;
	public float Buy;
	public float Sell;
	public String When;
	
	Currency Currency;
	
	
	/* 
	 *  TAK, WIEM ZE KOD JEST NIEMAL TAKI SAM (tylko select inny hehe) :<
	 *  chce tylko 3 przeciez 
	 *  */
	public static Rate[] getAllCurrencyRates()
	{
		List<Rate> kursy = new ArrayList<Rate>();
		Connection conn = DB.GetConnection();
		
		String query = "select Nazwa, Kod, Obrazek, Kupno, Sprzedaz, DataZmiany from Zmiany inner join Waluta on Waluta.ID = Zmiany.ID_Waluty order by DataZmiany desc";
		
		try
		{
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			
			while (result.next())
			{
				Rate kurs = new Rate();
				Currency waluta = new Currency();
				waluta.Id = 0; 		// nieuzywane
				waluta.nazwa = result.getString(1);
				waluta.ImageURL = result.getString(3);
				waluta.Kod = result.getString(2);
				kurs.Buy = Float.parseFloat(result.getString(4));
				kurs.Sell = Float.parseFloat(result.getString(5));
				kurs.When = result.getString(6);
				
				kurs.Currency = waluta;
				kursy.add(kurs);
			}
			
			return kursy.toArray(new Rate[] {});
			
		}
		catch (SQLException ex)
		{
			return null;
		}
	}
	
	public static Rate[] getCurrencyRates()
	{
		List<Rate> kursy = new ArrayList<Rate>();
		Connection conn = DB.GetConnection();
		
		String query = "select ID, Nazwa, Obrazek, (select top 1 CONCAT(DataZmiany, '|', Kupno, '|', Sprzedaz) From Zmiany as Z where Z.ID_Waluty = W.ID order by DataZmiany desc), Kod FROM Waluta as W";
		
		try
		{
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			String buysell[] = new String[3];
			
			while (result.next())
			{
				Rate kurs = new Rate();
				Currency waluta = new Currency();
				waluta.Id = result.getInt(1);
				waluta.nazwa = result.getString(2);
				waluta.ImageURL = result.getString(3);
				waluta.Kod = result.getString(5);
				buysell = result.getString(4).split("\\|");
				kurs.Buy = Float.parseFloat(buysell[1]);
				kurs.Sell = Float.parseFloat(buysell[2]);
				kurs.When =buysell[0];
				
				kurs.Currency = waluta;
				kursy.add(kurs);
			}
			
			return kursy.toArray(new Rate[] {});
			
		}
		catch (SQLException ex)
		{
			return null;
		}
		
	}
	
	public String toJSONString()
	{
		String pattern = "{ \"Sprzedaz\": %.2f, \"Kupno\": %.2f, \"Zmiana\": \"%s\", \"Waluta\": %s }";
		
		String json = String.format(Locale.ROOT, pattern, Buy, Sell, When, Currency.toJSONString());
		
		return json;
	}
	
	public static String getJSONStringFromArray(Rate kurs[])
	{
		String json = "[";
		
		int i = 0;
		
		for (; i<kurs.length-1; ++i)
		{
			json = json.concat(kurs[i].toJSONString()).concat(",");
		}
		
		json = json.concat(kurs[i].toJSONString());
		
		json = json.concat("]");
		
		return json;
	}
	
	public void insert(Connection conn)
	{
		if (conn == null)
		{
			conn = DB.GetConnection();
		}
		
		String insertPattern = "INSERT INTO Zmiany VALUES (%d, '%s', %d, %f, %f)";
		
		try 
		{
			Statement insert = conn.createStatement();
			String query = String.format(Locale.ROOT, insertPattern, Id, When, Currency.Id, Buy, Sell);
			insert.executeQuery(query);
		}
		catch (SQLException ex)
		{
			
		}
	}
	
}
