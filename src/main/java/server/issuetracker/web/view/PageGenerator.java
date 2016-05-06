package server.issuetracker.web.view;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PageGenerator {
    private static final String TEMPLATES = "templates";
    
    private static final Logger log = LoggerFactory.getLogger(PageGenerator.class);
    
    private static PageGenerator pageGenerator;
    
    private final Configuration configuration;
    
    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }
    
    public String getPage(String fileName, Map<String, Object> data) {
        Writer stream = new StringWriter();
        
        try {
            Template template = configuration.getTemplate(fileName);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            log.error("problems with template loading " + fileName);
        }
        
        return stream.toString();
    }
    
    private PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        ClassTemplateLoader loader = new ClassTemplateLoader(this.getClass(), File.separator + TEMPLATES);
        configuration.setTemplateLoader(loader);
    }
}
