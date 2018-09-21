package com.evolucao.rmlibrary.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.enumerators.FieldType;
import com.evolucao.rmlibrary.database.enumerators.LoadType;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.database.enumerators.OnDelete;
import com.evolucao.rmlibrary.database.enumerators.ReferentialIntegrityType;
import com.evolucao.rmlibrary.database.enumerators.TableStatus;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.DirectProcessingEvent.DirectProcessingEventListener;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent;
import com.evolucao.rmlibrary.database.events.processing.ReverseProcessingEvent.ReverseProcessingEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterCheckTableEvent;
import com.evolucao.rmlibrary.database.events.table.AfterCheckTableEvent.AfterCheckTableEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterFilterEvent;
import com.evolucao.rmlibrary.database.events.table.AfterFilterEvent.AfterFilterEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterInsertEvent;
import com.evolucao.rmlibrary.database.events.table.AfterInsertEvent.AfterInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterPostEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostEvent.AfterPostEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterPostInsertEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostInsertEvent.AfterPostInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterPostUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterPostUpdateEvent.AfterPostUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.AfterUpdateEvent.AfterUpdateEventListener;
import com.evolucao.rmlibrary.database.events.table.BeforeLoadDataEvent;
import com.evolucao.rmlibrary.database.events.table.BeforeLoadDataEvent.BeforeLoadDataEventListener;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent;
import com.evolucao.rmlibrary.database.events.table.BeforePostEvent.BeforePostEventListener;
import com.evolucao.rmlibrary.database.events.table.ExternalInsertEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent;
import com.evolucao.rmlibrary.database.events.table.InitialValueEvent.InitialValueEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfDeleteEvent.SpecialConditionOfDeleteEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfInsertEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfInsertEvent.SpecialConditionOfInsertEventListener;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent;
import com.evolucao.rmlibrary.database.events.table.SpecialConditionOfUpdateEvent.SpecialConditionOfUpdateEventListener;
import com.evolucao.rmlibrary.database.model.GenericModel;
import com.evolucao.rmlibrary.utils.Utils;

public class Table {
	boolean managedTable = true;
	
	private String fTableName = null;
	private List<Field> ffields = new ArrayList<Field>();
	private String fprimaryKey = null;
	private List<Index> findexes = new ArrayList<Index>();
	private List<Filter> ffilters = new ArrayList<Filter>();
	private List<Filter> mainFilters = new ArrayList<Filter>();
	private List<Filter> filterUpdateDelete = new ArrayList<Filter>();
	private List<Processing> processing = null;

	private TableStatus tableStatus = TableStatus.NONE;
	TableStatus lastTableStatus = TableStatus.NONE;
	
	// Usado para ligar e desligar todas as validacoes e checagens antes de executar uma acao de post na tabela
	private boolean activeControl = true;  
	private boolean fauditing = true;
	private boolean externalInsertControlEnabled = false;
	
	private String frecordId = null;
	private Integer flastInsertId = 0;
	private String tableTitle = null;
	private Boolean eof = false;
	
	//private String lastInsertUUID; // UUID do ultimo registro incluido
	private String uidLastRecordProccessed = null;
	
	private Integer offset = 0; // Pagina que deve ser apresentada no select
	private Integer flimit = 0; // Tamanho da pagina no select
	private String fwhere = null;
	
	Integer totalRecordFromTable = 0; // Total de registros da tabela consiederando o where
	Integer totalPages = 0; // Numero total de paginas possiveis considerando o where

	private List<Record> records = null;
	private List<GenericModel> genericModels = null;

	//private HashMap<String, SimpleRecord> simpleRecordMap = null;
	private List<SimpleRecord> simpleRecordList = null;
	private SimpleRecord currentSimpleRecord = null;
	
	private Integer recordCountTable = 0; // Total de registros da tabela
	private Integer recordCountLoad = 0; // Total de registro do load
	private Integer recno = 0; // Numero do registro atual;
	
	private List<Join> joins = new ArrayList<Join>();
	private List<ExternalInsert> externalInserts = new ArrayList<ExternalInsert>();
	private List<ReferentialIntegrity> referentialIntegrity = new ArrayList<ReferentialIntegrity>();
	protected EventListenerList listenerList = new EventListenerList();
	
	private String order = null;

	private String ffieldsInSelect = null;
	private List<Field> fieldsInSelectList = null;
	
	// Caso o relacionamento com masterTable seja atravez de um masterFieldName que indica qual campo na tabela filho esta sendo armazenado
	// o uid do registro pai
	private Table masterTable = null;
	private String masterFieldName = null;
	
	// Caso o relacionamento com masterTable seja atravez de um masterIndexName e relationalship onde no masterIndexName é indicado o nome do 
	// indice da tabela pai que possui a relacao de campos de relacionamento e em relationalship esta armazenado a relacao de campos na tabela child
	// que estao se relacionando com o indice da tabela pai
	String masterIndexName = null;
	String relationalship = null;
	
	// Relacao de childrens desta tabela
	private List<TableChild> tableChildrenList = null;
	
	private Database database = null;
	private Statement fstatement = null;
	private ResultSet fresultSet = null;
	
	private LoadType loadType = LoadType.SIMPLERECORD;
	private boolean dataLoaded = false;
	
	boolean debugQuery = false;
	boolean loadingDataToForm = false;
	boolean hasProcessing = false; // Usado para indicar se a tabela possui processamento inverso ou direto
	boolean loadingOldValues = false; // Usado para indicar que deve ler e gravar informacoes nos campos como oldValues, geralmente usado para processamento inverso
	
	/**
	 * ManagedTable tem sua estrutura e conteudos criados e atualizados automaticamente dentro do banco de dados
	 * tabelas que serao usadas somente em memoria nao podem ser managed
	 * @param managedTable
	 */
	public void setManagedTable(boolean managedTable) {
		this.managedTable = managedTable;
	}
	public boolean getManagedTable() {
		return this.managedTable;
	}
	
	/**
	 * Usado para indicar que deve gravar ou ler dados dos campos em oldValue
	 */
	public void setLoadingOldValues(boolean loadingOldValues) {
		this.loadingOldValues = loadingOldValues;
	}
	public boolean getLoadingOldValues() {
		return this.loadingOldValues;
	}
	
	/**
	 * Usado para indicar se a tabela possui processamento Direto ou inveso, caso possua, guarda o valor de todos os campos no inicio de uma edicao, para poder usar essa informacao
	 * durante o processamento reverso no momento do post dos dados, é necessario que seja nesse momento para evitar a perca de informacoes que pode ocorrer se o processamento
	 * inverso for executado no inicio da edicao e o usuario abandonar a aplicacao antes de confirmar ou cancelar o processo
	 * @param hasProcessing
	 */
	public void setHasProcessing(boolean hasProcessing) {
		this.hasProcessing = hasProcessing;
	}
	public boolean getHasProcessing() {
		return this.hasProcessing;
	}
	public boolean hasProcessing() {
		return this.hasProcessing;
	}
	
	/*
	 * Usado para indicar que os dados estao sendo carregados do banco de dados para o formulario de edicao
	 * durante essa fase não deve ser executado nenhuma validacao, pois ela vai ser executada durante a edicao quando o usuario alterar o conteudo de um campo
	 * e durante o post dos dados de volta para o banco de dados.
	 */
	public void setLoadingDataToForm(Boolean loadingDataToForm) {
		this.loadingDataToForm = loadingDataToForm;
	}
	public boolean getLoadingDataToForm() {
		return this.loadingDataToForm;
	}

	public void setDebugQuery(boolean debugQuery) {
		this.debugQuery = debugQuery;
	}
	public boolean getDebugQuery() {
		return this.debugQuery;
	}
	
	public void setTotalRecordFromTable(Integer totalRecordFromTable) {
		this.totalRecordFromTable = totalRecordFromTable;
	}
	public Integer getTotalRecordFromTable() {
		return this.totalRecordFromTable;
	}
	
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getTotalPages() {
		return this.totalPages;
	}
	
	public void setFilterUpdateDelete(List<Filter> filterUpdteDelete) {
		this.filterUpdateDelete = filterUpdateDelete;
	}
	public List<Filter> getFilterUpdateDelete() {
		return this.filterUpdateDelete;
	}
	
	public void setDataLoaded(boolean dataLoaded) {
		this.dataLoaded = dataLoaded;
	}
	public boolean getDataLoaded() {
		return this.dataLoaded;
	}
	
	public void setActiveControl(boolean activeControl) {
		this.activeControl = activeControl;
	}
	public boolean getActiveControl() {
		return this.activeControl;
	}
	
	// Define a lista de processamento
	public void setProcessing(List<Processing> processing) {
		this.processing = processing;
	}
	public List<Processing> getProcessing() {
		// Cria a lista de processamentos se necessario
		this.createProcessingList();
		
		return this.processing;
	}
	/**
	 * Adiciona a tabela um novo processamento
	 */
	public void createProcessingList() {
		if (this.processing==null) {
			this.processing = new ArrayList<Processing>();
		}
	}
	
	public void addProcessing(Processing processing) {
		// Cria a lista de processamentos se necessario
		this.createProcessingList();
		
		this.processing.add(processing);
	}
	/*
	 * Cria um novo processamento e retorna ele para que seja configurado
	 */
	public Processing addProcessing() {
		// Cria a lista de processamentos se necessario
		this.createProcessingList();
		
		Processing processing = new Processing();
		this.processing.add(processing);
		return processing;
	}
	
	public void setLastTableStatus(TableStatus lastTableStatus) {
		this.lastTableStatus = lastTableStatus;
	}
	public TableStatus getLastTableStatus() {
		return this.lastTableStatus;
	}
	
	/*
	 * Define o status da tabela, INSERT, UPDATE, DELETE, SELECT, NONE
	 */
	public void setTableStatus(TableStatus tableStatus) {
		this.tableStatus = tableStatus;
	}
	public TableStatus getTableStatus() {
		return this.tableStatus;
	}
	
	
	public void setFieldsInSelectList(List<Field> fieldsInSelect) {
		this.fieldsInSelectList = fieldsInSelect;
	}
	public List<Field> getFieldsInSelectList() {
		return this.fieldsInSelectList;
	}
	
	
	public void setReferentialIntegrity(List<ReferentialIntegrity> referentialIntegrity) {
		this.referentialIntegrity = referentialIntegrity;
	}
	public List<ReferentialIntegrity> getReferentialIntegrity() {
		return this.referentialIntegrity;
	}
	public ReferentialIntegrity addReferentialIntegrity(String tableName, String foreingKey, ReferentialIntegrityType referentialIntegrityType, OnDelete onDelete) {
		ReferentialIntegrity referentialIntegrity = new ReferentialIntegrity(tableName, foreingKey, referentialIntegrityType, onDelete);
		this.getReferentialIntegrity().add(referentialIntegrity);
		
		return referentialIntegrity;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrder() {
		return this.order;
	}

	public void setMasterTable(Table masterTable) {
		this.masterTable = masterTable;
	}
	public Table getMasterTable() {
		return this.masterTable;
	}

	/**
	 * Define o nome do campo na tabela child que esta armazenando o uid do registro pai
	 * @param masterFieldName
	 */
	public void setMasterFieldName(String masterFieldName) {
		this.masterFieldName=masterFieldName;
		this.fieldByName(masterFieldName).setAllowLike(AllowLike.NONE);
	}
	/**
	 * Pega o nome do campo na tabela child que esta armazenando o uid da tabela pai
	 * @return
	 */
	public String getMasterFieldName() {
		return this.masterFieldName;
	}
	
	
	/**
	 * Define o nome do indice que existe na tabela pai que sera utilizado no relacionamento com a tabela child
	 * @param masterIndexName
	 */
	public void setMasterIndexName(String masterIndexName) {
		this.masterIndexName = masterIndexName;
	}
	/**
	 * Retorna o nome do indice que existe na tabela pai que sera utilizado no relacionamento com a tabela child
	 * é ncessario informar tambem a relacao de campos que existem no child que sera usado nesse relacionamento
	 * na propriedade relationalship
	 * @return
	 */
	public String getMasterIndexName() {
		return this.masterIndexName;
	}
	
	/**
	 * Define a relacao de campos presentes na tabela child que sera utilizado no relacionamento com a masterTable
	 * atravez do indice indicado na propriedade masterIndexName 
	 * @param relationalShip
	 */
	public void setRelationalship(String relationalship) {
		this.relationalship = relationalship;
	}
	public String getRelationalship() {
		return this.relationalship;
	}
	
	// Inicializa tableChildrenList aso seja necessario
	public void prepareTableChildrenList() {
		if (this.tableChildrenList==null) {
			this.tableChildrenList = new ArrayList<TableChild>();
		}
	}
	
	public void setTableChildrenList(List<TableChild> tableChildrenList) {
		this.tableChildrenList = tableChildrenList;
	}
	public List<TableChild> getTableChildrenList() {
		// Inicializa se necessario o tableChildrenList
		this.prepareTableChildrenList();
		
		return this.tableChildrenList;
	}
	/**
	 * Adiciona um tableChild na tabela relacionado o child atravez de um campo indicado em masterFieldName que vai conter o UID do registro da tabela pai
	 * @param table Nome do tableChild
	 * @param masterFieldName Nome do campo no tableChild que armazena o uid dos registros do tableMaster
	 * @param autoInsert Em edicao em formulario, apos o post de um insert de um registro pai, inicia automaticamente a inclusao de um registro filho
	 */
	public void addTableChild(Table table, String masterFieldName, boolean autoInsert) {
		// Inicializa se necessario o tableChildrenList
		this.prepareTableChildrenList();
		
		// Inclui um novo tableChild somente se ele já não estiver entre os childrens
		boolean found = false;
		for (TableChild tableChild: this.tableChildrenList) {
			if (tableChild.getTable().getTableName().equalsIgnoreCase(table.getTableName())) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			TableChild tableChild = new TableChild(table, masterFieldName, autoInsert);
			this.tableChildrenList.add(tableChild);
			
			// Caso o novo children nao tenha pai definido ou
			// Caso o pai seja outra tabela que nao esta que esta sendo definida agora como pai
			// Atualiza o pai da tabela children
			if ((table.getMasterTable()==null) || (!table.getMasterTable().getTableName().equalsIgnoreCase(this.getTableName()))) {
				table.setMasterTable(this);
				table.setMasterFieldName(masterFieldName);
			}
		}
	}
	
	/**
	 * Adiciona um tableChild relacionado atravez de um indice da tabela mastris com uma relacao de campos da tabela child
	 * @param table Nome da tabela que passa a ser child
	 * @param masterIndexName Nome do indice presente na tabela pai que vai ser usado no relacionamento com a tabela child
	 * @param relationalship Relacao de campos na tabela chidl que se relaciona com os campos do indice da tabela pai
	 * @param autoInsert
	 */
	public void addTableChild(Table table, String masterIndexName, String relationalship, boolean autoInsert) {
		// Inicializa se necessario o tableChildrenList
		this.prepareTableChildrenList();
		
		// Inclui um novo tableChild somente se ele já não estiver entre os childrens
		boolean found = false;
		for (TableChild tableChild: this.tableChildrenList) {
			if (tableChild.getTable().getTableName().equalsIgnoreCase(table.getTableName())) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			TableChild tableChild = new TableChild();
			tableChild.setTable(table);
			tableChild.setMasterIndexName(masterIndexName);
			tableChild.setRelationalship(relationalship);
			tableChild.setAutoInsert(autoInsert);
			this.tableChildrenList.add(tableChild);
			
			// Caso o novo children nao tenha pai definido ou
			// Caso o pai seja outra tabela que nao esta que esta sendo definida agora como pai
			// Atualiza o pai da tabela children
			if ((table.getMasterTable()==null) || (!table.getMasterTable().getTableName().equalsIgnoreCase(this.getTableName()))) {
				table.setMasterTable(this);
				table.setMasterIndexName(masterIndexName);
				table.setRelationalship(relationalship);
			}
		}
	}
	
	/**
	 * Retorna a tabela children que foi passada como parametro
	 * @param tableName Nome do tableChild desejado
	 * @return
	 */
	public Table getTableChildren(String tableName) {
		Table retorno = null;
		boolean found = false;
		for (TableChild tableChild: this.tableChildrenList) {
			if (tableChild.getTable().getTableName().equalsIgnoreCase(tableName)) {
				retorno = tableChild.getTable();
				found = true;
				break;
			}
		}

		if (!found) {
			throw new IllegalAccessError("Nao foi localizado o tableChild: " + tableName + "!");			
		}
		
		return retorno;
	}

	public void setFields(List<Field> fields) {
		this.ffields = fields;
	}
	public List<Field> getFields() {
		return this.ffields;
	}
	
	public void setPrimaryKey(String primaryKey) {
		this.fprimaryKey = primaryKey;
	}
	public String getPrimaryKey() {
		return this.fprimaryKey;
	}
	
	public void setIndexes(List<Index> indexes) {
		this.findexes = indexes;
	}
	public List<Index> getIndexes() {
		return this.findexes;
	}

	public void setFilters(List<Filter> filters) {
		if ((this.getTableStatus()==TableStatus.UPDATE) || (this.getTableStatus()==TableStatus.DELETE)) {
			this.filterUpdateDelete = filters;
		}
		else {
			this.ffilters = filters;
		}
	}
	public List<Filter> getFilters() {
		List<Filter> retorno = this.ffilters;
		if ((this.getTableStatus()==TableStatus.UPDATE) || (this.getTableStatus()==TableStatus.DELETE)) {
			retorno = this.filterUpdateDelete;
		}
		return retorno;
	}
	
	public void setMainFilters(List<Filter> mainFilters) {
		this.mainFilters = mainFilters;
	}
	public List<Filter> getMainFilters() {
		return this.mainFilters;
	}
	
	/**
	 * Liga ou desliga a atualizacao automatica das datas de inclusao e modificacao dos registros.
	 * @param auditing
	 */
	public void setAuditing(boolean auditing) {
		this.fauditing = auditing;
	}
	public boolean isAuditing() {
		return this.fauditing;
	}
	
	public void setWhere(String where) {
		this.fwhere = where;
	}
	public String getWhere() {
		return this.fwhere;
	}
	
	public void setRecordId(String recordId) {
		this.frecordId = recordId;
	}
	public String getRecordId() {
		return this.frecordId;
	}
	
	public void setLastInsertId(Integer lastInsertId) {
		this.flastInsertId = lastInsertId;
	}
	public Integer getLastInsertId() {
		return this.flastInsertId;
	}
	
	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
	public String getTableTitle() {
		return this.tableTitle;
	}
	
	public void setExternalInserts(List<ExternalInsert> externalInserts) {
		this.externalInserts = externalInserts; 
	}
	public List<ExternalInsert> getExternalInserts() {
		return this.externalInserts;
	}

	public void setTableName(String tableName) {
		this.fTableName = tableName;
	}
	public String getTableName() {
		return this.fTableName;
	}

	public String getSpecialTableName() {
		return this.fTableName.substring(0, 1).toUpperCase()+this.fTableName.substring(1);
	}

	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}
	public List<Join> getJoins() {
		return this.joins;
	}
	public Join addJoin(String targetTableName, String foreignKey, String relationship) {
		Join retorno = this.addJoin(targetTableName, foreignKey, relationship, targetTableName);
		return retorno;
	}
	public Join addJoin(String targetTableName, String foreignKey, String relationship, String alias) {
		Join retorno = new Join(targetTableName, foreignKey, relationship, alias);
		retorno.setSourceTable(this);
		this.getJoins().add(retorno);
		return retorno;
	}

