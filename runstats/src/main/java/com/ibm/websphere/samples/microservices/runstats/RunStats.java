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

package com.ibm.websphere.samples.microservices.runstats;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.RunStatsDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;

public class RunStats {

    public RunStats() {}
   
    private static TradeDirect impl = new TradeDirect();
 
	/**
     * @see TradeServices#resetTrade(boolean)
     */
    @DELETE
    @Path("/{deleteAll}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public RunStatsDataBean resetTrade(@PathParam(value = "deleteAll") boolean deleteAll) throws Exception {
    	// TODO: this implementation will not work if separate quotes from accounts into their own containers
    	// and database. Instead,  you will need to implement the logic in this method by calling rest api's
    	// in the accounts and quotes microservices. Of course, you will also need to add REST APIs required
    	// to support this implementaiton.
		return impl.resetTrade(deleteAll);
	}

}
