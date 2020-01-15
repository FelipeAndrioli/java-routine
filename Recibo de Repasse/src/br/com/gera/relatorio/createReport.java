package br.com.gera.relatorio;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperPrintManager;
//import net.sf.jasperreports.engine.JasperReport;
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
	
	public String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void geraRelatiorio() {
		
		//String teste = "C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\";// + getYear() + "\\" + getMonth();
		Path path = null;//Paths.get(teste);
		//String diretorio = "C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\"+ getYear() + "\\" + getMonth() + "\\";
		
		//Diretorios dentro do servidor
		String teste = "C:\\FTP_SANKHYA\\RelatoriosAgendados\\ReciboRepasse\\";
		String diretorio = "C:\\FTP_SANKHYA\\RelatoriosAgendados\\ReciboRepasse\\" + getYear() + "\\" + getMonth() + "\\";
		
		//String diretorio = "C:\\FTP_SANKHYA\\RelatoriosAgendados\\ReciboRepasse\\";
		Connection conn = (Connection) new OracleCon().getConnection();
		ResultSet result = null;
		Statement stmt = null;
		//Conexao com o banco
		//consulta lista de parceiros
		
		System.out.println(diretorio);
		
		try {
			stmt = conn.createStatement();
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Realizando consulta...");
		try {
			//result = stmt.executeQuery("select codparc from dimob_loc group by codparc order by codparc");
			
			//result = stmt.executeQuery("select nufin, codparc from tgffin where timorigem = 'RP' and recdesp = -1 and dtvenc = to_date(sysdate) and dhbaixa is not null");
			
			result = stmt.executeQuery("select nufin, codparc from tgffin where timorigem = 'RP' and dtvenc = to_date(sysdate) and recdesp = -1 ");
			//result = stmt.executeQuery("select nufin, codparc from tgffin where timorigem = 'RP' and dtvenc between to_date(sysdate) - 19 and to_date(sysdate) and recdesp = -1");
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println("Armazenando resultado da consulta em uma arraylist");
		ArrayList<Map<String, Object>> parceiros = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> nufin = new ArrayList<Map<String, Object>>();
		
		try {
			while(result.next()) {
				Map<String, Object> row_parceiros = new HashMap<String, Object>();
				Map<String, Object> row_nufin = new HashMap<String, Object>();
				for(int i = 0; i < result.getFetchSize(); i++) {
					row_parceiros.put("PARCLOCADOR", result.getObject("codparc"));
					row_nufin.put("NUF", result.getObject("nufin"));
				}
				parceiros.add(row_parceiros);
				nufin.add(row_nufin);
			}
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Dados armazenados com sucesso!");
		System.out.println("Imprimindo conteudo da ArrayList");
		
		for(int i = 0; i < nufin.size(); i++) {
			System.out.println(parceiros.get(i));
			System.out.println(nufin.get(i));
		}
	
		if(Files.exists(path = Paths.get(teste + getYear() + "\\" + getMonth()))) {
			//caso as pastas com ano e mês já existam
			System.out.println(path);
		}else if(Files.exists(path = Paths.get(teste + getYear()))){
			System.out.println("Diretório Inexistente");
			//Quando só existir o ano
			File dir = new File(path + "\\" + getMonth() + "\\");
			boolean successful = dir.mkdir();
			if(successful) {
				System.out.println("Diretório criado com sucesso!");
			}else {
				System.out.println("Falha ao criar diretório!");
			}
		}else if(Files.notExists(path = Paths.get(teste + getYear() + "\\" + getMonth()))){
			System.out.println("Diretório Inexistente");
			File dir = new File(teste + getYear() + "\\" + getMonth() + "\\");
			boolean success = dir.mkdirs();
			if(success) {
				System.out.println("Diretório criado com sucesso!");
			}else {
				System.out.println("Falha ao criar diretório");
			}
		}
		
		try {
			//InputStream stream = getClass().getResourceAsStream("/br/com/gera/relatorio/RECIBODOREPASSE(2).jrxml");
			for(int i = 0; i < nufin.size(); i++) { 
			
				InputStream stream = getClass().getResourceAsStream("/br/com/gera/relatorio/RECIBODOREPASSE(2).jasper");
				JasperPrint jp = JasperFillManager.fillReport(stream, nufin.get(i), conn);
				
				//JasperPrint jp = JasperFillManager.fillReport(stream, nufin.get(i), conn);
				
				//InputStream stream = getClass().getResourceAsStream("/br/com/gera/relatorio/RECIBODOREPASSE(2).jrxml"); 
				//JasperReport jasperReport = JasperCompileManager.compileReport(stream); 
				//JasperPrint jp = JasperFillManager.fillReport(jasperReport, nufin.get(i), conn);
				
				//JasperExportManager.exportReportToPdfFile(jp, diretorio + parceiros.get(i).get("PARCLOCADOR") + ".pdf");
				JasperExportManager.exportReportToPdfFile(jp, diretorio + nufin.get(i).get("NUF") + ".pdf");
				System.out.println(parceiros.get(i).get("PARCLOCADOR"));
				System.out.println(nufin.get(i).get("NUF"));
				System.out.println("Arquivo criado com sucesso!"); 
			
			} 
		}catch (JRException e) {
			e.printStackTrace(); 
		}		 
	}
}