	/*
	 * Armazena a relacao de campos incluidos apos o comando select <campos>
	 * from <tabela>
	 */
	public void setFieldsInSelect(String fieldsInSelect) {
		this.ffieldsInSelect = fieldsInSelect;
	}
	public String getFieldsInSelect() {
		return this.ffieldsInSelect;
	}

	/*
	 * O codigo a baixo parece nao estar mais sendo utilizado no vaadin, podendo ser removido no futuro
	 * 
	public void getConfigs(TableClient tableCommon) {
		this.setTableName(tableCommon.getTableName());
		this.setTableTitle(tableCommon.getTableTitle());
		this.setFields(tableCommon.getFields());
		this.setPrimaryKey(tableCommon.getPrimaryKey());
		this.setMainFilters(tableCommon.getMainFilters());
		this.setJoins(tableCommon.getJoins());
		this.setExternalInserts(tableCommon.getExternalInserts());
		this.setReferentialIntegrity(tableCommon.getReferentialIntegrity());
	}
	*/

	/*
	 * Caso o field exista, retorna ele, caso contrario retorna null
	 * @param fieldName nome do field
	 */
	public Field fieldExist(String fieldName) {
		Field retorno = null;
		for (Field field : this.getFields()) {
			String vfieldName = field.getFieldName();
			if (vfieldName.equals(fieldName)) {
				retorno = field;
				break;
			}
		}
		
		return retorno;
	}
	
	/*
	 * @param fieldName - nome do campo que deve ser procurado na tabela
	 * @return - retorno Field se encontrado
	 */
	public Field fieldByName(String fieldName) {
		Field retorno = null;
		for (Field field : this.getFields()) {
			String vfieldName = field.getFieldName();
			if (vfieldName.equals(fieldName)) {
				retorno = field;
				break;
			}
		}
		
		if (retorno==null) {
			throw new IllegalAccessError("Field " + fieldName + " does not exists in table " + this.getTableName());
		}
		
		return retorno;
	}
	
	/*
	 * Define que o campo permite like nas pesquisas
	 * @param fieldName - Nome do campo
	 * @param fieldType - tipo do campo: varchar, integer, int
	 * @param fieldSize - tamanho do campo
	 * @return - Retorna um field
	 */
	public void setAllowLike(String fieldName, AllowLike allowLikeIn) {
		this.fieldByName(fieldName).setAllowLike(allowLikeIn);
	}
	public AllowLike getAllowLike(String fieldName) {
		return this.fieldByName(fieldName).getAllowLike();
	}
	
	public Index addIndex(String indexName, String indexFields) {
		Index index = new Index(indexName, indexFields);
		this.getIndexes().add(index);
		return index;
	}

	public Index addIndex(String indexName, String indexFields, Boolean unique) {
		Index index = new Index(indexName, indexFields, unique);
		this.getIndexes().add(index);
		return index;
	}

	public Index addIndex(String indexName, String indexFields, Boolean unique,
			Boolean internal) {
		Index index = new Index(indexName, indexFields, unique, internal);
		this.getIndexes().add(index);
		return index;
	}
	
	public Field setAutoIncrement(String fieldName, boolean autoIncrement) {
		Field field = this.fieldByName(fieldName);
		field.setAutoIncrement(autoIncrement);
		return field;
	}

	public void setMainFilter(String fieldName, String value) {
		if ((Object) value != null) {
			if (!value.isEmpty()) {
				Filter filter = this.mainFilterByName(fieldName);
				if (filter == null) {
					filter = new Filter(fieldName, value);
					this.getMainFilters().add(filter);
				} else {
					filter.setString(value);
				}
			}
		}
	}

	public void setMainFilter(String fieldName, String value, String value2) {
		if ((Object) value != null) {
			if (!value.isEmpty()) {
				Filter filter = this.mainFilterByName(fieldName);
				if (filter == null) {
					filter = new Filter(fieldName, value, value2);
					this.getMainFilters().add(filter);
				} else {
					filter.setString(value);
					filter.setString2(value2);
				}
			}
		}
	}

	
	/*
	 * Limpa os dados dos campos e das filtragem na tabela
	 */
	public void CleanValues() {
		for (Field field : this.getFields()) {
			field.cleanValues();
		}

		this.setFilters(new ArrayList<Filter>());
	}

	public void select(String fields) {
		select(fields, true);
	}
	
	public void select(String fields, Boolean cleanValues) {
		this.setLastTableStatus(this.getTableStatus());
		this.setTableStatus(TableStatus.BROWSE);
		
		// Limpa os dados dos campos e das filtragens
		if (cleanValues) {
			this.CleanValues();
		}
		
		// Armazena a relacao de campos em uma string
		this.setFieldsInSelect(fields);
		this.setFieldsInSelectList(this.generateListOfFieldsInSelect());
		//System.out.println("tableStatus - select");
	}

	public Boolean insert() {
		Boolean retorno = true;
		if (this.beforeInsert()) {
			// Limpa os dados dos campos e das filtragens
			this.setLastTableStatus(this.getTableStatus());
			this.setTableStatus(TableStatus.INSERT);
			this.CleanValues();
			
			// Inicializa os campos que nao foram autoincremental ou data
			for (Field field: this.getFields()) {
				if ((!field.getAutoIncrement()) && (!field.getFieldName().equals("uid"))) {
					switch (field.getFieldType()) {
					case VARCHAR:
						field.setString("");
						break;
					case TEXT:
						field.setString("");
						break;
					case INTEGER:
						field.setInteger(0);
						break;
					case DOUBLE:
						field.setDouble(0d);
						break;
					case FLOAT:
						field.setFloat(0f);
						break;
					}
				}
			}
			
			this.setString("uid", UUID.randomUUID().toString().toUpperCase());
			this.updateMasterFieldOnInsert();
			
			// Define valores iniciais para os campos
			InitialValueEvent event = new InitialValueEvent(this);
			event.setTable(this);
			fireInitialValueEvent(event);
			
			AfterInsertEvent afterInsertEvent = new AfterInsertEvent(this);
			afterInsertEvent.setTable(this);
			fireAfterInsertEvent(afterInsertEvent);
		}
		else {
			retorno = false;
		}
		
		//System.out.println("tableStatus - Insert");
		return retorno;
	}

	public void update() {
		// Caso tabela tenha master em edicao, executa post no master
		// Verifica condicao especial de update
		// Retorna verdadeiro caso nao tenha encontrado problemas
		if (this.beforeUpdate()) {
			// Processamento reverso agora vai ser disparado no post do update
			// Executa processamento inverso
			//ReverseProcessingEvent reverseProcessingEvent = new ReverseProcessingEvent(this);
			//reverseProcessingEvent.setTable(this);
			//this.fireReverseProcessingEvent(reverseProcessingEvent);
			
			// Limpa os dados dos campos e das fitragens
			this.setLastTableStatus(this.getTableStatus());
			this.setTableStatus(TableStatus.UPDATE);
			this.CleanValues();
			
			//this.afterUpdate();
			//System.out.println("tableStatus " + this.getTableName() + " - update");
		};
	}

	public void delete() {
		this.beforeDelete();
		
		// Limpa os dados dos campos e das filtragens
		this.setLastTableStatus(this.getTableStatus());
		this.setTableStatus(TableStatus.DELETE);
		this.CleanValues();
		//System.out.println("tableStatus " + this.getTableName() + " - delete");
	}
	
	public Boolean filter() {
		return this.filter(true);
	}
	
	public Boolean filter(Boolean cleanValues) {
		this.setLastTableStatus(this.getTableStatus());
		this.setTableStatus(TableStatus.FILTER);
		if (cleanValues) {
			this.CleanValues();
		}
		//System.out.println("tableStatus " + this.getTableName() + " - filter");

		AfterFilterEvent afterFilterEvent = new AfterFilterEvent(this);
		afterFilterEvent.setTable(this);
		this.fireAfterFilterEvent(afterFilterEvent);
		return true;
	}
	
	public void cancel() {
		this.setTableStatus(this.getLastTableStatus());
		this.setLastTableStatus(TableStatus.NONE);
		//System.out.println("tableStatus " + this.getTableName() + " - cancel");
	}
	
	public Filter mainFilterByName(String fieldName) {
		Filter retorno = null;
		for (Filter filter : this.getMainFilters()) {
			String vfieldName = filter.getFieldName();
			if (vfieldName.equals(fieldName)) {
				retorno = filter;
				break;
			}
		}
		return retorno;
	}

	/*
	 * Configura todos os campos para permitirem seus conteudos em branco ou vazio
	 */
	/*
	public void setAllAllowBlank(boolean allowBlank) {
		for (Field field: this.getFields()) {
			field.setAllowBlank(allowBlank);
		}
	}
	*/

	/*
	 * Define se deve estar ligado ou nao o controle de inclucoes externas, quando este recurso estiver ligado
	 * campos de controle SourceTableName e SourceUid serao criados na tabela 
	 */
	public void setExternalInsertControlEnabled(boolean externalInsertControl) {
		this.externalInsertControlEnabled = externalInsertControl;
		this.addFieldsControl();
	}
	public boolean getExternalInsertControlEnabled() {
		return this.externalInsertControlEnabled;
	}

	public void setDatabase(Database database) {
		this.database = database;
		/*
		if (database!=null) {
			database.addTable(this);
		}
		*/
	}
	public Database getDatabase() {
		return this.database;
	}

	public Table() {
		this.addFieldsControl();
	}
	
	public Table(Database database) {
		this.addFieldsControl();
		setDatabase(database);
	}
	
	public Table(Database database, String tableName) {
		this.database = database;
		this.setTableName(tableName);
	}

	public void setPassword(String fieldName, boolean password) {
		this.fieldByName(fieldName).setPassword(password);
	}
	public boolean getPassword(String fieldName) {
		return this.fieldByName(fieldName).getPassword();
	}
	
	public void setInternalSearch(String fieldName, InternalSearch internalSearch) {
		this.fieldByName(fieldName).setInternalSearch(internalSearch);
	}
	
	public void setRequired(String fieldName, boolean required) {
		this.fieldByName(fieldName).setRequired(required);
	}

	public void setMask(String fieldName, String mask) {
		this.fieldByName(fieldName).setMask(mask);
	}

	/*
	public void setSimpleRecordMap(HashMap<String, SimpleRecord> simpleRecords) {
		this.simpleRecordMap = simpleRecords;
	}
	public HashMap<String, SimpleRecord> getSimpleRecordMap() {
		return this.simpleRecordMap;
	}
	*/
	
	public void setSimpleRecordList(List<SimpleRecord> simpleRecordList) {
		this.simpleRecordList = simpleRecordList;
	}
	public List<SimpleRecord> getSimpleRecordList() {
		return this.simpleRecordList;
	}
	
	/*
	public void setLastInsertUUID(String uuid) {
		this.lastInsertUUID = uuid;
	}
	public String getLastInsertUUID() {
		return this.lastInsertUUID;
	}
	*/
	
	public void setUidLastRecordProccessed(String uidLastRecordProccessed) {
		this.uidLastRecordProccessed = uidLastRecordProccessed;
	}
	public String getUidLastRecordProccessed() {
		return this.uidLastRecordProccessed;
	}
	
	public void setEof(Boolean eof) {
		this.eof = eof;
	}
	public Boolean getEof() {
		return this.eof;
	}
	public Boolean eof() {
		return this.eof;
	}
	
	public void setLoadType(LoadType loadType) {
		this.loadType = loadType;
	}
	public LoadType getLoadType() {
		return this.loadType;
	}
	
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public List<Record> getRecords() {
		return this.records;
	}
	
	public Record getRecord() {
		return this.getRecords().get(this.getRecno());
	}
	
	public void setGenericModels(List<GenericModel> genericModels) {
		this.genericModels = genericModels;
	}
	public List<GenericModel> getGenericModels() {
		return this.genericModels;
	}
	
	public void setOffSet(Integer offSet) {
		this.offset = offSet;
	}
	public Integer getOffSet() {
		return this.offset;
	}
	
	public void setRecordCountTable(Integer recordCountTable) {
		this.recordCountTable = recordCountTable;
	}
	public Integer getRecordCountTable() {
		return this.recordCountTable;
	}
	
	public void setRecordCountLoad(Integer recordCountLoad) {
		this.recordCountLoad = recordCountLoad;
	}
	public Integer getRecordCountLoad() {
		return this.recordCountLoad;
	}
	
	public void setRecno(Integer recno) {
		this.recno = recno;
	}
	public Integer getRecno() {
		return this.recno;
	}

	public void setStatement(Statement statement) {
		this.fstatement = statement;
	}

	public Statement getStatement() {
		return this.fstatement;
	}

	public void setResultSet(ResultSet resultSet) {
		this.fresultSet = resultSet;
	}

	public ResultSet getResultSet() {
		return this.fresultSet;
	}

	public void setLimit(Integer limit) {
		this.flimit = limit;
	}
	public Integer getLimit() {
		return this.flimit;
	}
	
	public void setCurrentSimpleRecord(SimpleRecord simpleRecord) {
		this.currentSimpleRecord = simpleRecord;
	}
	
	public SimpleRecord getCurrentSimpleRecord() {
		return this.currentSimpleRecord;
	}

	public List<SimpleRecord> loadData() {
		return loadData(null);
	}
	
