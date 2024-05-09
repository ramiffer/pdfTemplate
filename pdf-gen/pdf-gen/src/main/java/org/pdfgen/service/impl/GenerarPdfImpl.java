package org.pdfgen.service.impl;

import org.pdfgen.dto.CabeceraPdf;
import org.pdfgen.dto.DetallePdf;
import org.pdfgen.service.GenerarPdf;
import org.pdfgen.service.PdfService;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerarPdfImpl implements GenerarPdf {

    private final PdfService pdfService;

    public GenerarPdfImpl(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Override
    public void generarPdf(CabeceraPdf cabeceraPdf, List<DetallePdf> detalles) {

        String nombreArchivo = "nuevoPdf.pdf";
        String rutaDocumento = Paths.get("/home/ramiffer/Documentos/pdf-gen/pdf-gen/" + nombreArchivo).toString();

        try (FileOutputStream outputStream = new FileOutputStream(rutaDocumento)) {
            outputStream.write(agregarParametros(cabeceraPdf, detalles));
        } catch (IOException ignore) {}
    }

    private byte[] agregarParametros(CabeceraPdf cabeceraPdf, List<DetallePdf> detalles) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cabecera", cabeceraPdf);
        parametros.put("detalles", detalles);

        return pdfService.template2pdfOpenHtml("nota_debito.ftl", parametros);

    }


}
