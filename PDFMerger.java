package lseg.task1.merge.pdf;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import epam.training.config.Config;

public class PDFMerger {
	static Config config = new Config();

	public static void main(String[] args) throws IOException {

		File pathToPDFFolder = getPDFFolder();
		File[] listOfFiles = getListOfFiles(pathToPDFFolder);
		ArrayList<File> listOfFileObjects = new ArrayList<>();

		for (int i = 0; i < listOfFiles.length; i++) {
			listOfFileObjects.add(new File(getPDFFolderPath() + getSeparator() + listOfFiles[i].getName()));
		}

		PDFMergerUtility mergerUtility = new PDFMergerUtility();

		mergerUtility.setDestinationFileName(getPDFFolderPath() + getSeparator() + generatePDFName());

		// Add all pdf files to merge together

		for (File fileObject : listOfFileObjects) {
			mergerUtility.addSource(fileObject);
		}

		mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

		System.out.println("All PDFs merged together");
	}

	private static String getSeparator() {
		return config.getPropertyInstance().getProperty("pathSeparator");
	}

	private static String generatePDFName() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		return config.getPropertyInstance().getProperty("nameOfPDF") + dtf.format(now) + config.getPropertyInstance().getProperty("pdfExtension");
	}

	private static File[] getListOfFiles(File folder) {
		return folder.listFiles();
	}

	private static String getPDFFolderPath() {
		return config.getPropertyInstance().getProperty("pdfFolderPath");
	}

	private static File getPDFFolder() {
		return new File(getPDFFolderPath());
	}

}
