/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.bvas.insight.data;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author khalid
 */
public class CreateVendorOrder {

	public static final String BY_OUR_NO = "O";
	public static final String BY_VENDOR_NO = "V";
	private String byWhosNoToCreateOrder;
	private String doesInputFileHasQty;
	private MultipartFile file;
	private String location;

	private String orderNo;
	private String supplierId;

	public String getByWhosNoToCreateOrder() {
		return byWhosNoToCreateOrder;
	}

	public String getDoesInputFileHasQty() {
		return doesInputFileHasQty;
	}

	public MultipartFile getFile() {
		return file;
	}

	public String getLocation() {
		return location;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public boolean isQtyInInputFile() {
		return doesInputFileHasQty.equalsIgnoreCase("Y");
	}

	public void setByWhosNoToCreateOrder(String byWhosNoToCreateOrder) {
		this.byWhosNoToCreateOrder = byWhosNoToCreateOrder;
	}

	public void setDoesInputFileHasQty(String doesInputFileHasQty) {
		this.doesInputFileHasQty = doesInputFileHasQty;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

}
