package org.pdfgen.flying;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.PatternSyntaxException;

/**
 * Implementación custom del loader de recursos para flying saucer que resuelve
 * issues al detectar archivos css e imágenes a usar en los html a procesar
 */
public class FlyingSaucerCustomResourceLoader extends ITextUserAgent {
    private static final Logger LOG = LoggerFactory.getLogger(FlyingSaucerCustomResourceLoader.class);

    private final String resourcesPath;


    public FlyingSaucerCustomResourceLoader(ITextOutputDevice outputDevice, String resourcesPath) {
        super(outputDevice);
        this.resourcesPath = resourcesPath;
    }


    @Override
    protected InputStream resolveAndOpenStream(String uri) {

        InputStream is = super.resolveAndOpenStream(uri);
        String fileName = "";

        try {
            String[] split = uri.split("/");
            fileName = split[split.length - 1];
        } catch (PatternSyntaxException e) {
            throw new IllegalStateException(e);
        }

        if (is == null) {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(resourcesPath + "/" + fileName);
            if(resource.exists()) {
                try {
                    is = resource.getInputStream();
                } catch (IOException e) {
                    LOG.warn("Error cargando recurso {}/{}", resourcesPath, fileName, e);
                }
            } else {
                LOG.error("No se pudo cargar el recurso {}/{}", resourcesPath, fileName);
            }
        }

        return is;
    }
}
