package com.esc.basic.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/*End point for swagger UI: http://localhost:8844/documentation/swagger-ui.html#/
*/	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
        		.produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
				.select()                                  
				//.apis(RequestHandlerSelectors.any())              
				.apis(RequestHandlerSelectors.basePackage("com.esc.basic.api"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.protocols(addProtocols())
				.securitySchemes(Lists.newArrayList(new BasicAuth("basic")))
				.securityContexts(addSecurityContexts());                                           
    }

 

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Danish", "http://www.intouch.io", "danish@intouch.io");
		return new ApiInfoBuilder() 
		         .title("Intouch APIs.") 
		         .description("Messaging gateway APIs") 
		         .version("1.0.0") 
		         .license("Apache 2.0") 
		         .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0") 
		         .contact(contact) 
		         .build();
	}
	
	private Set<String> addProtocols() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");
        return protocols;
    }
	
    private List<SecurityContext> addSecurityContexts() {
        return Arrays.asList(
                SecurityContext
                        .builder()
                        .forPaths(PathSelectors.any())
                        .securityReferences(addSecurityReferences())
                        .build());
    }

    private List<SecurityReference> addSecurityReferences() {
        return Arrays.asList(
                SecurityReference
                        .builder()
                        .reference("basic")
                        .scopes(new AuthorizationScope[0])
                        .build());
    }

}

