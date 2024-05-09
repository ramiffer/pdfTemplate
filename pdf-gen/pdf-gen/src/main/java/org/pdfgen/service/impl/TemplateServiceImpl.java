package org.pdfgen.service.impl;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.pdfgen.service.TemplateService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    private static final DateTimeFormatter DTF_DATE_LEGIBLE = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy");
    private static final DateTimeFormatter DTF_DATETIME_LEGIBLE = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");


    private final Configuration ftlConfig;


    public TemplateServiceImpl() throws IOException, TemplateException {
        ftlConfig = new Configuration(Configuration.VERSION_2_3_23);
        TemplateLoader loader = null;

        loader = new ClassTemplateLoader(getClass(), "/templates");

        ftlConfig.setDefaultEncoding("UTF-8");
        ftlConfig.setTemplateLoader(loader);
    }

    @Override
    public String process(String templateName, Map<String, Object> params)
            throws IOException, TemplateException {
        templateName = templateName.trim();

        if (!templateName.toLowerCase().endsWith(".ftl")) {
            templateName = templateName + ".ftl";
        }

        Template template = ftlConfig.getTemplate(templateName);
        try (StringWriter writer = new StringWriter()) {
            Map<String, Object> p = new HashMap<>();
            p.put("fecha", formatoFechaLegible(LocalDateTime.now()));
            p.putAll(params);

            template.process(p, writer);
            return writer.toString();
        }
    }

    @Override
    public String formatoFechaLegible(LocalDateTime ldt) {
        return DTF_DATE_LEGIBLE.format(ldt);
    }

    @Override
    public String formatoFechaHoraLegible(LocalDateTime ldt) {
        return DTF_DATETIME_LEGIBLE.format(ldt);
    }

}
