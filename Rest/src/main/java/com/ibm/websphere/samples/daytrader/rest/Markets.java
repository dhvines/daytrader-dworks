// DHV ADDED FOR STEP 2 

package com.ibm.websphere.samples.daytrader.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.MarketSummaryDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.impl.TradeAction;

@Path(value = "/")
public class Markets {

    public Markets() {}
   
    private static TradeAction impl = new TradeAction();
    
	/**
     * @see TradeServices#getMarketSummary()
     */
    @GET
    @Path("/markets")
    @Produces({"application/json"})
	public MarketSummaryDataBean getMarketSummary() throws Exception {
		return impl.getMarketSummary();
	}
	
}
