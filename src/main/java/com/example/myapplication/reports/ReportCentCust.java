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

public class ReportCentCust {
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
	
	public ReportCentCust() {
		
	}

	public void build() {
		FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		
		TextColumnBuilder<String> uidcentcust = col.column("Uid.Centro de custo", "uidcentcust", type.stringType());
		
		TextColumnBuilder<String> descuidcentcust = col.column("Centro de custo", "descuidcentcust", type.stringType());
		
		TextColumnBuilder<Date> data = col.column("Data", "data", type.dateType());
		data.setValueFormatter(new DateFormatter());
		data.setStyle(stl.style()
				.setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.TOP)
				.setPadding(2)
				.setBorder(stl.pen1Point()));
		data.setFixedWidth(70);
		data.setStretchWithOverflow(false);
		
		TextColumnBuilder<String> descricao = col.column("Descrição", "descricao", type.stringType());
		descricao.setWidth(400);
		descricao.setStretchWithOverflow(false);
		
		TextColumnBuilder<BigDecimal> valor = col.column("Valor", "valor", type.bigDecimalType());
		valor.setValueFormatter(new ValueFormatter());
		
		TextColumnBuilder<BigDecimal> perc = col.column("perc", "perc", type.bigDecimalType());
		StyleBuilder styleBuilder = stl.style();
		styleBuilder.setPadding(2);
		styleBuilder.setBorder(stl.pen1Point());
		perc.setStyle(styleBuilder);
		perc.setValueFormatter(new ValueFormatter());
		
		//AggregationSubtotalBuilder<Long> itemCount = sbt.count(uidcentcust).setLabel("quantidade");
		
		//***************************
		ColumnGroupBuilder grpDescUidCentCust = grp.group(descuidcentcust)
				.showColumnHeaderAndFooter();
		grpDescUidCentCust.setStyle(stl.style()
				.setPadding(2)
				.setBorder(stl.pen1Point())
				.bold()
				.setBackgroundColor(Color.LIGHT_GRAY));
		grpDescUidCentCust.setPadding(0);
		grpDescUidCentCust.setHeaderStyle(stl.style().setBorder(stl.pen1Point()));

		VariableBuilder<Long> quantitySum = variable(uidcentcust, Calculation.COUNT);
		quantitySum.setResetGroup(grpDescUidCentCust);

		VariableBuilder<BigDecimal> valorSum = variable(valor, Calculation.SUM);
		valorSum.setResetGroup(grpDescUidCentCust);

		VariableBuilder<BigDecimal> percSum = variable(perc, Calculation.SUM);
		percSum.setResetGroup(grpDescUidCentCust);
		
		//***************************************
		VariableBuilder<Long> totQuantitySum = variable(uidcentcust, Calculation.COUNT);
		VariableBuilder<BigDecimal> totValorSum = variable(valor, Calculation.SUM);
		VariableBuilder<BigDecimal> totPercSum = variable(perc, Calculation.SUM);
		//**************************************
		
