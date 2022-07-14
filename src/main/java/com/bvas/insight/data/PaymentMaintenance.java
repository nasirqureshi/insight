package com.bvas.insight.data;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class PaymentMaintenance {

	public BigDecimal appliedAmount;

	public String appliedDate;

	public BigDecimal balance;

	public String companyName;

	public BigDecimal creditBalance;

	public String invoiceNumber;

	public BigDecimal payingAmount;

	public String paymentTime;

	public String paymentType;

	public String userName;

	public BigDecimal getAppliedAmount() {

		return appliedAmount;
	}

	public String getAppliedDate() {

		return appliedDate;
	}

	public BigDecimal getBalance() {

		return balance;
	}

	public String getCompanyName() {

		return companyName;
	}

	public BigDecimal getCreditBalance() {

		return this.creditBalance;
	}

	public String getInvoiceNumber() {

		return invoiceNumber;
	}

	@Digits(fraction = 2, integer = 10, message = "Please enter valid value.")
	@NotNull(message = "Amount should not be empty.")
	public BigDecimal getPayingAmount() {

		return payingAmount;
	}

	public String getPaymentTime() {

		return paymentTime;
	}

	public String getPaymentType() {

		return paymentType;
	}

	public String getUserName() {

		return userName;
	}

	public void setAppliedAmount(BigDecimal appliedAmount) {

		this.appliedAmount = appliedAmount;
	}

	public void setAppliedDate(String appliedDate) {

		this.appliedDate = appliedDate;
	}

	public void setBalance(BigDecimal balance) {

		this.balance = balance;
	}

	public void setCompanyName(String companyName) {

		this.companyName = companyName;
	}

	public void setCreditBalance(BigDecimal creditBalance) {

		this.creditBalance = creditBalance;
	}

	public void setInvoiceNumber(String invoiceNumber) {

		this.invoiceNumber = invoiceNumber;
	}

	public void setPayingAmount(BigDecimal payingAmount) {

		this.payingAmount = payingAmount;
	}

	public void setPaymentTime(String paymentTime) {

		this.paymentTime = paymentTime;
	}

	public void setPaymentType(String paymentType) {

		this.paymentType = paymentType;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

}