	public List<SimpleRecord> loadData(String uidRecord) {
		BeforeLoadDataEvent beforeLoadDataEvent = new BeforeLoadDataEvent(this);
		beforeLoadDataEvent.setTable(this);
		this.fireBeforeLoadDataEvent(beforeLoadDataEvent);
		if (!beforeLoadDataEvent.getCancel()) {
			//Long startTime = System.currentTimeMillis();
			
	        boolean closeConnection = false;
	        
	        if (uidRecord==null) {
	            if (this.loadType==LoadType.SIMPLERECORD) {
	            	//this.simpleRecordMap = new HashMap<String, SimpleRecord>();
	            	this.simpleRecordList = new ArrayList<SimpleRecord>();
	            }
	            else if (this.loadType==LoadType.RECORDDATA) {
	            	this.records = new ArrayList<Record>();
	            }
	        }
	        
	        try {
	        	if (this.getDatabase()==null) {
	    			throw new IllegalAccessError("Table "+ this.getTableName()+" não possui banco de dados definido.");
	        	}
	        	
	        	if ((this.getFieldsInSelect()==null) || (this.getFieldsInSelect().isEmpty())) {
	    			throw new IllegalAccessError("Não foi definido lista de campos para o select na tabela "+ this.getTableName() + "!"); 
	        	}

	        	
	        	// Sinaliza q eu abri o banco de dados, entao tenho de fechar ele apos o processamento
	            if ((this.getDatabase().getConnection() == null) ||
	                (this.getDatabase().getConnection().isClosed())) {
	            	this.getDatabase().openConnection();
	                closeConnection = true;
	            }
	            
	            ResultSet resultSet = this.open(uidRecord);
	            
	            while (resultSet.next()) {
	        		SimpleRecord record = this.loadSimpleRecordFromResultSet(resultSet);
	        		this.getSimpleRecordList().add(record);
	            }
	            
	            this.updateTotalPages();
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        finally {
	        	// Caso eu tenha aberto o banco de dados, fecha ele agora
	            if (closeConnection) {
	            	this.getDatabase().closeConnection();
	            }
	        }
	        
	        this.dataLoaded = true;
	        this.executeAfterLoadData();
	        
	        //startTime = System.currentTimeMillis()-startTime;
	        //System.out.println("Tempo de execucao do Load da tabela: " + this.getTableName() + " delayed: " + startTime);
		}
		
        return this.getSimpleRecordList();
    }
	
	public void updateTotalPages() {
        // Atualizando os totais de registros presentes na tabela
		this.setTotalRecordFromTable(this.getRecordCountFromTable());
		
		Integer totalPaginas = 0;
		Double records = this.getTotalRecordFromTable() + 0d;
		Integer limit = this.getLimit();
		if (limit>0) {
    		Double paginas = (double) (records/limit) + .0d;
    		Double paginasAbs = (double) Math.round(paginas);
    		if (paginasAbs<paginas) {
    			paginasAbs++;
    		}
    		totalPaginas = (int) Math.abs(paginasAbs);
		}
		else {
			if (records>0) {
				totalPaginas = 1;
			}
		}
		this.setTotalPages(totalPaginas);
	}
	
	private SimpleRecord loadSimpleRecordFromResultSet(ResultSet resultSet) {
		SimpleRecord simpleRecord = new SimpleRecord();
		
		try {
			for (Field field: this.getFieldsInSelectList()) {
				FieldType fieldType = field.getFieldType();

				switch (fieldType) {
				case VARCHAR:
					if (resultSet.getString(field.getFieldName())==null) {
						simpleRecord.setString(field.getFieldName(), "");
					}
					else {
						simpleRecord.setString(field.getFieldName(), resultSet.getString(field.getFieldName()));
					}
					break;
				case TEXT:
					simpleRecord.setString(field.getFieldName(), resultSet.getString(field.getFieldName()));
					break;
				case INTEGER:
					simpleRecord.setInteger(field.getFieldName(), resultSet.getInt(field.getFieldName()));
					break;
				case DOUBLE:
					simpleRecord.setDouble(field.getFieldName(), resultSet.getDouble(field.getFieldName()));
					break;
				case FLOAT:
					simpleRecord.setFloat(field.getFieldName(), resultSet.getFloat(field.getFieldName()));
					break;
				case DATE:
					simpleRecord.setDateTime(field.getFieldName(), resultSet.getDate(field.getFieldName()));
					break;
				case DATETIME:
					//simpleRecord.setDateTime(field.getFieldName(), resultSet.getDate(field.getFieldName()));
					Timestamp timestamp = resultSet.getTimestamp(field.getFieldName());
					if (timestamp != null) {
						Date date = new Date(timestamp.getTime());
						simpleRecord.setDateTime(field.getFieldName(), date);
					}
					else {
						simpleRecord.setDateTime(field.getFieldName(), null);
					}
					
					break;
				case BOOLEAN:
					simpleRecord.setBoolean(field.getFieldName(), resultSet.getBoolean(field.getFieldName()));
					break;
				}
			}

			/*
			// Inclui o uid do registro imediatamente
			simpleRecord.setValue("uid", resultSet.getString("uid"));
			
			String campos = this.getFieldsInSelectOnlyFieldsName() + ",";
			campos = campos.replace(" ", "");
			while (campos.indexOf(",")>0) {
				String campo = campos.substring(0, campos.indexOf(","));
				campos = campos.substring(campos.indexOf(",")+1);
				
				Field field = this.fieldByName(campo);
				FieldType fieldType = field.getFieldType();
				
				if (fieldType.equals(FieldType.VARCHAR)) {
					if (resultSet.getString(field.getFieldName())==null) {
						simpleRecord.setString(campo, "");
					}
					else {
						simpleRecord.setString(campo, resultSet.getString(field.getFieldName()));
					}
				} else if (fieldType.equals(FieldType.TEXT)) {
					simpleRecord.setString(campo, resultSet.getString(field.getFieldName()));
				} else if (fieldType.equals(FieldType.INTEGER)) {
					simpleRecord.setInteger(campo, resultSet.getInt(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DOUBLE)) {
					simpleRecord.setDouble(campo, resultSet.getDouble(field.getFieldName()));
				} else if (fieldType.equals(FieldType.FLOAT)) {
					simpleRecord.setFloat(campo, resultSet.getFloat(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DATETIME)) {
					simpleRecord.setDateTime(campo, resultSet.getDate(field.getFieldName()));
				} else if (fieldType.equals(FieldType.BOOLEAN)) {
					simpleRecord.setBoolean(campo, resultSet.getBoolean(field.getFieldName()));
				} else if (fieldType.equals(FieldType.BIT)) {
					simpleRecord.setBoolean(campo, resultSet.getBoolean(field.getFieldName()));
				}
			}
			*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return simpleRecord;
	}

	private Record loadRecordFromResultSet(ResultSet resultSet) {
		Record record = new Record(this);

		try {
			String campos = this.getFieldsInSelectOnlyFieldsName() + ",";
			campos = campos.replace(" ", "");
			while (campos.indexOf(",")>0) {
				String campo = campos.substring(0, campos.indexOf(","));
				campos = campos.substring(campos.indexOf(",")+1);
				
				Field field = this.fieldByName(campo);
				FieldType fieldType = field.getFieldType();
				
				if (fieldType.equals(FieldType.VARCHAR)) {
					if (resultSet.getString(field.getFieldName())==null) {
						record.setString(campo, "");
					}
					else {
						record.setString(campo, resultSet.getString(field.getFieldName()));
					}
				} else if (fieldType.equals(FieldType.TEXT)) {
					record.setString(campo, resultSet.getString(field.getFieldName()));
				} else if (fieldType.equals(FieldType.INTEGER)) {
					record.setInteger(campo, resultSet.getInt(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DOUBLE)) {
					record.setDouble(campo, resultSet.getDouble(field.getFieldName()));
				} else if (fieldType.equals(FieldType.FLOAT)) {
					record.setFloat(campo, resultSet.getFloat(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DATE)) {
					record.setDateTime(campo, resultSet.getDate(field.getFieldName()));
				} else if (fieldType.equals(FieldType.DATETIME)) {
					record.setDateTime(campo, resultSet.getDate(field.getFieldName()));
				} else if (fieldType.equals(FieldType.BOOLEAN)) {
					record.setBoolean(campo, resultSet.getBoolean(field.getFieldName()));
				} else if (fieldType.equals(FieldType.BIT)) {
					record.setBoolean(campo, resultSet.getBoolean(field.getFieldName()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return record;
	}
	
	/*
	 * Bloco aparentemente nao esta sendo utilizado
	public List<GenericModel> loadGenericData() {
        boolean closeConnection = false;
        this.getRecords().clear();
        
        try {
        	// Sinaliza q eu abri o banco de dados, entao tenho de fechar ele apos o processamento
            if ((this.getDatabase().getConnection() == null) ||
                (this.getDatabase().getConnection().isClosed())) {
            	this.getDatabase().openConnection();
                closeConnection = true;
            }
            ResultSet resultSet = this.open();
            while (resultSet.next()) {
            	GenericModel genericModel = this.loadGenericModelFromResultSet(resultSet);
            	this.getGenericModels().add(genericModel);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        	// Caso eu tenha aberto o banco de dados, fecha ele agora
            if (closeConnection) {
            	this.getDatabase().closeConnection();
            }
        }

        this.loadType = LoadType.GENERICDATA;
        this.executeAfterLoadData();
        return this.getGenericModels();
    }
    */

	/*
	 * Bloco aparentemente nao esta sendo utilizado
	private GenericModel loadGenericModelFromResultSet(ResultSet resultSet) {
		GenericModel genericModel = new GenericModel();

		try {
			String campos = this.getFieldsInSelectOnlyFieldsName() + ",";
			campos = campos.replace(" ", "");
			while (campos.indexOf(",")>0) {
				String campo = campos.substring(0, campos.indexOf(","));
				campos = campos.substring(campos.indexOf(",")+1);
				
				Field field = this.fieldByName(campo);
				FieldType fieldType = field.getFieldType();
				
				switch (fieldType) {
				case VARCHAR:
					genericModel.setString(field.getAlias(), resultSet.getString(field.getFieldName()));
					break;
				case TEXT:
					genericModel.setString(field.getAlias(), resultSet.getString(field.getFieldName()));
					break;
				case INTEGER:
					genericModel.setInteger(field.getAlias(), resultSet.getInt(field.getFieldName()));
					break;
				case DOUBLE:
					genericModel.setDouble(field.getAlias(), resultSet.getDouble(field.getFieldName()));
					break;
				case FLOAT:
					genericModel.setFloat(field.getAlias(), resultSet.getFloat(field.getFieldName()));
					break;
				case DATETIME:
					genericModel.setDate(field.getAlias(), resultSet.getDate(field.getFieldName()));
					break;
				case BOOLEAN:
					genericModel.setBoolean(field.getAlias(), resultSet.getBoolean(field.getFieldName()));
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return genericModel;
	}
	*/
	

	/*
	 * Usado para ligar e desligar a atualizacao automatica e interna dos campos
	 * que registram a inclusao e modificacao dos registros
	 */

	public void setToolTip(String fieldName, String toolTip) {
		Field field = fieldByName(fieldName);
		field.setToolTip(toolTip);
	}

	public Filter filterByName(String fieldName) {
		Filter retorno = null;
		for (Filter filter : this.getFilters()) {
			String vfieldName = filter.getFieldName();
			if (vfieldName.equals(fieldName)) {
				retorno = filter;
				break;
			}
		}
		return retorno;
	}

	public void checkTable() {
		try {
			String comando = "SHOW TABLES LIKE '"+this.getTableName()+"'";
			ResultSet resultSet = this.getDatabase().executeSelect(comando);
			if (!resultSet.next()) {
				this.createTable();
			}
			else {
				this.alterTable();
			}
			
			AfterCheckTableEvent event = new AfterCheckTableEvent(this);
			event.setTable(this);
			this.fireAfterCheckTableEvent(event);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// private static final Logger log = Logger.getLogger(ClassName.class.getName());
	// log.log(Level.SEVERE, "comando errado" + e.getMessage(), e);
	public Boolean createTable() {
        boolean closeConnection = false;
		boolean vretorno = false;
		String comando = "";
		
		if (this.getManagedTable()) {
			/*
			 * try { this.getDatabase().executeCommand("teste"); } catch (Exception
			 * e) { log.log(Level.SEVERE, "comando errado" + e.getMessage(), e); }
			 */

			/*
			 * Validacoes para poder executar o comando
			 */
			if (this.getTableName()==null) {
				throw new IllegalAccessError("Error on createTable, TableName is missing.");
			}
			if (this.getPrimaryKey()==null) {
				throw new IllegalAccessError("Error on createTable " + this.getTableName() + ", Primary Key is missing.");
			}
			if (this.getDatabase()==null) {
				throw new IllegalAccessError("Error on createTable " + this.getTableName() + ", Database is missing.");
			}
			try {
				if (this.getDatabase().getConnection().isClosed()) {
					throw new IllegalAccessError("Error on createTable " + this.getTableName() + ", Database is not connected.");
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
			for (Field field : this.getFields()) {
				if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
					if (!comando.isEmpty()) {
						comando += ", ";
					}

					comando += field.getFieldName() + " ";

					switch (field.getFieldType()) {
					case VARCHAR:
						comando += "varchar(" + field.getFieldSize() + ")";
						break;
					case TEXT:
						comando += "text";
						break;
					case INTEGER:
						comando += "int";
						break;
					case DOUBLE:
						comando += "double";
						break;
					case FLOAT:
						comando += "float";
						break;
					case DATE:
						comando += "datetime";
						break;
					case DATETIME:
						comando += "datetime";
						break;
					case BOOLEAN:
						comando += "bit";
						break;
					case BIT:
						comando += "bit";
						break;
					default:
						String mensagemErro = "Erro na rotina: createTable() da tabela "
								+ this.getTableName()
								+ ",  FieldType "
								+ field.getFieldType()
								+ " para o campo "
								+ field.getFieldName() + " Ã© desconhecido!";
						System.out.println(mensagemErro);
						throw new IllegalAccessError(mensagemErro);
					}

					if (field.getAutoIncrement()) {
						comando += " auto_increment";
					}
				}
			}

			// geracao do comando para criar a tabela
			comando = "create table " + this.getTableName() + " (" + comando + ", primary key (" + this.getPrimaryKey() + ")) engine = myisam;";

			if (this.getDatabase()==null) {
				throw new IllegalAccessError("Erro na rotina open da tabela: " + this.getTableName() + " - Database is missing.");
			}
			
			try {
	            // Criando a tabela
				try {
					this.getDatabase().executeCommand(comando);
					vretorno = true;
				}
				catch (Exception e) {
					System.out.println("Table: " + this.getTableName() + " : Erro na rotina createTable - comando: " + comando + " - Mensagem de erro gerada pelo sistema: " + e.getMessage());
				}

				// Criando indices
				try {
					for (Index index : this.getIndexes()) {
						comando = "create index " + index.getIndexName() + " on "
								+ this.getTableName() + " (" + index.getIndexFields()
								+ ")";
						this.getDatabase().executeCommand(comando);
					}
				} catch (Exception e) {
					System.out.println("Table: " + this.getTableName() + " : Erro na rotina createTable ao criar os indices - comando: " + comando + " - Mensagem de erro gerada pelo sistema: " + e.getMessage());
				}
			} catch (Exception e) {
				System.out.println("Table: " + this.getTableName() + " : Erro na rotina createTable - Message: " + e.getMessage());
			}
		}

		return vretorno;
	}

	public Boolean dropTable() throws Exception {
		boolean vretorno = false;

		String comando = "drop table " + this.getTableName();

		Database database = new Database();
		try {
			database.executeCommand(comando);
			vretorno = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return vretorno;
	}

	public Boolean alterTable() {
		Boolean retorno = false;
		
		if (this.getManagedTable()) {
			String comando = "show columns from " + this.getTableName();
			Table TblEstrutura = new Table();
			TblEstrutura.getFields().clear();

			try {
				//this.getDatabase().openConnection();
				ResultSet rs = this.getDatabase().executeSelect(comando);
				while (rs.next()) {
					String vfieldTypeCurrent = null;
					FieldType fieldType = null;
					Integer vfieldLengthCurrent = 0;

					vfieldTypeCurrent = rs.getString("type");
					if (vfieldTypeCurrent.contains("varchar")) {
						vfieldLengthCurrent = Integer.valueOf(vfieldTypeCurrent.substring(vfieldTypeCurrent.indexOf("(") + 1,vfieldTypeCurrent.indexOf(")")));
						fieldType = FieldType.VARCHAR;
					} else if (vfieldTypeCurrent.contains("text")) {
						fieldType = FieldType.TEXT;
						vfieldLengthCurrent = 10;
					} else if (vfieldTypeCurrent.contains("int")) {
						fieldType = FieldType.INTEGER;
						vfieldLengthCurrent = 10;
					} else if (vfieldTypeCurrent.contains("double")) {
						fieldType = FieldType.DOUBLE;
						vfieldLengthCurrent = 10;
					} else if (vfieldTypeCurrent.contains("float")) {
						fieldType = FieldType.FLOAT;
						vfieldLengthCurrent = 10;
					} else if (vfieldTypeCurrent.contains("date")) {
						fieldType = FieldType.DATE;
						vfieldLengthCurrent = 10;
					} else if (vfieldTypeCurrent.contains("datetime")) {
						fieldType = FieldType.DATETIME;
						vfieldLengthCurrent = 10;
					} else if (vfieldTypeCurrent.contains("bit")) {
						fieldType = FieldType.BOOLEAN;
						vfieldLengthCurrent = 10;
					}

					TblEstrutura.addField(rs.getString("field"), fieldType, vfieldLengthCurrent);
				}
			} catch (Exception e) {
				System.out.println("Erro no metodo alterTable(), Mensagem: "
						+ e.getMessage());
			} finally {
				//this.getDatabase().closeConnection();
			}

			
			this.processFields();

			comando = "";
			// Monta o comando de alteracao
			for (Field field : this.getFields()) {
				Boolean vexecutaComando = false;
				// Caso o campo exista

				if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
					//Field currentField = TblEstrutura.fieldByName(field.getFieldName());
					
					Field currentField = TblEstrutura.fieldExist(field.getFieldName());
				
					// Caso o campo exista na estrutura atual, verifica seu tipo e
					// tamanho
					if (currentField != null) {
						// Verifica o tipo e o tamanho
						if ((currentField.getFieldType() != field.getFieldType())
								|| (currentField.getFieldSize() != field.getFieldSize()) || (currentField.getAutoIncrement()) ) {
							vexecutaComando = true;
							if (field.getFieldType().equals(FieldType.VARCHAR)) {
								comando = "alter table " + this.getTableName()
										+ " modify " + field.getFieldName()
										+ " varchar(" + field.getFieldSize() + ")";
							} else {
								comando = "alter table " + this.getTableName()
										+ " modify " + field.getFieldName() + " "
										+ field.getFieldType();
							}
							
							if (field.getAutoIncrement()) {
								comando += " auto_increment";
							}
						}
					}
					// caso o campo nao exista
					else {
						vexecutaComando = true;
						if (field.getFieldType().equals(FieldType.VARCHAR)) {
							comando = "alter table " + this.getTableName() + " add "
									+ field.getFieldName() + " varchar("
									+ field.getFieldSize() + ")";
						} else {
							comando = "alter table " + this.getTableName() + " add "
									+ field.getFieldName() + " " + field.getFieldType();
						}
						
						if (field.getAutoIncrement()) {
							comando += " auto_increment";
						}
					}
				}

				if (vexecutaComando) {
					try {
						//this.getDatabase().openConnection();
						this.getDatabase().executeCommand(comando);
					} catch (Exception e) {
						System.out.println("Erro no metodo alterTable(), Comando: "
								+ comando + " Mensagem de erro do sistema: "
								+ e.getMessage());
					} finally {
						//this.getDatabase().closeConnection();
					}
				}
			}
			
			try {
				//this.getDatabase().openConnection();
				for (Index index : this.getIndexes()) {
					comando = "show index from " + this.getTableName() + " where Key_name='" + index.getIndexName() + "'";
					ResultSet resultSet = this.getDatabase().executeSelect(comando);
					if (!resultSet.next()) {
						comando = "create index " + index.getIndexName() + " on "
								+ this.getTableName() + " (" + index.getIndexFields()
								+ ")";
						try {
						this.getDatabase().executeCommand(comando);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Erro no metodo createTable(), Mensagem: "
						+ e.getMessage());
			} finally {
				//this.getDatabase().closeConnection();
			}
		}

		return retorno;
	}

	public void setString(String fieldName, String value) {
		this.fieldByName(fieldName).setString(value);
	}
	public String getString(String fieldName) {
		String retorno = null;
		retorno=this.fieldByName(fieldName).getString();
		return retorno;
	}

	public void setInteger(String fieldName, Integer value) {
		this.fieldByName(fieldName).setInteger(value);
	}
	public Integer getInteger(String fieldName) {
		Integer retorno = null;
		retorno=this.fieldByName(fieldName).getInteger();
		return retorno;
	}

	public void setDouble(String fieldName, Double value) {
		this.fieldByName(fieldName).setDouble(value);
	}
	public Double getDouble(String fieldName) {
		Double retorno = null;
		retorno = this.fieldByName(fieldName).getDouble();
		return retorno;
	}

	public void setFloat(String fieldName, Float value) {
		this.fieldByName(fieldName).setFloat(value);
	}
	public Float getFloat(String fieldName) {
		Float retorno = null;
		retorno = this.fieldByName(fieldName).getFloat();
		return retorno;
	}

	public void setDate(String fieldName, Date value) {
		this.fieldByName(fieldName).setDate(value);
	}
	public Date getDate(String fieldName) {
		Date retorno = null;
		retorno=this.fieldByName(fieldName).getDate();
		return retorno;
	}

	public void setDateTime(String fieldName, Date value) {
		this.fieldByName(fieldName).setDate(value);
	}
	public Date setDateTime(String fieldName) {
		Date retorno = null;
		retorno = this.fieldByName(fieldName).getDate();
		return retorno;
	}

	public void setBoolean(String fieldName, Boolean value) {
		this.fieldByName(fieldName).setBoolean(value);
	}
	public Boolean getBoolean(String fieldName) {
		Boolean retorno = null;
		retorno = this.fieldByName(fieldName).getBoolean();
		return retorno;
	}

	// ***********************
	public void setValue(String fieldName, String value) {
		this.setString(fieldName, value);
	}

	public void setValue(String fieldName, Integer value) {
		this.setInteger(fieldName, value);
	}

	public void setValue(String fieldName, Double value) {
		this.setDouble(fieldName, value);
	}

	public void setValue(String fieldName, Float value) {
		this.setFloat(fieldName, value);
	}

	public void setValue(String fieldName, Date value) {
		this.setDate(fieldName, value);
	}

	public void setValue(String fieldName, Boolean value) {
		this.setBoolean(fieldName, value);
	}

	// *****************************
	public void setFilter(String fieldName, String value) {
		if ((Object) value != null) {
			if (!value.isEmpty()) {
				Field field = this.fieldByName(fieldName);
				if (field==null) {
					throw new IllegalAccessError("Erro in setFilter: Field " + fieldName +" not exists in table " + this.getTableName());
				}

				Filter filter = this.filterByName(fieldName);
				if (filter == null) {
					filter = new Filter(fieldName, value);
					this.getFilters().add(filter);
				} else {
					filter.setString(value);
				}
			}
		}
	}

	public void setFilter(String fieldName, String value, String value2) {
		if ((Object) value != null) {
			if (!value.isEmpty()) {
				Filter filter = this.filterByName(fieldName);
				if (filter == null) {
					filter = new Filter(fieldName, value, value2);
					this.getFilters().add(filter);
				} else {
					filter.setString(value);
					filter.setString2(value2);
				}
			}
		}
	}

	public void setFilter(String fieldName, Integer value) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getFilters().add(filter);
			} else {
				filter.setInteger(value);
			}
		}
	}

	public void setFilter(String fieldName, Integer value, Integer value2) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getFilters().add(filter);
			} else {
				filter.setInteger(value);
				filter.setInteger2(value2);
			}
		}
	}

	public void setMainFilter(String fieldName, Integer value) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getMainFilters().add(filter);
			} else {
				filter.setInteger(value);
			}
		}
	}

	public void setMainFilter(String fieldName, Integer value, Integer value2) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getMainFilters().add(filter);
			} else {
				filter.setInteger(value);
				filter.setInteger2(value2);
			}
		}
	}

	public void setFilter(String fieldName, Double value) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getFilters().add(filter);
			} else {
				filter.setDouble(value);
			}
		}
	}

	public void setFilter(String fieldName, Double value, Double value2) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getFilters().add(filter);
			} else {
				filter.setDouble(value);
				filter.setDouble2(value2);
			}
		}
	}

	public void setMainFilter(String fieldName, Double value) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getMainFilters().add(filter);
			} else {
				filter.setDouble(value);
			}
		}
	}

	public void setMainFilter(String fieldName, Double value, Double value2) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getMainFilters().add(filter);
			} else {
				filter.setDouble(value);
				filter.setDouble2(value2);
			}
		}
	}

	public void setFilter(String fieldName, Float value) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getFilters().add(filter);
			} else {
				filter.setFloat(value);
			}
		}
	}

	public void setFilter(String fieldName, Float value, Float value2) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getFilters().add(filter);
			} else {
				filter.setFloat(value);
				filter.setFloat2(value2);
			}
		}
	}

	public void setMainFilter(String fieldName, Float value) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getMainFilters().add(filter);
			} else {
				filter.setFloat(value);
			}
		}
	}

	public void setMainFilter(String fieldName, Float value, Float value2) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getMainFilters().add(filter);
			} else {
				filter.setFloat(value);
				filter.setFloat2(value2);
			}
		}
	}

	public void setFilter(String fieldName, Date value) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getFilters().add(filter);
			} else {
				filter.setDate(value);
			}
		}
	}

	public void setFilter(String fieldName, Date value, Date value2)
			throws Exception {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getFilters().add(filter);
			} else {
				filter.setDate(value);
				filter.setDate2(value2);
			}
		}
	}

	public void setMainFilter(String fieldName, Date value) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getMainFilters().add(filter);
			} else {
				filter.setDate(value);
			}
		}
	}

	public void setMainFilter(String fieldName, Date value, Date value2)
			throws Exception {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getMainFilters().add(filter);
			} else {
				filter.setDate(value);
				filter.setDate2(value2);
			}
		}
	}

	public void setFilter(String fieldName, Boolean value) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getFilters().add(filter);
			} else {
				filter.setBoolean(value);
			}
		}
	}

	public void setFilter(String fieldName, Boolean value, Boolean value2) {
		if ((Object) value != null) {
			Filter filter = this.filterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getFilters().add(filter);
			} else {
				filter.setBoolean(value);
				filter.setBoolean2(value2);
			}
		}
	}

	public void setMainFilter(String fieldName, Boolean value) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value);
				this.getMainFilters().add(filter);
			} else {
				filter.setBoolean(value);
			}
		}
	}

	public void setMainFilter(String fieldName, Boolean value, Boolean value2) {
		if ((Object) value != null) {
			Filter filter = this.mainFilterByName(fieldName);
			if (filter == null) {
				filter = new Filter(fieldName, value, value2);
				this.getMainFilters().add(filter);
			} else {
				filter.setBoolean(value);
				filter.setBoolean2(value2);
			}
		}
	}

	// Caso exista somente o UID no filtro entao, para acelerar o select retira do where o main filter
	public boolean isOnlyUidInFilter() {
		boolean retorno = false;
		boolean achouUid = false;
		boolean achouOutro = false;
		for (Filter filter : this.getFilters()) {
			if (filter.getFieldName().equals("uid")) {
				achouUid = true;
			}
			if (!filter.getFieldName().equals("uid")) {
				achouOutro = true;
			}
		}

		// Achou uid e nao achou outro entao retorno true
		if ((achouOutro == false) && (achouUid == true)) {
			retorno = true;
		}

		return retorno;
	}

	// **********************
	public boolean execute() {
		//System.out.println("tableStatus: " + this.getTableName() + " - execute");
		boolean retorno = false;
		
		// Usado para gerar a clausula sql atualizando o conteudo de campos de comboboxinterno
		processFields();
		
		// Caso a auditoria esteja ligada, atualiza as datas de inclusao e
		// modificacao
		if (this.isAuditing()) {
			String vuserMachine = this.getDatabase().getUserSystemLogged().getIpAddress();
			Date vcurrentDate = new Date();

			// Reseta o conteudo dos campos de controle para impedir modificacoes nao autorizadas
			this.fieldByName("incluser").setIsDefined(false);
			this.fieldByName("inclcomp").setIsDefined(false);
			this.fieldByName("incldata").setIsDefined(false);
			
			this.fieldByName("modiuser").setIsDefined(false);
			this.fieldByName("modicomp").setIsDefined(false);
			this.fieldByName("modidata").setIsDefined(false);
			
			if (this.getTableStatus()==TableStatus.INSERT) {
				if (this.fieldByName("uid").getString()==null) {
					this.setString("uid", UUID.randomUUID().toString().toUpperCase());
				}
				this.setString("incluser", this.getDatabase().getUserSystemLogged().getUserName()); 
				this.setString("inclcomp", vuserMachine);
				this.setDate("incldata", vcurrentDate);
			}

			if ((this.getTableStatus()==TableStatus.INSERT) || (this.getTableStatus()==TableStatus.UPDATE)) {
				this.setString("modiuser", this.getDatabase().getUserSystemLogged().getUserName());
				this.setString("modicomp", vuserMachine);
				this.setDate("modidata", vcurrentDate);
			}
		}

		if (this.getTableStatus()==TableStatus.INSERT) {
			String vcampos = "";
			String vvalores = "";
			String vautoIncrementalField = null;

			// Chama o evento beforepost e continuar a execucao caso ela nao
			// tenha sido cancelada
			BeforePostEvent event = new BeforePostEvent(this);
			event.setTable(this);
			fireBeforePostEvent(event);
			if (!event.isCanceled()) {
				for (Field field : this.getFields()) {
					// Guarda o uid do registro para uso futuro na atualizacao do browse
					if (field.getFieldName().equalsIgnoreCase("uid")) {
						this.setUidLastRecordProccessed(field.getString());
					}
					
					// Processa a inclusao somente de campos que nao forem resultado de expressao
					if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
						String vdelimitador = "";
						if ((field.getFieldType().equals(FieldType.VARCHAR))
								|| (field.getFieldType().equals(FieldType.TEXT))
								|| (field.getFieldType().equals(FieldType.DATE))
								|| (field.getFieldType().equals(FieldType.DATETIME)) ) {
							vdelimitador = "'";
						}
							
						if (!vcampos.isEmpty()) {
							vcampos += ", ";
							vvalores += ", ";
						}

						boolean isNull = false;
						if ((field.getFieldType().equals(FieldType.VARCHAR)) && (field.getString() == null)) {
							isNull = true;
						}
						if ((field.getFieldType().equals(FieldType.TEXT)) && (field.getString() == null)) {
							isNull = true;
						}
						if ((field.getFieldType().equals(FieldType.DATE)) && (field.getString() == null)) {
							isNull = true;
						}
						if ((field.getFieldType().equals(FieldType.DATETIME)) && (field.getString() == null)) {
							isNull = true;
						}

						if ((field.isDefined()) && (!isNull)) { 
							vcampos += field.getFieldName();
							
							// Quando o conteudo possui ' ou \ ocorre um erro no sql, para evitar estes erros
							// estou alterando este conteudo
							String conteudo = Utils.replaceAll(field.getString(), "\\", "\\\\");
							conteudo = Utils.replaceAll(conteudo, "'",  "\\'");
							
							vvalores += vdelimitador + conteudo + vdelimitador;
						} else {
							vcampos += field.getFieldName();
							vvalores += "null";
						}
						if (field.getAutoIncrement()) {
							// Caso o campo seja autoincremental, guarda essa informacao
							// para pegar o seu retorno apos o insert
							vautoIncrementalField = field.getFieldName();
						}
					}
				}
				
				// Verifica se a inclusao deste registro vai gerar problema de indice unico
				for (Index indice : this.getIndexes()) {
					if (indice.getUnique()) {
						
					}
				}

				boolean autoOpenDatabase = false;
				try {
					if (this.getDatabase().getConnection().isClosed()) {
						this.getDatabase().openConnection();
						autoOpenDatabase = true;
					}
					
					String comando = "insert into " + this.getTableName() + " (" + vcampos + ") values (" + vvalores + ")";
					
					if (this.getDebugQuery()) {
						System.out.println(this.getTableName() + " : " + comando);
					}
					
					try {
						String uid = this.getString("uid");
						if ((uid!=null) && (!uid.isEmpty())) {
							this.getDatabase().executeCommand(comando);
							retorno = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new IllegalAccessError("ERRO erro no insert da tabela: [" + this.getTableName() + "], comando: [" + comando + "], Mensagem de erro: " + e.getMessage());
					}
					
					/*
					final Logger log = Logger.getLogger(this.getClass().getName());
					log.log(Level.INFO, "INSERT EXECUTADO: " + comando);
					*/

					// Caso tenha algum campo autoincremental, pega o seu valor que
					// foi gerado durante a inclusao
					if ((vautoIncrementalField != null) && (!vautoIncrementalField.isEmpty())) {
						String uid = fieldByName("uid").getString();
						comando = "select " + vautoIncrementalField + " from " + this.getTableName() + " where (uid='" + fieldByName("uid").getString() + "')";
						try {
							ResultSet rs2 = this.getDatabase().executeSelect(comando);
							//rs2 = this.getDatabase().executeSelect(comando);
							while (rs2.next()) {
								this.setLastInsertId(rs2.getInt(vautoIncrementalField));
								this.setInteger(vautoIncrementalField, rs2.getInt(vautoIncrementalField));
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new IllegalAccessError("ERRO erro ao pegar ultima id autoincremental insert da tabela: ["+ this.getTableName() + "], comando: ["	+ comando + "], Mensagem de erro: " + e.getMessage());
						} 
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				finally {
					if (autoOpenDatabase) {
						this.getDatabase().closeConnection();
					}
				}
				

				// Lancamento automatico
				ExternalInsertEvent externalInsertEvent = null;
				for (ExternalInsert externalInsert : this.getExternalInserts()) {
					//DatabaseApp databaseApp = (DatabaseApp) this.getDatabase();
					Database database = this.getDatabase();
					
					// Preparando o evento para ser disparado
					externalInsertEvent = new ExternalInsertEvent(this);
					externalInsertEvent.sourceTable = this;
					//externalInsertEvent.targetTable = databaseApp.loadTableByName(externalInsert.getTargetTableName());
					externalInsertEvent.targetTable = database.loadTableByName(externalInsert.getTargetTableName());
					externalInsertEvent.targetTable.insert();
					externalInsert.fireExternalInsertEvent(externalInsertEvent);
					externalInsertEvent.targetTable.fieldByName("sourcetablename").setString(this.getTableName());
					externalInsertEvent.targetTable.fieldByName("sourceuid").setString(this.fieldByName("uid").getString());
					if (!externalInsertEvent.isCanceled()) {
						try {
							externalInsertEvent.targetTable.execute();
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
					else {
						externalInsertEvent.targetTable.close();
					}
				}
				
				// Processamento direto
				DirectProcessingEvent directProcessingEvent = new DirectProcessingEvent(this);
				directProcessingEvent.setTable(this);
				this.fireDirectProcessingEvent(directProcessingEvent);

				AfterPostInsertEvent afterPostInsertEvent = new AfterPostInsertEvent(this);
				afterPostInsertEvent.setTable(this);
				this.fireAfterPostInsertEvent(afterPostInsertEvent);
				
				AfterPostEvent afterPostEvent = new AfterPostEvent(this);
				afterPostEvent.setTable(this);
				this.fireAfterPostEvent(afterPostEvent);
			}

			this.setTableStatus(this.getLastTableStatus());
			this.setLastTableStatus(TableStatus.NONE);

			/*
			if (this.getTableStatus()==TableStatus.BROWSE) {
				this.loadData(uidRecord);
			}
			*/
		}

		if (this.getTableStatus()==TableStatus.UPDATE) {
			String vcampos = "";
			// Chama o evento beforepost e continuar a execucao caso ela nao
			// tenha sido cancelada
			BeforePostEvent event = new BeforePostEvent(this);
			event.setTable(this);
			fireBeforePostEvent(event);
			if (!event.isCanceled()) {
				// Excluir lancamentos automaticos
				// Pega a relacao de lancamentos automaticos
				// caso o lancamento esteja programado para exclusao durante uma
				// edicao
				// carrega a tabela alvo
				// carrega a relacao de registros que foram lancados
				// automaticamente
				// exclui um a um os registos
				
				// Executa processamento reverso, usando os oldValues gravados no inicio do update
				this.setLoadingOldValues(true);
				ReverseProcessingEvent reverseProcessingEvent = new ReverseProcessingEvent(this);
				reverseProcessingEvent.setTable(this);
				this.fireReverseProcessingEvent(reverseProcessingEvent);
				this.setLoadingOldValues(false);

				for (Field field : this.getFields()) {
					// Guarda o uid do registro para uso futuro na atualizacao do browse
					if (field.getFieldName().equalsIgnoreCase("uid")) {
						this.setUidLastRecordProccessed(field.getString());
					}
					
					if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
						String vdelimitador = "";
						if ((field.getFieldType().equals(FieldType.VARCHAR))
								|| (field.getFieldType().equals(FieldType.TEXT))
								|| (field.getFieldType().equals(FieldType.DATE))
								|| (field.getFieldType().equals(FieldType.DATETIME)) ) {
							vdelimitador = "'";
						}

						boolean isNull = false;
						if ((field.getFieldType().equals(FieldType.VARCHAR))
								&& (field.getString() == null)) {
							isNull = true;
						}
						if ((field.getFieldType().equals(FieldType.TEXT))
								&& (field.getString() == null)) {
							isNull = true;
						}
						if ((field.getFieldType().equals(FieldType.DATE))
								&& (field.getString() == null)) {
							isNull = true;
						}
						if ((field.getFieldType().equals(FieldType.DATETIME))
								&& (field.getString() == null)) {
							isNull = true;
						}

						if (field.isDefined()) {
							if (!vcampos.isEmpty()) {
								vcampos += ", ";
							}

							if (!isNull) {
								// Quando o conteudo possui ' ou \ ocorre um erro no sql, para evitar estes erros
								// estou alterando este conteudo
								String conteudo = Utils.replaceAll(field.getString(), "\\", "\\\\");
								conteudo = Utils.replaceAll(conteudo, "'",  "\\'");
								
								vcampos += field.getFieldName() + "=" + vdelimitador + conteudo + vdelimitador;
							} else {
								vcampos += field.getFieldName() + "=null";
							}
						}
					}
				}

				String vwhere = this.generateWhere();
				String comando = "update " + this.getTableName() + " set " + vcampos + vwhere ;

				if (this.getDebugQuery()) {
					System.out.println(this.getTableName() + " : " + comando);
				}
				
				try {
					boolean processa = false;
					String uid = null;
					
					if (this.filterByName("uid")!=null) {
						uid = this.filterByName("uid").getString();
					}
					
					if ((uid!=null) && (!uid.isEmpty())) {
						processa = true;
					}
					else {
						if (this.getFilters().size()>0) {
							processa = true;
						}
					}
					
					if (processa) {
						this.getDatabase().executeCommand(comando);
						//System.out.println(comando);
						retorno = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new IllegalAccessError("ERRO erro no update da tabela: ["+ this.getTableName() + "], comando: [" + comando	+ "], Mensagem de erro: " + e.getMessage());
				}
				
				/*
				final Logger log = Logger.getLogger(ClassName.class.getName());
				log.log(Level.INFO, "UPDATE EXECUTADO: " + comando);
				*/

				// Lancamento automatico
				// Pega a relacao de lancamentos automaticos que estao programados
				// cria o evento de que vai ser usado para testar se pode
				// executar o
				// lancamento automatico
				// chama o evento que testa se pode processar o lancamento
				// automatico
				// caso o lancamento automatico possa ser executado
				// carrega a tabela alvo
				// cria o evento que vai executar o lancamento automatico
				// coloca no evento o ponteiro para a tabela atual e para a
				// tabela
				// alvo
				// inicia a insercao na tabela alvo
				// executa a insercao na tabela alvo
				
				// Processamento direto
				DirectProcessingEvent directProcessingEvent = new DirectProcessingEvent(this);
				directProcessingEvent.setTable(this);
				this.fireDirectProcessingEvent(directProcessingEvent);
				
				AfterPostUpdateEvent afterPostUpdateEvent = new AfterPostUpdateEvent(this);
				afterPostUpdateEvent.setTable(this);
				this.fireAfterPostUpdateEvent(afterPostUpdateEvent);

				AfterPostEvent afterPostEvent = new AfterPostEvent(this);
				afterPostEvent.setTable(this);
				this.fireAfterPostEvent(afterPostEvent);
			}

			this.setTableStatus(this.getLastTableStatus());
			this.setLastTableStatus(TableStatus.NONE);
		}

		if (this.getTableStatus()==TableStatus.DELETE) {
			boolean execute = true;

			try {
				// Verifica se a exclusao do registro nao vai gerar problema de integridade referencial
	            if (this.getReferentialIntegrity().size()>0) {
	        		//DatabaseApp databaseApp = (DatabaseApp) this.getDatabase();
	            	Database database = this.getDatabase();
	            	
	    			// Pega a relacao de registros que serao excluidos para avaliar se sua exclusao vai gerar problema de integridade
	                // referencial
	    			Table tblBase = database.loadTableByName(this.getTableName());
	    			tblBase.select("uid");
	    			tblBase.setMainFilters(this.getMainFilters());
	    			tblBase.setFilters(this.getFilters());
	    			tblBase.loadData();
	    			
	    			for (Record registro: tblBase.getRecords()) {
	                	for (ReferentialIntegrity referentialIntegrity: this.getReferentialIntegrity()) {
	                		
	                		if (referentialIntegrity.getOnDelete().equals(OnDelete.NoAction)) {
	                			
	            				boolean closeConnection = false;
	                			try {
	                				if (this.getDatabase().getConnection().isClosed()) {
	                					this.getDatabase().openConnection();
	                					closeConnection = true;
	                				}
	                				
	                    			String comando = "select count(*) as qtd from "+referentialIntegrity.getTableName() + " where ("+referentialIntegrity.getForeingKey()+"=\""+registro.fieldByName("uid").getString()+"\")";
	                    			ResultSet resultSet = this.getDatabase().executeSelect(comando);
	                    			
	                    			while (resultSet.next()) {
	                    				if (resultSet.getInt("qtd")>0) {
	                                		//Table tblAlvo = databaseApp.loadTableByName(referentialIntegrity.getTableName());
	                                		Table tblAlvo = database.loadTableByName(referentialIntegrity.getTableName());
	                                		
	                    					execute = false;
	                    				}
	                    			}
	                			}
	                			catch (Exception e) {
	                				e.printStackTrace();
	                			}
	                			finally {
	                				if (closeConnection) {
	                					this.getDatabase().closeConnection();
	                				}
	                			}

	                			/*
	                    		TableBase tblAlvo = this.getTableControlCenter().loadTable(referentialIntegrity.getTableName());
	                    		tblAlvo.select("uid,"+referentialIntegrity.getForeingKey());
	                    		tblAlvo.setFilter(referentialIntegrity.getForeingKey(), registro.fieldByName("uid").getString());
	                    		tblAlvo.setLimit(1);
	                    		tblAlvo.loadData();
	                    		if (tblAlvo.getRecords().size()>0) {
	                    			execute = false;
	                    			resultExecute.setSuccess(false);
	                    			resultExecute.setResultMessage("Nao pode excluir");
	                    		}
	                    		tblAlvo.close();
	                    		*/
	                		}
	                	}
	    			}
	    			tblBase.close();
	            }
				
				// O registro pode ser excluido somente se nenhuma integridade referencial noAction 
				// for encontrado
	            if (execute) {
	        		//DatabaseApp databaseApp = (DatabaseApp) this.getDatabase();
	        		Database database = (Database) this.getDatabase();
	    			// Executa o processamento inverso
	    			
	    			// Excluir lancamentos automaticos TableBase table = null;
	    			for (ExternalInsert externalInsert : this.getExternalInserts()) {
	    				List<String> deleteUid = new ArrayList<String>();
	    				if (externalInsert.getPerformDeleteTargetIfUpdateSourceRecord()) {
	    					// Pega a lista de uid dos registros que foram gerados a partir deste registro que esta sendo excluido
	    					// todos os registros dentro da lista serao excluidos tambem.
	    					try {
	    						this.getDatabase().openConnection();
	    						ResultSet result1 = this.getDatabase().executeSelect("select uid from "+externalInsert.getTargetTableName() +" where sourceuid=\"" + this.filterByName("uid").getString()+"\"");
	    						while (result1.next()) {
	    							deleteUid.add(result1.getString("uid"));
	    						}
	    					}
	    					finally {
	    						this.getDatabase().closeConnection();
	    					}
	    					
	    					//Table table = databaseApp.loadTableByName(externalInsert.getTargetTableName());
	    					Table table = database.loadTableByName(externalInsert.getTargetTableName());
	    					// Varre a lista excluindo os registros encontrados
	    					for (String uid: deleteUid) {
	    						table.delete();
	    						table.setFilter("uid", uid);
	    						table.execute();
	    					}
	    				}
	    			}

	    			// Apaga os registros que estiverem se relacionando com OnDelete, que podem ser pai e filho
	    			// Verifica se a exclusao do registro nao vai gerar problema de integridade referencial
	                if (this.getReferentialIntegrity().size()>0) {
	        			// Pega a relacao de registros que serao excluidos para avaliar se sua exclusao vai gerar problema de integridade
	                    // referencial
	        			//Table tblBase = databaseApp.loadTableByName(this.getTableName());
	        			Table tblBase = database.loadTableByName(this.getTableName());
	        			tblBase.select("uid");
	        			tblBase.setMainFilters(this.getMainFilters());
	        			tblBase.setFilters(this.getFilters());
	        			tblBase.loadData();
	        			
	        			for (Record registro: tblBase.getRecords()) {
	                    	for (ReferentialIntegrity referentialIntegrity: this.getReferentialIntegrity()) {
	                    		
	                    		if (referentialIntegrity.getOnDelete().equals(OnDelete.Cascade)) {
	                        		//Table tblAlvo = databaseApp.loadTableByName(referentialIntegrity.getTableName());
	                        		Table tblAlvo = database.loadTableByName(referentialIntegrity.getTableName());
	                        		tblAlvo.delete();
	                        		tblAlvo.setFilter(referentialIntegrity.getForeingKey(), registro.fieldByName("uid").getString());
	                        		tblAlvo.execute();
	                    		}
	                    	}
	        			}
	        			tblBase.close();
	                }
	                
	                // Exclui registro filhos
	                if (this.getTableChildrenList().size()>0) {
	                	try {
	                		this.getDatabase().openConnection();

	    	                for (TableChild tableChild : this.getTableChildrenList()) {
	    	                	String tableName = tableChild.getTable().getTableName();
	    	                	String masterFieldName = tableChild.getMasterFieldName();
	    	                	String comando = "delete from " + tableName;

	    	                	// Caso o relacionamento esteja sendo feito atravez do uid do pai com o masterFieldName do filho (registro do filho que possui o uid do pai)
	    	                	if (tableChild.getMasterFieldName()!=null) {
		    	                	comando += " where (" + tableName + "." + masterFieldName + "='" + this.getString("uid") + "')";
	    	                	}
	    	                	// Caso o relacionamento esteja sendo feito atravez de um indice do pai com registros de relacionamento do filho
	    	                	else {
	    	                		comando += " where ";
	    	                		boolean filterInserted = false;
	    	                		
	    	                		String relationalshipFieldList = tableChild.getRelationalship() + ",";
	    	                		String masterIndexFieldList = this.getIndexByName(tableChild.getMasterIndexName()).getIndexFields() + ",";
	    	                		while (relationalshipFieldList.indexOf(",")>=0) {
	    	                			String relationalshipFieldName = relationalshipFieldList.substring(0, relationalshipFieldList.indexOf(",")).trim();
	    	                			relationalshipFieldList = relationalshipFieldList.substring(relationalshipFieldList.indexOf(",")+1).trim();
	    	                			
	    	                			String indexFieldName = masterIndexFieldList.substring(0, masterIndexFieldList.indexOf(",")).trim();
	    	                			masterIndexFieldList = masterIndexFieldList.substring(masterIndexFieldList.indexOf(",")+1).trim();
	    	                			
	    	                			// Caso já tenha sido incluido um filtro
	    	                			if (filterInserted) {
	    	                				comando += " and ";
	    	                			}
	    	                			
	    	                			Field field = this.fieldByName(indexFieldName);
	    	        					switch (field.getFieldType()) {
	    	        					case VARCHAR:
	    	        						comando += "(" + relationalshipFieldName + "='" + field.getString() + "')";
	    	        						break;
	    	        					case INTEGER:
	    	        						comando += "(" + relationalshipFieldName + "=" + field.getInteger() + ")";
	    	        						break;
	    	        					case DOUBLE:
	    	        						comando += "(" + relationalshipFieldName + "=" + field.getDouble() + ")";
	    	        						break;
	    	        					case FLOAT:
	    	        						comando += "(" + relationalshipFieldName + "=" + field.getFloat() + ")";
	    	        						break;
	    	        					case DATE:
	    	        						if (field.getDate()!=null) {
		    	        						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		    	        						comando += "(" + relationalshipFieldName +"='" + simpleDateFormat.format(field.getDate()) + "')";
	    	        						}
	    	        						else {
	    	        							comando += "(" + relationalshipFieldName + "=null)";
	    	        						}
	    	        						break;
	    	        					case DATETIME:
	    	        						if (field.getDate()!=null) {
		    	        						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		    	        						comando += "(" + relationalshipFieldName +"='" + simpleDateFormat.format(field.getDate()) + "')";
	    	        						}
	    	        						else {
	    	        							comando += "(" + relationalshipFieldName + "=null)";
	    	        						}
	    	        					}
	    	        					
	    	        					// informa que ja foi incluido um filtro
	    	        					filterInserted = true;
	    	                		}
	    	                	}
	    	                	
            					this.getDatabase().executeCommand(comando);
	    	                }
	                	}
	                	catch (Exception e) {
	                		System.out.println(e.getMessage());
	                	}
	                	finally {
	                		this.getDatabase().closeConnection();
	                	}
	                }

	                String uid = null;
	                if (this.filterByName("uid")!=null) {
		                uid = this.filterByName("uid").getString();
	                }
	                // Caso nao tenha o uid do registro que vai ser excluido nos filtros, pega isso direto na tabela
	                if (uid==null) {
	                	Table tblBase = database.loadTableByName(this.getTableName());
	                	tblBase.select("uid");
	        			tblBase.setMainFilters(this.getMainFilters());
	        			tblBase.setFilters(this.getFilters());
	        			tblBase.loadData();
	        			if (!tblBase.eof) {
	        				uid = tblBase.getString("uid");
	        			}
	        			tblBase.close();
	                }
	                
	                // Armazena o uid do registro que vai ser excluido
	                this.setUidLastRecordProccessed(uid);
	                
	    			// Executa processamento inverso
	    			ReverseProcessingEvent reverseProcessingEvent = new ReverseProcessingEvent(this);
	    			reverseProcessingEvent.setTable(this);
	    			this.fireReverseProcessingEvent(reverseProcessingEvent);
	                
	                // Agora vamos apagar o registro
	    			String vwhere = this.generateWhere();

	    			String comando = "delete from " + this.getTableName() + vwhere;
	    			
					if (this.getDebugQuery()) {
						System.out.println(this.getTableName() + " : " + comando);
					}

	    			try {
	    				this.getDatabase().executeCommand(comando);
	    				retorno = true;
	    				
	    				/*
	    				final Logger log = Logger.getLogger(ClassName.class.getName());
	    				log.log(Level.INFO, "DELETE EXECUTADO: " + comando);
	    				*/
	    				
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    				throw new Exception("ERRO erro no delete da tabela: ["+ this.getTableName() + "], comando: [" + comando	+ "], Mensagem de erro: " + e.getMessage());
	    			}
	    			
	    			/*
	    			final Logger log = Logger.getLogger(ClassName.class.getName());
	    			log.log(Level.INFO, "DELETE EXECUTADO: " + comando);
	    			*/
	    		}
			}
			catch (Exception e) {
				//resultExecute.setSuccess(false);
				//resultExecute.setResultMessage(e.getMessage());
				e.printStackTrace();
			}
            this.setTableStatus(this.getLastTableStatus());
            this.setLastTableStatus(TableStatus.NONE);
		}
		
		return retorno;
	}
	
	
	public String generateWhere() {
		String vretorno = "";

		String vmainWhere = "";
		String vwhere = "";

		// Caso no filtro so exista referencia ao uid, nao inclui o mainFilter
		// caso constrario, processa normalmente o mainFilter e o Filter
		if (!this.isOnlyUidInFilter()) {
			vmainWhere = this.processFilter(this.getMainFilters());
		}
		vwhere = this.processFilter(this.getFilters());

		if ((vmainWhere != null) && (!vmainWhere.isEmpty())) {
			vretorno = vmainWhere;
		}

		if ((vwhere != null) && (!vwhere.isEmpty())) {
			if (!vretorno.isEmpty()) {
				vretorno += " and ";
			}

			vretorno += vwhere;
		}
		
		if (!vretorno.isEmpty()) {
			vretorno = " where " + vretorno;
		}
		
		return vretorno;
	}

	public String processFilter(List<Filter> filters) {
		String vretorno = "";
		Field field = null;

		for (Filter filter : filters) {
			boolean processar = true;

			field = this.fieldByName(filter.getFieldName());
			String fieldInSelect = "";
			
			if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
				fieldInSelect += this.getTableName()+"."+field.getFieldName();
			}
			else {
				//fieldInSelect += "("+field.getExpression()+")" + " as " + field.getFieldName();
				fieldInSelect += "("+field.getExpression()+")";
			}

			if (processar) {
				String vdelimitador = "";
				if ((field.getFieldType().equals(FieldType.VARCHAR))
						|| (field.getFieldType().equals(FieldType.TEXT))
						|| (field.getFieldType().equals(FieldType.DATE))
						|| (field.getFieldType().equals(FieldType.DATETIME)) ) {
					vdelimitador = "'";
				}

				if (!vretorno.isEmpty()) {
					vretorno += " and ";
				}

				if (filter.isRange()) {
					vretorno += fieldInSelect + ">=" + vdelimitador + filter.getString() + vdelimitador;
					vretorno += " and ";
					vretorno += fieldInSelect + "<=" + vdelimitador + filter.getString2() + vdelimitador;
				} else {
					String caseSensitiveString = "";
					if (field.getCaseSensitive()) {
						caseSensitiveString = " binary ";
					}
					
					if ((field.getFieldType().equals(FieldType.VARCHAR)) && (!field.getAllowLike().equals(AllowLike.NONE))) {
						if (field.getAllowLike().equals(AllowLike.BEGIN)) {
							vretorno += fieldInSelect + caseSensitiveString + " like " + vdelimitador + "%" + filter.getString() + vdelimitador;
						}
						else if (field.getAllowLike().equals(AllowLike.END)) {
							vretorno += fieldInSelect + caseSensitiveString + " like " + vdelimitador + filter.getString() + "%" + vdelimitador;
						}
						else if ((field.getAllowLike().equals(AllowLike.BOTH)) || (field.getAllowLike().equals(AllowLike.UNDEFINED))) {
							vretorno += fieldInSelect + caseSensitiveString + " like " + vdelimitador + "%" + filter.getString() + "%" + vdelimitador;
						}
					} else {
						vretorno += caseSensitiveString + fieldInSelect + "=" + vdelimitador + filter.getString() + vdelimitador;
					}
				}
			}
		}

		if ((this.getWhere() != null) && (!this.getWhere().isEmpty())) {
			if (!vretorno.isEmpty()) {
				vretorno += " and ";
			}
			vretorno += this.getWhere();
		}

		return vretorno;
	}

	/*
	 * Usado para gerar a clausula sql atualizando o conteudo de campos de comboboxinterno
	 */
	public void processFields() {
		for (Field field: this.getFields()) {
			if (field.getComboBoxValue()!=null) {
				String expression = "";
				Field comboBoxField = this.fieldByName(field.getComboBoxValue());
				for (ChaveValor chaveValor: comboBoxField.getComboBoxItems()) {
					expression += " when " + field.getComboBoxValue()+"='"+chaveValor.getUid()+"' then '"+chaveValor.getDescricao()+"' ";
				}
				if (expression.length()>0) {
					expression = "case " + expression +" end";
					field.setExpression(expression);
				}
			}
			
			if (field.getValueOfInternalSearch()!=null) {
				InternalSearch internalSearch = this.fieldByName(field.getValueOfInternalSearch()).getInternalSearch();
				if (internalSearch!=null) {
					String expression="";
					for (KeyValue keyValue: internalSearch.getItems()) {
						expression += " when " + this.getTableName() + "." + field.getValueOfInternalSearch() +"='"+ keyValue.getKey() +"' then '"+keyValue.getValue()+"' ";
					}
					if (expression.length()>0) {
						expression = "case " + expression +" end";
						field.setExpression(expression);
					}
				}
			}
		}
	}
	
	public Integer getRecordCountFromTable() {
		Integer retorno = 0;
		
		ResultSet rs = null;
		String where = this.generateWhere();
		String comando = "select count(*) as qtd from " + this.getTableName() + " " + this.generateJoins() + where;
		
		if (this.getDebugQuery()) {
			System.out.println(comando);
		}
		
		try {
    		//Connection connection = this.getDatabase().getConnection();
		    //Statement sm = connection.createStatement();
			Statement sm = this.getStatement();
		    //this.setStatement(sm);
			
	        Long timeDelayed = System.currentTimeMillis();
			rs = sm.executeQuery(comando);
			while (rs.next()) {
				retorno = rs.getInt("qtd");
			}
	        timeDelayed = System.currentTimeMillis()-timeDelayed;
	        //System.out.println("Tempo: *** : " + timeDelayed);
		}
		catch (Exception e) {
			final Logger log = Logger.getLogger(this.getClass().getName());
			log.log(Level.SEVERE, "ERRO: " + e.getMessage());
			log.log(Level.SEVERE, "SELECT EXECUTADO COM ERRO: " + comando);
		}
		
		return retorno;
	}
	
	public ResultSet open() throws Exception {
		return open(null);
	}
	
	public ResultSet open(String uidRecord) throws Exception {
		// Caso nao esteja carregar do banco de dados um registro especifico mas sim um conjunto de registros
		if (uidRecord==null) {
			// Usado para gerar a clausula sql atualizando o conteudo de campos de comboboxinterno
			processFields();
			
			updateMasterField();
		}

		ResultSet rs = null;
		String vcampos = this.getFieldsInSelectProcessed();
		String vwhere = "";

		// Caso esteja pegando um conjunto de registro, moneta o where, caso esteja pegando um registro especifico 
		// pelo uid, nao é necessario montar where
		if (uidRecord==null) {
			vwhere = this.generateWhere();
		}
		else {
			vwhere = "where (" + this.getTableName() + ".uid='" + uidRecord + "')";
		}
		
		String comando = "select " + vcampos + " from " + this.getTableName() + " " + this.generateJoins() + vwhere;
		int qtd = 0;

		if (uidRecord==null) {
			String vorder = this.getOrder();
			if ((vorder != null) && (!vorder.isEmpty())) {
				comando += " order by " + vorder;
			}
		}

		if (this.getDatabase()==null) {
			throw new IllegalAccessError("Erro na rotina open da tabela: " + this.getTableName() + " - Database is missing.");
		}
		
		if (uidRecord==null) {
			if (this.getLimit() > 0) {
				/*
				ResultSet rsQtd = null;
				try {
					rsQtd = this.getDatabase().executeSelect("select count(*) as qtd from " + this.getTableName() + vwhere);
					while (rsQtd.next()) {
						qtd = rsQtd.getInt("qtd");
					}
					this.setRecordCount(qtd);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					rsQtd.close();
				}
				*/
				//Integer vinicio = this.getPage() - 1;
				//vinicio = vinicio * this.getLimit();
				//comando += " limit " + vinicio + ", " + this.getLimit();
				comando += " limit " + this.getOffSet() + ", " + this.getLimit();
			}
		}

		if (this.getDebugQuery()) {
			System.out.println(this.getTableName() + " : " + comando);
		}
		
		Connection connection = this.getDatabase().getConnection();
		Statement sm = connection.createStatement();
		this.setStatement(sm);

		Long delayedTime = System.currentTimeMillis();
		try {
			rs = sm.executeQuery(comando);
		}
		catch (Exception e) {
			final Logger log = Logger.getLogger(this.getClass().getName());
			log.log(Level.SEVERE, "ERRO: " + e.getMessage());
			log.log(Level.SEVERE, "SELECT EXECUTADO COM ERRO: " + comando);
		}
		
		delayedTime = System.currentTimeMillis() - delayedTime;
		//System.out.println("Tempo de execucao da query na tabela: " + this.getTableName() + " tempo: " + delayedTime);
		
		/*
		final Logger log = Logger.getLogger(this.getClass().getName());
		log.log(Level.INFO, "SELECT EXECUTADO: " + comando);
		*/

		this.setResultSet(rs);
		
		return rs;
	}

	public void close() {
		try {
			if (this.getResultSet() != null) {
				this.getResultSet().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (this.getStatement() != null) {
				this.getStatement().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (this.loadType == LoadType.RECORDDATA) {
			this.records = null;
		}
		else if (this.loadType == LoadType.SIMPLERECORD) {
			//this.setSimpleRecordMap(null);
			this.setSimpleRecordList(null);
		}
		
		this.dataLoaded = false;
		this.setFieldsInSelectList(new ArrayList<Field>());
	}

	public void getDataFromResultSet() throws Exception {
		try {
			ResultSet resultSet = this.getResultSet();
			
			String fieldsInSelect = ","+this.getFieldsInSelectOnlyFieldsName()+",";
			
			while (fieldsInSelect.indexOf(" ")>=0) {
				fieldsInSelect = fieldsInSelect.replace(" ", "");
			}

			for (Field field : this.getFields()) {
				FieldType fieldType = field.getFieldType();
				
				// Pega os dados no resultSet somente dos campos que estao presentes no select
				if (fieldsInSelect.indexOf(","+field.getFieldName()+",")>=0) {
					if (fieldType.equals(FieldType.VARCHAR)) {
						field.setString(resultSet.getString(field.getFieldName()));
					} else if (fieldType.equals(FieldType.TEXT)) {
						field.setString(resultSet.getString(field.getFieldName()));
					} else if (fieldType.equals(FieldType.INTEGER)) {
						field.setInteger(resultSet.getInt(field.getFieldName()));
					} else if (fieldType.equals(FieldType.DOUBLE)) {
						field.setDouble(resultSet.getDouble(field.getFieldName()));
					} else if (fieldType.equals(FieldType.FLOAT)) {
						field.setFloat(resultSet.getFloat(field.getFieldName()));
					} else if (fieldType.equals(FieldType.DATE)) {
						field.setDate(resultSet.getDate(field.getFieldName()));
					} else if (fieldType.equals(FieldType.DATETIME)) {
						field.setDate(resultSet.getDate(field.getFieldName()));
					} else if (fieldType.equals(FieldType.BOOLEAN)) {
						field.setBoolean(resultSet.getBoolean(field.getFieldName()));
					} else if (fieldType.equals(FieldType.BIT)) {
						field.setBoolean(resultSet.getBoolean(field.getFieldName()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Esta rotina retorna a relacao de campos presente no select 
	 * isto e usado na rotina que pega os dados do resultset, antes de tentar
	 * pegar os dados ele verifica se o campo que esta sendo processado esta presente no 
	 * select, para nao tentar pegar um campo que nao tenha sido retornardo pelo mysql
	 */
	public String getFieldsInSelectOnlyFieldsName() {
		String fieldsInSelect = this.getFieldsInSelect();
		if (fieldsInSelect.equals("*")) {
			fieldsInSelect = "";
			for (Field field: this.getFields()) {
				if (!fieldsInSelect.isEmpty()) {
					fieldsInSelect += ", ";
				}
				fieldsInSelect += field.getFieldName();
			}
		}
		
		return fieldsInSelect;
	}

	/*
	 * Gera uma lista com a relacao de campos que estao presentes no select
	 */
	public List<Field> generateListOfFieldsInSelect() {
		List<Field> retorno = new ArrayList<Field>();
		
		String fieldsInSelect = this.getFieldsInSelect();
		if (fieldsInSelect.equals("*")) {
			for (Field field: this.getFields()) {
				retorno.add(field);
			}
		}
		else {
			boolean uidInserted = false;
			String campos = fieldsInSelect+",";
			campos = campos.replace(" ", "");
			while (campos.indexOf(",")>0) {
				String campo = campos.substring(0, campos.indexOf(","));
				campos = campos.substring(campos.indexOf(",")+1);

				// Sinaliza que o uid ja foi incluido no select
				if ((!uidInserted) && (campo.equalsIgnoreCase("uid"))) {
					uidInserted = true;
				}
				
				Field field = this.fieldByName(campo);
				retorno.add(field);
			}
			
			// Caso uid não esteja incluido ainda, inclui agora
			if (!uidInserted) {
				Field field = this.fieldByName("uid");
				retorno.add(field);
			}
		}
		
		return retorno;
	}
	
	public String getFieldsInSelectProcessed() {
		this.setFieldsInSelectList(new ArrayList<Field>());
		
		String fieldsInSelect = this.getFieldsInSelect();
		if (fieldsInSelect.equals("*")) {
			fieldsInSelect = "";
			for (Field field: this.getFields()) {
				if (!fieldsInSelect.isEmpty()) {
					fieldsInSelect += ", ";
				}
				if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
					fieldsInSelect += this.getTableName()+"."+field.getFieldName();
				}
				else {
					fieldsInSelect += "("+field.getExpression()+")" + " as " + field.getFieldName();
				}
				
				this.getFieldsInSelectList().add(field);
			}
		}
		else {
			boolean uidInserted = false;
			String campos = fieldsInSelect+",";
			campos = campos.replace(" ", "");
			fieldsInSelect = "";
			while (campos.indexOf(",")>0) {
				String campo = campos.substring(0, campos.indexOf(","));
				campos = campos.substring(campos.indexOf(",")+1);

				// Sinaliza que o uid ja foi incluido no select
				if ((!uidInserted) && (campo.equalsIgnoreCase("uid"))) {
					uidInserted = true;
				}
				
				Field field = this.fieldByName(campo);
				if (!fieldsInSelect.isEmpty()) {
					fieldsInSelect += ", ";
				}
				if ((field.getExpression()==null) || (field.getExpression().isEmpty())) {
					fieldsInSelect += this.getTableName()+"."+field.getFieldName();
				}
				else {
					fieldsInSelect += "("+field.getExpression()+")" + " as " + field.getFieldName();
				}
				
				this.getFieldsInSelectList().add(field);
			}
			
			// Caso uid não esteja incluido ainda, inclui agora
			if (!uidInserted) {
				fieldsInSelect += ", "+this.getTableName()+"."+"uid";
				this.getFieldsInSelectList().add(this.fieldByName("uid"));
			}
		}
		
		return fieldsInSelect;
	}
	
	public Index getIndexByName(String indexName) {
		Index retorno = null;
		for (Index index: this.getIndexes()) {
			if (index.getIndexName().equals(indexName)) {
				retorno=index;
				break;
			}
		}
		
		if (retorno==null) {
			throw new IllegalAccessError("Nao foi localizado o indice : " + indexName + " na tabela " + this.getTableName() + "!");			
		}
		return retorno;
	}
	
	public String generateJoins() {
		//DatabaseApp databaseApp = (DatabaseApp) this.getDatabase();
		Database databaseApp = this.getDatabase();
		String retorno = "";
						
		for (Join join : this.getJoins()) {
			Table targetTable = databaseApp.loadTableByName(join.getTargetTableName());
			Index index = targetTable.getIndexByName(join.getForeignKey());
			String fieldsRelation[] = join.getRelationship().split(",");
			String fieldsIndex[] = index.getIndexFields().split(",");
			String retornoJoin = "";
			for (int i=0; i<=fieldsRelation.length-1; i++) {
	  		   if (!retornoJoin.isEmpty()) {
	  			   retornoJoin += " and ";
	  		   }

	  		   if (fieldsRelation[i].contains(".")) {
		  		   retornoJoin += "("+ join.getTargetTableName()+ "." + fieldsIndex[i] + "=" + fieldsRelation[i] +")";
	  		   }
	  		   else {
		  		   retornoJoin += "("+ join.getTargetTableName()+ "." + fieldsIndex[i] + "=" + this.getTableName() + "." + fieldsRelation[i] +")";
	  		   }
			}
			retornoJoin = "left join " + join.getTargetTableName() + " as " + join.getAlias() + " on " + retornoJoin + " ";
			retorno += retornoJoin;
		}
		
		return retorno;
	}


	// Faz uma recontagem de registros do load
	public void updateRecordCountLoad() {
		switch (this.getLoadType()) {
		case SIMPLERECORD:
			this.setRecordCountLoad(this.getSimpleRecordList().size());
			break;
		case RECORDDATA:
			this.setRecordCountLoad(this.getRecords().size());
			break;
		case GENERICDATA:
			this.setRecordCountLoad(this.getGenericModels().size());
			break;
		}
	}

	// Seta o numero o recno = 0;
	// Pegar o numero de registros retornados no load
	// Caso tenha limit no loadData, pega o numero total de registro da tabela
	// Seta o eof, caso nao tenha retornado registros, eof = true, caso tenha retornado registros eof = false
	// Caso recordcount>0, Carrega o primeiro registro
	public void executeAfterLoadData() {
		// Seta o numero o recno = 0;
		this.setRecno(0);
		
		// Pegar o numero de registros retornados no loadData
		this.updateRecordCountLoad();
		
		// Caso recordcount>0, Carrega o primeiro registro
		if (this.getRecordCountLoad()>0) {
			this.loadRecord(0);
			this.setEof(false);
		}
		else {
			this.setEof(true);
		}
	}

	// Caso o recno=numero de regostros do load, eof = true;
	// Caso o recno<numero de registros do load, atualiza recno + 1, carrega os dados do registro;
	public void next() {
		if (this.getRecno()==this.getRecordCountLoad()-1) {
			this.setEof(true);
		}
		else {
			this.setRecno(this.getRecno()+1);
		}
		
		// Carrega o conteudo do registo selecionado para os campos
		loadRecord(this.getRecno());
	}

	public void loadRecord(Integer recno) {
		switch (this.getLoadType()) {
		case SIMPLERECORD:
			this.loadRecordFromSimpleRecords(recno);
			break;
		case RECORDDATA:
			this.loadRecordFromRecords(recno);
			break;
		}
	}

	public void loadRecordFromSimpleRecords(Integer recno) {
		SimpleRecord record = this.getSimpleRecordList().get(recno);
		this.currentSimpleRecord = record;
		
		for (Field field : this.getFieldsInSelectList()) {
			switch (field.getFieldType()) {
			case VARCHAR:
				field.setValue(record.getString(field.getFieldName()));
				break;
			case TEXT:
				field.setValue(record.getString(field.getFieldName()));
				break;
			case INTEGER:
				field.setValue(record.getInteger(field.getFieldName()));
				break;
			case DOUBLE:
				field.setValue(record.getDouble(field.getFieldName()));
				break;
			case FLOAT:
				field.setValue(record.getFloat(field.getFieldName()));
				break;
			case DATE:
				field.setValue(record.getDate(field.getFieldName()));
				break;
			case DATETIME:
				field.setValue(record.getDate(field.getFieldName()));
				break;
			case BOOLEAN:
				field.setValue(record.getBoolean(field.getFieldName()));
				break;
			}
		}
	}
	
	public void loadRecordFromRecords(Integer recno) {
		Record record = this.getRecords().get(recno);

		for (Field field : this.getFields()) {
			switch (field.getFieldType()) {
			case VARCHAR:
				field.setValue(record.getString(field.getFieldName()));
				break;
			case TEXT:
				field.setValue(record.getString(field.getFieldName()));
				break;
			case INTEGER:
				field.setValue(record.getInteger(field.getFieldName()));
				break;
			case DOUBLE:
				field.setValue(record.getDouble(field.getFieldName()));
				break;
			case FLOAT:
				field.setValue(record.getFloat(field.getFieldName()));
				break;
			case DATE:
				field.setValue(record.getDate(field.getFieldName()));
				break;
			case DATETIME:
				field.setValue(record.getDate(field.getFieldName()));
				break;
			case BOOLEAN:
				field.setValue(record.getBoolean(field.getFieldName()));
				break;
			}
		}
	}
	
	/*
	public void loadRecordFromGenericModels(Integer recno) {
		GenericModel genericModel = this.getGenericModels().get(recno);
		
		for (Field field : this.getFields()) {
			switch (field.getFieldType()) {
			case VARCHAR:
				field.setValue(genericModel.getString(field.getAlias()));
				break;
			case TEXT:
				field.setValue(genericModel.getString(field.getAlias()));
				break;
			case INTEGER:
				field.setValue(genericModel.getInteger(field.getAlias()));
				break;
			case DOUBLE:
				field.setValue(genericModel.getDouble(field.getAlias()));
				break;
			case FLOAT:
				field.setValue(genericModel.getFloat(field.getAlias()));
				break;
			case DATETIME:
				field.setValue(genericModel.getDate(field.getAlias()));
				break;
			case BOOLEAN:
				field.setValue(genericModel.getBoolean(field.getAlias()));
				break;
			}
		}
	}
	*/
	
	// Verifica se os dados foram carregados na tabela, caso nao tenha sido carregados, gera um exception
	/*
	public Boolean allowedGetSetData() {
		Boolean retorno = false;
		
		if ((this.getTableStatus()==TableStatus.INSERT) || (this.getTableStatus()==TableStatus.UPDATE)) {
			retorno = true;
		}
		else if (this.dataLoaded) {
			retorno = true;
		}
		else {
			throw new IllegalAccessError("Not data loaded for table " + this.getTableName());
		}
		
		return retorno;
	}
	*/

	/**
	 * Inicia o processo de atualizacao de um registro 
	 * @param uid do registro que esta sendo atualizado
	 * @param loadDataFromTable Deseja carregar os dados dos campos do banco de dados, caso seja false, usa dos dados que ja estao carregados
	 * @return
	 */
	public Boolean update(String uid, Boolean loadDataFromTable) {
		Boolean retorno = true;

		if (beforeUpdate()) {
			if (loadDataFromTable) {
				//DatabaseApp databaseApp = (DatabaseApp) this.getDatabase();
				Database databaseApp = this.getDatabase();
				Table table = databaseApp.loadTableByName(this.getTableName());
				table.select("*");
				table.setFilter("uid", uid);
				table.loadData();
				
				this.update();
				this.setLoadingDataToForm(true);
				for (Field field : this.getFields()) {
					switch (field.getFieldType()) {
					case VARCHAR:
						field.setValue(table.getString(field.getFieldName()));
						break;
					case TEXT:
						field.setValue(table.getString(field.getFieldName()));
						break;
					case INTEGER:
						field.setValue(table.getInteger(field.getFieldName()));
						break;
					case DOUBLE:
						field.setValue(table.getDouble(field.getFieldName()));
						break;
					case FLOAT:
						field.setValue(table.getFloat(field.getFieldName()));
						break;
					case DATE:
						field.setValue(table.getDate(field.getFieldName()));
						break;
					case DATETIME:
						field.setValue(table.getDate(field.getFieldName()));
						break;
					case BOOLEAN:
						field.setValue(table.getBoolean(field.getFieldName()));
						break;
					}
				}
				this.setLoadingDataToForm(false);
				
				// Caso a tabela possua processamento direto ou inverso, guarda o valor dos campos em oldValues para ser usado
				// no processamento reverso que vai ser disparado no post do update
				if (this.hasProcessing()) {
					this.saveFieldsOldValues();
				}
				
				AfterUpdateEvent afterUpdateEvent = new AfterUpdateEvent(this);
				afterUpdateEvent.setTable(this);
				this.fireAfterUpdateEvent(afterUpdateEvent);
			}
			else {
				// Caso nao deseje carregar os dados, entao vai usar os dados que ja foram carregados anteriormente
				// seta o estado da tabela para update para poder processar corretamente o execute (post)
				// provavelmente esse update esta sendo disparado de um processamentoDireto ou de um botao salvar do formulario apos a tabela que possui filhos
				// ja ter o post do update executado automaticamente apos o inicio da inclusao/edicao de um registro filho
				this.setLastTableStatus(this.getTableStatus());
				this.setTableStatus(TableStatus.UPDATE);
			}
			
			this.setFilters(new ArrayList<Filter>());
			this.setFilter("uid", uid);
		}
		else {
			retorno = false;
		}
		
		return retorno;
	}
	
	public Boolean delete(String uid) {
		Boolean retorno = true;
		
		if (beforeDelete()) {
			//DatabaseApp databaseApp = (DatabaseApp) this.getDatabase();
			Database databaseApp = this.getDatabase();
			Table table = databaseApp.loadTableByName(this.getTableName());
			table.select("*");
			table.setFilter("uid", uid);
			table.loadData();
			
			this.delete();
			for (Field field : this.getFields()) {
				switch (field.getFieldType()) {
				case VARCHAR:
					field.setValue(table.getString(field.getFieldName()));
					break;
				case TEXT:
					field.setValue(table.getString(field.getFieldName()));
					break;
				case INTEGER:
					field.setValue(table.getInteger(field.getFieldName()));
					break;
				case DOUBLE:
					field.setValue(table.getDouble(field.getFieldName()));
					break;
				case FLOAT:
					field.setValue(table.getFloat(field.getFieldName()));
					break;
				case DATE:
					field.setValue(table.getDate(field.getFieldName()));
					break;
				case DATETIME:
					field.setValue(table.getDate(field.getFieldName()));
					break;
				case BOOLEAN:
					field.setValue(table.getBoolean(field.getFieldName()));
					break;
				}
			}
			this.setFilter("uid", table.getString("uid"));
		}
		else {
			retorno = false;
		}
		
		return retorno;
	}
	
	public String getRealFieldName(String beanFieldName) {
		Integer fieldNumber = 0;
		String retorno = null;
		
		for (Field field: this.getFieldsInSelectList()) {
			String alterFieldName = "field" + fieldNumber;
			
			if (alterFieldName.equals(beanFieldName)) {
				retorno = field.getFieldName();
				break;
			}
			
			fieldNumber++;
		}
		
		return retorno;
	}

	/*
	public BeanItemContainer getBeanItemContainer() {
		BeanItemContainer beanItemContainer = null;
		NumberFormat numberFormat = null;
		
		if (this.getFieldsInSelectList().size()<=10) {
			List<Model10> dataList = new ArrayList<Model10>();
			
			for (SimpleRecord simpleRecord: this.simpleRecordList) {
				Model10	model = new Model10();
				model.setUid(simpleRecord.getString("uid"));
				
				for (Field field: this.getFieldsInSelectList()) {
					switch (field.getFieldType()) {
					case VARCHAR:
						model.addNewFieldValue(simpleRecord.getString(field.getFieldName()));
						break;
					case INTEGER:
						numberFormat = new DecimalFormat("#,##0");
						model.addNewFieldValue(numberFormat.format(simpleRecord.getInteger(field.getFieldName())));
						break;
					case DOUBLE:
						numberFormat = new DecimalFormat("#,##0.00");
						model.addNewFieldValue(numberFormat.format(simpleRecord.getDouble(field.getFieldName())));
						break;
					case FLOAT:
						numberFormat = new DecimalFormat("#,##0.0000");
						model.addNewFieldValue(numberFormat.format(simpleRecord.getFloat(field.getFieldName())));
						break;
					case DATETIME:
						if (simpleRecord.getDate(field.getFieldName())!=null) {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
							model.addNewFieldValue(simpleDateFormat.format(simpleRecord.getDate(field.getFieldName())));
						}
						else {
							model.addNewFieldValue("");
						}
					}
				}
				
				dataList.add(model);
			}
			
			beanItemContainer = new BeanItemContainer<Model10>(Model10.class, dataList);
		}
		else if ((this.getFieldsInSelectList().size()>10) && (this.getFieldsInSelectList().size()<=20)) {
			List<Model20> dataList = new ArrayList<Model20>();
			
			for (SimpleRecord simpleRecord: this.simpleRecordList) {
				Model20	model = new Model20();
				
				for (Field field: this.getFieldsInSelectList()) {
					switch (field.getFieldType()) {
					case VARCHAR:
						model.addNewFieldValue(simpleRecord.getString(field.getFieldName()));
						break;
					case INTEGER:
						numberFormat = new DecimalFormat("#,##0");
						model.addNewFieldValue(numberFormat.format(simpleRecord.getInteger(field.getFieldName())));
						break;
					case DOUBLE:
						numberFormat = new DecimalFormat("#,##0.00");
						model.addNewFieldValue(numberFormat.format(simpleRecord.getDouble(field.getFieldName())));
						break;
					case FLOAT:
						numberFormat = new DecimalFormat("#,##0.0000");
						model.addNewFieldValue(numberFormat.format(simpleRecord.getDouble(field.getFieldName())));
						break;
					case DATETIME:
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						model.addNewFieldValue(simpleDateFormat.format(simpleRecord.getDate(field.getFieldName())));
					}
				}
				
				dataList.add(model);
			}
			
			beanItemContainer = new BeanItemContainer<Model20>(Model20.class, dataList);
		}
		
		return beanItemContainer;
	}
	*/

	public Field addField(String fieldName, FieldType fieldType, Integer fieldSize) {
		Field field = this.fieldExist(fieldName);
		
		if (field == null) {
			field = new Field(fieldName, fieldType, fieldSize);
			field.setTable(this);
			this.getFields().add(field);
		}
		
		return field;
	}
	
	public Field addField(String fieldName, FieldType fieldType, Integer fieldSize, String expression) {
		Field field = this.addField(fieldName, fieldType, fieldSize);
		field.setExpression(expression);
		return field;
	}
	
	/*
	public void setAllowBlank(String fieldName, boolean allowBlank) {
		Field field = fieldByName(fieldName);
		field.setAllowBlank(allowBlank);
	}
	*/
	
	/*
	 * Adiciona os campos de controle para a tabela
	 */
	public void addFieldsControl() {
		this.addField("incluser", FieldType.VARCHAR, 50);
		this.addField("inclcomp", FieldType.VARCHAR, 50);
		this.addField("incldata", FieldType.DATETIME, 10);

		this.addField("modiuser", FieldType.VARCHAR, 50);
		this.addField("modicomp", FieldType.VARCHAR, 50);
		this.addField("modidata", FieldType.DATETIME, 10);

		this.addField("uid", FieldType.VARCHAR, 50);
		this.setAllowLike("uid", AllowLike.NONE);
		
		this.addIndex("uid", "uid", true, true);

		if (this.getExternalInsertControlEnabled()) {
			this.addField("sourcetablename", FieldType.VARCHAR, 100);
			this.addField("sourceuid", FieldType.VARCHAR, 50);
			
			this.addIndex("sourcetablename_sourceuid", "sourcetablename, sourceuid", false, true);
			
			this.fieldByName("sourcetablename").setAllowLike(AllowLike.NONE);
			this.fieldByName("sourceuid").setAllowLike(AllowLike.NONE);
		}
	}
	
	/*
	 * Valida o conteudo dos campos antes de um post
	 */
	public boolean validate() {
		boolean retorno = true;
		for (Field field: this.getFields()) {
			if (!field.validate()) {
				System.out.println("Falha na validação do campo: " + field.getFieldName());
				retorno = false;
			}
		}
		
		return retorno;
	}
	
	/**
	 * Executa acoes de validacao antes de iniciar uma inclusao, caso exista masterTable em edicao, executa post da mesma
	 * retorna true caso possa dar sequencia ao processo de insert
	 * @return
	 */
	public boolean beforeInsert() {
		Boolean valid = true;
		String mensagemErro = "Inclusão não permitida!";
		
		// Caso os controle de validacao estejam ativos
		if (this.getActiveControl()) {
			// Caso a tabela possua um parent anexado em edição, tenta finalizar esta edição
			if ((this.getMasterTable()!=null) && ((this.getMasterTable().getTableStatus()==TableStatus.INSERT) || (this.getMasterTable().getTableStatus()==TableStatus.UPDATE))) {
				valid = this.getMasterTable().validate();
				
				if (!valid) {
					mensagemErro = "Verifique os campos que apresentaram erro antes de prosseguir!";
				}
				else {
					valid = this.getMasterTable().execute();
				}
			}
		}
		
		// Caso ainda esteja valido, ou nao passou por post no pai ou executou com sucesso post no pai
		if (valid) {
			SpecialConditionOfInsertEvent event = new SpecialConditionOfInsertEvent(this);
			try {
				event.setTable(this);
				event.setValid(valid);
				event.setErrorMessage(mensagemErro);
				fireSpecialConditionOfInsertEvent(event);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				if (!event.getValid()) {
					valid = false;
					mensagemErro = event.getErrorMessage();
				}
			}
		}
		
		if (!valid) {
			Utils.ShowMessageWindow("Atenção!", mensagemErro, 600, 180, MessageWindowType.ERROR);
		}
		
		return valid;
	}
	
	public void updateMasterFieldOnInsert() {
		if (this.getMasterTable()!=null) {
			if (this.getMasterFieldName()!=null) {
				Field field = this.fieldByName(this.getMasterFieldName());
				field.setValue(this.getMasterTable().getString("uid"));
			}
			else {
        		String relationalshipFieldList = this.getRelationalship() + ",";
        		String masterIndexFieldList = this.getMasterTable().getIndexByName(this.getMasterIndexName()).getIndexFields() + ",";
        		while (relationalshipFieldList.indexOf(",")>=0) {
        			String relationalshipFieldName = relationalshipFieldList.substring(0, relationalshipFieldList.indexOf(",")).trim();
        			relationalshipFieldList = relationalshipFieldList.substring(relationalshipFieldList.indexOf(",")+1).trim();
        			
        			String indexFieldName = masterIndexFieldList.substring(0, masterIndexFieldList.indexOf(",")).trim();
        			masterIndexFieldList = masterIndexFieldList.substring(masterIndexFieldList.indexOf(",")+1).trim();
        			
        			Field field = this.fieldByName(relationalshipFieldName);
					switch (field.getFieldType()) {
					case VARCHAR:
						this.setValue(relationalshipFieldName, this.getMasterTable().getString(indexFieldName));
						break;
					case INTEGER:
						this.setValue(relationalshipFieldName, this.getMasterTable().getInteger(indexFieldName));
						break;
					case DOUBLE:
						this.setValue(relationalshipFieldName, this.getMasterTable().getDouble(indexFieldName));
						break;
					case FLOAT:
						this.setValue(relationalshipFieldName, this.getMasterTable().getFloat(indexFieldName));
						break;
					case DATE:
						this.setValue(relationalshipFieldName, this.getMasterTable().getDate(indexFieldName));
						break;
					case DATETIME:
						this.setValue(relationalshipFieldName, this.getMasterTable().getDate(indexFieldName));
						break;
					}
        		}
			}
			/*
			switch (field.getFieldType()) {
			case VARCHAR:
				field.setValue(this.getMasterTable().getString(this.getMasterFieldName()));
				break;
			case INTEGER:
				field.setValue(this.getMasterTable().getInteger(this.getMasterFieldName()));
				break;
			case DOUBLE:
				field.setValue(this.getMasterTable().getDouble(this.getMasterFieldName()));
				break;
			case FLOAT:
				field.setValue(this.getMasterTable().getFloat(this.getMasterFieldName()));
				break;
			case DATETIME:
				field.setValue(this.getMasterTable().getDate(this.getMasterFieldName()));
				break;
			}
			*/
		}
	}
	
	public void updateMasterField() {
		if (this.getMasterTable()!=null) {
			this.getMainFilters().clear();

			//Field field = this.fieldByName(this.getMasterFieldName());
			// Se for uma inclusão no master, ainda nao tem uid para pesquisar os filhos, para nao puxar todos os registros do filho
			// cria uma filtragem temporaria
			if (this.getMasterTable().getTableStatus()==TableStatus.INSERT) {
				//this.setMainFilter(this.getMasterFieldName(), "T");
				this.setMainFilter("uid", "T");
			}
			else {
				// Necessario verificar qual o tipo de relacionamento com o masterTable esta sendo utilizado
				// para so entao definir a filtragem de forma adequada.
				if (this.getMasterFieldName()!=null) {
					this.setMainFilter(this.getMasterFieldName(), this.getMasterTable().getString("uid"));
				}
				else {
	        		String relationalshipFieldList = this.getRelationalship() + ",";
	        		String masterIndexFieldList = this.getMasterTable().getIndexByName(this.getMasterIndexName()).getIndexFields() + ",";
	        		while (relationalshipFieldList.indexOf(",")>=0) {
	        			String relationalshipFieldName = relationalshipFieldList.substring(0, relationalshipFieldList.indexOf(",")).trim();
	        			relationalshipFieldList = relationalshipFieldList.substring(relationalshipFieldList.indexOf(",")+1).trim();
	        			
	        			String indexFieldName = masterIndexFieldList.substring(0, masterIndexFieldList.indexOf(",")).trim();
	        			masterIndexFieldList = masterIndexFieldList.substring(masterIndexFieldList.indexOf(",")+1).trim();
	        			
	        			Field field = this.fieldByName(relationalshipFieldName);
						switch (field.getFieldType()) {
						case VARCHAR:
							this.setMainFilter(relationalshipFieldName, this.getMasterTable().getString(indexFieldName));
							break;
						case INTEGER:
							this.setMainFilter(relationalshipFieldName, this.getMasterTable().getInteger(indexFieldName));
							break;
						case DOUBLE:
							this.setMainFilter(relationalshipFieldName, this.getMasterTable().getDouble(indexFieldName));
							break;
						case FLOAT:
							this.setMainFilter(relationalshipFieldName, this.getMasterTable().getFloat(indexFieldName));
							break;
						case DATE:
							this.setMainFilter(relationalshipFieldName, this.getMasterTable().getDate(indexFieldName));
							break;
						case DATETIME:
							this.setMainFilter(relationalshipFieldName, this.getMasterTable().getDate(indexFieldName));
							break;
						}
	        		}
				}
			}
			/*
			switch (field.getFieldType()) {
			case VARCHAR:
				this.setMainFilter(this.getMasterFieldName(), this.getMasterTable().getString(this.getMasterFieldName()));
				break;
			case INTEGER:
				this.setMainFilter(this.getMasterFieldName(), this.getMasterTable().getInteger(this.getMasterFieldName()));
				break;
			case DOUBLE:
				this.setMainFilter(this.getMasterFieldName(), this.getMasterTable().getDouble(this.getMasterFieldName()));
				break;
			case FLOAT:
				this.setMainFilter(this.getMasterFieldName(), this.getMasterTable().getFloat(this.getMasterFieldName()));
				break;
			case DATETIME:
				this.setMainFilter(this.getMasterFieldName(), this.getMasterTable().getDate(this.getMasterFieldName()));
				break;
			}
			*/
		}
	}
	
	public void updateChildrens() {
		// Atualiza registros filhos
		for (TableChild tableChild: this.getTableChildrenList()) {
			// Define a filtragem basica, para a tabela
			tableChild.getTable().close();
			tableChild.getTable().getMainFilters().clear();
			
			if (tableChild.getMasterFieldName()!=null) {
				tableChild.getTable().setMainFilter(tableChild.masterFieldName, getString("uid"));
			}
			else {
        		String relationalshipFieldList = tableChild.getRelationalship() + ",";
        		String masterIndexFieldList = this.getIndexByName(tableChild.getMasterIndexName()).getIndexFields() + ",";
        		while (relationalshipFieldList.indexOf(",")>=0) {
        			String relationalshipFieldName = relationalshipFieldList.substring(0, relationalshipFieldList.indexOf(",")).trim();
        			relationalshipFieldList = relationalshipFieldList.substring(relationalshipFieldList.indexOf(",")+1).trim();
        			
        			String indexFieldName = masterIndexFieldList.substring(0, masterIndexFieldList.indexOf(",")).trim();
        			masterIndexFieldList = masterIndexFieldList.substring(masterIndexFieldList.indexOf(",")+1).trim();
        			
        			Field field = this.fieldByName(indexFieldName);
					switch (field.getFieldType()) {
					case VARCHAR:
						tableChild.getTable().setMainFilter(relationalshipFieldName, field.getString());
						break;
					case INTEGER:
						tableChild.getTable().setMainFilter(relationalshipFieldName, field.getInteger());
						break;
					case DOUBLE:
						tableChild.getTable().setMainFilter(relationalshipFieldName, field.getDouble());
						break;
					case FLOAT:
						tableChild.getTable().setMainFilter(relationalshipFieldName, field.getFloat());
						break;
					case DATE:
						tableChild.getTable().setMainFilter(relationalshipFieldName, field.getDate());
						break;
					case DATETIME:
						tableChild.getTable().setMainFilter(relationalshipFieldName, field.getDate());
						break;
					}
        		}
			}
			
			tableChild.getTable().loadData();
		}
	}
	
	public void afterInsert() {
		this.updateMasterField();
		// Atualiza tabelas filhas
		this.updateChildrens();
	}
	
	public Boolean beforeUpdate() {
		Boolean valid = true;
		String mensagemErro = "Modificação não permitida!";
		
		// Caso os controle de validacao estejam ativos
		if (this.getActiveControl()) {
			// Caso a tabela possua um parent anexado em edição, tenta finalizar esta edição
			if ((this.getMasterTable()!=null) && ((this.getMasterTable().getTableStatus()==TableStatus.INSERT) || (this.getMasterTable().getTableStatus()==TableStatus.UPDATE))) {
				valid = this.getMasterTable().execute();
			}
		}
		
		// Caso ainda esteja valido, ou nao passou por post no pai ou executou com sucesso post no pai
		if (valid) {
			SpecialConditionOfUpdateEvent event = new SpecialConditionOfUpdateEvent(this);
			try {
				event.setTable(this);
				event.setValid(valid);
				event.setErrorMessage(mensagemErro);
				fireSpecialConditionOfUpdateEvent(event);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				if (!event.getValid()) {
					valid = false;
					mensagemErro = event.getErrorMessage();
				}
			}
		}
		
		if (!valid) {
			Utils.ShowMessageWindow("Atenção!", mensagemErro, 600, 180, MessageWindowType.ERROR);
		}
		
		return valid;
	}

	/**
	 * Usado para guarda os valores dos campos em oldValues para uso prosterior como em processamentoReverso, a principio esse procedimento
	 * vai ser disparado somente em tabelas que possuem processamento Direto e inverso programado, mas isso pode ser mudado no futuro
	 */
    public void saveFieldsOldValues() {
		SimpleRecord simpleRecord = new SimpleRecord();
		for (Field field : this.getFields()) {
			switch (field.getFieldType()) {
			case VARCHAR:
				simpleRecord.setValue(field.getFieldName(), field.getString());
				break;
			case TEXT:
				simpleRecord.setValue(field.getFieldName(), field.getString());
				break;
			case INTEGER:
				simpleRecord.setValue(field.getFieldName(), field.getInteger());
				break;
			case DOUBLE:
				simpleRecord.setValue(field.getFieldName(), field.getDouble());
				break;
			case FLOAT:
				simpleRecord.setValue(field.getFieldName(), field.getFloat());
				break;
			case DATE:
				simpleRecord.setValue(field.getFieldName(), field.getDate());
				break;
			case DATETIME:
				simpleRecord.setValue(field.getFieldName(), field.getDate());
				break;
			case BOOLEAN:
				simpleRecord.setValue(field.getFieldName(), field.getBoolean());
				break;
			}
		}
		
		this.setLoadingOldValues(true);
		for (Field field : this.getFields()) {
			switch (field.getFieldType()) {
			case VARCHAR:
				field.setValue(simpleRecord.getString(field.getFieldName()));
				break;
			case TEXT:
				field.setValue(simpleRecord.getString(field.getFieldName()));
				break;
			case INTEGER:
				field.setValue(simpleRecord.getInteger(field.getFieldName()));
				break;
			case DOUBLE:
				field.setValue(simpleRecord.getDouble(field.getFieldName()));
				break;
			case FLOAT:
				field.setValue(simpleRecord.getFloat(field.getFieldName()));
				break;
			case DATE:
				field.setValue(simpleRecord.getDate(field.getFieldName()));
				break;
			case DATETIME:
				field.setValue(simpleRecord.getDate(field.getFieldName()));
				break;
			case BOOLEAN:
				field.setValue(simpleRecord.getBoolean(field.getFieldName()));
				break;
			}
		}
		setLoadingOldValues(false);
    }
	
	public void afterUpdate() {
		// Caso os controles estejam ativos
		if (this.getActiveControl()) {
			// Atualiza registros filhos, caso existam
			this.updateChildrens();
			
			// Executa processamento inverso
			
			// Executa lancamentos inversos
		}
	}
	
	public boolean beforeDelete() {
		Boolean valid = true;
		String mensagemErro = "Exclusão não permitida!";
		
		// Caso os controle de validacao estejam ativos
		if (this.getActiveControl()) {
			// Caso a tabela possua um parent anexado em edição, tenta finalizar esta edição
			if ((this.getMasterTable()!=null) && ((this.getMasterTable().getTableStatus()==TableStatus.INSERT) || (this.getMasterTable().getTableStatus()==TableStatus.UPDATE))) {
				valid = this.getMasterTable().execute();
			}
		}
		
		// Caso ainda esteja valido, ou nao passou por post no pai ou executou com sucesso post no pai
		if (valid) {
			SpecialConditionOfDeleteEvent event = new SpecialConditionOfDeleteEvent(this);
			try {
				event.setTable(this);
				event.setValid(valid);
				event.setErrorMessage(mensagemErro);
				fireSpecialConditionOfDeleteEvent(event);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				if (!event.getValid()) {
					valid = false;
					mensagemErro = event.getErrorMessage();
				}
			}
		}
		
		if (!valid) {
			Utils.ShowMessageWindow("Atenção!", mensagemErro, 600, 180, MessageWindowType.ERROR);
		}
		else {
			// Caso os controles estejam ativos
			if (this.getActiveControl()) {
				// Executa processamento inverso
				
				// Executa lancamentos inversos
				
				// Exclui registros filhos
			}
		}
		
		return valid;
	}
	
	public Integer getIndexOf(String fieldName) {
		Integer retorno = null;
		Integer indice = 0;
		for (Field field: this.getFieldsInSelectList()) {
			if (field.getFieldName().equals(fieldName)) {
				retorno = indice;
				break;
			}
			indice++;
		}
		
		if (retorno==null) {
			throw new IllegalAccessError("Field " + fieldName + " does not exists in Table: " + this.getTableName());
		}
		return retorno;
	}

	/*
	 * Cria uma list do tipo KeyValue usando dois campos da tabela, usado em campos foreingSearch
	 */
	public List<KeyValue> getKeyValueList(String keyFieldName, String valueFieldName) {
		List<KeyValue> retorno = new ArrayList<KeyValue>();
		for (SimpleRecord simpleRecord: this.getSimpleRecordList()) {
			retorno.add(new KeyValue(simpleRecord.getString(keyFieldName),simpleRecord.getString(valueFieldName)));
		}
		return retorno;
	}
	
	public int importData(String sourceFileName) {
		Table tblTabela = this.getDatabase().loadTableByName(this.getTableName());
		tblTabela.setAuditing(false);
		Integer numeregi = 0;
		
		try {
	        BufferedReader buffRead = null;
	        buffRead = new BufferedReader(new FileReader(sourceFileName));

	        SimpleRecord simpleRecord = new SimpleRecord();
	        
	        String linha = "";
	        while (true) {
	            if (linha == null) {
	                break;
	            }
	            linha = buffRead.readLine();
	            
	            if (linha!=null) {
		            simpleRecord.loadFromJson(linha);
		            
		            tblTabela.select("*");
		            tblTabela.setFilter("uid", simpleRecord.getString("uid"));
		            tblTabela.loadData();
		            if (tblTabela.eof()) {
		            	tblTabela.insert();
		            	for (Field field : tblTabela.getFields()) {
		            		if (simpleRecord.fieldExists(field.getFieldName())) {
								switch (field.getFieldType()) {
								case VARCHAR:
									field.setString(simpleRecord.getString(field.getFieldName()));
									break;
								case INTEGER:
									field.setInteger(simpleRecord.getInteger(field.getFieldName()));
									break;
								case DOUBLE:
									field.setDouble(simpleRecord.getDouble(field.getFieldName()));
									break;
								case FLOAT:
									field.setFloat(simpleRecord.getFloat(field.getFieldName()));
									break;
								case DATETIME:
									field.setDate(simpleRecord.getDate(field.getFieldName()));
									break;
								case TEXT:
									field.setString(simpleRecord.getString(field.getFieldName()));
									break;
								case BOOLEAN:
									field.setBoolean(simpleRecord.getBoolean(field.getFieldName()));
									break;
								}
		            		}
						}
		            	tblTabela.execute();
		            	numeregi++;
		            }
		            else {
		            	if ((tblTabela.getDate("modidata")==null) || (simpleRecord.getDate("modidata")==null) || (tblTabela.getDate("modidata").compareTo(simpleRecord.getDate("modidata"))!=0)) {
			            	tblTabela.update();
			            	for (Field field : tblTabela.getFields()) {
			            		if (simpleRecord.fieldExists(field.getFieldName())) {
									switch (field.getFieldType()) {
									case VARCHAR:
										field.setString(simpleRecord.getString(field.getFieldName()));
										break;
									case INTEGER:
										field.setInteger(simpleRecord.getInteger(field.getFieldName()));
										break;
									case DOUBLE:
										field.setDouble(simpleRecord.getDouble(field.getFieldName()));
										break;
									case FLOAT:
										field.setFloat(simpleRecord.getFloat(field.getFieldName()));
										break;
									case DATETIME:
										field.setDate(simpleRecord.getDate(field.getFieldName()));
										break;
									case TEXT:
										field.setString(simpleRecord.getString(field.getFieldName()));
										break;
									case BOOLEAN:
										field.setBoolean(simpleRecord.getBoolean(field.getFieldName()));
										break;
									}
			            		}
			            	}
			            	tblTabela.setFilter("uid", simpleRecord.getString("uid"));
			            	tblTabela.execute();
			            	numeregi++;
		            	}
		            }
	            }
	        }
	        buffRead.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());					
		}
		
        return numeregi;
	}
	
	/*
	 *******************************************************************************
	 * ADICIONA EVENTOS PARA O TABLE
	 * *****************************************************************************
	 */
	/**
	 * Verifica se uma listener esta anexada ao Table
	 * @param listenerClass - Classe da listener que deseja verificar 
	 * @return
	 */
	public boolean hasListenerAttached(Class listenerClass) {
		boolean retorno = false;
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == listenerClass) {
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}
	
	/**
	 * Retorna um listener que esteja anexado ao Table
	 * @param listenerClass - Classe da listener que deseja ser retornada
	 * @return
	 */
	public Object getListenerAttached(Class listenerClass) {
		Object retorno = null;
		
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == listenerClass) {
				retorno = (Object) (listeners[i + 1]);
				break;
			}
		}
		
		return retorno;
	}
	
	/*
	 * BeforePost
	 */
	public void addBeforePostEventListener(BeforePostEventListener listener) {
		listenerList.add(BeforePostEventListener.class, listener);
	}

	public void removeBeforePostEventListener(BeforePostEventListener listener) {
		listenerList.remove(BeforePostEventListener.class, listener);
	}

	void fireBeforePostEvent(BeforePostEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == BeforePostEventListener.class) {
				((BeforePostEventListener) listeners[i + 1]).onBeforePost(event);
			}
		}
	}
	
	/*
	 * AfterPostEvent 
	 */
	public void addAfterPostEventListener(AfterPostEventListener listener) {
		listenerList.add(AfterPostEventListener.class, listener);
	}

	public void removeAfterPostEventListener(AfterPostEventListener listener) {
		listenerList.remove(AfterPostEventListener.class, listener);
	}

	void fireAfterPostEvent(AfterPostEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterPostEventListener.class) {
				((AfterPostEventListener) listeners[i + 1]).onAfterPost(event);
			}
		}
	}
	
	/*
	 * AfterPostInsertEvent 
	 */
	public void addAfterPostInsertEventListener(AfterPostInsertEventListener listener) {
		listenerList.add(AfterPostInsertEventListener.class, listener);
	}

	public void removeAfterPostInsertEventListener(AfterPostInsertEventListener listener) {
		listenerList.remove(AfterPostInsertEventListener.class, listener);
	}

	void fireAfterPostInsertEvent(AfterPostInsertEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterPostInsertEventListener.class) {
				((AfterPostInsertEventListener) listeners[i + 1]).onAfterPostInsert(event);
			}
		}
	}
	
	/*
	 * AfterPostUpdateEvent 
	 */
	public void addAfterPostUpdateEventListener(AfterPostUpdateEventListener listener) {
		listenerList.add(AfterPostUpdateEventListener.class, listener);
	}

	public void removeAfterPostUpdateEventListener(AfterPostUpdateEventListener listener) {
		listenerList.remove(AfterPostUpdateEventListener.class, listener);
	}

	void fireAfterPostUpdateEvent(AfterPostUpdateEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterPostUpdateEventListener.class) {
				((AfterPostUpdateEventListener) listeners[i + 1]).onAfterPostUpdate(event);
			}
		}
	}
	/*
	 * Adicionando evento para ser disparando para gerar valores inicias para os campos da tabela
	 */
	public void addInitialValueEventListener(InitialValueEventListener listener) {
		listenerList.add(InitialValueEventListener.class, listener);
	}

	public void removeInitialValueEventListener(InitialValueEventListener listener) {
		listenerList.remove(InitialValueEventListener.class, listener);
	}

	void fireInitialValueEvent(InitialValueEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == InitialValueEventListener.class) {
				((InitialValueEventListener) listeners[i + 1]).onInitialValue(event);
			}
		}
	}
	
	/*
	 * Condicao especial de inclusao
	 */
	public void addSpecialConditionOfInsertEventListener(SpecialConditionOfInsertEventListener listener) {
		listenerList.add(SpecialConditionOfInsertEventListener.class, listener);
	}

	public void removeSpecialConditionOfInsertEventListener(SpecialConditionOfInsertEventListener listener) {
		listenerList.remove(SpecialConditionOfInsertEventListener.class, listener);
	}

	void fireSpecialConditionOfInsertEvent(SpecialConditionOfInsertEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == SpecialConditionOfInsertEventListener.class) {
				((SpecialConditionOfInsertEventListener) listeners[i + 1]).onSpecialConditionOfInsert(event);
			}
		}
	}
	
	/*
	 * Condicao especial de modificacao
	 */
	public void addSpecialConditionOfUpdateEventListener(SpecialConditionOfUpdateEventListener listener) {
		listenerList.add(SpecialConditionOfUpdateEventListener.class, listener);
	}

	public void removeSpecialConditionOfUpdateEventListener(SpecialConditionOfUpdateEventListener listener) {
		listenerList.remove(SpecialConditionOfUpdateEventListener.class, listener);
	}

	void fireSpecialConditionOfUpdateEvent(SpecialConditionOfUpdateEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == SpecialConditionOfUpdateEventListener.class) {
				((SpecialConditionOfUpdateEventListener) listeners[i + 1]).onSpecialConditionOfUpdate(event);
			}
		}
	}
	
	/*
	 * Condicao especial de exclusão
	 */
	public void addSpecialConditionOfDeleteEventListener(SpecialConditionOfDeleteEventListener listener) {
		listenerList.add(SpecialConditionOfDeleteEventListener.class, listener);
	}

	public void removeSpecialConditionOfDeleteEventListener(SpecialConditionOfDeleteEventListener listener) {
		listenerList.remove(SpecialConditionOfDeleteEventListener.class, listener);
	}

	void fireSpecialConditionOfDeleteEvent(SpecialConditionOfDeleteEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == SpecialConditionOfDeleteEventListener.class) {
				((SpecialConditionOfDeleteEventListener) listeners[i + 1]).onSpecialConditionOfDelete(event);
			}
		}
	}

	/**
	 * Inclui um evento para ser disparado apos o processo insert() da tabela ser executado. 
	 * @param listener
	 */
	public void addAfterInsertEventListener(AfterInsertEventListener listener) {
		listenerList.add(AfterInsertEventListener.class, listener);
	}

	public void removeAfterInsertEventListener(AfterInsertEventListener listener) {
		listenerList.remove(AfterInsertEventListener.class, listener);
	}

	void fireAfterInsertEvent(AfterInsertEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterInsertEventListener.class) {
				((AfterInsertEventListener) listeners[i + 1]).onAfterInsert(event);
			}
		}
	}
	
	/**
	 * Inclui um evento para ser disparado apos o processo filter() da tabela ser executado. 
	 * @param listener
	 */
	public void addAfterFilterEventListener(AfterFilterEventListener listener) {
		listenerList.add(AfterFilterEventListener.class, listener);
	}

	public void removeAfterFilterEventListener(AfterFilterEventListener listener) {
		listenerList.remove(AfterFilterEventListener.class, listener);
	}

	void fireAfterFilterEvent(AfterFilterEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterFilterEventListener.class) {
				((AfterFilterEventListener) listeners[i + 1]).onAfterFilter(event);
			}
		}
	}
	
	/*
	 * Processamento direto
	 */
	public void addDirectProcessingEventListener(DirectProcessingEventListener listener) {
		listenerList.add(DirectProcessingEventListener.class, listener);
		
		// Usado para indicar que a tabela possui processamento direto ou reverso
		this.setHasProcessing(true);
	}

	public void removeDirectProcessingEventListener(DirectProcessingEventListener listener) {
		listenerList.remove(DirectProcessingEventListener.class, listener);
	}

	void fireDirectProcessingEvent(DirectProcessingEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == DirectProcessingEventListener.class) {
				((DirectProcessingEventListener) listeners[i + 1]).onDirectProcessing(event);
			}
		}
	}
	
	/*
	 * Processamento reverso
	 */
	public void addReverseProcessingEventListener(ReverseProcessingEventListener listener) {
		listenerList.add(ReverseProcessingEventListener.class, listener);
		
		// Usado para indicar que a tabela possui processamento direto ou reverso
		this.setHasProcessing(true);
	}

	public void removeReverseProcessingEventListener(ReverseProcessingEventListener listener) {
		listenerList.remove(ReverseProcessingEventListener.class, listener);
	}

	void fireReverseProcessingEvent(ReverseProcessingEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ReverseProcessingEventListener.class) {
				((ReverseProcessingEventListener) listeners[i + 1]).onReverseProcessing(event);
			}
		}
	}
	
	/*
	 * 
	 */
	/*
	 * Processamento reverso
	 */
	public void addAfterUpdateEventListener(AfterUpdateEventListener listener) {
		listenerList.add(AfterUpdateEventListener.class, listener);
	}

	public void removeAfterUpdateEventListener(AfterUpdateEventListener listener) {
		listenerList.remove(AfterUpdateEventListener.class, listener);
	}

	void fireAfterUpdateEvent(AfterUpdateEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterUpdateEventListener.class) {
				((AfterUpdateEventListener) listeners[i + 1]).onAfterUpdate(event);
			}
		}
	}

	/**
	 * Evento disparado antes de limpar os dados da tabela e carregar os registros
	 * @param listener
	 */
	public void addBeforeLoadDataEventListener(BeforeLoadDataEventListener listener) {
		listenerList.add(BeforeLoadDataEventListener.class, listener);
	}

	public void removeBeforeLoadDataEventListener(BeforeLoadDataEventListener listener) {
		listenerList.remove(BeforeLoadDataEventListener.class, listener);
	}

	void fireBeforeLoadDataEvent(BeforeLoadDataEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == BeforeLoadDataEventListener.class) {
				((BeforeLoadDataEventListener) listeners[i + 1]).onBeforeLoadData(event);
			}
		}
	}
	
	/**
	 * Evento disparado apos a checagem da tabela onde é criada a tabela, campos e indices, quando necessario
	 * @param listener
	 */
	public void addAfterCheckTableEventListener(AfterCheckTableEventListener listener) {
		listenerList.add(AfterCheckTableEventListener.class, listener);
	}

	public void removeAfterCheckTableEventListener(AfterCheckTableEventListener listener) {
		listenerList.remove(AfterCheckTableEventListener.class, listener);
	}

	void fireAfterCheckTableEvent(AfterCheckTableEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == AfterCheckTableEventListener.class) {
				((AfterCheckTableEventListener) listeners[i + 1]).onAfterCheckTable(event);
			}
		}
	}
}

