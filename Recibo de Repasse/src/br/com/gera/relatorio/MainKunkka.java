package br.com.gera.relatorio;

/*
 * 
 * Rotina para gerar de forma automática os relatórios de Repasse dos Proprietários
 * 
 * Como os relatórios são gerados de forma mensal, foi necessário adaptar para que a 
 * rotina criasse diretórios com o ano e mês de geração dos relatórios para que pudessem
 * ser armazenados com certa organização e acessados pelo pessoal do site
 * 
 * 
 * */

public class MainKunkka {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createReport cr = new createReport();
		cr.geraRelatiorio();
	}

}
