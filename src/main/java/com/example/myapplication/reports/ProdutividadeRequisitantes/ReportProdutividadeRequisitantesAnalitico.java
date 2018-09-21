package com.example.myapplication.reports.ProdutividadeRequisitantes;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import static net.sf.dynamicreports.report.builder.DynamicReports.variable;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.evolucao.rmlibrary.database.SimpleRecord;
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
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class ReportProdutividadeRequisitantesAnalitico {
	Table table = null;
	String fileName = null;
	List<SimpleRecord> totalGrupoList = new ArrayList<SimpleRecord>();

	public void setTotalGrupoList(List<SimpleRecord> totalGrupoList) {
		this.totalGrupoList = totalGrupoList;
	}
	public List<SimpleRecord> getTotalGrupoList() {
		return this.totalGrupoList;
	}
	
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
	
	public ReportProdutividadeRequisitantesAnalitico() {
		
	}

	public void build() {
		FontBuilder boldFont = stl.fontArialBold().setFontSize(12);
		
		TextColumnBuilder<String> clmnUidRequisitante = col.column("Uid.Requisitante", "uidrequisitante", type.stringType());
		
		TextColumnBuilder<String> clmnRequisitante = col.column("Requisitante", "requisitante", type.stringType());
		clmnRequisitante.setWidth(200);
		clmnRequisitante.setStretchWithOverflow(false);
		
		TextColumnBuilder<String> clmnEspecialidade = col.column("Especialidade", "especialidade", type.stringType());
		clmnEspecialidade.setWidth(400);
		clmnEspecialidade.setStretchWithOverflow(false);
		
		TextColumnBuilder<BigDecimal> clmnQuantidade = col.column("Quant.", "quantidade", type.bigDecimalType());
		clmnQuantidade.setValueFormatter(new IntegerFormatter());
		clmnQuantidade.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		
		TextColumnBuilder<BigDecimal> clmnVlrcusto = col.column("Vlr.Custo", "vlrcusto", type.bigDecimalType());
		clmnVlrcusto.setValueFormatter(new ValueFormatter());
		
		TextColumnBuilder<BigDecimal> clmnVlrvenda = col.column("Vlr.Venda", "vlrvenda", type.bigDecimalType());
		clmnVlrvenda.setValueFormatter(new ValueFormatter());

		TextColumnBuilder<BigDecimal> clmnVlrliquido = col.column("Vlr.Liquido", "vlrliquido", type.bigDecimalType());
		clmnVlrliquido.setValueFormatter(new ValueFormatter());

		TextColumnBuilder<BigDecimal> clmnPerc = col.column("perc", "perc", type.bigDecimalType());
		StyleBuilder styleBuilder = stl.style();
		styleBuilder.setPadding(2);
		styleBuilder.setBorder(stl.pen1Point());
		clmnPerc.setStyle(styleBuilder);
		clmnPerc.setValueFormatter(new ValueFormatter());
		
		//***************************
		ColumnGroupBuilder grpGrupo = grp.group(clmnRequisitante)
				.showColumnHeaderAndFooter();
		grpGrupo.setStyle(stl.style()
				.setPadding(2)
				.setBorder(stl.pen1Point())
				.bold()
				.setBackgroundColor(Color.LIGHT_GRAY));
		grpGrupo.setPadding(0);
		grpGrupo.setHeaderStyle(stl.style().setBorder(stl.pen1Point()));

		// Totais do grupo
		VariableBuilder<Long> recordCountGrupo = variable(clmnEspecialidade, Calculation.COUNT);
		VariableBuilder<BigDecimal> quantidadeGrupo = variable(clmnQuantidade, Calculation.SUM);
		VariableBuilder<BigDecimal> vlrCustoGrupo = variable(clmnVlrcusto, Calculation.SUM);
		VariableBuilder<BigDecimal> vlrVendaGrupo = variable(clmnVlrvenda, Calculation.SUM);
		VariableBuilder<BigDecimal> vlrLiquidoGrupo = variable(clmnVlrliquido, Calculation.SUM);
		VariableBuilder<BigDecimal> percGrupo = variable(clmnPerc, Calculation.SUM);
		
		recordCountGrupo.setResetGroup(grpGrupo);
		quantidadeGrupo.setResetGroup(grpGrupo);
		vlrCustoGrupo.setResetGroup(grpGrupo);
		vlrVendaGrupo.setResetGroup(grpGrupo);
		vlrLiquidoGrupo.setResetGroup(grpGrupo);
		percGrupo.setResetGroup(grpGrupo);
		
		VariableBuilder<Long> recordCountReport = variable(clmnUidRequisitante, Calculation.COUNT);
		VariableBuilder<BigDecimal> quantidadeReport = variable(clmnQuantidade, Calculation.SUM);
		VariableBuilder<BigDecimal> vlrCustoReport = variable(clmnVlrcusto, Calculation.SUM);
		VariableBuilder<BigDecimal> vlrVendaReport = variable(clmnVlrvenda, Calculation.SUM);
		VariableBuilder<BigDecimal> vlrLiquidoReport = variable(clmnVlrliquido, Calculation.SUM);
		VariableBuilder<BigDecimal> percReport = variable(clmnPerc, Calculation.SUM);
		
		//TextFieldBuilder<String> groupSbt = cmp.text(new CustomTextSubtotal(quantitySum));
		HorizontalListBuilder groupSbt = cmp.horizontalList()
				.add(
						cmp.text(new RecordCount(recordCountGrupo)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2)).setFixedWidth(255), 
						cmp.text(new IntegerExpression(quantidadeGrupo)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)), 
						cmp.text(new ValorSum(vlrCustoGrupo)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
						cmp.text(new ValorSum(vlrVendaGrupo)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
						cmp.text(new ValorSum(vlrLiquidoGrupo)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
						cmp.text(new PercSum(percGrupo)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)))
				.newRow().add(cmp.text(""));
		
		//grpDescUidCentCust.footer(groupSbt);
		grpGrupo.addFooterComponent(groupSbt);
		
		ComponentBuilder summary = cmp.horizontalList().add(
					cmp.text(new RecordCount(recordCountReport)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2)).setFixedWidth(255), 
					cmp.text(new IntegerExpression(quantidadeReport)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)), 
					cmp.text(new ValorSum(vlrCustoReport)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
					cmp.text(new ValorSum(vlrVendaReport)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
					cmp.text(new ValorSum(vlrLiquidoReport)).setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)), 
					cmp.text("").setStyle(stl.style().bold().setBorder(stl.pen1Point()).setPadding(2).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
				)
				.newRow().add(
						cmp.text(""));
		
		try {
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/VAADIN/themes/mytheme/reports/";
			String fileName = "rpt-" + UUID.randomUUID().toString().toUpperCase() + ".pdf";
			this.setFileName(fileName);
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(basepath + fileName);

			report()
					.setTemplate(Templates2.reportTemplate)
					.setShowColumnTitle(false)
					.variables(recordCountGrupo, quantidadeGrupo, vlrCustoGrupo, vlrVendaGrupo, vlrLiquidoGrupo, percGrupo, 
							recordCountReport, quantidadeReport, vlrCustoReport, vlrVendaReport, vlrLiquidoReport, percReport)
					.columns(clmnEspecialidade, clmnQuantidade, clmnVlrcusto, clmnVlrvenda, clmnVlrliquido, clmnPerc)
					.groupBy(grpGrupo)
					.title(Templates2.createTitleComponent("PRODUTIVIDADE POR REQUISITANTE - ANALITICO - DE " + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "dd/MM/yyyy") + " A " + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "dd/MM/yyyy")))
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
		DRDataSource dataSource = new DRDataSource("uidrequisitante", "requisitante", "especialidade", "quantidade", "vlrcusto", "vlrvenda", "vlrliquido", "perc");
		
		MyUI ui = (MyUI) UI.getCurrent();
		
		String filtragem = "(left(carrcomp2.processado,11)>='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisinic"), "yyyy-MM-dd") + "') and (left(carrcomp2.processado,11)<='" + Utils.getSimpleDateFormat(this.getTable().getDate("emisfina"), "yyyy-MM-dd") + "')  and (devoreci.datadevo is null)";

		if (!this.getTable().getString("codicaix").isEmpty()) {
			filtragem = filtragem + " and (carrcomp2.codicaix='" + this.getTable().getString("codicaix") + "')";
		}
		
		if (!this.getTable().getString("uidrequisitante").isEmpty()) {
			filtragem = filtragem + " and (carrcomp2.uidrequisitante='" + this.getTable().getString("uidrequisitante") + "')";
		}
		
		try {
			ui.database.openConnection();
			
			String comando1 = ""; 
			comando1 += " select sum(carrconsexam.vlrvenda) as vlrvenda";
			comando1 += " from carrconsexam";
			comando1 += " left join carrcomp2 on (carrcomp2.uid=carrconsexam.uidcarrcomp)";
			comando1 += " left join requisitantes on (requisitantes.uid=carrcomp2.uidrequisitante)";
			comando1 += " left join recibos on (recibos.sequencia=carrconsexam.numereci)";
			comando1 += " left join devoreci on (devoreci.numereci=recibos.sequencia)";
			comando1 += " where " + filtragem;
			comando1 += " order by requisitantes.descricao";
			
			Double totalVendasReport = 1d;
			ResultSet rs1 = ui.database.executeSelect(comando1);
			if (rs1.next()) {
				totalVendasReport = rs1.getDouble("vlrvenda");
			}

			String comando2 = "";
			comando2 += " select distinct requisitantes.uid as uidrequisitante, requisitantes.descricao, especialidades.descricao as especialidade, count(*) as quantidade, sum(carrconsexam.vlrcusto) as vlrcusto, sum(carrconsexam.vlrvenda) as vlrvenda";
			comando2 += " from carrconsexam";
			comando2 += " left join carrcomp2 on (carrcomp2.uid=carrconsexam.uidcarrcomp)";
			comando2 += " left join requisitantes on (requisitantes.uid=carrcomp2.uidrequisitante)";
			comando2 += " left join recibos on (recibos.sequencia=carrconsexam.numereci)";
			comando2 += " left join devoreci on (devoreci.numereci=recibos.sequencia)";
			comando2 += " left join especialidades on (especialidades.codiespe=carrconsexam.codiespe)";
			comando2 += " where " + filtragem;
			comando2 += " group by requisitantes.descricao, especialidades.descricao";
			comando2 += " order by requisitantes.descricao, especialidades.descricao";
			
			ResultSet rs = ui.database.executeSelect(comando2);
			while (rs.next()) {
				try {
					Double quantidade = rs.getDouble("quantidade");
					Double vlrcusto = rs.getDouble("vlrcusto");
					Double vlrvenda = rs.getDouble("vlrvenda");
					Double vlrliquido = vlrvenda-vlrcusto;
					Double totalVendas = getTotalVendasRequisitante(rs.getString("uidrequisitante"), filtragem);
					Double perc = ((vlrliquido*100)/totalVendas);
					
					if ((rs.getString("uidrequisitante")==null)) {
						dataSource.add("uid-undefined","NÃO CLASSIFICADO", rs.getString("especialidade"), BigDecimal.valueOf(quantidade), BigDecimal.valueOf(vlrcusto), BigDecimal.valueOf(vlrvenda), BigDecimal.valueOf(vlrliquido), BigDecimal.valueOf(perc));
					}
					else {
						dataSource.add(rs.getString("uidrequisitante"), rs.getString("descricao"), rs.getString("especialidade"), BigDecimal.valueOf(quantidade), BigDecimal.valueOf(vlrcusto), BigDecimal.valueOf(vlrvenda), BigDecimal.valueOf(vlrliquido), BigDecimal.valueOf(perc));
					}
				}
				catch (Exception e) {
					System.out.println("Falha no registro: " + rs.getString("descricao") + " - " + e.getMessage());
				}
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
	};
	
	
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

	/**
	 * Usado para formatar o conteudo dos campos no band detail do relatorio
	 * @author RamatisMelo
	 *
	 */
	private static class IntegerFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;

		public String format(Number value, ReportParameters reportParameters) {
			NumberFormat numberFormat = new DecimalFormat("#,##0");
			String retorno = numberFormat.format(value);
			retorno.replaceAll(",", ".");
					
			return retorno;
		}
	}
	
	/**
	 * Usado para formatar o conteudo das variaveis utilizadas no groupfooter ou summary do relatorio
	 * @author RamatisMelo
	 *
	 */
	private class IntegerExpression extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;
		
		VariableBuilder<BigDecimal> valorSum;
		
		public IntegerExpression(VariableBuilder<BigDecimal> valorSum) {
			this.valorSum = valorSum;
		}

		public String evaluate(ReportParameters reportParameters) {
			String retorno = "";
			BigDecimal var = reportParameters.getValue(valorSum);
			//retorno = Utils.formatInteger(Integer.parseInt(var.toString()));
			retorno = Utils.formatInteger(var.intValue());

			return retorno;
		}
	}
	
	public Double getTotalVendasRequisitante(String uidRequisitante, String filtragem) {
		Double retorno = 0d;
		boolean foundInternal = false;
		MyUI ui = (MyUI) UI.getCurrent();
		
		// Verifica se já possui o total das vendas do requisitante na memoria, caso tenha
		// simplesmente retorna ele
		for (SimpleRecord simpleRecord: this.getTotalGrupoList()) {
			if ((uidRequisitante==null) && (simpleRecord.getString("uidRequisitante")==null)) {
				retorno = simpleRecord.getDouble("totalVendas");
				foundInternal = true;
				break;
			}
			else if ((simpleRecord.getString("uidRequisitante")!=null) && (uidRequisitante!=null) && (simpleRecord.getString("uidRequisitante").equalsIgnoreCase(uidRequisitante))) {
				retorno = simpleRecord.getDouble("totalVendas");
				foundInternal = true;
				break;
			}
		}

		// Caso nao tgenha o total das vendas do requisitante na memoria, 
		// tenta pegar do bando de dados
		if (!foundInternal) {
			String filtragem2 = "";
			if (uidRequisitante == null) {
				filtragem2 = filtragem + " and (carrcomp2.uidrequisitante is null)";  
			}
			else {
				filtragem2 = filtragem + " and (carrcomp2.uidrequisitante='" + uidRequisitante + "')";  
			}
			
			String comando2 = "";
			comando2 += " select distinct requisitantes.uid, sum(carrconsexam.vlrvenda-carrconsexam.vlrcusto) as vlrvenda";
			comando2 += " from carrconsexam";
			comando2 += " left join carrcomp2 on (carrcomp2.uid=carrconsexam.uidcarrcomp)";
			comando2 += " left join requisitantes on (requisitantes.uid=carrcomp2.uidrequisitante)";
			comando2 += " left join recibos on (recibos.sequencia=carrconsexam.numereci)";
			comando2 += " left join devoreci on (devoreci.numereci=recibos.sequencia)";
			comando2 += " where " + filtragem2;
			comando2 += " group by requisitantes.uid";
			
			try {
				ResultSet rs = ui.database.executeSelect(comando2);
				if (rs.next()) {
					SimpleRecord newSimpleRecord = new SimpleRecord();
					newSimpleRecord.setValue("uidRequisitante", rs.getString("uid"));
					newSimpleRecord.setValue("totalVendas", rs.getDouble("vlrvenda"));
					this.getTotalGrupoList().add(newSimpleRecord);
					retorno = rs.getDouble("vlrvenda");
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return retorno;
	}
}
