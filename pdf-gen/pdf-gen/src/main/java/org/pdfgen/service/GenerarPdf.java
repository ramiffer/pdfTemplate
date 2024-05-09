package org.pdfgen.service;

import org.pdfgen.dto.CabeceraPdf;
import org.pdfgen.dto.DetallePdf;

import java.util.List;

public interface GenerarPdf {

    void generarPdf(CabeceraPdf cabeceraPdf, List<DetallePdf> detalles);

}
