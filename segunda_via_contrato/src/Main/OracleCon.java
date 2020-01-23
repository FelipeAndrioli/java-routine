package Main;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class OracleCon {

	public java.sql.Connection getConnection() {
		
		System.out.println("Procurando por conex�o com Banco de Dados Oracle...");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			System.out.println("Banco de Dados Oracle n�o encontrado!!!");
			e.printStackTrace();
		}
		
		System.out.println("Banco de Dados Oracle encontrado!");
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@177.85.37.157:1521:ORCL", "sankhya", "tecsis");
			/*
			 * Para a rotina conectar no banco estando dentro do servidor o endere�o de IP muda,
			 * sendo assim a segunda vari�vel conn armazena o IP de dentro do servidor e a primeira
			 * vari�vel conn armazena o endere�o IP de fora do servidor
			 * 
			 * */
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@10.100.69.243:1521:ORCL", "sankhya", "tecsis");
			System.out.println(conn);
		}catch(SQLException e) {
			System.out.println("Falha ao Conectar! Cheque o usu�rio e a senha");
			e.printStackTrace();
		}
		
		if(conn != null) {
			System.out.println("Conex�o Bem Sucedida!");
		}else {
			System.out.println("N�o foi poss�vel se conectar!");
		}
		
		return conn;
		
	}
}
