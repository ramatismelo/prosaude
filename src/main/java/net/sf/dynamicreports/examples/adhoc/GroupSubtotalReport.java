package net.sf.dynamicreports.examples.adhoc;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.math.BigDecimal;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class GroupSubtotalReport {

	public GroupSubtotalReport() {
		build();
	}

	private void build() {
		TextColumnBuilder<String> countryColumn = col.column("Country", "country", type.stringType());
		TextColumnBuilder<String> itemColumn = col.column("Item", "item", type.stringType());
		TextColumnBuilder<Integer> quantityColumn = col.column("Quantity", "quantity", type.integerType());
		TextColumnBuilder<BigDecimal> priceColumn = col.column("Price", "price", type.bigDecimalType());

		ColumnGroupBuilder countryGroup = grp.group(countryColumn);

		try {
			report()
					.setTemplate(Templates.reportTemplate)
					.columns(
							countryColumn, itemColumn, quantityColumn, priceColumn)
					.groupBy(
							countryGroup)
					.subtotalsAtFirstGroupFooter(
							sbt.sum(quantityColumn))
					.subtotalsAtGroupFooter(
							countryGroup, sbt.sum(priceColumn))
					.subtotalsAtSummary(
							sbt.text("Total", itemColumn), sbt.sum(quantityColumn), sbt.sum(priceColumn))
					.title(Templates.createTitleComponent("GroupSubtotal"))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createDataSource())
					.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("country", "item", "quantity", "price");
		dataSource.add("USA", "Tablet", 4, new BigDecimal(150));
		dataSource.add("USA", "Tablet", 3, new BigDecimal(190));
		dataSource.add("USA", "Laptop", 2, new BigDecimal(250));
		dataSource.add("USA", "Laptop", 1, new BigDecimal(420));
		dataSource.add("Canada", "Tablet", 6, new BigDecimal(120));
		dataSource.add("Canada", "Tablet", 2, new BigDecimal(180));
		dataSource.add("Canada", "Laptop", 3, new BigDecimal(300));
		dataSource.add("Canada", "Laptop", 2, new BigDecimal(390));
		return dataSource;
	}

	public static void main(String[] args) {
		new GroupSubtotalReport();
	}
}
