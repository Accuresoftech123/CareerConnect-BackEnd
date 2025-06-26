package com.example.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.entity.JobSeeker;
import com.example.entity.Payment;
import com.example.entity.Receipt;
import com.example.repository.ReceiptRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class ReceiptService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ReceiptRepository receiptRepository;

    // Generate, email, and save receipt
    public void generateSendAndSaveReceipt(String email, double amount, Payment payment, JobSeeker jobSeeker) throws Exception {

        // 1️⃣ Create PDF in memory
        byte[] pdfBytes = createPdfBytes(email, amount);

        // 2️⃣ Send Email with PDF attachment
        String fileName = "receipt_" + System.currentTimeMillis() + ".pdf";
        sendEmailWithAttachmentBytes(email, pdfBytes, fileName);

        // 3️⃣ Save Receipt in DB
        Receipt receipt = new Receipt();
        receipt.setEmail(email);
        receipt.setAmount(amount);
        receipt.setCreatedAt(LocalDateTime.now());
        receipt.setPayment(payment);
        receipt.setJobSeeker(jobSeeker);

        receiptRepository.save(receipt);
    }

    // Create PDF as byte array
    private byte[] createPdfBytes(String email, double amount) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Receipt")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Customer Email: " + email));
        document.add(new Paragraph("Amount Paid: ₹" + amount));
        document.add(new Paragraph("Date: " + LocalDateTime.now()));

        document.close();
        return out.toByteArray();
    }

    // Send email with PDF attachment (from byte array)
    private void sendEmailWithAttachmentBytes(String toEmail, byte[] pdfBytes, String fileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Your Payment Receipt");
            helper.setText("Thanks for your payment. Please find your receipt attached.");

            DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
            helper.addAttachment(fileName, dataSource);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Find receipts by JobSeeker ID
    public List<Receipt> getReceiptsByJobSeekerId(int jobSeekerId) {
        return receiptRepository.findByJobSeeker_Id(jobSeekerId);
    }
}
