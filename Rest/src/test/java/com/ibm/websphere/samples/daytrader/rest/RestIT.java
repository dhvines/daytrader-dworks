/*******************************************************************************
 * Copyright (c) 2016 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the \"License\");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/ 
package com.ibm.websphere.samples.daytrader.rest;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Test;

import com.ibm.websphere.samples.daytrader.TradeAction;

import com.ibm.websphere.samples.daytrader.AccountDataBean;
import com.ibm.websphere.samples.daytrader.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.HoldingDataBean;
import com.ibm.websphere.samples.daytrader.MarketSummaryDataBean;
import com.ibm.websphere.samples.daytrader.OrderDataBean;
import com.ibm.websphere.samples.daytrader.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.RunStatsDataBean;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

// TODO: split htis into multiple files ... split test into multiple tests

public class RestIT {
	
	private static TradeAction tAction = new TradeAction();
	
   // DHV
   // ADDED FOR STEP 2 ==> Traders
   @Test
   public void testResetTrade() throws Exception{
      tAction.resetTrade(false);
   }

   
   //
   // ****************************
   //
   
   // DHV
   // ADDED FOR STEP 2 ==> Market
   @Test
   public void testGetMarketSummary() throws Exception {
        tAction.getMarketSummary();
     }
   
   //
   // ****************************
   //
   
   // DHV ==> Quotes
   // ADDED FOR STEP 2 
   @Test
   public void testCreateQuote() throws Exception {
      java.util.UUID uuid = java.util.UUID.randomUUID();
      String symbol = "s:" + uuid.toString();
      String companyName = "S" + uuid.toString() + " Incorporated";
      BigDecimal price = new BigDecimal(138.00);
      tAction.createQuote(symbol, companyName, price);
   }

   // DHV ==> Quotes
   // ADDED FOR STEP 2
   @Test
   public void testGetQuote() throws Exception {
      QuoteDataBean quote = tAction.getQuote("s:1");
   }

   // DHV ==> Quotes
   // ADDED FOR STEP 2
   @Test
   public void testGetAllQuotes() throws Exception {
      Collection<QuoteDataBean> quotesList = (Collection<QuoteDataBean>)tAction.getAllQuotes();
   }

   // DHV ==> Quotes
   // ADDED FOR STEP 2
   @Test
   public void testUpdateQuotePriceVolume() throws Exception {
      tAction.updateQuotePriceVolume( "s:0", new BigDecimal(0.0), 100.0);
   }

   //
   // ****************************
   //
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testGetHoldings() throws Exception {
      Collection<HoldingDataBean> holdingsList = (Collection<HoldingDataBean>)tAction.getHoldings("uid:0");
   }

   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testGetHolding() throws Exception {
      HoldingDataBean holding = tAction.getHolding(1);
   }
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testGetAccountData() throws Exception {
      AccountDataBean account = tAction.getAccountData("uid:0");
   }

   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testGetAccountProfileData() throws Exception {
      AccountProfileDataBean accountProfile = tAction.getAccountProfileData("uid:0");
   }
   

   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testUpdateAccountProfile() throws Exception {
      AccountProfileDataBean accountProfileData = new AccountProfileDataBean();
      accountProfileData.setAddress("nyc");
      accountProfileData.setCreditCard("999999999");
      accountProfileData.setEmail("uid:0" + "@ibm.com");
      accountProfileData.setFullName("foo");
      accountProfileData.setPassword("xxx");
      accountProfileData.setUserID("uid:0");
      
      tAction.updateAccountProfile(accountProfileData);
   }

   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testLogin() throws Exception {
      AccountDataBean accountData = tAction.login("uid:0","xxx");
   }
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testLogout() throws Exception {
      tAction.logout("uid:0");
   }

   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testRegister() throws Exception {
	  String userID = java.util.UUID.randomUUID().toString();
      tAction.register(userID, "xxx", "foo", "nyc",  "foo@ibm.com", "999999999",  new BigDecimal(10000.00));
   }
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testBuy() throws Exception {
      tAction.buy("uid:0","s:1",100,0);
   }
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testSell() throws Exception {
      tAction.sell("uid:0", 3, 0);
   }
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testGetOrders() throws Exception {
   	tAction.getOrders("uid:0");
   }
   
   // DHV ==> Accounts
   // ADDED FOR STEP 2
   @Test
   public void testGetCosedOrders() throws Exception {
      tAction.getClosedOrders("uid:0");
   }
   
   //
   // ****************************
   //
   
   // DHV ==> Addresses
   // ADDED FOR STEP 1
    @Test
    public void testGetAddresses() {
    	String expectedOutput = null;
        testEndpoint("/rest/addresses", expectedOutput, "GET");
    }
      
    @Test
    public void testGetAddress() {
    	String expectedOutput = null;
    	testEndpoint("/rest/addresses/Entry4", expectedOutput, "GET");
    }

    @Test
    public void testSearch() {
    	String expectedOutput = null;
    	testEndpoint("/rest/addresses/search/Entry4", expectedOutput, "GET");
    }
    
    private void testEndpoint(String endpoint, String expectedOutput, String httpMethod) {
        String route = System.getProperty("rest.app.route");
    
        Response response = sendRequest(route + endpoint, httpMethod);
        int responseCode = response.getStatus();
        assertTrue("Incorrect response code: " + responseCode,responseCode == 200);
        
        String responseString = response.readEntity(String.class);
        if (expectedOutput != null) {
        	assertTrue("Incorrect response, response is " + responseString, responseString.contains(expectedOutput));
        }
        response.close();
    }

    private Response sendRequest(String url, String requestType) {
        Client client = ClientBuilder.newClient();
    	client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        WebTarget target = client.target(url);
        Invocation.Builder invoBuild = target.request();
        Response response = null;
        response = invoBuild.build(requestType).invoke();
        return response;
    }
    
}
