package org.pdfgen;

import org.pdfgen.service.ServicioMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PdfGenApplication {

    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(PdfGenApplication.class, args);
       context.getBean(ServicioMain.class).iniciarProceso();
    }

}
