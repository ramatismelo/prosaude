package com.evolucao.rmlibrary.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.evolucao.rmlibrary.database.events.CommandEvent;
import com.evolucao.rmlibrary.database.events.LoadRmGridEvent;
import com.evolucao.rmlibrary.database.events.LoadTableEvent;
import com.evolucao.rmlibrary.database.model.User;
import com.evolucao.rmlibrary.database.registry.CommandRegistry;
import com.evolucao.rmlibrary.events.ApplicationEventBus;
import com.evolucao.rmlibrary.ui.production.RmGrid;
import com.vaadin.server.VaadinService;

/*
    // Sendo o nome de banco de dados "prosaude", usuario: "usuario", senha: "rataplan"
    // altere os arquivo a baixo para os seguintes parametros:
    //  
    // Arquivo /WEB-INF/web.xml
    <resource-ref>
       <description>DB Connection</description>
       <res-ref-name>jdbc/prosaude</res-ref-name>
       <res-type>javax.sql.DataSource</res-type>
       <res-auth>Container</res-auth>
       <res-sharing-scope>Shareable</res-sharing-scope>
    </resource-ref>
    
    // Arquivo /META-INF/context.xml
    <Context> 
       <Resource auth="Container"
          driverClassName="com.mysql.jdbc.Driver"
          maxActive="100" maxIdle="30" maxWait="10000"
          name="jdbc/prosaude"  
          type="javax.sql.DataSource"
          url="jdbc:mysql://localhost:3306/prosaude"  
          username="usuario" 
          password="rataplan" />
    </Context>
 */

public class Database {
	public String testeUid = "";
	//public static Database Instance;
	private final ApplicationEventBus applicationEventbus = new ApplicationEventBus();
	
	List<TableRegistry> tableRegistryList = new ArrayList<TableRegistry>();
	List<RmGridRegistry> rmGridRegistryList = new ArrayList<RmGridRegistry>();
	List<CommandRegistry> commandRegistryList = new ArrayList<CommandRegistry>();
	
	private InitialContext finitialContext = null;
	private DataSource fdataSource = null;
	private Connection fconnection = null;
	private Statement fstatement = null;
	private ResultSet fresultSet = null;

	private String fdatabaseServer = null;
	private String fdatabaseName = null;
	private String fuserName = null;
	private String fpassword = null;
	
	private List<Table> tables = new ArrayList<Table>();

	// Tipo de acesso ao banco de dados, 0 - diretor, 1 - pool
	private int ftypeAccess = 0;
	
	private UserSystemLogged userSystemLogged = new UserSystemLogged();

    public ApplicationEventBus getApplicationEventbus() {
    	return this.applicationEventbus;
    }

    public void setCommandRegistry(List<CommandRegistry> commandRegistry) {
		this.commandRegistryList = commandRegistry;
	}
	public List<CommandRegistry> getCommandRegistryList() {
		return this.commandRegistryList;
	}
	
	public void setTableRegistryList(List<TableRegistry> tableRegistry) {
		this.tableRegistryList = tableRegistry;
	}
	public List<TableRegistry> getTableRegistryList() {
		return this.tableRegistryList;
	}
	
	public void setRmGridRegistryList(List<RmGridRegistry> rmGridRegistry) {
		this.rmGridRegistryList = rmGridRegistry;
	}
	public List<RmGridRegistry> getRmGridRegistryList() {
		return this.rmGridRegistryList;
	}
	
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	public List<Table> getTables() {
		return this.tables;
	}
	
	/*
	public Table loadTable(String tableName) {
		Table retorno = null;
		for (Table table : this.getTables()) {
			if (table.getTableName()==tableName) {
				retorno = table;
				break;
			}
		}
		
		if (retorno == null) {
			throw new IllegalAccessError("Table called " + tableName + " not found in loadTable!");
		}
		
		return retorno;
	}
	*/
	
	/*
	public void addTable(Table table) {
		this.getTables().add(table);
	}
	*/
	
	public void setInitialContext(InitialContext initialContext) {
		this.finitialContext = initialContext;
	}

	public InitialContext getInitialContext() {
		return this.finitialContext;
	}

	public void setDataSource(DataSource dataSource) {
		this.fdataSource = dataSource;
	}

	public DataSource getDataSource() {
		return this.fdataSource;
	}

	public void setConnection(Connection connection) {
		this.fconnection = connection;
	}

