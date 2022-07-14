package com.bvas.insight.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bvas.insight.data.CreateVendorOrder;

@Repository
@SuppressWarnings({ "deprecation", "unused" })
public class VendorOrderDaoImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(VendorOrderDaoImpl.class);

	@Autowired
	private DataSource chdatasourceref;

	@Autowired
	private DataSource grdatasourceref;

	@Autowired
	private DataSource mpdatasourceref;

	public String createVedorOrder(CreateVendorOrder vendorOrder) throws IOException {
		if (vendorOrder.getLocation().equals("CH")) {
			return createVedorOrder(vendorOrder, chdatasourceref);
		} else if (vendorOrder.getLocation().equalsIgnoreCase("MP")) {
			return createVedorOrder(vendorOrder, mpdatasourceref);
		} else if (vendorOrder.getLocation().equalsIgnoreCase("GR")) {
			return createVedorOrder(vendorOrder, grdatasourceref);
		}
		return "Please select a valid location";
	}

	private String createVedorOrder(CreateVendorOrder data, DataSource dataSource) throws IOException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		String SQL1 = "Insert Into VendorOrder (OrderNo, SupplierId, OrderDate, DeliveredDate, OrderStatus, OrderTotal,"
				+ " UpdatedInventory, UpdatedPrices) values (?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(SQL1,
				new Object[] { data.getOrderNo(), data.getSupplierId(), date, date, "New", 0.0, "N", "N" });
		File file = File.createTempFile("tmpOrder", "txt");
		data.getFile().transferTo(file);
		FileReader reader = new FileReader(file);
		BufferedReader dataFile = new BufferedReader(reader);

		String line = "";
		int cnt1 = 0;

		while ((line = dataFile.readLine()) != null) {
			cnt1++;
			// LOGGER.info(cnt1);

			String partNo = "";
			String vendPartNo = "";
			int qty = 0;
			double price = 0.0;
			int noOrder = 0;
			StringTokenizer st = new StringTokenizer(line.trim(), "\t");
			int cnt = 0;

			while (st.hasMoreTokens()) {
				cnt++;
				if ((cnt == 1) && (data.getByWhosNoToCreateOrder().equalsIgnoreCase(CreateVendorOrder.BY_OUR_NO))) {
					partNo = st.nextToken().trim();
					if (partNo.length() > 5) {
						partNo = partNo.substring(0, 5);
					}
				} else if ((cnt == 1)
						&& (data.getByWhosNoToCreateOrder().equalsIgnoreCase(CreateVendorOrder.BY_VENDOR_NO))) {
					vendPartNo = st.nextToken().trim();
				}

				if ((cnt == 2) && data.isQtyInInputFile()) {
					qty = Integer.parseInt(st.nextToken().trim());
				} else if (cnt == 2) {
					qty = 1;
				}

				if (cnt > 2) {
					break;
				}
			}

			noOrder = cnt1;
			if (data.getByWhosNoToCreateOrder().equalsIgnoreCase(CreateVendorOrder.BY_OUR_NO)) {
				String SQL2 = "Select VendorPartNo from VendorItems Where SupplierId=? and PartNo = ?";
				List<String> vendorPartNoList = jdbcTemplate.queryForList(SQL2,
						new Object[] { data.getSupplierId(), partNo }, String.class);
				if (vendorPartNoList != null && !vendorPartNoList.isEmpty()) {
					vendPartNo = vendorPartNoList.get(0);
				} else {
					vendPartNo = "";
				}
			} else if (data.getByWhosNoToCreateOrder().equalsIgnoreCase(CreateVendorOrder.BY_VENDOR_NO)) {
				String SQL2 = "Select PartNo from VendorItems Where SupplierId=? and VendorPartNo = ?";
				List<String> partNoList = jdbcTemplate.queryForList(SQL2,
						new Object[] { data.getSupplierId(), vendPartNo }, String.class);
				if (partNoList != null && !partNoList.isEmpty()) {
					partNo = partNoList.get(0);
				} else {
					continue;
				}
				if (partNo == null) {
					partNo = "";
				}

			}
			String SQL3 = "INSERT INTO VendorOrderedItems (OrderNo, PartNo, VendorPartNo, Quantity, Price, NoOrder) "
					+ "VALUES (?,?,?,?,?,?)";
			jdbcTemplate.update(SQL3, new Object[] { data.getOrderNo(), partNo, vendPartNo, qty, price, noOrder });
		}
		dataFile.close();
		return "";
	}

}
