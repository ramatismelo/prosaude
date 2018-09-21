package net.sf.dynamicreports.examples.adhoc;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

public class TesteColumnGridReport {
	private final int columns_count = 12;

	public TesteColumnGridReport() {
		build();
	}

	private void build() {
		@SuppressWarnings("unchecked")
		TextColumnBuilder<String>[] columns = new TextColumnBuilder[columns_count];
		for (int i = 1; i <= columns_count; i++) {
			columns[i - 1] = col.column("Column" + i, "column" + i, type.stringType());
		}
		columns[columns_count / 2].setFixedWidth(300);

		try {
			JasperReportBuilder report = report();
			report.setTextStyle(stl.style(stl.pen1Point()));
			report.columns(columns);
			report.columnGrid(grid.horizontalFlowColumnGridList(columns));
			//report.detail(cmp.verticalGap(10));
			report.setDataSource(createDataSource());
			report.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private JRDataSource createDataSource() {
		String[] columns = new String[columns_count];
		for (int i = 1; i <= columns_count; i++) {
			columns[i - 1] = "column" + i;
		}
		DRDataSource dataSource = new DRDataSource(columns);
		for (int i = 1; i <= 5; i++) {
			Object[] row = new Object[columns_count];
			for (int j = 0; j < columns_count; j++) {
				row[j] = "row " + i;
			}
			dataSource.add(row);
		}
		return dataSource;
	}

	public static void main(String[] args) {
		new TesteColumnGridReport();
	}
}
