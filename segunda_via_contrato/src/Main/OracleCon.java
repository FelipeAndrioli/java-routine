package Main;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class OracleCon {

	public java.sql.Connection getConnection() {
		
		System.out.println("Procurando por conexão com Banco de Dados Oracle...");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			System.out.println("Banco de Dados Oracle não encontrado!!!");
			e.printStackTrace();
		}
		
		System.out.println("Banco de Dados Oracle encontrado!");
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@177.85.37.157:1521:ORCL", "sankhya", "tecsis");
			/*
			 * Para a rotina conectar no banco estando dentro do servidor o endereço de IP muda,
			 * sendo assim a segunda variável conn armazena o IP de dentro do servidor e a primeira
			 * variável conn armazena o endereço IP de fora do servidor
			 * 
			 * */
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@10.100.69.243:1521:ORCL", "sankhya", "tecsis");
			System.out.println(conn);
		}catch(SQLException e) {
			System.out.println("Falha ao Conectar! Cheque o usuário e a senha");
			e.printStackTrace();
		}
		
		if(conn != null) {
			System.out.println("Conexão Bem Sucedida!");
		}else {
			System.out.println("Não foi possível se conectar!");
		}
		
		return conn;
		
	}
}
