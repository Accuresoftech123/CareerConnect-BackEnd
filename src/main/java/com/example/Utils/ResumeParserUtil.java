package com.example.Utils;

import com.example.entity.profile.Education;
import com.example.entity.profile.Experience;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeParserUtil {

    public static String extractTextFromPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream(); PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public static String extractName(String content) {
        String[] lines = content.split("\n");
        return lines.length > 0 ? lines[0].trim() : "Not found";
    }

    public static String extractEmail(String content) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "Not found";
    }

    public static String extractPhone(String content) {
        Pattern pattern = Pattern.compile("(\\+\\d{1,3}[- ]?)?\\d{10}");
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "Not found";
    }

    public static List<String> extractSkills(String content) {
        List<String> skills = new ArrayList<>();
        String[] knownSkills = {"Java", "Python", "Spring Boot", "React", "MySQL", "Docker", "AWS", "JavaScript", "HTML", "CSS"};
        for (String skill : knownSkills) {
            if (content.toLowerCase().contains(skill.toLowerCase())) {
                skills.add(skill);
            }
        }
        return skills;
    }

    public static String extractCity(String content) {
        String[] knownCities = {"Pune", "Mumbai", "Hyderabad", "Bangalore", "Delhi", "Chennai", "Nagpur", "Kolkata"};
        for (String city : knownCities) {
            if (content.toLowerCase().contains(city.toLowerCase())) {
                return city;
            }
        }
        return "Unknown";
    }

    public static String extractLinkedin(String content) {
        Pattern pattern = Pattern.compile("https?://(www\\.)?linkedin\\.com/in/[a-zA-Z0-9\\-_%]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "";
    }

    public static List<Education> extractEducations(String content) {
        List<Education> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("(B\\.?[A-Z]{1,3}|M\\.?[A-Z]{1,3})[^\\n]*\\n([^\\n]*)\\n?(\\d{4})[-–](\\d{4})?");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            Education edu = new Education();
            edu.setDegree(matcher.group(1).replace(".", "").trim());
            edu.setInstitution(matcher.group(2).trim());
            list.add(edu);
        }
        return list;
    }

    public static List<Experience> extractExperiences(String content) {
        List<Experience> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("(Software Engineer|Java Developer)[^\\n]*\\n([^\\n]*)\\n?(\\d{4})[-–](\\d{4}|Present)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            Experience exp = new Experience();
            exp.setJobTitle(matcher.group(1).trim());
            exp.setCompanyName(matcher.group(2).trim());
            try {
                if (matcher.group(3) != null)
                    exp.setStartDate(LocalDate.of(Integer.parseInt(matcher.group(3)), 1, 1));
                if (matcher.group(4) != null && !matcher.group(4).equalsIgnoreCase("Present"))
                    exp.setEndDate(LocalDate.of(Integer.parseInt(matcher.group(4)), 1, 1));
                else exp.setEndDate(null);
            } catch (Exception ignored) {
                exp.setStartDate(null);
                exp.setEndDate(null);
            }
            list.add(exp);
        }
        return list;
    }
}
