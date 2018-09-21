package com.evolucao.rmlibrary.database.dataprovider;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.SimpleRecord;
import com.evolucao.rmlibrary.database.Table;
import com.evolucao.rmlibrary.database.enumerators.AllowLike;
import com.evolucao.rmlibrary.database.model.Model10;
import com.evolucao.rmlibrary.ui.fields.RmComboBox;
import com.evolucao.weblibrary.ApplicationUI;
import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.ui.UI;

public class ComboBoxDataProvider extends AbstractBackEndDataProvider<SimpleRecord, String> {
    private static final long serialVersionUID = 1L;
    Table table = null;
    String tableName = null;
    String displayFieldName = null;
    String tagFieldName = null;
    String resultFieldName = null;
    String additionalFieldsToLoad = null;
    RmComboBox rmComboBox = null;
    
    public void setAdditionalFieldsToLoad(String additionalFieldsToLoad) {
    	this.additionalFieldsToLoad = additionalFieldsToLoad;
    }
    public String getAdditionalFieldsToLoad() {
    	return this.additionalFieldsToLoad;
    }
    
    public void setResultFieldName(String resultFieldName) {
    	this.resultFieldName = resultFieldName;
    }
    public String getResultFieldName() {
    	return this.resultFieldName;
    }
    
    public void setRmComboBox(RmComboBox rmComboBox) {
    	this.rmComboBox = rmComboBox;
    }
    public RmComboBox getRmComboBox() {
    	return this.rmComboBox;
    }
    
    public void setTable(Table table) {
    	this.table = table;
    }
    public Table getTable() {
    	return this.table;
    }
    
    public void setTableName(String tableName) {
    	this.tableName = tableName;
    }
    public String getTableName() {
    	return this.tableName;
    }
    
    public void setDisplayFieldName(String displayFieldName) {
    	this.displayFieldName = displayFieldName;
    }
    public String getDisplayFieldName() {
    	return this.displayFieldName;
    }
    
    public void setTagFieldName(String tagFieldName) {
    	this.tagFieldName = tagFieldName;
    }
    public String getTagFieldName() {
    	return this.tagFieldName;
    }
    
    public ComboBoxDataProvider(String tableName) {
		ApplicationUI ui = (ApplicationUI) UI.getCurrent();
		this.setTable(ui.database.loadTableByName(tableName));
    }
    
