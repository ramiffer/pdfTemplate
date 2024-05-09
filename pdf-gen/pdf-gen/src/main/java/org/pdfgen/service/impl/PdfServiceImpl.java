package org.pdfgen.service.impl;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.pdfgen.flying.FlyingSaucerCustomResourceLoader;
import org.pdfgen.service.PdfService;
import org.pdfgen.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

@Service
public class PdfServiceImpl implements PdfService {

    private static final Logger LOG = LoggerFactory.getLogger(PdfServiceImpl.class);

    private final String pdfResourcesPath;
    private final TemplateService templateService;

    public PdfServiceImpl(@Value("${pdf.resources-path}") String pdfResourcesPath, TemplateService templateService) {
        super();
        this.pdfResourcesPath = pdfResourcesPath;
        this.templateService = templateService;
    }

    @Override
    public byte[] template2pdf(String templateName, Map<String, Object> params) {
        try {
            return html2pdf(templateService.process(templateName, params));
        } catch (Exception e) {
            LOG.error("Error en generación del archivo PDF. Template: {}", templateName);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] html2pdf(String html) throws IOException {
        try (ByteArrayOutputStream pdfOS = new ByteArrayOutputStream()) {

            // https://stackoverflow.com/questions/20495092/flying-saucer-set-custom-dpi-for-output-pdf
            // DPPoint -> 600dpi/72ppi=8.3333 ; DPPixel -> 600dpi/96pxperinch=6.25
            // (8.3333 ; 6.25) * 4 -> 33,3332 ; 25
            ITextRenderer renderer = new ITextRenderer(33.3332f, 25);

            FlyingSaucerCustomResourceLoader loader = new FlyingSaucerCustomResourceLoader(renderer.getOutputDevice(), this.pdfResourcesPath);
            loader.setSharedContext(renderer.getSharedContext());

            renderer.getSharedContext().setUserAgentCallback(loader);
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(pdfOS);

            return pdfOS.toByteArray();
        }
    }


    @Override
    public byte[] template2pdfOpenHtml(String templateName, Map<String, Object> params) {
        try {
            return html2pdfOpenHtml(templateService.process(templateName, params));
        } catch (Exception e) {
            LOG.error("Error en generación del archivo PDF. Template: {}", templateName);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] html2pdfOpenHtml(String html) throws IOException {
        try (ByteArrayOutputStream pdfOS = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder
                    .useFastMode()
                    .withHtmlContent(html, this.pdfResourcesPath)
                    .useUriResolver((baseUri, uri) -> {
                        String fileName = "";
                        try {
                            String[] split = uri.split("/");
                            fileName = split[split.length - 1];
                        } catch (PatternSyntaxException e) {
                            throw new IllegalStateException(e);
                        }

                        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                        Resource resource = resolver.getResource(pdfResourcesPath + "/" + fileName);
                        if (resource.exists()){
                            try {
                                return resource.getURL().toString();
                            } catch (IOException e) {
                                LOG.warn("No se pudo cargar el recurso {}/{}", pdfResourcesPath, fileName, e);
                            }
                        } else {
                            LOG.error("No se pudo cargar el recurso {}/{}", pdfResourcesPath, fileName);
                        }
                        return null;
                    })
                    .toStream(pdfOS)
                    .run();

            return pdfOS.toByteArray();
        }
    }
}
