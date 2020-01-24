package Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.omg.CORBA_2_3.portable.InputStream;



import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;

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
		
			//======================= Creating Header ===============================
			
			XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
			if(headerFooterPolicy == null) {
				headerFooterPolicy = doc.createHeaderFooterPolicy();
			}
			
			XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
			XWPFParagraph headerParagraph = header.createParagraph();
			headerParagraph.setAlignment(ParagraphAlignment.CENTER);
			headerParagraph.setSpacingBeforeLines(0);
			XWPFRun headerParagraphRun = headerParagraph.createRun();
			
			File imageFile = new File("C:\\RocaDocLogo.jpg");
			int width = 120;
			int height = 85;
			String imgName = null;
			int imgFormat = 0;
			
			try {
				System.out.println("Coletando dados da imagem...");
				BufferedImage bimg = ImageIO.read(imageFile);
				imgName = imageFile.getName();
				imgFormat = Document.PICTURE_TYPE_JPEG;
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			
			headerParagraphRun.addBreak();
			headerParagraphRun.addPicture(new FileInputStream(imageFile), imgFormat, imgName, Units.toEMU(width), Units.toEMU(height));
			
			CTSectPr sectPr = doc.getDocument().getBody().getSectPr();
			if(sectPr == null) {
				sectPr = doc.getDocument().getBody().addNewSectPr();
			}
			
			CTPageMar pageMar = sectPr.getPgMar();
			if(pageMar == null) {
				pageMar = sectPr.addNewPgMar();
			}
			
			pageMar.setLeft(BigInteger.valueOf(0));
			pageMar.setRight(BigInteger.valueOf(0));
			pageMar.setTop(BigInteger.valueOf(0));
			pageMar.setBottom(BigInteger.valueOf(0));
			
			pageMar.setHeader(BigInteger.valueOf(908));
			
			/*TODO
			 * 
			 * Set the margin of the header to the minimum possible
			 * 
			 * Style of phrases to include in the middle of a paragraph
			 * different from the style of the rest
			 * 
			 * */
			
			//======================= End of the header ==============================
			
			XWPFParagraph paragraphOne = doc.createParagraph();
			paragraphOne.setAlignment(ParagraphAlignment.CENTER);
			paragraphOne.setFirstLineIndent(0);
			XWPFRun paragraphOneRunOne = paragraphOne.createRun();
			paragraphOneRunOne.setText("Teste de cabeçalho");
			
			/*
			
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
			
			XWPFParagraph paragraphThree = doc.createParagraph();
			paragraphThree.setAlignment(ParagraphAlignment.CENTER);
			paragraphThree.setIndentationRight(200);
			XWPFRun paragraphThreeRunOne = paragraphThree.createRun();
			
			File imageFile = new File("C:\\RocaDocLogo.jpg");
			int width = 100;
			int height = 100;
			String imgName = null;
			int imgFormat = 0;
			
			try {
				System.out.println("Coletando dados da imagem...");
				BufferedImage bimg1 = ImageIO.read(imageFile);
				//width = bimg1.getWidth();
				//height = bimg1.getHeight();
				imgName = imageFile.getName();
				imgFormat = Document.PICTURE_TYPE_JPEG;
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			
			paragraphThreeRunOne.addBreak();
			paragraphThreeRunOne.addPicture(new FileInputStream(imageFile), imgFormat, imgName, Units.toEMU(width), Units.toEMU(height));	
			
			*/
			
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

		createDocFile("C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\DocxFile.docx");
		//createDocFile("C:\\Users\\felipe.andrioli.ROCA-IMOVEIS\\Desktop\\Destino\\DocFile.doc");
	}

}
