
// DHV
// ADDED FOR STEP 2

package com.ibm.websphere.samples.daytrader.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.RunStatsDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.impl.TradeAction;


@Path(value = "/")
public class Traders {

    public Traders() {}
   
    // DHV
    private static TradeAction impl = new TradeAction();
 
	/**
     * @see TradeServices#resetTrade(boolean)
     */
    @DELETE
    @Path("traders/{deleteAll}")
    @Produces({"application/json"})
	public RunStatsDataBean resetTrade(@PathParam(value = "deleteAll") boolean deleteAll) throws Exception {
    	return impl.resetTrade(deleteAll);	
    	}

}
