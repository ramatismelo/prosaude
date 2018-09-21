package com.example.myapplication.view;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.KeyValue;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.RecordButton;
import com.evolucao.rmlibrary.ui.controller.ControlForm;
import com.evolucao.rmlibrary.ui.controller.ControlGrid;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.weblibrary.ApplicationUI;
import com.example.myapplication.models.MedicoAgenda;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Agenda2View extends VerticalLayout {
	HorizontalLayout buttonsNavigator = new HorizontalLayout();
	VerticalLayout vlHorarios = new VerticalLayout();
	
	Date dataInicialAgenda = new Date();
	Date dataSelecionadaAgenda = new Date();
	String horario = null;
	Integer codigoEspecialidade = null;
	Boolean permiteAgendamentoConsulta = false;
	//Integer codigoMedico = null;
	//String nomeMedico = null;
	Boolean retorno = false;
	RmGrid rmGrid = null;
	
	SimpleRecord simpleRecord = null; 
	
	String tipoAtendimento = null;
	
	MedicoAgenda medicoAgendaSelected = null; 
	List<MedicoAgenda> medicoAgendaList = new ArrayList<MedicoAgenda>();
	
	boolean showAgendamento = false;
	
	RmFormWindow rmFormWindow = null;
	Table table = null;

	public void setPermiteAgendamentoConsulta(Boolean permiteAgendamentoConsulta) {
		this.permiteAgendamentoConsulta = permiteAgendamentoConsulta;
	}
	public Boolean getPermiteAgendamentoConsulta() {
		return this.permiteAgendamentoConsulta;
	}
	
	public void setSimpleRecord(SimpleRecord simpleRecord) {
		this.simpleRecord = simpleRecord;
	}
	public SimpleRecord getSimpleRecord() {
		return this.simpleRecord;
	}
	
	public void setTipoAtendimento(String tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}
	public String getTipoAtendimento() {
		return this.tipoAtendimento;
	}

	public void setMedicoAgendaSelected(MedicoAgenda medicoAgendaSelected) {
		this.medicoAgendaSelected = medicoAgendaSelected;
	}
	public MedicoAgenda getMedicoAgendaSelected() {
		return this.medicoAgendaSelected;
	}
	
	public void setMedicoAgendaList(List<MedicoAgenda> medicoAgendaList) {
		this.medicoAgendaList = medicoAgendaList;
	}
	public List<MedicoAgenda> getMedicoAgendaList() {
		return this.medicoAgendaList;
	}
	
	public void setShowAgendamento(boolean showAgendamento) {
		this.showAgendamento = showAgendamento;
	}
	public boolean getShowAgendamento() {
		return this.showAgendamento;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return this.table;
	}
	
	public void setRmFormWindow(RmFormWindow rmFormWindow) {
		this.rmFormWindow = rmFormWindow;
	}
	public RmFormWindow getRmFormWindow() {
		return this.rmFormWindow;
	}
	
	public void setRetorno(Boolean retorno) {
		this.retorno = retorno;
	}
	public Boolean getRetorno() {
		return this.retorno;
	}
	
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getHorario() {
		return this.horario;
	}
	
	public void setCodigoEspecialidade(Integer codigoEspecialidade) {
		this.codigoEspecialidade = codigoEspecialidade;
	}
	public Integer getCodigoEspecialidade() {
		return this.codigoEspecialidade;
	}
	
	//public void setCodigoMedico(Integer codigoMedico) {
	//	this.codigoMedico = codigoMedico;
	//}
	//public Integer getCodigoMedico() {
	//	return this.codigoMedico;
	//}
	
	//public void setNomeMedico(String nomeMedico) {
	//	this.nomeMedico = nomeMedico;
	//}
	//public String getNomeMedico() {
	//	return this.nomeMedico;
	//}
	
	public void setDataInicialAgenda(Date dataInicialAgenda) {
		// Caso a data que foi passada como parametro for anterior a data atual
		if (dataInicialAgenda.before(new Date())) {
			this.dataInicialAgenda = new Date();
		}
		else {
			this.dataInicialAgenda = dataInicialAgenda;
		}
	}
	public Date getDataInicialAgenda() {
		return this.dataInicialAgenda;
	}
	
	public void setDataSelecionadaAgenda(Date dataSelecionadaAgenda) {
		// Caso a data que foi passada como parametro for anterior a data atual
		if (dataSelecionadaAgenda.before(new Date())) {
			this.dataSelecionadaAgenda = new Date();
		}
		else {
			this.dataSelecionadaAgenda = dataSelecionadaAgenda;
		}
	}
	public Date getDataSelecionadaAgenda() {
		return this.dataSelecionadaAgenda;
	}
	
	public Agenda2View(Integer codigoEspecialidade, Integer codigoMedico, String tipoAtendimento, boolean showAgendamento) {
		this.setCodigoEspecialidade(codigoEspecialidade);
		this.setShowAgendamento(showAgendamento);
		this.setTipoAtendimento(tipoAtendimento);
		
		Database database = ((ApplicationUI) UI.getCurrent()).database;
		try {
			database.openConnection();

			Table tblEspecialidades = database.loadTableByName("especialidades");
			tblEspecialidades.select("permagencons");
			tblEspecialidades.setFilter("codiespe", codigoEspecialidade);
			tblEspecialidades.loadData();
			if (tblEspecialidades.getString("permagencons").equalsIgnoreCase("S")) {
				this.setPermiteAgendamentoConsulta(true);
			}
			else {
				this.setPermiteAgendamentoConsulta(false);
			}
			
			ResultSet resultSet = database.executeSelect("select uid, codimedi, nome from medicos where (medicos.codimedi='" + codigoMedico + "')");
			if (resultSet.next()) {
				MedicoAgenda medicoAgenda = new MedicoAgenda();
				medicoAgenda.setCodiMedi(resultSet.getInt("codimedi"));
				medicoAgenda.setUid(resultSet.getString("uid"));
				medicoAgenda.setNome(resultSet.getString("nome"));
				this.setMedicoAgendaSelected(medicoAgenda);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
		
		setMargin(false);
		setSpacing(false);
		setResponsive(true);
		setSizeFull();
		addStyleName("agenda");
		
		this.buttonsNavigator.setWidth("100%");
		addComponent(buttonsNavigator);
		{
			buttonsNavigator.addComponent(this.getNavigatorButtons());
		}
		
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(false);
		vl.setSpacing(false);
		vl.setSizeFull();
		addComponent(vl);
		{
			HorizontalLayout hl2 = new HorizontalLayout();
			hl2.setWidth("100%");
			hl2.setHeight("100%");
			vl.addComponent(hl2);
			{
				VerticalLayout vlMedicos = new VerticalLayout();
				vlMedicos.addStyleName("dados-container");
				vlMedicos.setMargin(false);
				vlMedicos.setHeight("100%");
				hl2.addComponent(vlMedicos);
				{
					//List<KeyValue> medicosList = new ArrayList<KeyValue>();
					//List<KeyValue> medicosUidList = new ArrayList<KeyValue>();
					//KeyValue medicoSelected = null;
					
					try {
						database.openConnection();
						String comando = "select medicos.uid, espemedi.codiespe, espemedi.codimedi, medicos.nome from espemedi";
						comando += " left join medicos on (medicos.uid=espemedi.uidmedico)";
						comando += " where (espemedi.codiespe='" + getCodigoEspecialidade() + "') and (medicos.situacao='A')";
						comando += " order by medicos.nome";
						ResultSet resultSet = database.executeSelect(comando);
						while (resultSet.next()) {
							MedicoAgenda medicoAgenda = new MedicoAgenda();
							medicoAgenda.setUid(resultSet.getString("uid"));
							medicoAgenda.setCodiMedi(resultSet.getInt("codimedi"));
							medicoAgenda.setNome(resultSet.getString("nome"));
							
							// Permite selecionar varios medicos somente se for para consulta, ou caso seja uma especialidade
							// que nao precisa pagar no caixa para conseguir uma consulta
							if ((this.getTipoAtendimento().equalsIgnoreCase("C")) || (this.getTipoAtendimento().equalsIgnoreCase("E")) || (this.getPermiteAgendamentoConsulta()) ) {
								this.getMedicoAgendaList().add(medicoAgenda);
							}
							// Caso seja um retorno, inclui o medico que fez o atendimento na lista
							// somente ele vai aparecer pois nao é permitido selecionar um medico para retorno
							else if ((this.getTipoAtendimento().equalsIgnoreCase("R")) && (resultSet.getInt("codimedi")==this.getMedicoAgendaSelected().getCodiMedi())) {
								this.getMedicoAgendaList().add(medicoAgenda);
							}

							if (this.getMedicoAgendaSelected()==null) {
								this.setMedicoAgendaSelected(medicoAgenda);
							}
							else if (resultSet.getInt("codimedi")==this.getMedicoAgendaSelected().getCodiMedi()) {
								this.setMedicoAgendaSelected(medicoAgenda);
							}
						}
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
					finally {
						database.closeConnection();
					}
					
					ListSelect<MedicoAgenda> lstMedicos = new ListSelect<MedicoAgenda>(null, this.getMedicoAgendaList());
					if (this.getMedicoAgendaSelected()!=null) {
						lstMedicos.select(this.getMedicoAgendaSelected());
					}
					
					lstMedicos.setWidth("100%");
					lstMedicos.setHeight("100%");
					vlMedicos.addComponent(lstMedicos);
					lstMedicos.setItemCaptionGenerator(new ItemCaptionGenerator<MedicoAgenda>() {
						@Override
						public String apply(MedicoAgenda item) {
							return item.getNome();
						}
					});
					
			        lstMedicos.addValueChangeListener(new ValueChangeListener<Set<MedicoAgenda>>() {
						private static final long serialVersionUID = 1L;
						
						@Override
						public void valueChange(com.vaadin.data.HasValue.ValueChangeEvent<Set<MedicoAgenda>> event) {
							MedicoAgenda medicoAgenda = event.getValue().iterator().next();
							setMedicoAgendaSelected(medicoAgenda);
							
							updateHorariosMedicoSelected();
							// Cria a relacao de horarios para o medico selecionado
							//vlHorarios.removeAllComponents();
							//vlHorarios.addComponent(montaAgenda(getMedicoAgendaSelected().getCodiMedi(), getDataSelecionadaAgenda()));
							
							//rmGrid.getTable().setFilter("uidmedico", medicoAgenda.getUid());
							//rmGrid.updateRecords();
						}
					});
				}
				
				VerticalLayout vl2 = new VerticalLayout();
				vl2.addStyleName("vl2");
				vl2.setMargin(false);
				hl2.addComponent(vl2);
				{
					CssLayout vl3 = new CssLayout();
					vl3.addStyleName("vl3");
					vl3.setHeight("300px");
					vl3.setWidth("100%");
					//vl3.setSpacing(false);
					//vl3.setMargin(false);
					//vl2.setWidth("100%");
					vl2.addComponent(vl3);
					{
						Label lblTitulo = new Label("Horários de atendimento:");
						vl3.addComponent(lblTitulo);
						
						ApplicationUI ui = (ApplicationUI) UI.getCurrent();
						rmGrid = ui.database.loadRmGridByName("agenda2");
						rmGrid.updateContent();
						rmGrid.getTable().setFilter("uidmedico", "ramatis");
						rmGrid.updateRecords();
						vl3.addComponent(rmGrid);
						
						/* Container com os horarios que o médico possui disponiveis */
						vlHorarios.setMargin(false);
						vlHorarios.addStyleName("agenda-horarios");
						vlHorarios.setHeight("100%");
						vl3.addComponent(vlHorarios);
						{
							vlHorarios.addComponent(this.montaAgenda(this.getMedicoAgendaSelected().getCodiMedi(), tipoAtendimento, this.getDataSelecionadaAgenda()));
						}
					}
				}
			}
		}
		setExpandRatio(vl, 1);
		
		this.updateHorariosMedicoSelected();
	}
	
	public HorizontalLayout getNavigatorButtons() {
		HorizontalLayout retorno = new HorizontalLayout();
		retorno.setSpacing(false);
		retorno.setWidth("100%");
		{
			retorno.addStyleName("agenda-controller");
			
			CssLayout verticalButtons = new CssLayout();
			verticalButtons.addStyleName("flex-direction-row");
			retorno.addComponent(verticalButtons);
			{
				Button priorButton = new Button();
				priorButton.addStyleName("remove-button");
				priorButton.addStyleName("button");
				priorButton.addStyleName("flex-grow-1");
				priorButton.addStyleName("button-especial");
				priorButton.addStyleName("button-prior");
				priorButton.setIcon(FontAwesome.CHEVRON_LEFT);
				verticalButtons.addComponent(priorButton);
				priorButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						String data1 = dateFormat.format(getDataInicialAgenda());
						String data2 = dateFormat.format(new Date());
						if (!data1.equalsIgnoreCase(data2)) {
							Calendar calendar = new GregorianCalendar();
							calendar.setTime(getDataInicialAgenda());
							calendar.add(Calendar.DAY_OF_MONTH, -1);
							setDataInicialAgenda(calendar.getTime());
							buttonsNavigator.removeAllComponents();
							buttonsNavigator.addComponent(getNavigatorButtons());
						}
					}
				});
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(getDataInicialAgenda());
				
				for (int i=0; i<=7; i++) {
					CssLayout divButton = new CssLayout();
					divButton.addStyleName("button");
					divButton.addStyleName("flex-direction-column");
					divButton.addStyleName("flex-grow-1");
					verticalButtons.addComponent(divButton);
					{
						RecordButton btnTitle = new RecordButton(Utils.getNomeDiaSemana(calendar.getTime()).toUpperCase().substring(0,3));
						btnTitle.addStyleName("remove-button");
						btnTitle.addStyleName("button-title");
						btnTitle.setValue("data", calendar.getTime());
						divButton.addComponent(btnTitle);
						btnTitle.addClickListener(new ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								setDataSelecionadaAgenda(((RecordButton) event.getButton()).getDate("data"));
								buttonsNavigator.removeAllComponents();
								buttonsNavigator.addComponent(getNavigatorButtons());
								
								vlHorarios.removeAllComponents();
								vlHorarios.addComponent(montaAgenda(getMedicoAgendaSelected().getCodiMedi(), tipoAtendimento, getDataSelecionadaAgenda()));
							}
						});
						
						RecordButton btnData = new RecordButton(dateFormat.format(calendar.getTime()));
						btnData.addStyleName("remove-button");
						btnData.addStyleName("button-data");
						btnData.setValue("data", calendar.getTime());
						divButton.addComponent(btnData);
						btnData.addClickListener(new ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								setDataSelecionadaAgenda(((RecordButton) event.getButton()).getDate("data"));
								buttonsNavigator.removeAllComponents();
								buttonsNavigator.addComponent(getNavigatorButtons());
								
								vlHorarios.removeAllComponents();
								vlHorarios.addComponent(montaAgenda(getMedicoAgendaSelected().getCodiMedi(), tipoAtendimento, getDataSelecionadaAgenda()));
							}
						});
					}

					SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
					
					String data1 = simple.format(getDataSelecionadaAgenda());
					String data2 = simple.format(calendar.getTime());
					if (data1.equalsIgnoreCase(data2)) {
						divButton.addStyleName("button-hoje");
					}
					
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}

				Button nextButton = new Button();
				nextButton.addStyleName("remove-button");
				nextButton.addStyleName("button-especial");
				nextButton.addStyleName("button-next");
				nextButton.addStyleName("flex-grow-1");
				nextButton.setIcon(FontAwesome.CHEVRON_RIGHT);
				nextButton.addStyleName("button");
				verticalButtons.addComponent(nextButton);
				nextButton.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(getDataInicialAgenda());
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						setDataInicialAgenda(calendar.getTime());
						buttonsNavigator.removeAllComponents();
						buttonsNavigator.addComponent(getNavigatorButtons());
					}
				});
			}
		}
		return retorno;
	}
	
	private boolean temAgenda(Integer codigoMedico) {
		boolean retorno = false;
		Database database = ((ApplicationUI) UI.getCurrent()).database;
		
		try {
			database.openConnection();
			
			String comando = "select diasemana from agenda";
			comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
			comando += " where (medicos.codimedi='" + codigoMedico + "') limit 1";
			
			ResultSet result = database.executeSelect(comando);
			
			if (result.next()) {
				retorno = true;
			}
			result.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
		return retorno;
	}
	
	/**
	 * Retorna o numero de vagas disponiveis para o medico indicado na data indicada, caso nao tenha vagas retorna zero
	 * @param codigoMedico
	 * @param data
	 * @return
	 */
	private Integer getNumeroVagas(Integer codigoMedico, String tipoAtendimento, Date data) {
		//boolean retorno = false;
		Integer retorno = 0;
		Database database = ((ApplicationUI) UI.getCurrent()).database;
		
		try {
			database.openConnection();
			Integer diasemana = Utils.getDiaSemana(data);
			Integer numevagas = 0;
			Integer numeagendamentos = 0;
			
			String comando = "select sum(numeatend) as qtdconsultas, sum(numereto) as qtdretornos from agenda ";
			comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
			comando += " where (medicos.codimedi=" + codigoMedico + ") and (diasemana="+ diasemana +")";
			ResultSet result = database.executeSelect(comando);
			if (result.next()) {
				if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
					numevagas = result.getInt("qtdconsultas");
				}
				else {
					numevagas = result.getInt("qtdretornos");
				}
			}
			result.close();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			comando = "select * from alteagenmedi ";
			comando += "where (alteagenmedi.codimedi=" + codigoMedico + ") and (alteagenmedi.data='"  + dateFormat.format(data) + "')";
			result = database.executeSelect(comando);
			if (result.next()) {
				if (result.getString("tipoalte").equalsIgnoreCase("E")) {
					numevagas = 0;
				}
				else if (result.getString("tipoalte").equalsIgnoreCase("I")) {
					if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
						numevagas = result.getInt("numeatend");
					}
					else {
						numevagas = result.getInt("numereto");
					}
				}
			}

			if (numevagas>0) {
				// Pega a forma de totalizacao do medico
				String tipoTotalizacao = "1"; // numero de atendimentos
				comando = "select tipotota from medicos ";
				comando += "where (medicos.codimedi='" + codigoMedico + "')";
				result = database.executeSelect(comando);
				if (result.next()) {
					tipoTotalizacao = result.getString("tipotota");
				}
				
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				if (tipoTotalizacao.equalsIgnoreCase("1")) {
					result = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.tipoatend='" + this.getTipoAtendimento() + "')");
				}
				else if (tipoTotalizacao.equalsIgnoreCase("2")) {
					result = database.executeSelect("select sum(numeproc) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.tipoatend='" + this.getTipoAtendimento() + "')");
				}
				
				if (result.next()) {
					numeagendamentos = result.getInt("qtd");
				}
				
				if (numevagas>numeagendamentos) {
					//retorno = true;
					retorno = numevagas-numeagendamentos;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			database.closeConnection();
		}
		
		return retorno;
	}
	
	public VerticalLayout montaAgenda(Integer codigoMedico, String tipoAtendimento, Date data) {
		VerticalLayout agendaMedica = new VerticalLayout();
		agendaMedica.setMargin(false);
		
		if (this.getShowAgendamento()) {
			Button btn2 = new Button("Enviar para agendamento");
			btn2.setWidth("100%");
			agendaMedica.addComponent(btn2);
			btn2.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if (getMedicoAgendaSelected().getCodiMedi()!=0) {
						getTable().setValue("codimedi", getMedicoAgendaSelected().getCodiMedi());
						getTable().setValue("desccodimedi", getMedicoAgendaSelected().getNome());
						getRmFormWindow().close();
					}
				}
			});
		}
		else {
			if (this.temAgenda(codigoMedico)) {
				Integer numeroVagas = this.getNumeroVagas(codigoMedico, tipoAtendimento, data);
				if (numeroVagas>0) {
					agendaMedica = this.montaAgendaHorarios(agendaMedica, codigoMedico, data);
				}
				// Caso nao tenha vaga, procura a proxima data livre
				else {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(this.getDataSelecionadaAgenda());
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					Date dataDisponivel = calendar.getTime();
					
					numeroVagas = this.getNumeroVagas(codigoMedico, tipoAtendimento, dataDisponivel);
					while (numeroVagas==0) {
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						dataDisponivel = calendar.getTime();
						numeroVagas = this.getNumeroVagas(codigoMedico, tipoAtendimento, dataDisponivel);
					}

					agendaMedica.removeAllComponents();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					RecordButton btn = null;
					if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
						btn = new RecordButton("Disponível (" + Utils.getNomeDiaSemana(dataDisponivel).toUpperCase() + ") - " + dateFormat.format(dataDisponivel) + " - " + numeroVagas + " Consultas");
					}
					else {
						btn = new RecordButton("Disponível (" + Utils.getNomeDiaSemana(dataDisponivel).toUpperCase() + ") - " + dateFormat.format(dataDisponivel) + " - " + numeroVagas + " Retornos");
					}
					
					btn.setWidth("100%");
					btn.setValue("dataInicialAgenda", this.getDataInicialAgenda());
					btn.setValue("dataDisponivel", dataDisponivel);
					agendaMedica.addComponent(btn);
					btn.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							RecordButton btn = (RecordButton) event.getButton();

							// Caso a diferenca entre a data de disponibilidade menos a data de hoje seja menor que 7, data inicial = hoje, dataselecionada = datadisponibilidade
							// Caso contrario, datainicial=datadisponibilidade, dataselecionada=datadisponibilidade
							long diferenca = Utils.getDateDiff(btn.getDate("dataInicialAgenda"), btn.getDate("dataDisponivel"), TimeUnit.DAYS);
							if (diferenca<=7) {
								setDataInicialAgenda(btn.getDate("dataInicialAgenda"));
								setDataSelecionadaAgenda(btn.getDate("dataDisponivel"));
								
								buttonsNavigator.removeAllComponents();
								buttonsNavigator.addComponent(getNavigatorButtons());
								
								vlHorarios.removeAllComponents();
								vlHorarios.addComponent(montaAgenda(getMedicoAgendaSelected().getCodiMedi(), tipoAtendimento, getDataSelecionadaAgenda()));
							}
							else {
								setDataInicialAgenda(btn.getDate("dataDisponivel"));
								setDataSelecionadaAgenda(btn.getDate("dataDisponivel"));
								
								buttonsNavigator.removeAllComponents();
								buttonsNavigator.addComponent(getNavigatorButtons());
								
								vlHorarios.removeAllComponents();
								vlHorarios.addComponent(montaAgenda(getMedicoAgendaSelected().getCodiMedi(), tipoAtendimento, getDataSelecionadaAgenda()));
							}
						}
					});
				}
			}
			else {
				Label lbl = new Label("Médico não possui agenda.");
				agendaMedica.addComponent(lbl);
				
				/*
				Button btn = new Button("Enviar para agendamento");
				btn.setWidth("100%");
				agendaMedica.addComponent(btn);
				btn.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						if (getCodigoMedico()!=0) {
							getTable().setValue("codimedi", getCodigoMedico());
							getTable().setValue("desccodimedi", getNomeMedico());
							getRmFormWindow().close();
						}
					}
				});
				*/
			}
		}
		
		return agendaMedica;
	}
	
	public VerticalLayout montaAgendaHorarios(VerticalLayout verticalLayout, Integer codigoMedico, Date data) {
		Database database = ((ApplicationUI) UI.getCurrent()).database;

		if (this.getShowAgendamento()) {
			Button btn = new Button("Enviar para agendamento");
			btn.setWidth("100%");
			verticalLayout.addComponent(btn);
			btn.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if (getMedicoAgendaSelected().getCodiMedi()!=0) {
						getTable().setValue("codimedi", getMedicoAgendaSelected().getCodiMedi());
						getTable().setValue("desccodimedi", getMedicoAgendaSelected().getNome());
						getRmFormWindow().close();
					}
				}
			});
		}
		else {
			try {
				database.openConnection();
				Integer diasemana = Utils.getDiaSemana(data);
				Integer numevagas = 0;
				Integer numeagendamentos = 0;

				// Pega o tipo de totalizacao do medico
				String tipoTotalizacao = "1"; // numero de atendimentos
				String comando = "select tipotota from medicos ";
				comando += "where (medicos.codimedi='" + codigoMedico + "')";
				ResultSet result = database.executeSelect(comando);
				if (result.next()) {
					tipoTotalizacao = result.getString("tipotota");
				}
				
				comando = "select horainic, numeatend, numereto from agenda ";
				comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
				comando += " where (medicos.codimedi=" + codigoMedico + ") and (diasemana="+ diasemana +")";
				ResultSet resultSet = database.executeSelect(comando);
				ResultSet resultSet2 = null;
				while (resultSet.next()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					if (tipoTotalizacao.equalsIgnoreCase("1")) {
						resultSet2 = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi="+ codigoMedico +") and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.horacons='" + resultSet.getString("horainic") + "') and (marccons.tipoatend='" + this.getTipoAtendimento() + "')");
					}
					else {
						resultSet2 = database.executeSelect("select sum(numeproc) as qtd from marccons where (marccons.codimedi="+ codigoMedico +") and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.horacons='" + resultSet.getString("horainic") + "') and (marccons.tipoatend='" + this.getTipoAtendimento() + "')");
					}
					
					if (resultSet2.next()) {
						numeagendamentos = resultSet2.getInt("qtd");
					}
					
					if ((this.getTipoAtendimento().equalsIgnoreCase("C")) || (this.getTipoAtendimento().equalsIgnoreCase("E"))) {
						numevagas = resultSet.getInt("numeatend") - resultSet2.getInt("qtd");
					}
					else {
						numevagas = resultSet.getInt("numereto") - resultSet2.getInt("qtd");
					}
					
					comando = "select * from alteagenmedi ";
					comando += "where (alteagenmedi.codimedi=" + codigoMedico + ") and (alteagenmedi.data='"  + dateFormat.format(data) + "')";
					result = database.executeSelect(comando);
					if (result.next()) {
						if (result.getString("tipoalte").equalsIgnoreCase("E")) {
							numevagas = 0;
						}
						else if (result.getString("tipoalte").equalsIgnoreCase("I")) {
							if ((tipoAtendimento.equalsIgnoreCase("C")) || (tipoAtendimento.equalsIgnoreCase("E"))) {
								numevagas = result.getInt("numeatend") - resultSet2.getInt("qtd");
							}
							else {
								numevagas = result.getInt("numereto") - resultSet2.getInt("qtd");
							}
						}
					}
					
					if (numevagas>0) {
						RecordButton button = new RecordButton();
						button.setWidth("100%");
						String descricao = "";
						if (Utils.getJustDate(data).equals(Utils.getJustDate(new Date()))) {
							descricao = "Disponível (HOJE)";
						}
						else {
							descricao = "Disponível (" + Utils.getNomeDiaSemana(data).toUpperCase() + ")";
						}
						
						if ((this.getTipoAtendimento().equalsIgnoreCase("C")) || (this.getTipoAtendimento().equalsIgnoreCase("E"))) {
							button.setCaption(descricao + " - "  + Utils.simpleDateFormat(data) + " - " + resultSet.getString("horainic") + " - " + numevagas + " Consultas");
						}
						else {
							button.setCaption(descricao + " - "  + Utils.simpleDateFormat(data) + " - " + resultSet.getString("horainic") + " - " + numevagas + " Retornos");
						}
						
						button.setValue("horario", resultSet.getString("horainic"));
						verticalLayout.addComponent(button);
						button.addClickListener(new ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								RecordButton button = (RecordButton) event.getButton();
								setHorario(button.getString("horario"));

								Calendar calendar = new GregorianCalendar();
								calendar.setTime(getDataSelecionadaAgenda());
								String diasema = Utils.getNomeDiaSemana(calendar.getTime()).toUpperCase();
								
								//getTable().setValue("codimedi", getMedicoAgendaSelected().getCodiMedi());
								//getTable().setValue("desccodimedi", getMedicoAgendaSelected().getNome());
								//getTable().setValue("datacons", Utils.getJustDate(getDataSelecionadaAgenda()));
								//getTable().setValue("horacons", getHorario());
								//getTable().setValue("diasema", diasema);
								
								setSimpleRecord(new SimpleRecord());
								getSimpleRecord().setValue("codimedi", getMedicoAgendaSelected().getCodiMedi());
								getSimpleRecord().setValue("desccodimedi", getMedicoAgendaSelected().getNome());
								getSimpleRecord().setValue("datacons", Utils.getJustDate(getDataSelecionadaAgenda()));
								getSimpleRecord().setValue("horacons", getHorario());
								getSimpleRecord().setValue("diasema", diasema);
								
								getRmFormWindow().close();
							}
						});
					}
				}
				resultSet.close();
				resultSet2.close();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				database.closeConnection();
			}
		}
		
		return verticalLayout;
	}
	
	public void updateHorariosMedicoSelected() {
		// Cria a relacao de horarios para o medico selecionado
		vlHorarios.removeAllComponents();
		vlHorarios.addComponent(montaAgenda(getMedicoAgendaSelected().getCodiMedi(), getTipoAtendimento(), getDataSelecionadaAgenda()));
		
		rmGrid.getTable().setFilter("uidmedico", getMedicoAgendaSelected().getUid());
		rmGrid.updateRecords();
		
	}
}
