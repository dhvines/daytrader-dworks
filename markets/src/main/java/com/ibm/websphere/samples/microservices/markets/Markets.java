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

package com.ibm.websphere.samples.microservices.markets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.MarketSummaryDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;

public class Markets {

    public Markets() {}
   
    private static TradeDirect impl = new TradeDirect();
    
	/**
     * @see TradeServices#getMarketSummary()
     */
    @GET
    @Path("/summary")
    @Produces({"application/json"})
	public MarketSummaryDataBean getMarketSummary() throws Exception {
		return impl.getMarketSummary();
	}
	
}
