package com.example.myapplication.view;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.KeyValue;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.ui.RecordButton;
import com.evolucao.rmlibrary.utils.Utils;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class AgendaFornecedorView extends VerticalLayout {
	HorizontalLayout buttonsNavigator = new HorizontalLayout();
	VerticalLayout vlHorarios = new VerticalLayout();
	
	Date dataInicialAgenda = new Date();
	Date dataSelecionadaAgenda = new Date();
	String horario = null;
	Integer codigoEspecialidade = null;
	Integer codigoMedico = null;
	Integer codigoFornecedor = null;
	String uidFornecedor = null;
	String nomeMedico = null;
	Boolean retorno = false;
	Double valorCusto = null;
	
	boolean showAgendamento = false;
	
	public void setShowAgendamento(boolean showAgendamento) {
		this.showAgendamento = showAgendamento;
	}
	public boolean getShowAgendamento() {
		return this.showAgendamento;
	}
	
	public void setUidFornecedor(String uidFornecedor) {
		this.uidFornecedor = uidFornecedor;
	}
	public String getUidFornecedor() {
		return this.uidFornecedor;
	}
	
	public void setValorCusto(Double valorCusto) {
		this.valorCusto = valorCusto;
	}
	public Double getValorCusto() {
		return this.valorCusto;
	}
	
	public void setCodigoFornecedor(Integer codigoFornecedor) {
		this.codigoFornecedor = codigoFornecedor;
	}
	public Integer getCodigoFornecedor() {
		return this.codigoFornecedor;
	}
	
	RmFormWindow rmFormWindow = null;
	Table table = null;
	
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
	
	public void setCodigoMedico(Integer codigoMedico) {
		this.codigoMedico = codigoMedico;
	}
	public Integer getCodigoMedico() {
		return this.codigoMedico;
	}
	
	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}
	public String getNomeMedico() {
		return this.nomeMedico;
	}
	
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
	
	public AgendaFornecedorView(Integer codigoEspecialidade, Integer codigoMedico, boolean showAgendamento) {
		this.setCodigoEspecialidade(codigoEspecialidade);
		this.setCodigoMedico(codigoMedico);
		this.setShowAgendamento(showAgendamento);
		
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
					Database database = ((ApplicationUI) UI.getCurrent()).database;
					//List<KeyValue> medicosList = new ArrayList<KeyValue>();
					List<SimpleRecord> medicosList = new ArrayList<SimpleRecord>();
					SimpleRecord medicoSelected = null;
					
					try {
						database.openConnection();
						String comando = "select espeforn.codiespe, fornecedores.uid as uidfornecedor, fornecedores.codiforn, fornecedores.descricao, fornecedores.codimedi, medicos.nome, espeforn.vlrcusto from espeforn";
						comando += " left join fornecedores on (fornecedores.uid=espeforn.uidfornecedor)";
						comando += " left join medicos on (medicos.codimedi=fornecedores.codimedi)";
						comando += " where (espeforn.codiespe='" + getCodigoEspecialidade() + "') and (fornecedores.situacao='A')";
						comando += " order by fornecedores.descricao";
						ResultSet resultSet = database.executeSelect(comando);
						while (resultSet.next()) {
							SimpleRecord simpleRecord = new SimpleRecord();
							simpleRecord.setValue("codimedi", resultSet.getInt("codimedi"));
							simpleRecord.setValue("descricao", resultSet.getString("descricao"));
							simpleRecord.setValue("uidfornecedor", resultSet.getString("uidfornecedor"));
							simpleRecord.setValue("codiforn", resultSet.getInt("codiforn"));
							simpleRecord.setValue("vlrcusto", resultSet.getDouble("vlrcusto"));
							medicosList.add(simpleRecord);
							
							if (getCodigoMedico()==null) {
								setCodigoMedico(resultSet.getInt("codimedi"));
								setNomeMedico(resultSet.getString("nome"));
								setUidFornecedor(resultSet.getString("uidFornecedor"));
								setCodigoFornecedor(resultSet.getInt("codiforn"));
								setValorCusto(resultSet.getDouble("vlrcusto"));
							}
							
							if (resultSet.getInt("codimedi")==getCodigoMedico()) {
								medicoSelected = simpleRecord;
								setNomeMedico(resultSet.getString("nome"));
								setUidFornecedor(resultSet.getString("uidfornecedor"));
								setCodigoFornecedor(resultSet.getInt("codiforn"));
								setValorCusto(resultSet.getDouble("vlrcusto"));
							}
						}
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
					finally {
						database.closeConnection();
					}
					
					ListSelect<SimpleRecord> lstMedicos = new ListSelect<SimpleRecord>(null, medicosList);
					if (medicoSelected!=null) {
						lstMedicos.select(medicoSelected);
					}
					lstMedicos.setWidth("100%");
					lstMedicos.setHeight("100%");
					vlMedicos.addComponent(lstMedicos);
					lstMedicos.setItemCaptionGenerator(new ItemCaptionGenerator<SimpleRecord>() {
						@Override
						public String apply(SimpleRecord item) {
							return item.getString("descricao");
						}
					});
			        lstMedicos.addValueChangeListener(new ValueChangeListener<Set<SimpleRecord>>() {
						private static final long serialVersionUID = 1L;
						
						@Override
						public void valueChange(com.vaadin.data.HasValue.ValueChangeEvent<Set<SimpleRecord>> event) {
							for (SimpleRecord simpleRecord : event.getValue()) {
								setCodigoMedico(simpleRecord.getInteger("codimedi"));
								setUidFornecedor(simpleRecord.getString("uidfornecedor"));
								setCodigoFornecedor(simpleRecord.getInteger("codiforn"));
								setNomeMedico(simpleRecord.getString("descricao"));
								setValorCusto(simpleRecord.getDouble("vlrcusto"));
								
								// Cria a relacao de horarios para o medico selecionado
								vlHorarios.removeAllComponents();
								vlHorarios.addComponent(montaAgenda(getCodigoMedico(), getDataSelecionadaAgenda()));
							}
						}
					});
				}
				
				/* Container com os horarios que o médico possui disponiveis */
				//vlHorarios.addStyleName("dados-container");
				vlHorarios.setMargin(false);
				vlHorarios.setHeight("100%");
				hl2.addComponent(vlHorarios);
				{
					vlHorarios.addComponent(this.montaAgenda(getCodigoMedico(), this.getDataSelecionadaAgenda()));
				}
			}
		}
		setExpandRatio(vl, 1);
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
								vlHorarios.addComponent(montaAgenda(getCodigoMedico(), getDataSelecionadaAgenda()));
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
								vlHorarios.addComponent(montaAgenda(getCodigoMedico(), getDataSelecionadaAgenda()));
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
	
	private boolean temVaga(Integer codigoMedico, Date data) {
	boolean retorno = false;
		Database database = ((ApplicationUI) UI.getCurrent()).database;
		
		try {
			database.openConnection();
			Integer diasemana = Utils.getDiaSemana(data);
			Integer numevagas = 0;
			Integer numeagendamentos = 0;
			
			String comando = "select sum(numeatend) as qtd from agenda ";
			comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
			comando += " where (medicos.codimedi=" + codigoMedico + ") and (diasemana="+ diasemana +")";
			ResultSet result = database.executeSelect(comando);
			if (result.next()) {
				numevagas = result.getInt("qtd");
			}
			result.close();

			if (numevagas>0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				result = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi='"+ codigoMedico +"') and (marccons.datacons='"+dateFormat.format(data)+"')");
				if (result.next()) {
					numeagendamentos = result.getInt("qtd");
				}
				
				if (numevagas>numeagendamentos) {
					retorno = true;
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
	
	public VerticalLayout montaAgenda(Integer codigoMedico, Date data) {
		VerticalLayout agendaMedica = new VerticalLayout();
		agendaMedica.setMargin(false);
		
		if (this.getShowAgendamento()) {
			Button btn2 = new Button("Enviar para agendamento");
			btn2.setWidth("100%");
			agendaMedica.addComponent(btn2);
			btn2.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if (getCodigoMedico()!=0) {
						getTable().setValue("codimedi", getCodigoMedico());
						getTable().setValue("desccodimedi", getNomeMedico());
						getTable().setValue("uidfornecedor", getUidFornecedor());
						getTable().setValue("codiforn", getCodigoFornecedor());
						getTable().setValue("vlrcusto", getValorCusto());
						
						getRmFormWindow().close();
					}
				}
			});
		}
		else {
			if (this.temAgenda(codigoMedico)) {
				if (this.temVaga(codigoMedico, data)) {
					agendaMedica = this.montaAgendaHorarios(agendaMedica, codigoMedico, data);
				}
				// Caso nao tenha vaga, procura a proxima data livre
				else {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(this.getDataSelecionadaAgenda());
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					Date dataDisponivel = calendar.getTime();
					
					while (!temVaga(this.getCodigoMedico(), dataDisponivel)) {
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						dataDisponivel = calendar.getTime();
					}
					
					agendaMedica.removeAllComponents();

					/*
					Button btn2 = new Button("Enviar para agendamento");
					btn2.setWidth("100%");
					agendaMedica.addComponent(btn2);
					btn2.addClickListener(new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							if (getCodigoMedico()!=0) {
								getTable().setValue("codimedi", getCodigoMedico());
								getTable().setValue("desccodimedi", getNomeMedico());
								getTable().setValue("uidfornecedor", getUidFornecedor());
								getTable().setValue("codiforn", getCodigoFornecedor());
								getTable().setValue("vlrcusto", getValorCusto());
								
								getRmFormWindow().close();
							}
						}
					});
					*/
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					RecordButton btn = new RecordButton("Disponível a partir de " + dateFormat.format(dataDisponivel));
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
								vlHorarios.addComponent(montaAgenda(getCodigoMedico(), getDataSelecionadaAgenda()));
							}
							else {
								setDataInicialAgenda(btn.getDate("dataDisponivel"));
								setDataSelecionadaAgenda(btn.getDate("dataDisponivel"));
								
								buttonsNavigator.removeAllComponents();
								buttonsNavigator.addComponent(getNavigatorButtons());
								
								vlHorarios.removeAllComponents();
								vlHorarios.addComponent(montaAgenda(getCodigoMedico(), getDataSelecionadaAgenda()));
							}
						}
					});
				}
			}
			else {
				Label lbl = new Label("Clinica não possui agenda.");
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
							getTable().setValue("uidfornecedor", getUidFornecedor());
							getTable().setValue("codiforn", getCodigoFornecedor());
							getTable().setValue("vlrcusto", getValorCusto());
							
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
					if (getCodigoMedico()!=0) {
						getTable().setValue("codimedi", getCodigoMedico());
						getTable().setValue("desccodimedi", getNomeMedico());
						getTable().setValue("uidfornecedor", getUidFornecedor());
						getTable().setValue("codiforn", getCodigoFornecedor());
						getTable().setValue("vlrcusto", getValorCusto());
						
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
				
				String comando = "select horainic, numeatend from agenda ";
				comando += " left join medicos on (medicos.uid=agenda.uidmedico)";
				comando += " where (medicos.codimedi=" + codigoMedico + ") and (diasemana="+ diasemana +")";
				ResultSet resultSet = database.executeSelect(comando);
				ResultSet resultSet2 = null;
				while (resultSet.next()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					resultSet2 = database.executeSelect("select count(*) as qtd from marccons where (marccons.codimedi="+ codigoMedico +") and (marccons.datacons='"+dateFormat.format(data)+"') and (marccons.horacons='" + resultSet.getString("horainic") + "')");
					if (resultSet2.next()) {
						numeagendamentos = resultSet2.getInt("qtd");
					}
					
					numevagas = resultSet.getInt("numeatend") - resultSet2.getInt("qtd");
					if (numevagas>0) {
						RecordButton button = new RecordButton();
						button.setWidth("100%");
						button.setCaption("Horario: " + resultSet.getString("horainic") + " - Vagas disponíveis: " + numevagas);
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
								
								getTable().setValue("codimedi", getCodigoMedico());
								getTable().setValue("desccodimedi", getNomeMedico());
								getTable().setValue("datacons", getDataSelecionadaAgenda());
								getTable().setValue("horacons", getHorario());
								getTable().setValue("diasema", diasema);
								
								getTable().setValue("uidfornecedor", getUidFornecedor());
								getTable().setValue("codiforn", getCodigoFornecedor());
								getTable().setValue("vlrcusto", getValorCusto());
								
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
}