    @Override
    protected Stream<SimpleRecord> fetchFromBackEnd(Query<SimpleRecord, String> query) {
    	Stream<SimpleRecord> retorno = null;
    	
    	String filter = query.getFilter().orElse(null);
    	int limit  = query.getLimit();
    	int offset = query.getOffset();
    
    	if ((filter==null) && (this.getRmComboBox().getComboBox().getValue()!=null)) {
    		filter = this.getRmComboBox().getComboBox().getValue().getString(this.getDisplayFieldName()); 
    	}

    	String fieldList = this.getDisplayFieldName();
    	
    	//if (this.getRmComboBox().getField().getComboBox().getAdditionalFieldsToLoad()!=null) {
    	//	fieldList += ", " + this.getRmComboBox().getField().getComboBox().getAdditionalFieldsToLoad();
    	//}
    	
    	if (this.getAdditionalFieldsToLoad()!=null) {
    		fieldList += ", " + this.getAdditionalFieldsToLoad();
    	}
    	
    	if (!this.getResultFieldName().equalsIgnoreCase("uid")) {
    		fieldList += ", " + this.getResultFieldName();
    	}
    	
    	this.getTable().select(fieldList);
    	this.getTable().setLimit(limit);
    	this.getTable().setOffSet(offset);
    	this.getTable().setOrder(this.displayFieldName);
    	if (filter!=null) {
			filter = filter.trim();
			// Para que o combo funcione corretamente é necessario permitir o like nas pesquisas
			//this.getTable().fieldByName(this.getTagFieldName()).setAllowLike(AllowLike.BOTH);
			
        	String filtro = "";
        	if (filter!=null) {
        		filter = filter.trim();
        		Field field = this.getTable().fieldByName(this.getTagFieldName());
        		if (field.getAllowLike()==AllowLike.BOTH) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like '%"+ filter +"%')";
        		}
        		else if (field.getAllowLike()==AllowLike.BEGIN) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like \"%"+ filter +"\")";
        		}
        		else if (field.getAllowLike()==AllowLike.END) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like \""+ filter +"%\")";
        		}
        		else if (field.getAllowLike()==AllowLike.NONE) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like '"+ filter +"')";
        		}
        	}

        	if (this.getRmComboBox().getField().getComboBox().getWhere()!=null) {
        		if (!filtro.isEmpty()) {
        			filtro += " and ";
        		}
        		filtro+="(" + this.getRmComboBox().getField().getComboBox().getWhere() + ")";
        	}
        	
    		this.getTable().setWhere("(" + filtro + ")");
        	
    		//this.getTable().setFilter(this.getTagFieldName(), filter);
    	}
    	
    	
		//if (this.getRmComboBox().getField().getComboBox().getWhere()!=null) {
		//	this.getTable().setWhere("(" + this.getRmComboBox().getField().getComboBox().getWhere() + ")");
		//}
    	
    	//employeeService.fetchEmployees(query.getFilter().orElse(null), query.getLimit(), query.getOffset(), query.getSortOrders()).stream();
    	
		List<SimpleRecord> dataretorno = this.getTable().loadData();
		if (!this.getTable().eof()) {
			retorno = dataretorno.stream();
		}
		else {
			dataretorno = new ArrayList<SimpleRecord>();
			SimpleRecord simpleRecord = new SimpleRecord();
			simpleRecord.setString(this.getDisplayFieldName(), "NENHUMA CORESPONDENCIA ENCONTRADA");
			simpleRecord.setString("uid", "");
			if (!this.getResultFieldName().equalsIgnoreCase("uid")) {
				switch (this.getTable().fieldByName(this.getResultFieldName()).getFieldType()) {
				case VARCHAR:
					simpleRecord.setString(this.getResultFieldName(), "");
					break;
				case INTEGER:
					simpleRecord.setInteger(this.getResultFieldName(), 0);
					break;
				}
			}
			dataretorno.add(simpleRecord);
			
			retorno = dataretorno.stream();
		}
		
    	return retorno;
    }
    
    @Override
    protected int sizeInBackEnd(Query<SimpleRecord, String> query) {
    	//retorno = employeeService.countEmployees(query.getFilter().orElse(null));
    	int retorno = 0;
    	//this.getRmComboBox().getComboBox().clear();
    	
    	if (this.getTableName()!=null) {
    		ApplicationUI ui = (ApplicationUI) UI.getCurrent();

        	String filter = query.getFilter().orElse(null);
    		
        	SimpleRecord simpleRecord = this.getRmComboBox().getComboBox().getValue();
        	
        	if ((filter==null) && (this.getRmComboBox().getComboBox().getValue()!=null)) {
        		filter = this.getRmComboBox().getComboBox().getValue().getString(this.getDisplayFieldName()); 
        	}
        	
        	String comando = "select count(*) as qtd from " + this.getTableName();
        		
        	String filtro = "";
        	if (filter!=null) {
        		filter = filter.trim();
        		Field field = this.getTable().fieldByName(this.getTagFieldName());
        		if (field.getAllowLike()==AllowLike.BOTH) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like '%"+ filter +"%')";
        		}
        		else if (field.getAllowLike()==AllowLike.BEGIN) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like \"%"+ filter +"\")";
        		}
        		else if (field.getAllowLike()==AllowLike.END) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like \""+ filter +"%\")";
        		}
        		else if (field.getAllowLike()==AllowLike.NONE) {
        			filtro = "(" + this.getTableName() + "." + this.getTagFieldName() +" like '"+ filter +"')";
        		}
        	}
        		
        	if (this.getRmComboBox().getField().getComboBox().getWhere()!=null) {
        		if (!filtro.isEmpty()) {
        			filtro += " and ";
        		}
        		filtro+="(" + this.getRmComboBox().getField().getComboBox().getWhere() + ")";
        	}
        	
        	if (!filtro.isEmpty()) {
            	comando += " where " + filtro;
        	}

        	try {
        		ui.database.openConnection();
        		ResultSet resultSet = ui.database.executeSelect(comando);
        		if (resultSet.next()) {
        			retorno = resultSet.getInt("qtd");
        		}
        	}
        	catch (Exception e) {
        		System.out.println(e.getMessage());
        	}
        	finally {
        		ui.database.closeConnection();
        	}
    	}
    	
    	// Caso nao tenha encontrado registros, indica que pode criar uma linha
    	// para informar que nao foi encontrado registros
    	if (retorno==0) {
    		retorno=1;
    	}
    	
    	return retorno; 
    }
    
    @Override
    public Object getId(SimpleRecord item) {
    	//return item.getString("uid");
    	return item.getString(this.resultFieldName);
    }
}