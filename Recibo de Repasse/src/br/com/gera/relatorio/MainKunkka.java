package br.com.gera.relatorio;

/*
 * 
 * Rotina para gerar de forma autom�tica os relat�rios de Repasse dos Propriet�rios
 * 
 * Como os relat�rios s�o gerados de forma mensal, foi necess�rio adaptar para que a 
 * rotina criasse diret�rios com o ano e m�s de gera��o dos relat�rios para que pudessem
 * ser armazenados com certa organiza��o e acessados pelo pessoal do site
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