// Incluir campos autoRepetir
// Liga e desliga validacao dos campos
// Liga e desliga executa lancamentos
// Liga e desliga executa processamento
// Insert em sequencia

// Marcar registros no grid
// Ordem inicial

// Verifica o processamentos no insert, update, delete
// Verifica o lancamento automatico no insert, update, delete
// Pega o valor autoincremental do ultimo lancamento incluido usando uid
// Quando chamar o update passando o uid, carrega o registro usando outra table

// Quando chamar o update, desfaz os lancamentos, processamentos
// Caso seja feito o post, refaz os lancamentos e processamentos
// Caso seja abandonado o update com cancel, refaz os lancamentos e processamentos

// Inclui campos com pesquisa externa
// Inclui campos com checagem de existencia
// Inclui evento para ser disparado apos a checagem de existencia, para atualizar campos que forem necessarios

// Analizar o que acontece no insert do delphi
// Analizar o que acontece no edit do delphi
// Analizar a sequencia de acoes que acontece no delete do delphi
// Analizar a sequencia de acoes que acontece no cancel do delphi

// Criar evento para gerar valor inicial, na tabela
// Incluir eventos de condicoes para insert
// Incluir evento de condicoes para update
// Incluir evento de condicoes para delete

// Verificar como atualizar conteudo de campos apos checagem de existencia, apos checar se o codigo de um fornecedor
// existe, posso querer pegar o nome, telefone, endereco entre outras informacoes do fornecedor

