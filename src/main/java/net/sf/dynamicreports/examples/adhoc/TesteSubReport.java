package net.sf.dynamicreports.examples.adhoc;

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


public class TesteSubReport {

	public TesteSubReport() {
		build();
	}

	private void build() {
		SubreportBuilder subreport = cmp.subreport(new SubreportExpression());
		subreport.setDataSource(new SubreportDataSourceExpression());

		try {
			JasperReportBuilder report = report();
			report.title(Templates.createTitleComponent("DetailDynamicSubreport"));
			report.detail(subreport, cmp.verticalGap(20));
			report.pageFooter(Templates.footerComponent);
			report.setDataSource(createDataSource());
			report.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private JRDataSource createDataSource() {
		return new JREmptyDataSource(5);
	}

	private class SubreportExpression extends AbstractSimpleExpression<JasperReportBuilder> {
		private static final long serialVersionUID = 1L;

		public JasperReportBuilder evaluate(ReportParameters reportParameters) {
			int masterRowNumber = reportParameters.getReportRowNumber();
			JasperReportBuilder report = report();
			report.setTemplate(Templates.reportTemplate);
			
			TextFieldBuilder<String> textFieldBuilder = cmp.text("Subreport" + masterRowNumber);
			textFieldBuilder.setStyle(Templates.bold12CenteredStyle);
			report.title(textFieldBuilder);
			
			for (int i = 1; i <= masterRowNumber; i++) {
				report.addColumn(col.column("Column" + i, "column" + i, type.stringType()));
			}

			return report;
		}
	}

	private class SubreportDataSourceExpression extends AbstractSimpleExpression<JRDataSource> {
		private static final long serialVersionUID = 1L;

		public JRDataSource evaluate(ReportParameters reportParameters) {
			int masterRowNumber = reportParameters.getReportRowNumber();
			String[] columns = new String[masterRowNumber];
			for (int i = 1; i <= masterRowNumber; i++) {
				columns[i - 1] = "column" + i;
			}
			DRDataSource dataSource = new DRDataSource(columns);

			for (int i = 1; i <= masterRowNumber; i++) {
				Object[] values = new Object[masterRowNumber];
				for (int j = 1; j <= masterRowNumber; j++) {
					values[j - 1] = "row" + i + "_column" + j;
				}
				dataSource.add(values);
			}

			return dataSource;
		}
	}

	public static void main(String[] args) {
		new TesteSubReport();
	}
}
