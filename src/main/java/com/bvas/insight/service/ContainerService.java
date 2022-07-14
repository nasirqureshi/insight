package com.bvas.insight.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.ContainerOrderDetails;
import com.bvas.insight.entity.VendorItems;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;

@Repository
public class ContainerService {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<ContainerOrderDetails> getContainerOrderDetails(Integer supplierid, Integer orderno) {

		List<ContainerOrderDetails> newcontainerorderlist = new LinkedList<ContainerOrderDetails>();
		String hqlQuery = "SELECT  p.partno AS partno, vo.vendorpartno AS vendorpartno, CONCAT (p.manufacturername , ' ' , p.makemodelname) AS description1, p.partdescription  AS description2,"
				+ " p.unitsinstock  AS unitsinstock,p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,vo.quantity AS 	quantity , p.capa as capa , p.ordertype as ordertype, vo.price as price, (vo.price * vo.quantity) as totalcost"
				+ " FROM parts p, vendorordereditems vo WHERE p.PartNo = vo.PartNo AND vo.OrderNo = :orderno";

		String hqlQueryPrice = "from VendorItems vendoritems where vendoritems.supplierid =:supplierid and vendoritems.vendorpartno = :vendorpartno)";
		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("orderno", orderno)).addScalar("partno")
				.addScalar("vendorpartno").addScalar("description1").addScalar("description2").addScalar("capa")
				.addScalar("unitsinstock").addScalar("unitsonorder").addScalar("reorderlevel").addScalar("quantity")
				.addScalar("ordertype").addScalar("price").addScalar("totalcost")
				.setResultTransformer(Transformers.aliasToBean(ContainerOrderDetails.class));

		@SuppressWarnings("unchecked")
		List<ContainerOrderDetails> results = query.list();
		if (results != null) {
			if (results.size() > 0) {
				for (ContainerOrderDetails containerorder : results) {
					BigDecimal quantity = new BigDecimal(containerorder.getQuantity());
					BigDecimal zero = new BigDecimal("0.00");
					int checkval = containerorder.getPrice().compareTo(zero);

					if (checkval <= 0) {
						Query queryprice = session.createQuery(hqlQueryPrice);
						queryprice.setParameter("supplierid", supplierid);
						queryprice.setParameter("vendorpartno", containerorder.getVendorpartno());
						@SuppressWarnings("unchecked")
						List<VendorItems> vendoritem = queryprice.list();
						if (vendoritem != null) {
							if (vendoritem.size() > 0) {
								BigDecimal totalprice = vendoritem.get(0).getSellingrate().multiply(quantity);
								containerorder.setPrice(vendoritem.get(0).getSellingrate());
								containerorder.setTotalcost(totalprice);
							}
						}

					}

					newcontainerorderlist.add(containerorder);
				}
			}
		}
		session.flush();
		session.clear();
		return newcontainerorderlist;

	}

	@Transactional
	public List<VendorOrder> getVendorOrder(Integer supplierid, Integer orderno) {

		String hqlQuery = "From VendorOrder vendororder  where vendororder.supplierid=:supplierid and vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("supplierid", supplierid);
		query.setParameter("orderno", orderno);

		@SuppressWarnings("unchecked")
		List<VendorOrder> vendororders = query.list();
		session.flush();
		session.clear();
		return vendororders;

	}

	@Transactional
	public void updateVendorItemPrices(Integer supplierid, List<VendorOrderedItems> vendororderitemslist) {

		BigDecimal zero = new BigDecimal("0.00");
		String hqlQuery = "From VendorItems vendoritems  where vendoritems.supplierid=:supplierid and vendoritems.partno=:partno and vendoritems.vendorpartno=:vendorpartno";
		Session session = sessionFactory.getCurrentSession();
		for (VendorOrderedItems vendororderitems : vendororderitemslist) {
			if (vendororderitems.getPrice().compareTo(zero) == 1) {
				Query query = session.createQuery(hqlQuery);
				query.setParameter("supplierid", supplierid);
				query.setParameter("partno", vendororderitems.getPartno());
				query.setParameter("vendorpartno", vendororderitems.getVendorpartno());
				@SuppressWarnings("unchecked")
				List<VendorItems> results = query.list();
				if (results != null) {
					if (results.size() > 0) {
						VendorItems vendoritem = results.get(0);
						vendoritem.setSellingrate(vendororderitems.getPrice());
						session.save(vendoritem);
					}
				}
			}

		}
		session.flush();
		session.clear();

	}

}
