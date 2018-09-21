package com.example.myapplication.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.utils.Utils;
import com.example.myapplication.MyUI;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import net.sf.dynamicreports.examples.adhoc.Templates;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.base.datatype.DRDataType;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.VariableBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.group.CustomGroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ReportCentCust2 {
	Table table = null;
	String fileName = null;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return this.fileName;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public ReportCentCust2() {
		
	}

	public void build() {
		FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		
		TextColumnBuilder<String> descuidcentcust = col.column("Centro de custo", "descuidcentcust", type.stringType());
		descuidcentcust.setWidth(400);
		descuidcentcust.setStretchWithOverflow(false);
		
		TextColumnBuilder<BigDecimal> valor = col.column("Valor", "valor", type.bigDecimalType());
		valor.setValueFormatter(new ValueFormatter());
		
		TextColumnBuilder<BigDecimal> perc = col.column("perc", "perc", type.bigDecimalType());
		StyleBuilder styleBuilder = stl.style();
		styleBuilder.setPadding(2);
		styleBuilder.setBorder(stl.pen1Point());
		perc.setStyle(styleBuilder);
		perc.setValueFormatter(new ValueFormatter());
		
		VariableBuilder<Long> totQuantitySum = variable(valor, Calculation.COUNT);
		VariableBuilder<BigDecimal> totValorSum = variable(valor, Calculation.SUM);
		VariableBuilder<BigDecimal> totPercSum = variable(perc, Calculation.SUM);
		
		ComponentBuilder summary = cmp.horizontalList().add(
					cmp.text(new RecordCount(totQuantitySum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2)).setFixedWidth(383), 
					cmp.text(new ValorSum(totValorSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
					cmp.text(new PercSum(totPercSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
				)
				.newRow().add(
						cmp.text(""))
				.newRow().add(
						cht.pieChart()
						.setTitle("ANÁLISE DAS DESPESAS")
						.setTitleFont(boldFont)
						.setKey(descuidcentcust)
						.setShowLegend(true)
						.series(
								cht.serie(valor))
						);
		try {
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/VAADIN/themes/mytheme/reports/";
			String fileName = "rpt-" + UUID.randomUUID().toString().toUpperCase() + ".pdf";
			this.setFileName(fileName);
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(basepath + fileName);

			report()
					.setTemplate(Templates2.reportTemplate)
					.variables(totQuantitySum, totValorSum, totPercSum)
					.columns(descuidcentcust, valor, perc)
					.title(Templates2.createTitleComponent("RELATÓRIO DE SAÍDAS DO CAIXA POR CENTRO DE CUSTO - SINTETICO - DE " + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "dd/MM/yyyy") + " A " + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "dd/MM/yyyy")))
					.pageFooter(Templates2.footerComponent)
					.setDataSource(createDataSource())
					.summary(summary)
					.toPdf(pdfExporter)
					;
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("descuidcentcust", "valor", "perc");
		
		MyUI ui = (MyUI) UI.getCurrent();
		
		String filtragem = "(saidcaix.data>='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "yyyy-MM-dd") + "') and (saidcaix.data<='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "yyyy-MM-dd") + "')  and (saidcaix.codicaix='" + this.getTable().getString("codicaix") + "')";
		
		if (!this.getTable().getString("uidcentcust").isEmpty()) {
			filtragem = filtragem + " and (saidcaix.uidcentcust='" + this.getTable().getString("uidcentcust") + "')";
		}

		String comando1 = ""; 
		comando1 += "select sum(saidcaix.valor) as valor from saidcaix ";
		comando1 += "left join centroscusto on (centroscusto.uid=saidcaix.uidcentcust) ";
		comando1 += "where " + filtragem + " ";
		
		String comando2 = ""; 
		comando2 += "select distinct centroscusto.descricao, sum(saidcaix.valor) as valor from saidcaix ";
		comando2 += "left join centroscusto on (centroscusto.uid=saidcaix.uidcentcust) ";
		comando2 += "where " + filtragem + " ";
		comando2 += "group by centroscusto.descricao ";
		comando2 += "order by centroscusto.descricao";
		
		try {
			ui.database.openConnection();

			Double totalSaidas = 1d;
			ResultSet rs1 = ui.database.executeSelect(comando1);
			if (rs1.next()) {
				totalSaidas = rs1.getDouble("valor");
			}
			
			ResultSet rs = ui.database.executeSelect(comando2);
			while (rs.next()) {
				System.out.println(rs.getString("descricao"));
				
				if ((rs.getString("descricao")==null)) {
					dataSource.add("DESPESAS NÃO CLASSIFICADAS", BigDecimal.valueOf(rs.getDouble("valor")), BigDecimal.valueOf((rs.getDouble("valor")*100)/totalSaidas));
				}
				else {
					dataSource.add(rs.getString("descricao"), BigDecimal.valueOf(rs.getDouble("valor")), BigDecimal.valueOf((rs.getDouble("valor")*100)/totalSaidas));
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
		
		return dataSource;
	}
	
	private static class ValueFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;

		public String format(Number value, ReportParameters reportParameters) {
			NumberFormat numberFormat = new DecimalFormat("#,##0.00");
			String retorno = numberFormat.format(value);
			retorno.replaceAll(",", "a");
			retorno.replaceAll(".", ",");
			retorno.replaceAll("a", ".");
					
			return retorno;
		}
	}
	
	private static class DateFormatter extends AbstractValueFormatter<String, Date> {
		private static final long serialVersionUID = 1L;

		@Override
		public String format(Date value, ReportParameters reportParameters) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return simpleDateFormat.format(value);
		}
	}
	
	private class PercSum extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;
		
		VariableBuilder<BigDecimal> percSum;
		
		public PercSum(VariableBuilder<BigDecimal> percSum) {
			this.percSum = percSum;
		}

		public String evaluate(ReportParameters reportParameters) {
			String retorno = "";
			BigDecimal var = reportParameters.getValue(percSum);
			retorno = Utils.formatDouble(Double.parseDouble(var.toString()));

			return retorno;
		}
	}
	
	private class ValorSum extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;
		
		VariableBuilder<BigDecimal> valorSum;
		
		public ValorSum(VariableBuilder<BigDecimal> valorSum) {
			this.valorSum = valorSum;
		}

		public String evaluate(ReportParameters reportParameters) {
			String retorno = "";
			BigDecimal var = reportParameters.getValue(valorSum);
			retorno = Utils.formatDouble(Double.parseDouble(var.toString()));

			return retorno;
		}
	}
	
	private class RecordCount extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;

		private VariableBuilder<Long> varRecordCount;

		public RecordCount(VariableBuilder<Long> varRecordCount) {
			this.varRecordCount = varRecordCount;
		}

		public String evaluate(ReportParameters reportParameters) {
			String retorno = "";
			Long var = reportParameters.getValue(varRecordCount);
			if (reportParameters.getValue(varRecordCount)==1) {
				retorno = var.toString() + " Registro emitido.";
			}
			else {
				retorno = var.toString() + " Registros emitidos.";
			}

			return retorno;
		}
	}
}
