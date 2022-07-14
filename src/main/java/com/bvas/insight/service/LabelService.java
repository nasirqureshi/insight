package com.bvas.insight.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bvas.insight.data.Label;
import com.bvas.insight.data.PartnoMakemodelname;
import com.bvas.insight.data.PartnoQuantity;
import com.bvas.insight.jdbc.ChStocksDAOImpl;
import com.bvas.insight.jdbc.GrStocksDAOImpl;
import com.bvas.insight.jdbc.MpStocksDAOImpl;

@Repository
@SuppressWarnings("unchecked")
public class LabelService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(LabelService.class);

	@Autowired
	@Qualifier("chpartsdao")
	public ChStocksDAOImpl chpartsdao;

	@Autowired
	@Qualifier("grpartsdao")
	public GrStocksDAOImpl grpartsdao;

	@Autowired
	@Qualifier("mppartsdao")
	public MpStocksDAOImpl mppartsdao;

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	private Label getLabel(String partno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = "Select PartNo AS partno, keystonenumber as keystonenumber, InterChangeNo AS interchangeno, b.MakeModelName  AS  makemodelname, PartDescription  AS partdescription, Year AS year, OEMNumber AS oemnumber, Location AS location from Parts a, MakeModel b Where a.MakeModelCode=b.MakeModelCode and PartNo=:partno";
		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("partno", partno)).addScalar("partno")
				.addScalar("interchangeno").addScalar("makemodelname").addScalar("partdescription").addScalar("year")
				.addScalar("oemnumber").addScalar("location").addScalar("keystonenumber")
				.setResultTransformer(Transformers.aliasToBean(Label.class));
		List<Label> results = query.list();

		session.flush();
		session.clear();

		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}

		// transaction.commit();
	}

	@Transactional
	private String getOEMNo(String partno) {

		Session session = sessionFactory.getCurrentSession();
		String oemno = "";
		String hqlQuery = "Select OEMNo from VendorItems Where partno=:partno and OEMNo!='' ";

		// transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hqlQuery);

		query.setParameter("partno", partno);

		List<String> results = query.list();

		session.flush();
		session.clear();

		if (results.size() > 0) {
			return oemno = results.get(0);
		}

		// transaction.commit();
		return oemno;
	}

	@Transactional
	private List<PartnoMakemodelname> getPartnoMakemodel1(String param1, String selectSQL) {

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("param1", param1)).addScalar("partno")
				.addScalar("makemodelname").setResultTransformer(Transformers.aliasToBean(PartnoMakemodelname.class));
		List<PartnoMakemodelname> results = query.list();

		session.flush();
		session.clear();

		if (results.size() > 0) {
			return results;
		} else {
			return null;
		}

		// transaction.commit();
	}

	@Transactional
	private List<PartnoMakemodelname> getPartnoMakemodel2(String param1, String param2, String sqlquery2) {

		Session session = sessionFactory.getCurrentSession();
		Query query = ((SQLQuery) session.createSQLQuery(sqlquery2).setParameter("param1", param1)
				.setParameter("param2", param2)).addScalar("partno").addScalar("makemodelname")
						.setResultTransformer(Transformers.aliasToBean(PartnoMakemodelname.class));
		List<PartnoMakemodelname> results = query.list();

		session.flush();
		session.clear();

		if (results.size() > 0) {
			return results;
		} else {
			return null;
		}

		// transaction.commit();
	}

	@Transactional
	private List<PartnoQuantity> getPartnoQuantities(Integer orderno) {

		Session session = sessionFactory.getCurrentSession();
		String selectSQL = "Select a.PartNo AS partno, a.Quantity AS quantity from VendorOrderedItems a, parts b, makemodel c Where a.OrderNo=:orderno"
				+ " and a.partno=b.partno and b.makemodelcode=c.makemodelcode Order By b.subcategory, c.manufacturerid, c.makemodelname ";
		Query query = ((SQLQuery) session.createSQLQuery(selectSQL).setParameter("orderno", orderno))
				.addScalar("partno").addScalar("quantity")
				.setResultTransformer(Transformers.aliasToBean(PartnoQuantity.class));
		List<PartnoQuantity> results = query.list();

		session.flush();
		session.clear();

		if (results.size() > 0) {
			return results;
		} else {
			return null;
		}
	}

	@Transactional
	private String getSupplInvNo(Integer orderno) {

		String supplinvno = "";
		String hqlQuery = "Select supplinvno from VendorOrder where orderno =:orderno";
		Session session = sessionFactory.getCurrentSession();

		// transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hqlQuery);

		query.setParameter("orderno", orderno);

		List<String> results = query.list();

		session.flush();
		session.clear();

		if (results.size() > 0) {
			return supplinvno = results.get(0);
		}

		// transaction.commit();
		return supplinvno;
	}

	@Transactional
	public File processLabelFile(Integer orderno, String filePath, String alllocations) throws IOException {

		String sqlquery1 = "Select PartNo AS partno, keystonenumber as keystonenumber, b.MakeModelName AS makemodelname from Parts a, MakeModel b Where a.MakeModelCode=b.MakeModelCode and InterChangeNo =:param1";
		String sqlquery2 = "Select PartNo  AS partno, keystonenumber as keystonenumber, b.MakeModelName AS makemodelname from Parts a, MakeModel b Where a.MakeModelCode=b.MakeModelCode and InterChangeNo =:param1 and PartNo!=:param2";
		String sqlquery3 = "Select PartNo AS partno, keystonenumber as keystonenumber, b.MakeModelName AS makemodelname from Parts a, MakeModel b Where a.MakeModelCode=b.MakeModelCode and PartNo=:param1";
		File file = new File(filePath + "Label_" + orderno + ".txt");
		FileWriter labelData = new FileWriter(file);
		String supplinvno = getSupplInvNo(orderno);
		List<PartnoQuantity> partnoquantities = getPartnoQuantities(orderno);

		for (PartnoQuantity partnoquantity : partnoquantities) {
			String partno = partnoquantity.getPartno().trim();
			String keystonenumber = "";
			Integer qty = partnoquantity.getQuantity();
			Integer cnt = qty;
			String interChangeNo = "";
			String oemNo = "";
			String oemNo1 = "";
			String model = "";
			String year = "";
			String desc = "";
			String alsoFitsModel = "";
			String alsoFitsModel1 = "";
			String alsoFitsModel2 = "";
			String alsoFitsPartNos = "";
			String rightOrLeft = "";
			String location = "";
			String locationCH = "";
			String locationGR = "";
			String locationMP = "";
			Label label = getLabel(partno);

			if (label != null) {
				interChangeNo = label.getInterchangeno();
				keystonenumber = label.getKeystonenumber();

				if (interChangeNo == null) {
					interChangeNo = "";
				}

				model = label.getMakemodelname();

				if (model == null) {
					model = "";
				}

				year = label.getYear();

				if (year == null) {
					year = "";
				} else if (year.trim().length() >= 3) {
					String xx = year;

					year = xx.substring(0, 2) + "-" + xx.substring(2);
				}

				desc = label.getPartdescription();

				if (desc == null) {
					desc = "";
				}

				oemNo1 = label.getOemnumber();

				if (oemNo1 == null) {
					oemNo1 = "";
				}

				location = label.getLocation();
				if (location == null) {
					location = "";
				}

				if (alllocations.equalsIgnoreCase("alllocations")) {
					locationCH = chpartsdao.getLocation(partno);
					locationGR = grpartsdao.getLocation(partno);
					locationMP = mppartsdao.getLocation(partno);
				}

			} // if label

			String oemcheck = getOEMNo(partno);

			if (!oemcheck.equalsIgnoreCase("")) {
				oemNo = "RPLCE : " + oemcheck;
			} else if (!oemNo1.trim().equals("")) {
				oemNo = "RPLCE : " + oemNo1;
			}

			// Deciding Right Or Left
			if ((desc.indexOf(" RH ") != -1) || (desc.indexOf(" Rh ") != -1) || (desc.indexOf(" rh ") != -1)
					|| (desc.indexOf(" rH ") != -1) || (desc.indexOf(" RH") != -1) || (desc.indexOf(" rh") != -1)) {
				rightOrLeft = "R";
			}

			if ((desc.indexOf(" LH ") != -1) || (desc.indexOf(" Lh ") != -1) || (desc.indexOf(" lh ") != -1)
					|| (desc.indexOf(" lH ") != -1) || (desc.indexOf(" LH") != -1) || (desc.indexOf(" lh") != -1)) {
				rightOrLeft = "L";
			}

			if (interChangeNo.trim().equals("")) {
				List<PartnoMakemodelname> rs4List = getPartnoMakemodel1(partno, sqlquery1);

				if (rs4List != null) {
					for (PartnoMakemodelname rs4 : rs4List) {
						String interModel = rs4.getMakemodelname();

						if (interModel == null) {
							interModel = "";
						}

						String interPart = rs4.getPartno();

						if (interPart == null) {
							interPart = "";
						}

						if (!alsoFitsModel.trim().equals("")) {
							alsoFitsModel += ",  ";
						}

						if (!alsoFitsPartNos.trim().equals("")) {
							alsoFitsPartNos += ",  ";
						}

						alsoFitsModel += interModel.trim();
						alsoFitsPartNos += interPart.trim();
					}
				}
			} // if (interChangeNo.trim().equals(""))
			else {
				List<PartnoMakemodelname> rs5List = getPartnoMakemodel1(interChangeNo.trim(), sqlquery3);

				if (rs5List != null) {
					for (PartnoMakemodelname rs5 : rs5List) {
						String interModel = rs5.getMakemodelname();

						if (interModel == null) {
							interModel = "";
						}

						String interPart = rs5.getPartno();

						if (interPart == null) {
							interPart = "";
						}

						if (!alsoFitsModel.trim().equals("")) {
							alsoFitsModel += ",  ";
						}

						if (!alsoFitsPartNos.trim().equals("")) {
							alsoFitsPartNos += ",  ";
						}

						alsoFitsModel += interModel.trim();
						alsoFitsPartNos += interPart.trim();
					}
				}

				List<PartnoMakemodelname> rs6List = getPartnoMakemodel2(partno.trim(), interChangeNo.trim(), sqlquery2);

				if (rs6List != null) {
					for (PartnoMakemodelname rs6 : rs6List) {
						String interModel = rs6.getMakemodelname();

						if (interModel == null) {
							interModel = "";
						}

						String interPart = rs6.getPartno();

						if (interPart == null) {
							interPart = "";
						}

						if (!alsoFitsModel.trim().equals("")) {
							alsoFitsModel += ",  ";
						}

						if (!alsoFitsPartNos.trim().equals("")) {
							alsoFitsPartNos += ",  ";
						}

						alsoFitsModel += interModel.trim();
						alsoFitsPartNos += interPart.trim();
					}
				}
			}

			if (alsoFitsModel.trim().length() > 40) {
				alsoFitsModel1 = ": " + alsoFitsModel.trim().substring(0, 40);

				if (alsoFitsModel.length() > 80) {
					alsoFitsModel2 = "        " + alsoFitsModel.trim().substring(40, 80);
				} else {
					alsoFitsModel2 = "            " + alsoFitsModel.trim().substring(40);
				}
			} else {
				if (!alsoFitsModel.trim().equals("")) {
					alsoFitsModel1 = ": " + alsoFitsModel.trim();
				}
			}

			if (!alsoFitsPartNos.trim().equals("")) {
				alsoFitsPartNos = "" + alsoFitsPartNos;

				if (alsoFitsPartNos.length() > 40) {
					alsoFitsPartNos = alsoFitsPartNos.substring(0, 40);
				}
			}

			while (cnt > 0) {
				cnt--;

				String dataString = "";

				dataString += rightOrLeft + "\t";
				dataString += partno + "\t";
				if (alllocations.equalsIgnoreCase("alllocations")) {
					dataString += "*" + partno + "*" + "\t";
				}
				dataString += "PL# " + keystonenumber + "\t";
				dataString += year + "\t";
				dataString += model + "\t";
				dataString += desc + "\t";
				if (alsoFitsPartNos.equalsIgnoreCase("") || alsoFitsPartNos == null) {
					dataString += "" + "\t";
				} else {
					dataString += "Also Fit:" + "\t";
				}

				dataString += "" + "\t";
				dataString += alsoFitsPartNos + "\t";
				dataString += oemNo + "\t";
				if (alllocations.equalsIgnoreCase("alllocations")) {
					dataString += "CH-" + locationCH + "\t";
					dataString += "GR-" + locationGR + "\t";
					dataString += "MP-" + locationMP + "\t";
				} else {
					dataString += location + "\t";
				}

				dataString += orderno + "\t";
				dataString += supplinvno + "\t";

				dataString += "\n";

				// if (((desc.indexOf("FENDER") != -1) ||
				// (desc.indexOf("Fender") != -1) || (desc.indexOf("fender") !=
				// -1))
				// && (desc.indexOf("inner") == -1) && (desc.indexOf("INNER") ==
				// -1)
				// && (desc.indexOf("liner") == -1) && (desc.indexOf("LINER") ==
				// -1)) {
				// labelData.write(dataString);
				// labelData.write(dataString);
				// } else {
				labelData.write(dataString);
				// }
			} // while cnt
		} // main for loop

		labelData.flush();
		labelData.close();

		return file;
	}
}
