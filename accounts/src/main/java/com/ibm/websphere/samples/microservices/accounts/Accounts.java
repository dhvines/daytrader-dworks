/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.ibm.websphere.samples.microservices.accounts;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.AccountDataBean;
import com.ibm.websphere.samples.daytrader.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.HoldingDataBean;
import com.ibm.websphere.samples.daytrader.OrderDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;

public class Accounts {

    public Accounts() {}
   
    private static TradeDirect impl = new TradeDirect();

	/**
     * @see TradeServices#getHoldings(String)
     */
    @GET
    @Path("/{userID}/holdings")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Collection<HoldingDataBean> getHoldings(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getHoldings(userID);
	}

	/**
     * @see TradeServices#getHolding(Integer)
     */
    @GET
    @Path("/{userID}/holdings/{holdingID}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public HoldingDataBean getHolding(@PathParam(value = "holdingID") Integer holdingID) throws Exception {
		return impl.getHolding(holdingID);
	}

	/**
     * @see TradeServices#getAccountData(String)
     */
    @GET
    @Path("/{userID}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public AccountDataBean getAccountData(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getAccountData(userID);
	}

	/**
     * @see TradeServices#getAccountProfileData(String)
     */
    @GET
    @Path("/{userID}/profile")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public AccountProfileDataBean getAccountProfileData(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getAccountProfileData(userID);
	}

	/**
     * @see TradeServices#updateAccountProfile(AccountProfileDataBean)
     */
    @PUT
    @Path("/{userID}/profile")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean profileData) throws Exception {
		return impl.updateAccountProfile(profileData);
	}

	/**
     * @see TradeServices#login(String, String)
     */
    @PUT
    @Path("/{userID}/{password}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public AccountDataBean login(@PathParam(value = "userID") String userID, 
			@PathParam(value = "password") String password) throws Exception {
		return impl.login(userID, password);
	}

	/**
     * @see TradeServices#logout(String)
     */
    @PUT
    @Path("/{userID}")
    @Consumes({"application/json"})
	public void logout(@PathParam(value = "userID") String userID) throws Exception {
		impl.logout(userID);
	}

	/**
     * @see TradeServices#register(String, String, String, String, String,
     *      String, BigDecimal, boolean)
     */
    @POST
    @Path("/{userID}/{password}/{fullname}/{address}/{email}/{creditcard}/{openBalance}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public AccountDataBean register(@PathParam(value = "userID") String userID, 
			@PathParam(value = "password") String password, @PathParam(value = "fullname") String fullname, 
			@PathParam(value = "address") String address, @PathParam(value = "email") String email, 
			@PathParam(value = "creditcard") String creditcard, 
			@PathParam(value = "openBalance") BigDecimal openBalance) throws Exception {
		return impl.register(userID, password, fullname, address, email, creditcard, openBalance);
	}
    
    //
    // Orders are tightly coupled to accounts wrt database queries
    //

	/**
     * @see TradeServices#buy(String, String, double)
     */
    @POST
    @Path("/{userID}/orders/{symbol}/{quantity}/{orderProcessingMode}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public OrderDataBean buy(@PathParam(value = "userID") String userID, @PathParam(value = "symbol") String symbol, 
			@PathParam(value = "quantity") double quantity, 
			@PathParam(value = "orderProcessingMode") int orderProcessingMode) throws Exception {
		return impl.buy(userID, symbol, quantity, orderProcessingMode);
	}	
    
	/**
     * @see TradeServices#sell(String, Integer)
     */
    @POST
    @Path("/{userID}/orders/{holdingID}/{orderProcessingMode}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public OrderDataBean sell(@PathParam(value = "userID") String userID, @PathParam(value = "holdingID") Integer holdingID, 
			@PathParam(value = "orderProcessingMode") int orderProcessingMode) throws Exception {
		return impl.sell(userID, holdingID, orderProcessingMode);
	}
    
    //
    // Note: although the daytrader monolith  does not use the userid in this implementation, 
    // i feel as if it should still be passed in simply because of the tight coupling between 
    // the account and orders in the database. of course, this smight also be an area for the
    // subsequent separation of orders from accounts but to do so you will need to eliminate
    // the tight coupling in the database (e.g. the queries over accounts and orders table)
    //
    
	/**
     * @see TradeServices#queueOrder(Integer, boolean)
     */
    @PUT
    @Path("{userid}/orders/{orderID}/{twoPhase}")
    @Consumes({"application/json"})
	public void queueOrder(@PathParam(value = "userID") Integer userID, @PathParam(value = "orderID") Integer orderID, 
			@PathParam(value = "twoPhase") boolean twoPhase) throws Exception {
		impl.queueOrder(orderID, twoPhase);
	}
    
    //
    // Note: although the daytrader monolith  does not use the userid in this implementation, 
    // i feel as if it should still be passed in simply because of the tight coupling between 
    // the account and orders in the database. of course, this smight also be an area for the
    // subsequent separation of orders from accounts but to do so you will need to eliminate
    // the tight coupling in the database (e.g. the queries over accounts and orders table)
    //
    
	/**
     * @see TradeServices#completeOrder(Integer, boolean)
     */
    @POST
    @Path("{userid}/orders/{orderID}/{twoPhase}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public OrderDataBean completeOrder(@PathParam(value = "userID") Integer userID, @PathParam(value = "orderID") Integer orderID, 
    		@PathParam(value = "twoPhase") boolean twoPhase) throws Exception {
		return impl.completeOrder(orderID, twoPhase);
	}

    //
    // Note: although the daytrader monolith  does not use the userid in this implementation, 
    // i feel as if it should still be passed in simply because of the tight coupling between 
    // the account and orders in the database. of course, this smight also be an area for the
    // subsequent separation of orders from accounts but to do so you will need to eliminate
    // the tight coupling in the database (e.g. the queries over accounts and orders table)
    //
    
	/**
     * @see TradeServices#cancelOrder(Integer, boolean)
     */
    @DELETE
    @Path("{userid}/orders/{orderID}/{twoPhase}")
    @Consumes({"application/json"})
	public void cancelOrder(@PathParam(value = "userID") Integer userID, @PathParam(value = "orderID") Integer orderID, 
			@PathParam(value = "twoPhase") boolean twoPhase) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:cancelOrder method not supported" );
	}

	/**
     * @see TradeServices#orderCompleted(String, Integer)
     */
    @PUT
    @Path("/{userID/orders/{orderID}/{twoPhase}")
    @Consumes({"application/json"})
	public void orderCompleted(@PathParam(value = "userID") String userID, 
			@PathParam(value = "orderID") Integer orderID) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:orderCompleted method not supported" );
	}

	/**
     * @see TradeServices#getOrders(String)
     */
    @GET
    @Path("/{userID}/orders")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public Collection<OrderDataBean> getOrders(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getOrders(userID);
	}
    
	/**
     * @see TradeServices#getClosedOrders(String)
     */
    @GET
    @Path("/{userID}/closedorders")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Collection<OrderDataBean> getClosedOrders(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getOrders(userID);
	}

}
