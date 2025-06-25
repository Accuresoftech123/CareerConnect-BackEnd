package com.example.service;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.entity.Receipt;
import com.example.repository.ReceiptRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ReceiptService {
	
	  @Autowired
	    private JavaMailSender mailSender;

	    @Autowired
	    private ReceiptRepository receiptRepository;

	    public void generateSendAndSaveReceipt(String email, double amount) throws Exception {

	        // 1️⃣ Create PDF
	        String fileName = "receipt_" + System.currentTimeMillis() + ".pdf";
	        String filePath = "C:/receipts/" + fileName;
	        createPdf(filePath, email, amount);

	        // 2️⃣ Send Email with PDF
	        sendEmailWithAttachment(email, filePath);

	        // 3️⃣ Save in DB
	        Receipt receipt = new Receipt();
	        receipt.setEmail(email);
	        receipt.setAmount(amount);
	        receipt.setFilePath(filePath);
	        receipt.setCreatedAt(LocalDateTime.now());
	        receiptRepository.save(receipt);
	    }

	    private void createPdf(String filePath, String email, double amount) throws Exception {
	        // Create parent directory if not exists
	        java.io.File file = new java.io.File(filePath);
	        java.io.File parentDir = file.getParentFile();
	        if (!parentDir.exists()) {
	            parentDir.mkdirs(); // creates the receipts folder
	        }

	        PdfWriter writer = new PdfWriter(filePath);
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);

	        document.add(new Paragraph("Receipt")
	                .setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));
	        document.add(new Paragraph(" "));
	        document.add(new Paragraph("Customer Email: " + email));
	        document.add(new Paragraph("Amount Paid: ₹" + amount));
	        document.add(new Paragraph("Date: " + LocalDateTime.now()));

	        document.close();
	    }
	    private void sendEmailWithAttachment(String toEmail, String attachmentPath) {
	        MimeMessage message = mailSender.createMimeMessage();
	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setTo(toEmail);
	            helper.setSubject("Your Payment Receipt");
	            helper.setText("Thanks for your payment. Please find the receipt attached.");

	            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
	            helper.addAttachment(file.getFilename(), file);

	            mailSender.send(message);

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }


}