		//TextFieldBuilder<String> groupSbt = cmp.text(new CustomTextSubtotal(quantitySum));
		HorizontalListBuilder groupSbt = cmp.horizontalList()
				.add(
						cmp.text(new RecordCount(quantitySum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2)).setFixedWidth(406), 
						cmp.text(new ValorSum(valorSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
						cmp.text(new PercSum(percSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)))
				.newRow().add(cmp.text(""));
		
		//grpDescUidCentCust.footer(groupSbt);
		grpDescUidCentCust.addFooterComponent(groupSbt);
		
		ComponentBuilder summary = cmp.horizontalList().add(
					cmp.text(new RecordCount(totQuantitySum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2)).setFixedWidth(406), 
					cmp.text(new ValorSum(totValorSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
					cmp.text(new PercSum(totPercSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
				)
				.newRow().add(
						cmp.text(""));
		
		try {
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/VAADIN/themes/mytheme/reports/";
			String fileName = "rpt-" + UUID.randomUUID().toString().toUpperCase() + ".pdf";
			this.setFileName(fileName);
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(basepath + fileName);

			//.subtotalsAtGroupFooter(grpDescUidCentCust, sbt.sum(valor).setStyle(stl.style().setPadding(2).setBorder(stl.pen1Point())), sbt.sum(perc).setStyle(stl.style().setPadding(2).setBorder(stl.pen1Point())))
			//.setGroupFooterStyle(grpDescUidCentCust, stl.style().setBorder(stl.pen1Point()))

			//report()
			//.setTemplate(Templates2.reportTemplate)
			//.setShowColumnTitle(false)
			//.columns(descuidcentcust, data, descricao, valor, perc)
			//.groupBy(grpDescUidCentCust)
			//.setGroupFooterStyle(grpDescUidCentCust, stl.style().setBorder(stl.pen1Point()).setSpacingAfter(100).setBackgroundColor(Color.red))
			//.addGroupFooter(grpDescUidCentCust, cmp.text("que coisa chata viu"), cmp.text("dsdsfsfsdfsdf"))
			//.title(Templates.createTitleComponent("PieChart"))
			//.pageFooter(Templates.footerComponent)
			//.setDataSource(createDataSource())
			//.toPdf(pdfExporter)
			//;
			
			//.addGroupFooter(grpDescUidCentCust, groupSbt)
			//.setGroupFooterStyle(grpDescUidCentCust, stl.style().setBorder(stl.pen1Point()).setSpacingAfter(10))
			
			//.subtotalsAtGroupFooter(grpDescUidCentCust, sbt.sum(valor).setStyle(stl.style().setPadding(2).setBorder(stl.pen1Point())), sbt.sum(perc).setStyle(stl.style().setPadding(2).setBorder(stl.pen1Point())))
			//.setGroupFooterStyle(grpDescUidCentCust, stl.style().setBorder(stl.pen1Point()).setSpacingAfter(10))
			
			report()
					.setTemplate(Templates2.reportTemplate)
					.setShowColumnTitle(false)
					.variables(quantitySum, valorSum, percSum, totQuantitySum, totValorSum, totPercSum)
					.columns(descuidcentcust, data, descricao, valor, perc)
					.groupBy(grpDescUidCentCust)
					.title(Templates2.createTitleComponent("RELATÓRIO DE SAÍDAS DO CAIXA POR CENTRO DE CUSTO - ANALITICO - DE " + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "dd/MM/yyyy") + " A " + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "dd/MM/yyyy")))
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
		DRDataSource dataSource = new DRDataSource("uidcentcust", "descuidcentcust", "data", "descricao", "valor", "perc");
		
		MyUI ui = (MyUI) UI.getCurrent();
		
		String filtragem = "(saidcaix.data>='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "yyyy-MM-dd") + "') and (saidcaix.data<='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "yyyy-MM-dd") + "')  and (saidcaix.codicaix='" + this.getTable().getString("codicaix") + "')";
		
		if (!this.getTable().getString("uidcentcust").isEmpty()) {
			filtragem = filtragem + " and (saidcaix.uidcentcust='" + this.getTable().getString("uidcentcust") + "')";
		}

		try {
			ui.database.openConnection();
			
			String comando1 = ""; 
			comando1 += "select sum(saidcaix.valor) as valor from saidcaix ";
			comando1 += "left join centroscusto on (centroscusto.uid=saidcaix.uidcentcust) ";
			comando1 += "where " + filtragem + " ";
			
			Double totalSaidas = 1d;
			ResultSet rs1 = ui.database.executeSelect(comando1);
			if (rs1.next()) {
				totalSaidas = rs1.getDouble("valor");
			}
			
			Table tblSaidCaix = ui.database.loadTableByName("saidcaix");
			tblSaidCaix.setDebugQuery(true);
			tblSaidCaix.select("*");
			tblSaidCaix.setWhere(filtragem);
			tblSaidCaix.setOrder("uidcentcust, data, descricao");
			tblSaidCaix.loadData();
			while (!tblSaidCaix.eof()) {
				if (tblSaidCaix.getString("descuidcentcust")==null) {
					dataSource.add("1", "DESPESAS NÃO CLASSIFICADAS" , tblSaidCaix.getDate("data"), tblSaidCaix.getString("descricao"), BigDecimal.valueOf(tblSaidCaix.getDouble("valor")), BigDecimal.valueOf((tblSaidCaix.getDouble("valor")*100)/totalSaidas));
				}
				else {
					dataSource.add(tblSaidCaix.getString("uidcentcust"), tblSaidCaix.getString("descuidcentcust") , tblSaidCaix.getDate("data"), tblSaidCaix.getString("descricao"), BigDecimal.valueOf(tblSaidCaix.getDouble("valor")), BigDecimal.valueOf((tblSaidCaix.getDouble("valor")*100)/totalSaidas));
				}
				
				tblSaidCaix.next();
			}
		}
		catch (Exception e) {
			System.out.println(e);
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
