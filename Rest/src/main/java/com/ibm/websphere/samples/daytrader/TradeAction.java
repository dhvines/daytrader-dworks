
// TODO: split into multiple files and change this one to call them

package com.ibm.websphere.samples.daytrader;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

//Jackson
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.websphere.samples.daytrader.util.Log;

public class TradeAction implements TradeServices {

	private static ObjectMapper mapper = new ObjectMapper(); // create once, reuse

    @Override
	public RunStatsDataBean resetTrade(boolean deleteAll) throws Exception {
	   	String responseString = invokeEndpoint("/rest/traders/" + deleteAll, "DELETE");
        RunStatsDataBean responseObject;
        responseObject = mapper.readValue(responseString,RunStatsDataBean.class);
        return responseObject;
	}
	
    @Override
	public MarketSummaryDataBean getMarketSummary() throws Exception {
	   	String responseString = invokeEndpoint("/rest/markets", "GET");
        MarketSummaryDataBean responseObject;
        responseObject = mapper.readValue(responseString,MarketSummaryDataBean.class);
        return responseObject;
	}
	
    @Override
	public QuoteDataBean createQuote(String symbol, String companyName, BigDecimal price) throws Exception {
	   	String responseString = invokeEndpoint("/rest/quotes/" + symbol + "/" + companyName + "/" + price , "POST");
        QuoteDataBean responseObject;
        responseObject = mapper.readValue(responseString,QuoteDataBean.class);
        return responseObject;
	}

    @Override
	public QuoteDataBean getQuote(String symbol) throws Exception {
	   	String responseString = invokeEndpoint("/rest/quotes/" + symbol, "GET");
        QuoteDataBean responseObject;
        responseObject = mapper.readValue(responseString,QuoteDataBean.class);
        return responseObject;
	}
	
    @Override
	public Collection<?> getAllQuotes() throws Exception {
	   	String responseString = invokeEndpoint("/rest/quotes", "GET");
        Collection<QuoteDataBean> responseObject;
        responseObject = mapper.readValue(responseString,Collection.class);
        return responseObject;
	}

    @Override
    public QuoteDataBean updateQuotePriceVolume(String symbol, BigDecimal changeFactor, double sharesTraded) throws Exception {
	   	String responseString = invokeEndpoint("/rest/quotes/" + symbol + "/" + changeFactor + "/" + sharesTraded, "PUT");
        QuoteDataBean responseObject;
        responseObject = mapper.readValue(responseString,QuoteDataBean.class);
        return responseObject;
	}

    @Override
    public Collection<?> getHoldings(String userID) throws Exception {
	   	String responseString = invokeEndpoint("/rest/accounts/" + userID + "/holdings", "GET");
        Collection<HoldingDataBean> responseObject;
        responseObject = mapper.readValue(responseString,Collection.class);
        return responseObject;
    }

    @Override
    public HoldingDataBean getHolding(Integer holdingID) throws Exception {
	   	String responseString = invokeEndpoint("/rest/accounts/" + "UNUSED" + "/holdings/" + holdingID, "GET");
        HoldingDataBean responseObject;
        responseObject = mapper.readValue(responseString,HoldingDataBean.class);
        return responseObject;
    }

    @Override
    public AccountDataBean getAccountData(String userID) throws Exception {
	   	String responseString = invokeEndpoint("/rest/accounts/" + userID, "GET");
        AccountDataBean responseObject;
        responseObject = mapper.readValue(responseString,AccountDataBean.class);
        return responseObject;
    }
 
    @Override
    public AccountProfileDataBean getAccountProfileData(String userID) throws Exception {
	   	String responseString = invokeEndpoint("/rest/accounts/" + userID + "/profile", "GET");
        AccountProfileDataBean responseObject;
        responseObject = mapper.readValue(responseString,AccountProfileDataBean.class);
        return responseObject;
    }

