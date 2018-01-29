package ee.hardi.pdfdemo;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Service
public class PdfService {


    public File constructPdf(String template) {
        return createPdfFrom(getPdfTemplate(template));
    }

    private String getPdfTemplate(String template) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("content", new Data());

        // Get the plain HTML with the resolved ${name} variable!
        return templateEngine.process(template, context);
    }

    private File createPdfFrom(String html) {
        File temppdf;
        try {
            temppdf = File.createTempFile("temppdf", ".pdf");
            OutputStream outputStream = new FileOutputStream(temppdf);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            outputStream.close();
            return temppdf;
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Everyhting went wrong " + e);
        }
    }
}
