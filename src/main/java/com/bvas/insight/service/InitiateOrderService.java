package com.bvas.insight.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.entity.Subcategory;

@Repository
public class InitiateOrderService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Map<String, String> getRelatedSubcategory(String categoryid) {

		Session session = sessionFactory.getCurrentSession();
		String hSql = "FROM Subcategory subcategory where subcategory.categorycode =:categorycode ORDER BY subcategory.subcategoryname";
		Query query = session.createQuery(hSql);
		query.setParameter("categorycode", categoryid);
		Map<String, String> subcategorylist = new HashMap<String, String>();
		subcategorylist.put("ALL", "-1");

		@SuppressWarnings("unchecked")
		List<Subcategory> subcategories = query.list();

		if (subcategories != null) {
			if (subcategories.size() > 0) {
				for (Subcategory subcategory : subcategories) {
					subcategorylist.put(subcategory.getSubcategoryname(), subcategory.getSubcategorycode());
				}
			}
		}

		session.flush();
		session.clear();

		return subcategorylist;
	}
}
