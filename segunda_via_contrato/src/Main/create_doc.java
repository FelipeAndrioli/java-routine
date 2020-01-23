package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.omg.CORBA_2_3.portable.InputStream;

/*
 * 
 * Vai gerar para todos os inquilinos ativos com a flag
 * responsável pelo boleto marcada
 * 
 * Nome do arquivo vai com o número do contrato
 * 
 * */

public class create_doc {

	public static void createDocFile(String filename) {
		
		Connection conn = (Connection) new OracleCon().getConnection();
		ResultSet result = null;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Realizando consulta...");
		
		/*
		//Execução de query para geração dos contratos
		try {
			result = stmt.executeQuery("");
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		
		*/
		
		try {
			
			File file = new File(filename);
			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
			
			XWPFDocument doc = new XWPFDocument();
			
			XWPFParagraph paragraphOne = doc.createParagraph();
		
			paragraphOne.setAlignment(ParagraphAlignment.CENTER);
			paragraphOne.setBorderBottom(Borders.SINGLE);
			paragraphOne.setBorderTop(Borders.SINGLE);
			paragraphOne.setBorderRight(Borders.SINGLE);
			paragraphOne.setBorderLeft(Borders.SINGLE);
			paragraphOne.setBorderBetween(Borders.SINGLE);
			
			XWPFRun paragraphOneRunOne = paragraphOne.createRun();
			
			paragraphOneRunOne.setBold(true);
			paragraphOneRunOne.setItalic(true);
			paragraphOneRunOne.setText("Hello world! This is paragraph one!");
			paragraphOneRunOne.addBreak();
			
			XWPFRun paragraphOneRunTwo = paragraphOne.createRun();
			paragraphOneRunTwo.setText("Run two!");
			paragraphOneRunTwo.setTextPosition(100);
			
			XWPFRun paragraphOneThree = paragraphOne.createRun();
			paragraphOneThree.setStrike(true);
			paragraphOneThree.setFontSize(20);
			paragraphOneThree.setSubscript(VerticalAlign.SUBSCRIPT);
			paragraphOneThree.setText("More text in paragraph one...");
			
			XWPFParagraph paragraphTwo = doc.createParagraph();
			paragraphTwo.setAlignment(ParagraphAlignment.DISTRIBUTE);
			paragraphTwo.setIndentationRight(200);
			XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();
			paragraphTwoRunOne.setText("And this is paragraph two");
			
			
			
			//FileInputStream picturePath = new FileInputStream("/segunda_via_contrato/src/Main/RocaDocLogo.jpg");
			
			FileInputStream picturePath = new FileInputStream("/segunda_via_contrato/src/Main/RocaDocLogo.jpg");
			
			
			XWPFParagraph paragraphThree = doc.createParagraph();
			paragraphThree.setAlignment(ParagraphAlignment.DISTRIBUTE);
			paragraphThree.setIndentationRight(200);
			XWPFRun paragraphThreeRunOne = paragraphThree.createRun();
			paragraphThreeRunOne.addPicture(picturePath, Document.PICTURE_TYPE_JPEG, "RocaDocLogo", Units.toEMU(200), Units.toEMU(200));
			
			/*
			 * InputStream stream = getClass().getResourceAsStream("/br/com/gera/relatorio/RelacaoRendimentos-DIMOB_NEW.jasper");
			 * 
			 * addPicture(java.io.InputStream pictureData, int pictureType, java.lang.String filename, int width, int height)
			 * Adds a picture to the run.
			 * 
			 * */
			
			//FileInputStream picturePath = new FileInputStream("/segunda_via_contrato/src/Main/RocaDocLogo.jpg");
			//paragraphOneRunOne.addPicture(picturePath, Document.PICTURE_TYPE_JPEG, "RocaDocLogo", Units.toEMU(200), Units.toEMU(200));
			
			
			
			try {
				doc.write(fos);
				doc.close();
				fos.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			
			System.out.println(file.getAbsolutePath() + " created successfully!");
			
		}catch(Exception e){
			
			e.printStackTrace();
			System.out.println("File not created!");
			
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String diretorio = "C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\";
		createDocFile("C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\DocxFile.docx");
		createDocFile("C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\DocFile.doc");
	}

}
