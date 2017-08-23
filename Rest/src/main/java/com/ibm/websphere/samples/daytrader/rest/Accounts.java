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

package com.ibm.websphere.samples.daytrader.rest;

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
import com.ibm.websphere.samples.daytrader.impl.TradeAction;

@Path(value = "/")
public class Accounts {

    public Accounts() {}
   
    private static TradeAction impl = new TradeAction();

	/**
     * @see TradeServices#getHoldings(String)
     */
    @GET
    @Path("accounts/{userID}/holdings")
    @Produces({"application/json"})
    public Collection<?> getHoldings(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getHoldings(userID);
	}

	/**
     * @see TradeServices#getHolding(Integer)
     */
    @GET
    @Path("accounts/{userID}/holdings/{holdingID}")
    @Produces({"application/json"})
    public HoldingDataBean getHolding(@PathParam(value = "holdingID") Integer holdingID) throws Exception {
		return impl.getHolding(holdingID);
	}

	/**
     * @see TradeServices#getAccountData(String)
     */
    @GET
    @Path("accounts/{userID}")
    @Produces({"application/json"})
	public AccountDataBean getAccountData(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getAccountData(userID);
	}

	/**
     * @see TradeServices#getAccountProfileData(String)
     */
    @GET
    @Path("accounts/{userID}/profile")
    @Produces({"application/json"})
    public AccountProfileDataBean getAccountProfileData(
    		@PathParam(value = "userID") String userID) throws Exception {
		return impl.getAccountProfileData(userID);
	}

	/**
     * @see TradeServices#updateAccountProfile(AccountProfileDataBean)
     */
    @PUT
    @Path("accounts/{userID}/profile/{password}/{fullName}/{address}/{email}/{creditCard}")
    @Produces({"application/json"})
	public AccountProfileDataBean updateAccountProfile(
				@PathParam(value = "userID") String userID,
				@PathParam(value = "password") String password,
				@PathParam(value = "fullName") String fullName,
				@PathParam(value = "address") String address,
				@PathParam(value = "email") String email,
				@PathParam(value = "creditCard") String creditCard) throws Exception {
  	    AccountProfileDataBean profileData = new AccountProfileDataBean(
			  userID,password,fullName,address,email,creditCard);  
    	return impl.updateAccountProfile(profileData);
	}

	/**
     * @see TradeServices#login(String, String)
     */
    @PUT
    @Path("accounts/{userID}/{password}")
    @Produces({"application/json"})
	public AccountDataBean login(@PathParam(value = "userID") String userID, 
			@PathParam(value = "password") String password) throws Exception {
		return impl.login(userID, password);
	}

	/**
     * @see TradeServices#logout(String)
     */
    @PUT
    @Path("accounts/{userID}")
	public String logout(@PathParam(value = "userID") String userID) throws Exception {
		try {
			impl.logout(userID);
			return userID;
		} catch (Exception e) {
			return e.toString();
		}
	}

	/**
     * @see TradeServices#register(String, String, String, String, String,
     *      String, BigDecimal, boolean)
     */
    @POST
    @Path("accounts/{userID}/{password}/{fullname}/{address}/{email}/{creditcard}/{openBalance}")
    @Produces({"application/json"})
	public AccountDataBean register(
			@PathParam(value = "userID") String userID, 
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
    @Path("accounts/{userID}/buyorders/{symbol}/{quantity}/{orderProcessingMode}")
    @Produces({"application/json"})
	public OrderDataBean buy(@PathParam(value = "userID") String userID, @PathParam(value = "symbol") String symbol, 
			@PathParam(value = "quantity") double quantity, 
			@PathParam(value = "orderProcessingMode") int orderProcessingMode) throws Exception {
		return impl.buy(userID, symbol, quantity, orderProcessingMode);
	}	
    
	/**
     * @see TradeServices#sell(String, Integer)
     */
    @POST
    @Path("accounts/{userID}/sellorders/{holdingID}/{orderProcessingMode}")
    @Produces({"application/json"})
	public OrderDataBean sell(@PathParam(value = "userID") String userID, @PathParam(value = "holdingID") Integer holdingID, 
			@PathParam(value = "orderProcessingMode") int orderProcessingMode) throws Exception {
		return impl.sell(userID, holdingID, orderProcessingMode);
	}

	/**
     * @see TradeServices#getOrders(String)
     */
    @GET
    @Path("accounts/{userID}/orders")
    @Produces({"application/json"})
	public Collection<?> getOrders(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getOrders(userID);
	}
    
	/**
     * @see TradeServices#getClosedOrders(String)
     */
    @PUT
    @Path("accounts/{userID}/closedorders")
    @Produces({"application/json"})
    public Collection<?> updateOrderStatus(@PathParam(value = "userID") String userID) throws Exception {
   		return impl.getClosedOrders(userID);
	}

}
