package org.pdfgen.service.impl;

import org.pdfgen.dto.CabeceraPdf;
import org.pdfgen.dto.DetallePdf;
import org.pdfgen.service.GenerarPdf;
import org.pdfgen.service.ServicioMain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioMainImpl implements ServicioMain {

    private final GenerarPdf generarPdf;

    public ServicioMainImpl(GenerarPdf generarPdf) {
        this.generarPdf = generarPdf;
    }

    @Override
    public void iniciarProceso() {

        CabeceraPdf cabeceraPdf = new CabeceraPdf();

        List<DetallePdf> lista = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            lista.add(new DetallePdf());
        }

        generarPdf.generarPdf(cabeceraPdf, lista);
    }
}
