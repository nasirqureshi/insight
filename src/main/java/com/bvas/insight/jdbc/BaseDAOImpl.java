package com.bvas.insight.jdbc;

import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;

public interface BaseDAOImpl {

	public String getLocation(String partno);

	public int getSalesOfPartForGivenBranchDuration(String partno, String fromdate, String todate);

	void insertVendorOrderedItemsJDBC(VendorOrderedItems voi);

	void insertVendorOrderJDBC(VendorOrder vendorOrder);

}
