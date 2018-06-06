function populateRateTable(adminpage)
{
	var tabeladom = $("#content_table");
	tabeladom.empty();
	var tabela = 0;
	
	$.ajax("Kurs").done(function(data){
		tabela = JSON.parse(data);
		
		for (var i = 0; i<tabela.length; ++i)
		{
			tabeladom.append("<tr>");
			tabeladom.append(`<td> <img class='pic' src='${tabela[i].Waluta.Obrazek}'></img> </td>`);
			tabeladom.append(`<td> ${tabela[i].Waluta.nazwa} </td>`);
			tabeladom.append(`<td> ${tabela[i].Waluta.Kod} </td>`);
			tabeladom.append(`<td> ${tabela[i].Kupno} </td>`);
			tabeladom.append(`<td> ${tabela[i].Sprzedaz} </td>`);
			tabeladom.append(`<td> ${tabela[i].Zmiana} </td>`);
			
			if (adminpage === true)
			{
				var domcontent = "<td>";
				domcontent += `<button onclick="changeBuy('${tabela[i].Waluta.Kod}', true, 0, 0)"> Zmien kurs kupna </button>`;
				domcontent += `<button onclick="nbpsync('${tabela[i].Waluta.Kod}')"> Pobierz kurs z NBP </button>`;
				domcontent += "</td>";
				tabeladom.append(domcontent);
				//tabeladom.append(`<td> ${tabela[i].Zmiana} </td>`);
			}
			
			tabeladom.append("</tr>");
		}
		
	});
}

function nbpsync(currency)
{
	$.ajax(`http://api.nbp.pl/api/exchangerates/rates/C/${currency}/?format=json`).done(function(data){
		
		var buy = data.rates[0].bid;
		var sell = data.rates[0].ask;
		
		changeBuy(currency, false, buy, sell);
		
	});
}

function changeBuy(currency, prompt, buy, sell)
{
	if (prompt === true) buy = prompt("Podaj nowy kurs kupna");
	
	if (isNaN(buy))
	{
		alert("Wprowadzono niepoprawny kurs kupna!");
		return;
	}
	
	if (prompt === true) sell = prompt("Podaj nowy kurs sprzedazy");
	
	if (isNaN(sell))
	{
		alert("Wprowadzono niepoprawny kurs sprzedazy!");
		return;
	}
	
	$.ajax(`UpdateRate?Currency=${currency}&ValueBuy=${buy}&ValueSell=${sell}`).done(function(data)
	{
		if (data.length == 0)
		{
			// udalo sie
		}
	});
}

function MainInit()
{
	populateRateTable(false);
}

function populateHistoryTable()
{
	var tabelahistory = $("#history");
	tabelahistory.empty();
	var tabela = 0;
	
	$.ajax("History").done(function(data){
		tabela = JSON.parse(data);
		
		for (var i = 0; i<tabela.length; ++i)
		{
			tabelahistory.append("<tr>");
			tabelahistory.append(`<td> <img class='pic' src='${tabela[i].Waluta.Obrazek}'></img> </td>`);
			tabelahistory.append(`<td> ${tabela[i].Waluta.nazwa} </td>`);
			tabelahistory.append(`<td> ${tabela[i].Waluta.Kod} </td>`);
			tabelahistory.append(`<td> ${tabela[i].Kupno} </td>`);
			tabelahistory.append(`<td> ${tabela[i].Sprzedaz} </td>`);
			tabelahistory.append(`<td> ${tabela[i].Zmiana} </td>`);
			tabelahistory.append("</tr>");
		}
		
	});
}


// wiem.. znowu
function AdminInit()
{
	populateHistoryTable();
	populateRateTable(true);
}