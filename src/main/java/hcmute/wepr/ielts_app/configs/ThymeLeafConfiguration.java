package hcmute.wepr.ielts_app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeLeafConfiguration {
	@Bean
    ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("META-INF/views/"); // Location of your templates
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML); //
        return templateResolver;
    }
}