	public Connection getConnection() {
		return this.fconnection;
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

	public void setDatabaseServer(String databaseServer) {
		this.fdatabaseServer = databaseServer;
	}

	public String getDatabaseServer() {
		return this.fdatabaseServer;
	}

	public void setDatabaseName(String databaseName) {
		this.fdatabaseName = databaseName;
	}

	public String getDatabaseName() {
		return this.fdatabaseName;
	}

	public void setUserName(String userName) {
		this.fuserName = userName;
	}

	public String getUserName() {
		return this.fuserName;
	}

	public void setPassword(String password) {
		this.fpassword = password;
	}

	public String getPassword() {
		return this.fpassword;
	}

	// Tipo de acesso ao banco de dados, 0 - diretor, 1 - pool
	public void setTypeAccess(int typeAccess) {
		this.ftypeAccess = typeAccess;
	}

	public int getTypeAccess() {
		return this.ftypeAccess;
	}
	
	public UserSystemLogged getUserSystemLogged() {
		return this.userSystemLogged;
	}
	public void setUserSystemLogged(UserSystemLogged userLogged) {
		this.userSystemLogged = userSystemLogged;
	}

	public Database() {
		//this.Instance = this;
		this.readConfigure();
	}
	
	public Database(String serverIP, String databaseName, String userName, String password) {
		//this.Instance = this;
		this.readConfigure();
		this.setDatabaseServer(serverIP);
		this.setDatabaseName(databaseName);
		this.setUserName(userName);
		this.setPassword(password);
		
		this.testeUid = UUID.randomUUID().toString().toUpperCase();
		System.out.println("Criou banco de dados: " + this.testeUid);
	}

	public void Atualiza(String databaseServer) {
		this.setDatabaseServer(databaseServer);
	}

	public void readConfigure() {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		File file = new File(basepath + "/config_properties.xml");
		if (file.exists()) {
			try {
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = (Document) dBuilder.parse(file);

				String databaseServer = doc.getElementsByTagName("databaseServer").item(0).getFirstChild().getNodeValue();
				this.setDatabaseServer(databaseServer);

				String databaseName = doc.getElementsByTagName("databaseName").item(0).getFirstChild().getNodeValue();
				this.setDatabaseName(databaseName);

				String userName = doc.getElementsByTagName("userName").item(0).getFirstChild().getNodeValue();
				this.setUserName(userName);

				String password = doc.getElementsByTagName("password").item(0).getFirstChild().getNodeValue();
				this.setPassword(password);
					
				String typeAccess = doc.getElementsByTagName("typeAccess").item(0).getFirstChild().getNodeValue();
				this.setTypeAccess(Integer.parseInt(typeAccess));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void openConnection() {
		// Acesso direto
		
		if (this.getTypeAccess() == 0) {
			String vurl = "jdbc:mysql://" + this.getDatabaseServer() + "/" + this.getDatabaseName();

			try {
				Class.forName("com.mysql.jdbc.Driver");
				//Class.forName("com.mysql.jdbc.Driver").newInstance();				
				fconnection = DriverManager.getConnection(vurl, this.getUserName(), this.getPassword());
				this.setConnection(fconnection);
			} catch (Exception e) {
				// NEVER catch exceptions like this
				System.out.println(e.getMessage());
			}
		}
		// Acesso atraves do pool
		else {
			try {
				InitialContext vinitialContext = new InitialContext();
				DataSource vdataSource = (DataSource) vinitialContext.lookup("java:comp/env/jdbc/" + this.getDatabaseName());
				Connection vconnection = vdataSource.getConnection();

				this.setInitialContext(vinitialContext);
				this.setDataSource(vdataSource);
				this.setConnection(vconnection);

			} catch (Exception e) {
				// NEVER catch exception like this
				System.out.println(e.getMessage());
			}
		}
	}

	public void closeConnection() {
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

		try {
			if (this.getConnection() != null) {
				this.getConnection().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (this.getInitialContext() != null) {
				this.getInitialContext().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Executa pesquisas no banco de dados
	 * 
	 * @param sql - Comando SELECT SQL para ser executado
	 * 
	 * @return - Retorna o ResultSet com os dados pesquisados
	 * 
	 * @throws
	 */
	public ResultSet executeSelect(String sql) throws SQLException {
		Statement sm = this.getConnection().createStatement();
		this.setStatement(sm);

		ResultSet rs = sm.executeQuery(sql);

		/*
		final Logger log = Logger.getLogger(ClassName.class.getName());
		log.log(Level.INFO, "SELECT EXECUTADO: " + sql);
		*/

		this.setResultSet(rs);

		return rs;
	}

	public void executeCommand(String sql) throws Exception {
		boolean closeConnection = false;
		try {
			if ((this.getConnection() == null) || (this.getConnection().isClosed())) {
				closeConnection = true;
				this.openConnection();
			}

			Statement sm = this.getConnection().createStatement();
			this.setStatement(sm);
			sm.execute(sql);
		} catch (Exception e) {
			String mensagem = "ERRO ao executar comando: [" + sql + "], Mensagem de erro: " + e.getMessage();
			System.out.println(mensagem);
			throw new Exception(mensagem);
		} finally {

			if (closeConnection) {
				this.closeConnection();
			}
		}
	}
	
    public User authenticate(String userName, String password) {
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole("admin");
        String email = user.getFirstName().toLowerCase() + "."
                + user.getLastName().toLowerCase() + "@"
                + "company.com";
        user.setEmail(email.replaceAll(" ", ""));
        user.setLocation("Location");
        user.setBio("Quis aute iure reprehenderit in voluptate velit esse."
                + "Cras mattis iudicium purus sit amet fermentum.");
        return user;
    }
    
	public TableRegistry addTableRegistry(String tableName) {
		TableRegistry tableRegistry = new TableRegistry();
		tableRegistry.setTableName(tableName);
		this.getTableRegistryList().add(tableRegistry);
		return tableRegistry;
	}
	
	public Table loadTableByName(String tableName) {
		Table tblRetorno = null;
		for (TableRegistry tableRegistry : this.getTableRegistryList()) {
			if (tableRegistry.getTableName().equalsIgnoreCase(tableName)) {
				LoadTableEvent event = new LoadTableEvent(this);
				Table tblTabela = new Table();
				tblTabela.setDatabase(this);
				event.setTable(tblTabela); 
				tableRegistry.fireLoadTableEvent(event);
				tblRetorno = event.getTable();
				break;
			}
		}
		
		if (tblRetorno==null) {
			throw new IllegalAccessError("Nao foi localizado a tabela: " + tableName + "!");			
		}
		return tblRetorno;
	}
	
	public RmGridRegistry addRmGridRegistry(String rmGridName) {
		RmGridRegistry rmGridRegistry = new RmGridRegistry();
		rmGridRegistry.setRmGridName(rmGridName);
		this.getRmGridRegistryList().add(rmGridRegistry);
		return rmGridRegistry;
	}

	public RmGrid loadRmGridByName(String rmGridName) {
		return loadRmGridByName(rmGridName, null);
	}
	
	public RmGrid loadRmGridByName(String rmGridName, Table table) {
		RmGrid rmGridRetorno = null;
		for (RmGridRegistry rmGridRegistry : this.getRmGridRegistryList()) {
			if (rmGridRegistry.getRmGridName().equalsIgnoreCase(rmGridName)) {
				LoadRmGridEvent event = new LoadRmGridEvent(this);
				RmGrid rmGrid = new RmGrid();
				event.setRmGrid(rmGrid);
				event.setTable(table);
				rmGridRegistry.fireLoadRmGridEvent(event);
				rmGridRetorno = event.getRmGrid();
				break;
			}
		}
		
		if (rmGridRetorno==null) {
			throw new IllegalAccessError("Nao foi localizado o RmGrid: " + rmGridName + "!");			
		}
		return rmGridRetorno;
	}
	
	public CommandRegistry addCommandRegistry(String commandName) {
		CommandRegistry command = new CommandRegistry();
		command.setCommandName(commandName);
		this.getCommandRegistryList().add(command);
		return command;
	}
	
	public void executeComandByName(String commandName) {
		Boolean executado = false;
		for (CommandRegistry commandRegistry: this.getCommandRegistryList()) {
			if ((commandRegistry.getCommandName()!=null) && (commandRegistry.getCommandName().equalsIgnoreCase(commandName))) {
				CommandEvent event = new CommandEvent(this);
				commandRegistry.fireCommandEvent(event);
				executado = true;
				break;
			}
		}
		
		if (!executado) {
			throw new IllegalAccessError("Nao foi localizado o command: " + commandName + "!");			
		}
	}
}
