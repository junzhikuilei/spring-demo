package xyz.kuilei.spring.demo.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Value("${knife4j.swagger.group-name}")
    String groupName;
    @Value("${knife4j.swagger.title}")
    String title;
    @Value("${knife4j.swagger.description}")
    String description;
    @Value("${knife4j.swagger.version}")
    String version;
    @Value("${knife4j.swagger.contact-name}")
    String contactName;

    @Bean
    public Docket demoDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(demoApiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo demoApiInfo() {
        Contact contact = new Contact(contactName, "", "");

        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(contact)
                .version(version)
                .build();
    }
}
