package org.pdfgen.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public interface TemplateService {

    String process(String templateName, Map<String, Object> params)
            throws Exception;
    String formatoFechaLegible(LocalDateTime ldt);
    String formatoFechaHoraLegible(LocalDateTime ldt);

}
