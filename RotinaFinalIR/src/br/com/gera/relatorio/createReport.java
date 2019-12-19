package br.com.gera.relatorio;

import java.io.InputStream;
//import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperFillManager;

/*
 * jars necessários para gerar e exportar o relatório
 * acessando o banco de dados
 * 
 * commons-beanutils-1.8.0
 * commons-collections-2.1.1
 * commons-digester-1.7
 * commons-javaflow-20060411
 * commons-logging-1.0.4
 * groovy-all-1.7.5
 * iText-2.1.7
 * jasperreports-4.5.0 (any version)
 * png-encoder-1.5
 * poi-3.7-20101029 (Or poi-3.6)
 * ojdbc
 * 
 * */

public class createReport {
	
	public void geraRelatiorio() {
		
		//String diretorio = "C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\";
		String diretorio = "C:\\FTP_SANKHYA\\RelatoriosAgendados\\IRSite\\";
		Connection conn = (Connection) new OracleCon().getConnection();
		ResultSet result = null;
		Statement stmt = null;
		//Conexao com o banco
		//consulta lista de parceiros
		try {
			stmt = conn.createStatement();
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Realizando consulta...");
		try {
			//result = stmt.executeQuery("select codparc from dimob_loc where codparc = 47 group by codparc order by codparc");
			result = stmt.executeQuery("select codparc from dimob_loc group by codparc order by codparc");
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Armazenando resultado da consulta em uma arraylist");
		ArrayList<Map<String, Object>> parceiros = new ArrayList<Map<String, Object>>();
		
		try {
			while(result.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
				for(int i = 0; i < result.getFetchSize(); i++) {
					row.put("PARCLOCADOR", result.getObject("codparc"));
				}
				parceiros.add(row);
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Dados armazenados com sucesso!");
		System.out.println("Imprimindo conteudo da ArrayList");
		
		for(int i = 0; i < parceiros.size(); i++) {
			System.out.println(parceiros.get(i));
		}
		
		try {
			for(int i = 0; i < parceiros.size(); i++) {
				InputStream stream = getClass().getResourceAsStream("/br/com/gera/relatorio/RelacaoRendimentos-DIMOB_NEW.jasper");
				JasperPrint jp = JasperFillManager.fillReport(stream, parceiros.get(i), conn);
				JasperExportManager.exportReportToPdfFile(jp, diretorio + parceiros.get(i).get("PARCLOCADOR") + ".pdf");
				System.out.println(parceiros.get(i).get("PARCLOCADOR"));
				System.out.println("Arquivo criado com sucesso!");
			}
		}catch (JRException e) {
			e.printStackTrace();
		}	
	}
}
