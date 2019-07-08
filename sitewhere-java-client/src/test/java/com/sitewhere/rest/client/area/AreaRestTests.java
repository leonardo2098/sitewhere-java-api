/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.client.area;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.sitewhere.rest.client.AbstractCRUDRestClientTests;
import com.sitewhere.rest.model.area.Area;
import com.sitewhere.rest.model.area.request.AreaCreateRequest;
import com.sitewhere.rest.model.device.asset.DeviceAlertWithAsset;
import com.sitewhere.rest.model.device.event.DeviceCommandInvocation;
import com.sitewhere.rest.model.device.marshaling.MarshaledDeviceAssignment;
import com.sitewhere.rest.model.search.DateRangeSearchCriteria;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.rest.model.search.area.AreaSearchCriteria;
import com.sitewhere.rest.model.search.device.DeviceAssignmentForAreaSearchCriteria;
import com.sitewhere.spi.SiteWhereException;

/**
 *
 * @author Jorge Villaverde
 *
 */
public class AreaRestTests extends AbstractCRUDRestClientTests<Area, AreaCreateRequest> {

    private String areaTypeToken = "construction";

    private String parentToken = "southeast";

    private String name = "Test Area Name";
    
    // ------------------------------------------------------------------------
    // CREATE
    // ------------------------------------------------------------------------
    
    @Override
    protected AreaCreateRequest buildCreateRequest(String token) {
	AreaCreateRequest.Builder builder = new AreaCreateRequest.Builder(areaTypeToken, parentToken, token, name);
	
	builder.withDescription("Some area");
	
	return builder.build();
    }

    @Override
    protected Area createEntity(AreaCreateRequest createRequest) throws SiteWhereException {
	return getClient().createArea(getTenatAuthentication(), createRequest);
    }

    // ------------------------------------------------------------------------
    // READ
    // ------------------------------------------------------------------------

    @Override
    protected Area findEntityByToken(String token) throws SiteWhereException {
	return getClient().getAreaByToken(getTenatAuthentication(), token);
    }

    // ------------------------------------------------------------------------
    // UPDATE
    // ------------------------------------------------------------------------

    @Override
    protected AreaCreateRequest buildUpdateRequest(String token) throws SiteWhereException {
	AreaCreateRequest.Builder builder = new AreaCreateRequest.Builder(areaTypeToken, parentToken, token, name);
	
	builder.withDescription("Some updated description");
	
	return builder.build();
    }

    @Override
    protected Area updateEntity(String token, AreaCreateRequest updateRequest) throws SiteWhereException {
	return getClient().updateArea(getTenatAuthentication(), token, updateRequest);
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @Override
    protected Area deleteEntity(String token) throws SiteWhereException {
	return getClient().deleteArea(getTenatAuthentication(), token);
    }

    // ------------------------------------------------------------------------
    // LIST
    // ------------------------------------------------------------------------
    
    @Override
    protected SearchResults<Area> listEntities() throws SiteWhereException {
	AreaSearchCriteria searchCriteria = new AreaSearchCriteria(0, 10);
	return getClient().listAreas(getTenatAuthentication(), searchCriteria);
    }
    
    @Test
    public void testListAlerts() throws SiteWhereException {
	Calendar cal = Calendar.getInstance();
	
	cal.setTime(new Date());
	cal.add(Calendar.YEAR, -1);
	
	Date startDate = cal.getTime();
	Date endDate = new Date();
	
	DateRangeSearchCriteria searchCriteria = new DateRangeSearchCriteria(1, 10, startDate, endDate);
	SearchResults<DeviceAlertWithAsset> alerts = getClient()
		.listAlertsForArea(getTenatAuthentication(), parentToken, searchCriteria);
	
	assertNotNull(alerts);
    }

    @Test
    public void testListAssignments() throws SiteWhereException {
	DeviceAssignmentForAreaSearchCriteria searchCriteria = new DeviceAssignmentForAreaSearchCriteria(1, 1);
	SearchResults<MarshaledDeviceAssignment> assignments = 
		getClient().listDeviceAssignmentsForArea(
			getTenatAuthentication(), parentToken, searchCriteria);
	assertNotNull(assignments);
    }
    
    @Test
    public void testListCommandInvocations() throws SiteWhereException {
	Calendar cal = Calendar.getInstance();
	
	cal.setTime(new Date());
	cal.add(Calendar.YEAR, -1);
	
	Date startDate = cal.getTime();
	Date endDate = new Date();
	
	DateRangeSearchCriteria searchCriteria = new DateRangeSearchCriteria(1, 10, startDate, endDate);
	SearchResults<DeviceCommandInvocation> commandInvocations = getClient()
		.listCommandInvocationForArea(getTenatAuthentication(), parentToken, searchCriteria);
	
	assertNotNull(commandInvocations);
    }
    
}