// Campo do tipo expressao devem ser capazes de pegar o conteudo de algum campo de uma tabela onde esta sendo feito uma checagem de existencia

// Posso fazar update de uma tabela que nao esteja em select, assim nao preciso pegar os dados da tabela 

// Posso fazer update de uma tabela que esteja em select, pego os dados do registro que esta sendo editado da tabela para ser apresentado 
// em um form, por esse motivo todos os campos devem ser coletados da tabela


/*
 * 
 */
// PROCESSAMENTO ****************
// Processamento e uma classe que possui dois eventos processamento direto e processamento inverso
// Processamento em table pai, deve alterar em tempo real o conteudo dos campos no form (caso esteja trabalhando com form)
// Processamento em outras tabelas que nao sejam o pai, abre a tabela usando o relacionamento
// O ideal no processamento seria poder atualizar apenas o conteudo dos campos de acordo com o que se deseja, o proprio table faz o post 
// na tabela alvo do processamento
// Exemplo de processamento:
//Processamento no parent (pai)
// Table tableAlvo = event.getTable().getParent();
// tableAlvo.update;
// tableAlvo.setInteger(tableAlvo.getInteger("quantidade")+event.gettable.getInteger("quantidade")); 
// tableAlvo.execute;
//
//Processamento em outra tabela 
// Table tblEspecialidades = ((DatabaseApp) event.getTable.getDatabase).loadEspecialidades;
// tblEspecialidades.update(event.getTable().getString("codiespe"));
// tblEspecialidades.setInteger(tblEspecialidades.getInteger("numemedi")+1);
// tblEspecialidades.execute;