    @Override
    public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean accountProfileData) throws Exception {
    	String userID = accountProfileData.getUserID();
    	String password = accountProfileData.getPassword();
    	String fullName = accountProfileData.getFullName();
    	String address = accountProfileData.getAddress();
    	String email = accountProfileData.getEmail();
    	String creditCard = accountProfileData.getCreditCard();
        
	   	String responseString = invokeEndpoint("/rest/accounts/" + userID + "/profile/" + password
	   			+ "/" + fullName + "/" + address + "/" + email + "/" + creditCard, "PUT");
        AccountProfileDataBean responseObject;
        responseObject = mapper.readValue(responseString,AccountProfileDataBean.class);
        return responseObject;
    }
    
    @Override
    public AccountDataBean login(String userID, String password) throws Exception {
	   	String responseString = invokeEndpoint("/rest/accounts/" + userID + "/" + password, "PUT");
        AccountDataBean responseObject;
        responseObject = mapper.readValue(responseString,AccountDataBean.class);
        return responseObject;
    }

    @Override
    public void logout(String userID) throws Exception {
	   	invokeEndpoint("/rest/accounts/" + userID, "PUT");
    }

    @Override
    public AccountDataBean register(String userID, String password, String fullname, String address, String email, String creditCard, BigDecimal openBalance) throws Exception {
	   	String responseString = invokeEndpoint("/rest/accounts/" + 
	   			userID + "/" + password + "/" +
	   			fullname + "/" + address + "/" +
	   			email + "/" + creditCard + "/" +
	   			openBalance, "POST");
        AccountDataBean responseObject;
        responseObject = mapper.readValue(responseString,AccountDataBean.class);
        return responseObject;
    }

    @Override
	public OrderDataBean buy(String userID, String symbol, double quantity, int orderProcessingMode) throws Exception {
	   	String responseString;
	   	responseString = invokeEndpoint(
	   			"/rest/accounts/" + userID + "/buyorders/" + symbol + "/" + quantity + "/" + orderProcessingMode, 
	   			"POST");
        OrderDataBean responseObject = mapper.readValue(responseString,OrderDataBean.class);
        return responseObject;
        
	}
	
    @Override
	public OrderDataBean sell(String userID, Integer holdingID, int orderProcessingMode) throws Exception {
	   	String responseString;
	   	responseString = invokeEndpoint(
	   			"/rest/accounts/" + userID + "/sellorders/" + holdingID + "/" + orderProcessingMode, 
	   			"POST");
	   	OrderDataBean responseObject = mapper.readValue(responseString,OrderDataBean.class);
	   	return responseObject;
	}

    @Override
	public Collection<?> getOrders(String userID) throws Exception {
	   	String responseString = invokeEndpoint( "/rest/accounts/" + userID + "/orders", "GET");
	   	Collection<OrderDataBean> responseObject = mapper.readValue(responseString,Collection.class);
	   	return responseObject;
	}

    @Override
	public Collection<?> getClosedOrders(String userID) throws Exception {
	   	String responseString = invokeEndpoint( "/rest/accounts/" + userID + "/closedorders", "PUT");
	   	Collection<OrderDataBean> responseObject = mapper.readValue(responseString,Collection.class);
	   	return responseObject;
	}

    @Override
	public void queueOrder(Integer orderID, boolean twoPhase) throws Exception {
    	throw new UnsupportedOperationException("TradeAction:queueOrder method not supported");
	}

    @Override
	public OrderDataBean completeOrder(Integer orderID, boolean twoPhase) throws Exception {
    	throw new UnsupportedOperationException("TradeAction:completeOrder method not supported");
	}

    @Override
	public void cancelOrder(Integer orderID, boolean twoPhase) throws Exception {
    	throw new UnsupportedOperationException("TradeAction:cancelOrder method not supported");
	}

    @Override
	public void orderCompleted(String userID, Integer orderID) throws Exception {
    	throw new UnsupportedOperationException("TradeAction:orderCompleted method not supported");
	}
	
    private String invokeEndpoint(String endpoint, String method) {
        String route = System.getProperty("rest.app.route");
    
        Response response = sendRequest(route + endpoint, method);
        int responseCode = response.getStatus();
        if ( responseCode != 200 )  {
        	throw new BadRequestException("Incorrect response dode: " + responseCode);
        }
        
        String responseString = response.readEntity(String.class);
        response.close();
        return responseString;
    }
    
    private Response sendRequest(String url, String method) {
        Client client = ClientBuilder.newClient();
        // Require to avoid an illegal state exception: entity must not be null for http method put
    	client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        WebTarget target = client.target(url);
        Invocation.Builder invoBuild = target.request();
        Response response = null;
        response = invoBuild.build(method).invoke();
        return response;
    }

}
