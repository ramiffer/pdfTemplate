package org.pdfgen.service;

import java.io.IOException;
import java.util.Map;

public interface PdfService {


     byte[] template2pdf(String templateName, Map<String, Object> params);

     byte[] html2pdf(String html) throws IOException;

     byte[] template2pdfOpenHtml(String templateName, Map<String, Object> params);

     byte[] html2pdfOpenHtml(String html) throws IOException;
}