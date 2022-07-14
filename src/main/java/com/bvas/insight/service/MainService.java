package com.bvas.insight.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.entity.Branch;
import com.bvas.insight.entity.Category;
import com.bvas.insight.entity.Customer;
import com.bvas.insight.entity.Driver;
import com.bvas.insight.entity.MakeModel;
import com.bvas.insight.entity.Manufacturer;
import com.bvas.insight.entity.PaymentPolicy;
import com.bvas.insight.entity.Shipping;
import com.bvas.insight.entity.Subcategory;
import com.bvas.insight.entity.Users;
import com.bvas.insight.entity.Vendors;
import com.bvas.insight.utilities.AppUser;

@Repository
@SuppressWarnings("unchecked")
public class MainService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainService.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Map<String, String> getAllActiveBranches(String basebranch) {

		Map<String, String> branchMap = new LinkedHashMap<String, String>();
		String hqlQuery = "FROM Branch branch WHERE branch.branchcode <> :basebranch and active = 1";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("basebranch", basebranch);
		List<Branch> branchlist = query.list();

		// vendorMap.put("", 0);
		session.flush();
		session.clear();

		if (branchlist.size() > 0) {
			for (Branch branch : branchlist) {
				// LOGGER.info("branch " + branch.getBranchcode());
				branchMap.put(branch.getBranchcode(), branch.getBranchcode());
			}
			branchMap.remove("");
			return branchMap;
		} else {
			return null;
		}

	}

	@Transactional
	public Map<String, String> getAllCategory() {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Category category ORDER BY category.categoryname";
		Query query = session.createQuery(hSql);
		Map<String, String> categorylist = new HashMap<String, String>();

		List<Category> categories = query.list();

		if (categories != null) {
			if (!(categories.contains("ALL"))) {
				Category allcategory = new Category();
				allcategory.setCategorycode("-1");
				allcategory.setCategoryname("ALL");
				categories.add(allcategory);
			}
			if (categories.size() > 0) {
				for (Category category : categories) {
					categorylist.put(category.getCategoryname(), category.getCategorycode());
				}
			}
		}

		session.flush();
		session.clear();

		return categorylist;
	}

	@Transactional
	public List<Customer> getAllCustomer() {

		String hqlQuery = "From Customer customer  ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Customer> results = query.list();
		if (results != null) {
			session.flush();
			session.clear();
			return results;
		} else {
			results = new ArrayList<Customer>();
			Customer customer = new Customer();
			customer.setCustomerid("1111111111");
			customer.setCompanyname("WALK-IN (WHOLESALE PRICE)");
			customer.setPaymentterms("O");
			customer.setCreditlimit(new BigDecimal("35000"));
			customer.setAddress1("20% DISCOUNT");
			customer.setSt("IL");
			customer.setRte("Ws");
			results.add(customer);
			session.flush();
			session.clear();
			return results;
		}

	}

	@Transactional
	public Map<String, Integer> getAllDriver() {

		Map<String, Integer> driverMap = new LinkedHashMap<String, Integer>();
		String hqlQuery = "FROM Driver driver WHERE driver.active=1  ORDER BY driver.drivername";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Driver> driverlist = query.list();

		// vendorMap.put("", 0);
		session.flush();
		session.clear();

		if (driverlist.size() > 0) {
			for (Driver driver : driverlist) {
				driverMap.put(driver.getDrivername(), driver.getSerial());
			}

			return driverMap;
		} else {
			return null;
		}
	}

	@Transactional
	public Map<String, String> getAllMakeModelMap() {
		Map<String, String> makemodelMap = new LinkedHashMap<String, String>();
		String hqlQuery = "from MakeModel makemodel order by makemodel.makemodelname";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<MakeModel> makemodels = query.list();

		session.flush();
		session.clear();

		if (makemodels.size() > 0) {
			for (MakeModel makemodel : makemodels) {
				makemodelMap.put(makemodel.getMakemodelname(), makemodel.getMakemodelcode());
			}

			return makemodelMap;
		} else {
			return null;
		}
	}

	@Transactional
	public Map<String, Integer> getAllManufacturersMap() {

		Map<String, Integer> manufacturerMap = new LinkedHashMap<String, Integer>();
		String hqlQuery = "from Manufacturer manufacturers order by manufacturers.manufacturername";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Manufacturer> manufacturers = query.list();
		manufacturerMap.put("ALL", -1);
		session.flush();
		session.clear();

		if (manufacturers.size() > 0) {
			for (Manufacturer manufacturer : manufacturers) {
				manufacturerMap.put(manufacturer.getManufacturername(), manufacturer.getManufacturerid());
			}

			return manufacturerMap;
		} else {
			return null;
		}
	}

	@Transactional
	public List<String> getAllOrderType() {

		List<String> ordertypelistdd = new ArrayList<String>();

		String hqlQuery = "select distinct parts.ordertype FROM Parts parts";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(hqlQuery);
		ordertypelistdd = query.list();
		ordertypelistdd.add("-");

		session.flush();
		session.clear();

		if (ordertypelistdd.size() > 0) {
			return ordertypelistdd;
		} else {
			return null;
		}
	}

	@Transactional
	public Map<String, String> getAllOtherBranches(String basebranch) {

		Map<String, String> branchMap = new LinkedHashMap<String, String>();
		String hqlQuery = "FROM Branch branch WHERE branch.branchcode <> :basebranch";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("basebranch", basebranch);
		List<Branch> branchlist = query.list();

		// vendorMap.put("", 0);
		session.flush();
		session.clear();

		if (branchlist.size() > 0) {
			for (Branch branch : branchlist) {
				// LOGGER.info("branch " + branch.getBranchcode());
				branchMap.put(branch.getBranchcode(), branch.getBranchcode());
			}
			return branchMap;
		} else {
			return null;
		}

	}

	@Transactional
	public Map<String, String> getAllPaymentPolicy() {
		Map<String, String> paymentpolicyMap = new LinkedHashMap<String, String>();
		String hqlQuery = "from PaymentPolicy paymentpolicy order by paymentpolicy.termdescription";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<PaymentPolicy> makemodels = query.list();

		session.flush();
		session.clear();

		if (makemodels.size() > 0) {
			for (PaymentPolicy paymentpolicy : makemodels) {
				paymentpolicyMap.put(paymentpolicy.getTermdescription(), paymentpolicy.getTermcode());
			}

			return paymentpolicyMap;
		} else {
			return null;
		}
	}

	@Transactional
	public List<String> getAllRoute() {

		List<String> routelist = new LinkedList<String>();

		routelist.clear();

		String hqlQuery = "select distinct address.region from Address address ORDER BY address.region";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(hqlQuery);

		routelist = query.list();
		routelist.add("ALL");
		Collections.sort(routelist);
		session.flush();
		session.clear();

		if (routelist.size() > 0) {
			return routelist;
		} else {
			return null;
		}
	}

	@Transactional
	public List<Users> getAllSalesPersons() {

		String hqlQuery = "from Users where actualrole='sales' order by username";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Users> spList = query.list();

		session.flush();
		session.clear();

		return spList;
	}

	@Transactional
	public Map<String, String> getAllSubCategory() {

		Map<String, String> subcategoryMap = new LinkedHashMap<String, String>();
		String hqlQuery = "FROM Subcategory subcategory ORDER BY subcategory.subcategoryname";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Subcategory> subcategorylist = query.list();

		subcategoryMap.put("ALL", "ALL");
		session.flush();
		session.clear();

		if (subcategorylist.size() > 0) {

			for (Subcategory subcategory : subcategorylist) {

				subcategoryMap.put(subcategory.getSubcategoryname(), subcategory.getSubcategorycode());
			}
			subcategoryMap.remove("");
			return subcategoryMap;
		} else {
			return null;
		}
	}

	@Transactional
	public Map<String, String> getAllSubCategoryMinusAll() {

		Map<String, String> subcategoryMap = new LinkedHashMap<String, String>();
		String hqlQuery = "FROM Subcategory subcategory ORDER BY subcategory.subcategoryname";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Subcategory> subcategorylist = query.list();

		session.flush();
		session.clear();

		if (subcategorylist.size() > 0) {
			for (Subcategory subcategory : subcategorylist) {
				subcategoryMap.put(subcategory.getSubcategoryname(), subcategory.getSubcategorycode());
			}

			return subcategoryMap;
		} else {
			return null;
		}
	}

	@Transactional
	public Map<String, Integer> getAllVendors() {

		Map<String, Integer> vendorMap = new LinkedHashMap<String, Integer>();
		String hqlQuery = "FROM Vendors vendors WHERE vendors.companytype <> 'Z' ORDER BY vendors.companyname";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Vendors> vendorslist = query.list();

		// vendorMap.put("", 0);
		session.flush();
		session.clear();

		if (vendorslist.size() > 0) {
			for (Vendors vendors : vendorslist) {
				vendorMap.put(vendors.getCompanyname(), vendors.getSupplierid());
			}

			return vendorMap;
		} else {
			return null;
		}
	}

	@Transactional
	public List<String> getInventoryTransferItems() {

		// String hqlQuery = "select
		// parts.PartNo,parts.PartDescription,parts.UnitsInStock From
		// Subcategory subcategory, Parts parts where
		// subcategory.SubCategoryName=:subCategoryName and
		// subcategory.SubCategoryCode=parts.SubCategory and parts.UnitsInStock
		// >= :stockLevel";

		String hqlQuery = "SELECT m.manufacturername,m.MakeModelName, p.PartNo,p.UnitsInStock,P.UNITSONORDER,p.year,p.PartDescription,p.location"
				+ "FROM parts p, makemodel m" + "WHERE p.MakeModelCode = m.MakeModelCode" + "AND P.InterchangeNo = ''"
				+ "AND p.SubCategory = '6'" + "AND p.UnitsInStock > 5"
				+ "ORDER BY p.location, M.manufacturerid, M.makemodelname , p.PartNo";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);

		List<String> items = query.list();

		session.flush();
		session.clear();

		if (items.size() <= 0) {
			return null;
		} else {
			return items;
		}
	}

	@Transactional
	public TreeMap<String, Integer> getShippingTypes() {

		TreeMap<String, Integer> shippingMap = new TreeMap<String, Integer>();
		String hqlQuery = "FROM Shipping shipping";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		List<Shipping> shippinglist = query.list();

		session.flush();
		session.clear();

		if (shippinglist.size() > 0) {
			for (Shipping shipping : shippinglist) {
				shippingMap.put(shipping.getShiptype(), shipping.getSerialno());
			}

			return shippingMap;
		} else {
			return null;
		}
	}

	@Transactional
	public AppUser getUser(String username, String password) {

		String hqlQuery = "From Users users  where users.username=:username and users.password=:password";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);

		query.setParameter("username", username);
		query.setParameter("password", password);

		List<Users> users = query.list();

		session.flush();
		session.clear();

		if (users.size() <= 0) {
			return null;
		} else {
			AppUser appuser = new AppUser(users.get(0).getActualrole(), users.get(0).getRole(),
					users.get(0).getUsername());
			return appuser;
		}
	}

	@Transactional
	public List<Integer> getYears() {

		List<Integer> modelyearsinitial = new ArrayList<Integer>();
		List<Integer> modelyears = new ArrayList<Integer>();
		Set<Integer> set = new HashSet<Integer>();
		String hqlQueryYearFrom = "SELECT DISTINCT parts.yearfrom FROM Parts parts WHERE parts.yearfrom IS NOT NULL AND parts.yearfrom <> '' ORDER BY parts.yearfrom";
		Session session = sessionFactory.getCurrentSession();
		Query queryfrom = session.createQuery(hqlQueryYearFrom);
		List<Integer> yearfrom = queryfrom.list();
		String hqlQueryYearTo = "SELECT DISTINCT parts.yearto FROM Parts parts WHERE parts.yearto IS NOT NULL AND parts.yearto <> '' ORDER BY parts.yearto";
		Query queryto = session.createQuery(hqlQueryYearTo);
		List<Integer> yearto = queryto.list();

		session.flush();
		session.clear();
		modelyearsinitial.addAll(yearfrom);
		modelyearsinitial.addAll(yearto);
		Collections.reverse(modelyearsinitial);

		for (Integer year : modelyearsinitial) {
			if (set.add(year)) {
				modelyears.add(year);
			}
		}

		return modelyears;
	}

}
