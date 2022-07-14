package com.bvas.insight.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.entity.Address;
import com.bvas.insight.entity.Customer;
import com.bvas.insight.entity.PaymentPolicy;

@Repository
public class CustomerService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Customer getCustomerDetails(String customerId) {

		String hqlQuery = "From Customer customer  where customer.customerid =:customerid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("customerid", customerId.trim());

		Customer results = (Customer) query.uniqueResult();
		if (results != null) {
			session.flush();
			session.clear();
			return results;
		} else {
			return results;
		}

	}

	@Transactional
	public Customer getCustomerDetailsNoNull(String customerId) {
		String hqlQuery = "From Customer customer  where customer.customerid =:customerid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("customerid", customerId.trim());

		Customer results = (Customer) query.uniqueResult();
		if (results != null) {
			session.flush();
			session.clear();
			return results;
		} else {
			return new Customer(customerId);
		}
	}

	@Transactional
	public String getTermsFromPaymentTerms(String paymentterms) {

		if ((paymentterms != null) && (!paymentterms.equalsIgnoreCase(""))) {
			String hqlQuery = "From PaymentPolicy paymentpolicy  where paymentpolicy.termcode =:paymentterms";
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(hqlQuery);
			query.setParameter("paymentterms", paymentterms);

			PaymentPolicy paymentpolicy = (PaymentPolicy) query.uniqueResult();

			if (paymentpolicy != null) {
				session.flush();
				session.clear();
				return paymentpolicy.getTermdescription();
			} else {
				return "";
			}
		} else {
			return "";
		}

	}

	@Transactional
	public void updateCustomerMaintenance(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);

		String hqlQuery = "From Address address  where address.id =:customerid and type = 'Standard' ";
		Query query = session.createQuery(hqlQuery);
		query.setParameter("customerid", customer.getCustomerid().trim());

		Address address = (Address) query.uniqueResult();
		if (address != null) {
			address.setId(customer.getCustomerid().trim());
			address.setAddr1(customer.getAddress1());
			address.setAddr2(customer.getAddress2());
			address.setCity(customer.getTown());
			address.setPhone(customer.getPh());
			address.setPostalcode(customer.getZip());
			address.setRegion(customer.getRte());
			address.setState(customer.getSt());
			address.setType("Standard");
			address.setWho("Cust");
			address.setInvoicenumber(0);
			session.saveOrUpdate(address);
		} else {
			Address addressnew = new Address();
			addressnew.setId(customer.getCustomerid().trim());
			addressnew.setAddr1(customer.getAddress1());
			addressnew.setAddr2(customer.getAddress2());
			addressnew.setCity(customer.getTown());
			addressnew.setPhone(customer.getPh());
			addressnew.setPostalcode(customer.getZip());
			addressnew.setRegion(customer.getRte());
			addressnew.setState(customer.getSt());
			addressnew.setType("Standard");
			addressnew.setWho("Cust");
			addressnew.setInvoicenumber(0);
			session.saveOrUpdate(addressnew);
		}

		session.flush();
		session.clear();

	}

	@Transactional
	public void updateCustomerOnly(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);

		session.flush();
		session.clear();

	}

}
