package com.example.myapplication.reports.ProdutividadeRequisitantes;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import static net.sf.dynamicreports.report.builder.DynamicReports.variable;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.utils.Utils;
import com.example.myapplication.MyUI;
import com.example.myapplication.reports.Templates2;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.VariableBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ReportProdutividadeRequisitantesSintetico {
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
	
	public ReportProdutividadeRequisitantesSintetico() {
		
	}

	public void build() {
		FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

		TextColumnBuilder<String> uidRequisitante = col.column("Uid.Requisitante", "uidrequisitante", type.stringType());
		
		TextColumnBuilder<String> requisitante = col.column("Requisitante", "requisitante", type.stringType());
		requisitante.setWidth(250);
		requisitante.setStretchWithOverflow(false);
		
		TextColumnBuilder<String> especialidade = col.column("Especialidade", "especialidade", type.stringType());
		especialidade.setWidth(250);
		especialidade.setStretchWithOverflow(false);
		
		TextColumnBuilder<BigDecimal> quantidade = col.column("Quant.", "quantidade", type.bigDecimalType());
		quantidade.setValueFormatter(new ValueFormatter());
		quantidade.setWidth(80);
		
		TextColumnBuilder<BigDecimal> vlrcusto = col.column("Vlr.Custo", "vlrcusto", type.bigDecimalType());
		vlrcusto.setValueFormatter(new ValueFormatter());
		
		TextColumnBuilder<BigDecimal> vlrvenda = col.column("Vlr.Venda", "vlrvenda", type.bigDecimalType());
		vlrvenda.setValueFormatter(new ValueFormatter());

		TextColumnBuilder<BigDecimal> vlrliquido = col.column("Vlr.Liquido", "vlrliquido", type.bigDecimalType());
		vlrliquido.setValueFormatter(new ValueFormatter());

		TextColumnBuilder<BigDecimal> perc = col.column("%", "perc", type.bigDecimalType());
		StyleBuilder styleBuilder = stl.style();
		styleBuilder.setPadding(2);
		styleBuilder.setBorder(stl.pen1Point());
		styleBuilder.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		perc.setStyle(styleBuilder);
		perc.setValueFormatter(new ValueFormatter());
		perc.setWidth(80);
		

		//********************************
		VariableBuilder<Long> numerecords = variable(uidRequisitante, Calculation.COUNT);
		VariableBuilder<BigDecimal> totQuantidadeSum = variable(quantidade, Calculation.SUM);
		VariableBuilder<BigDecimal> totvlrcustoSum = variable(vlrcusto, Calculation.SUM);
		VariableBuilder<BigDecimal> totvlrvendaSum = variable(vlrvenda, Calculation.SUM);
		VariableBuilder<BigDecimal> totLiquidoSum = variable(vlrliquido, Calculation.SUM);
		VariableBuilder<BigDecimal> totPercSum = variable(perc, Calculation.SUM);
		
		//********************************
		
		//TextColumnBuilder<String> descuidcentcust = col.column("Centro de custo", "descuidcentcust", type.stringType());
		//descuidcentcust.setWidth(400);
		//descuidcentcust.setStretchWithOverflow(false);
		
		//TextColumnBuilder<BigDecimal> valor = col.column("Valor", "valor", type.bigDecimalType());
		//valor.setValueFormatter(new ValueFormatter());
		
		//TextColumnBuilder<BigDecimal> perc = col.column("perc", "perc", type.bigDecimalType());
		//StyleBuilder styleBuilder = stl.style();
		//styleBuilder.setPadding(2);
		//styleBuilder.setBorder(stl.pen1Point());
		//perc.setStyle(styleBuilder);
		//perc.setValueFormatter(new ValueFormatter());
		
		//VariableBuilder<Long> totQuantitySum = variable(valor, Calculation.COUNT);
		//VariableBuilder<BigDecimal> totValorSum = variable(valor, Calculation.SUM);
		//VariableBuilder<BigDecimal> totPercSum = variable(perc, Calculation.SUM);
		
		ComponentBuilder summary = cmp.horizontalList().add(
					cmp.text(new RecordCount(numerecords)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2)).setFixedWidth(299), 
					cmp.text(new ValorSum(totQuantidadeSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).setFixedWidth(48), 
					cmp.text(new ValorSum(totvlrcustoSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).setFixedWidth(60), 
					cmp.text(new ValorSum(totvlrvendaSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).setFixedWidth(60), 
					cmp.text(new ValorSum(totLiquidoSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)).setFixedWidth(60), 
					cmp.text(new PercSum(totPercSum)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)).setFixedWidth(48)
				)
				.newRow().add(
						cmp.text(""))
				.newRow().add(
						cht.pieChart()
						.setTitle("ANÁLISE DAS DESPESAS")
						.setTitleFont(boldFont)
						.setKey(requisitante)
						.setShowLegend(true)
						.setShowPercentages(true)
						.series(
								cht.serie(vlrvenda))
						);
		try {
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/VAADIN/themes/mytheme/reports/";
			String fileName = "rpt-" + UUID.randomUUID().toString().toUpperCase() + ".pdf";
			this.setFileName(fileName);
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(basepath + fileName);

			report()
					.setTemplate(Templates2.reportTemplate)
					.variables(numerecords, totQuantidadeSum, totvlrcustoSum, totvlrvendaSum, totLiquidoSum, totPercSum)
					.columns(requisitante, especialidade, quantidade, vlrcusto, vlrvenda, vlrliquido, perc)
					.title(Templates2.createTitleComponent("PRODUTIVIDADE POR REQUISITANTE - SINTETICO - DE " + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "dd/MM/yyyy") + " A " + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "dd/MM/yyyy")))
					.pageFooter(Templates2.footerComponent)
					.setDataSource(createDataSource())
					.summary(summary)
					.toPdf(pdfExporter)
					;
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	TextColumnBuilder<BigDecimal> perc = col.column("perc", "perc", type.bigDecimalType());
	StyleBuilder styleBuilder = stl.style();
	
	private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("uidrequisitante", "requisitante", "especialidade", "quantidade", "vlrcusto", "vlrvenda", "vlrliquido", "perc");
		
		MyUI ui = (MyUI) UI.getCurrent();
		
		String filtragem = "(left(carrcomp2.processado,11)>='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "yyyy-MM-dd") + "') and (left(carrcomp2.processado,11)<='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "yyyy-MM-dd") + "')  and (devoreci.datadevo is null)";

		if (!this.getTable().getString("codicaix").isEmpty()) {
			filtragem = filtragem + " and (carrcomp2.codicaix='" + this.getTable().getString("codicaix") + "')";
		}
		
		if (!this.getTable().getString("uidrequisitante").isEmpty()) {
			filtragem = filtragem + " and (carrcomp2.uidrequisitante='" + this.getTable().getString("uidrequisitante") + "')";
		}

		String comando1 = ""; 
		comando1 += " select sum(carrconsexam.vlrvenda) as vlrvenda";
		comando1 += " from carrconsexam";
		comando1 += " left join carrcomp2 on (carrcomp2.uid=carrconsexam.uidcarrcomp)";
		comando1 += " left join requisitantes on (requisitantes.uid=carrcomp2.uidrequisitante)";
		comando1 += " left join recibos on (recibos.sequencia=carrconsexam.numereci)";
		comando1 += " left join devoreci on (devoreci.numereci=recibos.sequencia)";
		comando1 += " where " + filtragem;
		comando1 += " order by requisitantes.descricao";
		
		String comando2 = ""; 
		comando2 += " select distinct requisitantes.sequencia, requisitantes.descricao, requisitantes.especialidade, count(*) as quantidade, sum(carrconsexam.vlrcusto) as vlrcusto, sum(carrconsexam.vlrvenda) as vlrvenda";
		comando2 += " from carrconsexam";
		comando2 += " left join carrcomp2 on (carrcomp2.uid=carrconsexam.uidcarrcomp)";
		comando2 += " left join requisitantes on (requisitantes.uid=carrcomp2.uidrequisitante)";
		comando2 += " left join recibos on (recibos.sequencia=carrconsexam.numereci)";
		comando2 += " left join devoreci on (devoreci.numereci=recibos.sequencia)";
		comando2 += " left join especialidades on (especialidades.codiespe=carrconsexam.codiespe)";
		comando2 += " where " + filtragem;
		comando2 += " group by requisitantes.sequencia, requisitantes.descricao, requisitantes.especialidade";
		comando2 += " order by requisitantes.descricao";
		
		try {
			ui.database.openConnection();

			Double totalVendas = 1d;
			ResultSet rs1 = ui.database.executeSelect(comando1);
			if (rs1.next()) {
				totalVendas = rs1.getDouble("vlrvenda");
			}

			ResultSet rs = ui.database.executeSelect(comando2);
			while (rs.next()) {
				Double quantidade = rs.getDouble("quantidade");
				Double vlrcusto = rs.getDouble("vlrcusto");
				Double vlrvenda = rs.getDouble("vlrvenda");
				Double vlrliquido = vlrvenda-vlrcusto;
				Double perc = ((rs.getDouble("vlrvenda")*100)/totalVendas); 
				
				if ((rs.getString("descricao")==null)) {
					dataSource.add("uid-undefined","NÃO CLASSIFICADO", "", BigDecimal.valueOf(quantidade), BigDecimal.valueOf(vlrcusto), BigDecimal.valueOf(vlrvenda), BigDecimal.valueOf(vlrliquido), BigDecimal.valueOf(perc));
				}
				else {
					dataSource.add(rs.getString("sequencia"), rs.getString("descricao"), rs.getString("especialidade"), BigDecimal.valueOf(quantidade), BigDecimal.valueOf(vlrcusto), BigDecimal.valueOf(vlrvenda), BigDecimal.valueOf(vlrliquido), BigDecimal.valueOf(perc));
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
