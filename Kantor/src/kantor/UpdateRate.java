package kantor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateRate
 */
@WebServlet("/UpdateRate")
public class UpdateRate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRate()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String temp = null;
		//$.ajax(`History?Currency=${currency}&Value=${buy}&Type=Buy`).done(function(data)
		temp = request.getParameter("ValueBuy");
		float valueBuy = temp != null ? Float.parseFloat(temp) : 0;
		
		temp = request.getParameter("ValueSell");
		float valueSell = temp != null ? Float.parseFloat(temp) : 0;
		
		temp = request.getParameter("Currency");
		String currency = temp != null ? temp : "USD";
		
		Connection conn = DB.GetConnection();
		
		Rate rate = new Rate();
		rate.Currency = Currency.getCurrencyByCode(currency, conn);
		rate.Buy = valueBuy;
		rate.Sell = valueSell;
		rate.When = new Timestamp(System.currentTimeMillis()).toString();
		rate.Id = DB.getNextIdForTable(conn, "Zmiany");
		
		
		rate.insert(conn);
		
		try
		{
			conn.close();
		}
		catch (SQLException ex)
		{
			
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
