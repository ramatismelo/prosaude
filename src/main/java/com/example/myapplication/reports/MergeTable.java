package com.example.myapplication.reports;

import com.evolucao.rmlibrary.database.Field;
import com.evolucao.rmlibrary.database.Table;
import com.example.myapplication.MyUI;
import com.vaadin.ui.UI;

public class MergeTable {
	public MergeTable() {
		MyUI ui = (MyUI) UI.getCurrent();
		
		Integer incluidos = 0;
		Integer modificados = 0;
		
		// Associados matriz
		Table tblAssociados = ui.database.loadTableByName("associados");
		tblAssociados.setAuditing(false);
		
		// Associados aparecida
		Table tblAssociados2 = ui.database.loadTableByName("associados2");
		tblAssociados2.setAuditing(false);
		
		Table tblAssociados3 = ui.database.loadTableByName("associados2");
		tblAssociados3.setAuditing(false);
		
		try {
			ui.database.openConnection();
			ui.database.executeCommand("update associados2 set processamento=null");
			
			boolean continuar = true;
			while (continuar) {
				tblAssociados2.select("*");
				tblAssociados2.setDebugQuery(true);
				tblAssociados2.setLimit(100);
				tblAssociados2.setWhere("(associados2.processamento is null)");
				tblAssociados2.loadData();
				if (tblAssociados2.eof()) {
					break;
				}
				else {
					while (!tblAssociados2.eof()) {
						// Procura o cadastro na matriz, 
						tblAssociados.select("*");
						tblAssociados.setFilter("nome", tblAssociados2.getString("nome"));
						tblAssociados.setFilter("nasc", tblAssociados2.getDate("nasc"));
						tblAssociados.loadData();
						
						// Caso nao exista, cria
						if (tblAssociados.eof()) {
							tblAssociados.insert();
							for (Field field: tblAssociados.getFields()) {
								if ((tblAssociados2.fieldExist(field.getFieldName())!=null) && (!field.getFieldName().equalsIgnoreCase("sequencia"))) {
									switch (field.getFieldType()) {
									case VARCHAR:
										if (tblAssociados2.getString(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getString(field.getFieldName()));
										}
										else {
											field.setString(null);
										}
										break;
									case TEXT:
										if (tblAssociados2.getString(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getString(field.getFieldName()));
										}
										else {
											field.setString(null);
										}
										break;
									case INTEGER:
										if (tblAssociados2.getInteger(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getInteger(field.getFieldName()));
										}
										else {
											field.setInteger(null);
										}
										break;
									case FLOAT:
										if (tblAssociados2.getFloat(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getFloat(field.getFieldName()));
										}
										else {
											field.setFloat(null);
										}
										break;
									case DOUBLE:
										if (tblAssociados2.getDouble(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getDouble(field.getFieldName()));
										}
										else {
											field.setDouble(null);
										}
										break;
									case DATE:
										if (tblAssociados2.getDate(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getDate(field.getFieldName()));
										}
										else {
											field.setDate(null);
										}
										break;
									case DATETIME:
										if (tblAssociados2.getDate(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getDate(field.getFieldName()));
										}
										else {
											field.setDate(null);
										}
										break;
									case BOOLEAN:
										if (tblAssociados2.getBoolean(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getBoolean(field.getFieldName()));
										}
										else {
											field.setBoolean(null);
										}
										break;
									}
								}
							}
							tblAssociados.execute();
							incluidos++;
						}
						// Caso exista atualiza, mas somente se aqui for mais atual
						else if (tblAssociados.getDate("modidata").before(tblAssociados2.getDate("modidata"))) {
							tblAssociados.update();
							for (Field field: tblAssociados.getFields()) {
								if ((tblAssociados2.fieldExist(field.getFieldName())!=null) && (!field.getFieldName().equalsIgnoreCase("sequencia"))) {
									switch (field.getFieldType()) {
									case VARCHAR:
										if (tblAssociados2.getString(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getString(field.getFieldName()));
										}
										else {
											field.setString(null);
										}
										break;
									case TEXT:
										if (tblAssociados2.getString(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getString(field.getFieldName()));
										}
										else {
											field.setString(null);
										}
										break;
									case INTEGER:
										if (tblAssociados2.getInteger(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getInteger(field.getFieldName()));
										}
										else {
											field.setInteger(null);
										}
										break;
									case FLOAT:
										if (tblAssociados2.getFloat(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getFloat(field.getFieldName()));
										}
										else {
											field.setFloat(null);
										}
										break;
									case DOUBLE:
										if (tblAssociados2.getDouble(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getDouble(field.getFieldName()));
										}
										else {
											field.setDouble(null);
										}
										break;
									case DATE:
										if (tblAssociados2.getDate(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getDate(field.getFieldName()));
										}
										else {
											field.setDate(null);
										}
										break;
									case DATETIME:
										if (tblAssociados2.getDate(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getDate(field.getFieldName()));
										}
										else {
											field.setDate(null);
										}
										break;
									case BOOLEAN:
										if (tblAssociados2.getBoolean(field.getFieldName())!=null) {
											field.setValue(tblAssociados2.getBoolean(field.getFieldName()));
										}
										else {
											field.setBoolean(null);
										}
										break;
									}
								}
							}
							tblAssociados.execute();
							modificados++;
						}
						
						tblAssociados3.update();
						tblAssociados3.setValue("processamento", "1");
						tblAssociados3.setFilter("uid", tblAssociados2.getString("uid"));
						tblAssociados3.execute();
						
						tblAssociados2.next();
					}
				}
			}
			System.out.println("processo concluido: " + incluidos + " incluidos - " + modificados + " modificados");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			ui.database.closeConnection();
		}
	}
}
