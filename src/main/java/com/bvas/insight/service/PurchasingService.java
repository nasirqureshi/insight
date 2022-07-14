package com.bvas.insight.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.ActualPriceList;
import com.bvas.insight.data.VendorOrderedItemsDetails;
import com.bvas.insight.entity.BranchTransfer;
import com.bvas.insight.entity.LocalOrders;
import com.bvas.insight.entity.Parts;
import com.bvas.insight.entity.PartsChanges;
import com.bvas.insight.entity.VendorOrder;
import com.bvas.insight.entity.VendorOrderedItems;
import com.bvas.insight.jdbc.BaseDAOImpl;
import com.bvas.insight.utilities.InsightUtils;

@Repository
@SuppressWarnings("unchecked")
public class PurchasingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchasingService.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public String addUnitsOnOrder(Integer supplierid, Integer orderno) {

		try {
			Session session = sessionFactory.getCurrentSession();
			String partsSQL = "from Parts parts where parts.partno=:partno";
			Query query = null;
			Query queryInterchange = null;

			String message = "";
			List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
			List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
			vendororderlist.clear();
			vendororderitemslist.clear();
			vendororderlist = getVendorOrder(supplierid, orderno);
			if (vendororderlist != null) {
				if (vendororderlist.size() > 0) {
					vendororderitemslist = getVendorOrderedItems(orderno);
					if (vendororderitemslist != null) {
						if (vendororderitemslist.size() > 0) {
							message = vendororderitemslist.size() + " PARTS UNITS ON ORDER ADDED";
							for (VendorOrderedItems voi : vendororderitemslist) {
								query = session.createQuery(partsSQL);
								query.setParameter("partno", voi.getPartno());
								List<Parts> parts = query.list();
								if (parts != null) {
									if (parts.size() > 0) {
										Parts part = parts.get(0);
										if (part.getInterchangeno().trim().equalsIgnoreCase("")) {
											Integer addunitsonorder = part.getUnitsonorder() + voi.getQuantity();
											part.setUnitsonorder(addunitsonorder);
											session.save(part);
										} else {
											queryInterchange = session.createQuery(partsSQL);
											queryInterchange.setParameter("partno", part.getInterchangeno().trim());
											List<Parts> partsinterchange = queryInterchange.list();
											if (partsinterchange != null) {
												if (partsinterchange.size() > 0) {
													Parts partinter = partsinterchange.get(0);

													Integer addunitsonorder = partinter.getUnitsonorder()
															+ voi.getQuantity();
													partinter.setUnitsonorder(addunitsonorder);
													session.save(partinter);

												}
											}
										}
									}
								}
							}
						} else {
							message = "NO PARTS FOUND IN ORDER";
						}
					} else {
						message = "NO PARTS FOUND IN ORDER";
					}
				} else {
					message = "NO ORDER FOUND";
				}
			} else {
				message = "NO ORDER FOUND";
			}
			session.flush();
			session.clear();
			return message;
		} catch (NullPointerException e) {
			return e.toString();
		}

	}

	@Transactional
	public String copyOrderToOtherBranch(Integer supplierid, Integer orderno, BaseDAOImpl basedao) {

		try {

			String branchselected = "";
			String message = "";
			List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
			List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
			vendororderlist.clear();
			vendororderitemslist.clear();
			vendororderlist = getVendorOrder(supplierid, orderno);
			if (vendororderlist != null) {
				if (vendororderlist.size() > 0) {
					vendororderitemslist = getVendorOrderedItems(orderno);
					if (vendororderitemslist != null) {
						basedao.insertVendorOrderJDBC(vendororderlist.get(0));
						if (vendororderitemslist.size() > 0) {
							message = vendororderitemslist.size() + " PARTS ON ORDERNO: " + orderno + " TO BRANCH "
									+ branchselected;
							for (VendorOrderedItems voi : vendororderitemslist) {
								basedao.insertVendorOrderedItemsJDBC(voi);
							}
						} else {
							message = "NO PARTS FOUND ON ORDERNO: " + orderno;
						}
					} else {
						message = "NO PARTS FOUND ON ORDERNO: " + orderno;
					}
				} else {
					message = "NO ORDER FOUND ORDERNO: " + orderno;
				}
			} else {
				message = "NO ORDER FOUND ORDERNO: " + orderno;
			}

			return message;
		} catch (NullPointerException e) {
			return e.toString();
		}

	}

	@Transactional
	public List<ActualPriceList> getActualPriceList(Integer orderno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = " SELECT p.partno AS partno, p.actualprice AS actualprice, vo.quantity AS quantity, vo.orderno AS orderno, (p.actualprice * vo.quantity) AS total"
				+ " FROM parts p, vendorordereditems vo WHERE p.PartNo = vo.PartNo AND vo.OrderNo =:orderno ORDER BY vo.noorder";
		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("orderno", orderno))
				.addScalar("partno").addScalar("actualprice").addScalar("quantity").addScalar("orderno")
				.addScalar("total").setResultTransformer(Transformers.aliasToBean(ActualPriceList.class));

		List<ActualPriceList> results = query.list();
		session.flush();
		session.clear();
		return results;
	}

	@Transactional
	private Integer getMaxPartsChanges(String partno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = "FROM   PartsChanges  pc WHERE pc.partno = :partno ORDER BY pc.modifiedorder DESC";
		Query query = session.createQuery(selectSQL);
		query.setParameter("partno", partno);

		List<PartsChanges> results = query.list();
		session.flush();
		session.clear();
		if (results != null) {
			if (results.size() > 0) {
				return results.get(0).getModifiedorder();
			} else {
				return 0;
			}
		} else {
			return 0;
		}

	}

	@Transactional
	public List<VendorOrder> getVendorOrder(Integer supplierid, Integer orderno) {

		String hqlQuery = "From VendorOrder vendororder  where vendororder.supplierid=:supplierid and vendororder.orderno=:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("supplierid", supplierid);
		query.setParameter("orderno", orderno);

		List<VendorOrder> vendororders = query.list();
		session.flush();
		session.clear();
		return vendororders;

	}

	@Transactional
	public List<VendorOrderedItems> getVendorOrderedItems(Integer orderno) {

		String hqlQuery = "FROM VendorOrderedItems vendorordereditems where  vendorordereditems.orderno =:orderno";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hqlQuery);
		query.setParameter("orderno", orderno);
		List<VendorOrderedItems> results = query.list();
		session.flush();
		session.clear();
		return results;

	}

	@Transactional
	public List<VendorOrderedItemsDetails> getVendorOrderedItemsDetails(Integer orderno) {

		String hqlQuery = "SELECT  mk.makemodelname AS makemodelname, mk.manufacturername AS manufacturername, p.partno AS partno,  p.year AS year, p.capa AS capa, vo.vendorpartno  AS vendorpartno, p.partdescription   AS partdescription,p.unitsinstock AS unitsinstock,"
				+ " p.unitsonorder  AS unitsonorder,p.reorderlevel  AS reorderlevel,p.returncount  AS returncount,vo.price  AS price , vo.quantity  AS quantity, (vo.quantity * vo.price)   AS totalprice, p.ordertype as ordertype"
				+ " FROM Parts p, VendorOrderedItems vo, makemodel mk"
				+ " WHERE p.partno = vo.partno AND p.makemodelcode = mk.makemodelcode"
				+ " AND vo.orderno =:orderno order by p.partno";
		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(hqlQuery).setParameter("orderno", orderno)).addScalar("partno")
				.addScalar("vendorpartno").addScalar("partdescription").addScalar("year").addScalar("capa")
				.addScalar("makemodelname").addScalar("manufacturername").addScalar("unitsinstock")
				.addScalar("unitsonorder").addScalar("reorderlevel").addScalar("price").addScalar("quantity")
				.addScalar("totalprice").addScalar("ordertype")
				.setResultTransformer(Transformers.aliasToBean(VendorOrderedItemsDetails.class));

		List<VendorOrderedItemsDetails> results = query.list();
		session.flush();
		session.clear();
		return results;

	}

	@Transactional
	public String makefinalOnOrder(Integer supplierid, Integer orderno) {
		Session session = sessionFactory.getCurrentSession();
		String updateSql = "Update VendorOrder vendororder Set vendororder.IsFinal='Y' WHERE vendororder.orderno=:orderno";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("orderno", orderno);
		query.executeUpdate();
		session.flush();
		session.clear();
		return "Order Made Final";

	}

	@Transactional
	public String removeFax(Integer supplierid, Integer orderno, Integer transfersupplierid, String transfercode) {

		Session session = sessionFactory.getCurrentSession();
		String partsSQL = "from Parts parts where parts.partno=:partno";
		Query query = null;
		Query queryInterchange = null;
		String type = "F";

		String message = "";
		List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
		List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
		vendororderlist.clear();
		vendororderitemslist.clear();
		vendororderlist = getVendorOrder(supplierid, orderno);
		if (vendororderlist != null) {
			if (vendororderlist.size() > 0) {
				vendororderitemslist = getVendorOrderedItems(orderno);
				if (vendororderitemslist != null) {
					if (vendororderitemslist.size() > 0) {
						vendororderlist.get(0).setOrderstatus("REMOVED");
						session.saveOrUpdate(vendororderlist.get(0));
						message = vendororderitemslist.size() + " PARTS UNITS IN STOCK REMOVED FOR ORDER " + orderno;
						for (VendorOrderedItems voi : vendororderitemslist) {

							// local orders
							LocalOrders localorder = new LocalOrders();
							localorder.setInvoiceno(orderno);
							localorder.setDateentered(InsightUtils.getCurrentSQLDate());
							localorder.setSupplierid(transfersupplierid);
							localorder.setPartno(voi.getPartno());
							localorder.setVendorpartno(voi.getVendorpartno());
							localorder.setQuantity(-1 * voi.getQuantity());
							localorder.setPrice(voi.getPrice());
							localorder.setVendorinvno(orderno.toString());
							localorder.setVendorinvdate(InsightUtils.getCurrentSQLDate());
							session.save(localorder);

							// parts changes
							Integer max = getMaxPartsChanges(voi.getPartno()) + 1;
							PartsChanges partschanges = new PartsChanges();
							partschanges.setPartno(voi.getPartno());
							partschanges.setModifiedby("PRG");
							partschanges.setModifieddate(InsightUtils.getCurrentSQLDate());
							partschanges.setModifiedorder(max);
							partschanges.setRemarks("Quantity changed quantity transfered to supplierid: "
									+ transfersupplierid + " " + voi.getQuantity() + " Order#" + orderno);
							session.save(partschanges);

							// branchtransfer
							BranchTransfer branchtransfer = new BranchTransfer();
							branchtransfer.setOrderno(orderno);
							branchtransfer.setRemovedate(InsightUtils.getCurrentSQLDate());
							branchtransfer.setTransfercode(transfercode);
							branchtransfer.setType(type);
							session.save(branchtransfer);

							// units in stock removal
							query = session.createQuery(partsSQL);
							query.setParameter("partno", voi.getPartno());
							List<Parts> parts = query.list();
							if (parts != null) {
								if (parts.size() > 0) {
									Parts part = parts.get(0);
									if (part.getInterchangeno().trim().equalsIgnoreCase("")) {
										Integer removeunitsinstock = part.getUnitsinstock() - voi.getQuantity();
										LOGGER.info("Inventory Log:  method:removeFax(), partNo=" + part.getPartno()
												+ ",old unitsinstock:" + part.getUnitsinstock() + ",new unitsinstock:"
												+ removeunitsinstock + ",changed quantity:" + voi.getQuantity());
										part.setUnitsinstock(removeunitsinstock);
										session.save(part);
									} else {
										queryInterchange = session.createQuery(partsSQL);
										queryInterchange.setParameter("partno", part.getInterchangeno().trim());
										List<Parts> partsinterchange = queryInterchange.list();
										if (partsinterchange != null) {
											if (partsinterchange.size() > 0) {
												Parts partinter = partsinterchange.get(0);

												Integer removeunitsinstock = part.getUnitsinstock() - voi.getQuantity();
												LOGGER.info("Inventory Log:  method:removeFax(), Interchange partNo="
														+ partinter.getPartno() + ",old unitsinstock:"
														+ partinter.getUnitsinstock() + ",new unitsinstock:"
														+ removeunitsinstock + ",changed quantity:"
														+ voi.getQuantity());
												partinter.setUnitsinstock(removeunitsinstock);
												session.save(partinter);

											}
										}
									}
								}
							}
						}
					} else {
						message = "NO PARTS FOUND IN ORDER";
					}
				} else {
					message = "NO PARTS FOUND IN ORDER";
				}
			} else {
				message = "NO ORDER FOUND";
			}
		} else {
			message = "NO ORDER FOUND";
		}
		session.flush();
		session.clear();
		return message;

	}

	@Transactional
	public String removefinalOnOrder(Integer supplierid, int orderno) {
		Session session = sessionFactory.getCurrentSession();
		String updateSql = "Update VendorOrder vendororder Set vendororder.IsFinal='N' WHERE vendororder.orderno=:orderno";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("orderno", orderno);
		query.executeUpdate();
		session.flush();
		session.clear();
		return "Order Final Remove";
	}

	@Transactional
	public String removePartList(Integer supplierid, Integer orderno, Integer transfersupplierid, String transfercode) {

		Session session = sessionFactory.getCurrentSession();
		String partsSQL = "from Parts parts where parts.partno=:partno";
		String auditPartSql = "delete from stockaudit where partno=:partno";

		Query querydelete = null;

		Query query = null;
		Query queryInterchange = null;
		String type = "P";

		String message = "";
		List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
		List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
		vendororderlist.clear();
		vendororderitemslist.clear();
		vendororderlist = getVendorOrder(supplierid, orderno);
		if (vendororderlist != null) {
			if (vendororderlist.size() > 0) {
				vendororderitemslist = getVendorOrderedItems(orderno);
				if (vendororderitemslist != null) {
					if (vendororderitemslist.size() > 0) {
						vendororderlist.get(0).setOrderstatus("REMOVED");
						session.saveOrUpdate(vendororderlist.get(0));
						message = vendororderitemslist.size() + " PARTS UNITS IN STOCK REMOVED FOR ORDER " + orderno;
						for (VendorOrderedItems voi : vendororderitemslist) {

							// local orders
							LocalOrders localorder = new LocalOrders();
							localorder.setInvoiceno(orderno);
							localorder.setDateentered(InsightUtils.getCurrentSQLDate());
							localorder.setSupplierid(transfersupplierid);
							localorder.setPartno(voi.getPartno());
							localorder.setVendorpartno(voi.getVendorpartno());
							localorder.setQuantity(-1 * voi.getQuantity());
							localorder.setPrice(voi.getPrice());
							localorder.setVendorinvno(orderno.toString());
							localorder.setVendorinvdate(InsightUtils.getCurrentSQLDate());
							session.save(localorder);

							// parts changes
							Integer max = getMaxPartsChanges(voi.getPartno()) + 1;
							PartsChanges partschanges = new PartsChanges();
							partschanges.setPartno(voi.getPartno());
							partschanges.setModifiedby("PRG");
							partschanges.setModifieddate(InsightUtils.getCurrentSQLDate());
							partschanges.setModifiedorder(max);
							partschanges.setRemarks("Quantity changed quantity transfered to supplierid: "
									+ transfersupplierid + " " + voi.getQuantity() + " Order#" + orderno);
							session.save(partschanges);

							// branchtransfer
							BranchTransfer branchtransfer = new BranchTransfer();
							branchtransfer.setOrderno(orderno);
							branchtransfer.setRemovedate(InsightUtils.getCurrentSQLDate());
							branchtransfer.setTransfercode(transfercode);
							branchtransfer.setType(type);
							session.save(branchtransfer);

							// units in stock removal
							query = session.createQuery(partsSQL);
							query.setParameter("partno", voi.getPartno());
							List<Parts> parts = query.list();
							if (parts != null) {
								if (parts.size() > 0) {
									Parts part = parts.get(0);

									if (part.getInterchangeno().trim().equalsIgnoreCase("")) {
										Integer removeunitsinstock = part.getUnitsinstock() - voi.getQuantity();
										LOGGER.info(
												"Inventory Log: Quantity changed quantity transfered to supplierid: "
														+ transfersupplierid + " " + voi.getQuantity() + " Order#"
														+ orderno + "  method:removePartList(), partNo"
														+ part.getPartno() + ",old unitsinstock:"
														+ part.getUnitsinstock() + ",new unitsinstock:"
														+ removeunitsinstock + ",changed quantity:"
														+ voi.getQuantity());
										part.setUnitsinstock(removeunitsinstock);
										session.save(part);
									} else {
										queryInterchange = session.createQuery(partsSQL);
										queryInterchange.setParameter("partno", part.getInterchangeno().trim());
										List<Parts> partsinterchange = queryInterchange.list();
										if (partsinterchange != null) {
											if (partsinterchange.size() > 0) {
												Parts partinter = partsinterchange.get(0);

												Integer removeunitsinstock = partinter.getUnitsinstock()
														- voi.getQuantity();
												LOGGER.info(
														"Inventory Log:  method:removePartList(), InterchangePart part NO="
																+ partinter.getPartno() + ",old unitsinstock:"
																+ part.getUnitsinstock() + "new unitsinstock:"
																+ removeunitsinstock + ",changed quantity:"
																+ voi.getQuantity());
												partinter.setUnitsinstock(removeunitsinstock);
												session.save(partinter);

											}
										}
									}

								}
							}
							// LOGGER.info("Delete from stockaudit-partno:" + voi.getPartno());
							querydelete = session.createSQLQuery(auditPartSql);
							querydelete.setParameter("partno", voi.getPartno().trim());
							querydelete.executeUpdate();
						} // vendorlist
					} else {
						message = "NO PARTS FOUND IN ORDER";
					}
				} else {
					message = "NO PARTS FOUND IN ORDER";
				}
			} else {
				message = "NO ORDER FOUND";
			}
		} else {
			message = "NO ORDER FOUND";
		}
		session.flush();
		session.clear();
		return message;

	}

	@Transactional
	public String removeUnitsOnOrder(Integer supplierid, Integer orderno) {

		try {
			Session session = sessionFactory.getCurrentSession();
			String partsSQL = "from Parts parts where parts.partno=:partno";
			Query query = null;
			Query queryInterchange = null;

			String message = "";
			List<VendorOrder> vendororderlist = new ArrayList<VendorOrder>();
			List<VendorOrderedItems> vendororderitemslist = new ArrayList<VendorOrderedItems>();
			vendororderlist.clear();
			vendororderitemslist.clear();
			vendororderlist = getVendorOrder(supplierid, orderno);
			if (vendororderlist != null) {
				if (vendororderlist.size() > 0) {
					vendororderitemslist = getVendorOrderedItems(orderno);
					if (vendororderitemslist != null) {
						if (vendororderitemslist.size() > 0) {
							message = vendororderitemslist.size() + " PARTS UNITS ON ORDER REMOVED";
							for (VendorOrderedItems voi : vendororderitemslist) {
								query = session.createQuery(partsSQL);
								query.setParameter("partno", voi.getPartno());
								List<Parts> parts = query.list();
								if (parts != null) {
									if (parts.size() > 0) {
										Parts part = parts.get(0);
										if (part.getInterchangeno().trim().equalsIgnoreCase("")) {
											Integer rmvunitsonorder = part.getUnitsonorder() - voi.getQuantity();
											part.setUnitsonorder(rmvunitsonorder);
											session.save(part);
										} else {
											queryInterchange = session.createQuery(partsSQL);
											queryInterchange.setParameter("partno", part.getInterchangeno().trim());
											List<Parts> partsinterchange = queryInterchange.list();
											if (partsinterchange != null) {
												if (partsinterchange.size() > 0) {
													Parts partinter = partsinterchange.get(0);

													Integer rmvunitsonorder = partinter.getUnitsonorder()
															- voi.getQuantity();
													partinter.setUnitsonorder(rmvunitsonorder);
													session.save(partinter);

												}
											}
										}
									}
								}
							}
						} else {
							message = "NO PARTS FOUND IN ORDER";
						}
					} else {
						message = "NO PARTS FOUND IN ORDER";
					}
				} else {
					message = "NO ORDER FOUND";
				}
			} else {
				message = "NO ORDER FOUND";
			}
			session.flush();
			session.clear();
			return message;
		} catch (NullPointerException e) {
			return e.toString();
		}
	}

	@Transactional
	public void updatePricesOnOrder(Integer supplierid, Integer orderno) {

		Session session = sessionFactory.getCurrentSession();
		String updateSql = "UPDATE vendorordereditems st INNER JOIN parts sc ON st.partno = sc.partno SET st.price = sc.actualprice WHERE st.orderno=:orderno AND st.orderno IN (SELECT orderno FROM  vendororder WHERE supplierid IN (24,25,29,41,45,47,49,50))";
		Query query = session.createSQLQuery(updateSql);
		query.setParameter("orderno", orderno);
		query.executeUpdate();
		session.flush();
		session.clear();

	}

}