/*
 * TRABALHANDO COM ARQUIVOS FILHOS 
 */
/*
    //Definicao dos childrens no momento da definicao da estrutura
    load Table loadNotaFiscal() {
        Table tblNotaFiscal = new Table(this);
        tblNotaFiscal.setTableName("notafiscal");
        tblNotaFiscal.addField(.....
        tblNotaFiscal.setPrimaryKey(...

        tblNotaFiscal.addTableChildren(loadProdNota(), "uidnota", true, true);
        tblNotaFiscal.addTableChilfren(loadParcelas(), "uidnota", true, true); 

        return TblNotaFiscal;
    }

    //Carregando childrens a partir do pai
    Table tblNotaFiscal = database.loadNotaFiscal();
    Table tblProdNota = tblNotaFiscal.getTableChildren("prodnota");
    Table tblParcelas = tblNotaFiscal.getTableChildren("parcelas");

    //Definicao dos childrens no momento do instanciamento
    Table tblNotaFiscal = database.loadNotaFiscal();

    // Carregamento e definicao de um novo children no instanciamento
    Table tblProdNota = database.loadProdNota();
    tblNotaFiscal.addTableChildren(tblProdNota, "uidnota", true, true);

    // Carregamento e definicao de um novo children no instanciamento
    Table tblParcelas = database.loadParcelas();
    tblNotaFiscal.addTableChildren(tblParcelas, "uidnota", true, true);

    //Quando executar loadRecord de uma tabela que tenha filhos
    //Carrega os dados das tabelas filhos que correspondem ao registro pai que esta sendo carregado
*/



