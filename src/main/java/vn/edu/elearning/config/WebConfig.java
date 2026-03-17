package vn.edu.elearning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // Ensure UTF-8 for all String responses
    converters.stream()
        .filter(c -> c instanceof StringHttpMessageConverter)
        .forEach(c -> ((StringHttpMessageConverter) c).setDefaultCharset(StandardCharsets.UTF_8));
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer
        .defaultContentType(MediaType.TEXT_HTML)
        .mediaType("html", new MediaType("text", "html", StandardCharsets.UTF_8));
  }
}
