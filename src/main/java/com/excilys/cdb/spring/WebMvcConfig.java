package com.excilys.cdb.spring;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.excilys.cdb.servlet.ErrorServlet;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.excilys.cdb"})
public class WebMvcConfig implements WebMvcConfigurer {
	
   @Bean
   public InternalResourceViewResolver resolver() {
      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
      resolver.setViewClass(JstlView.class);
      resolver.setPrefix("/WEB-INF/views/");
      resolver.setSuffix(".jsp");
      return resolver;
   }
   
   @Bean("messageSource")
   public MessageSource messageSource() {
	   ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
	   messageSource.setBasename("classpath:locale/messages");
	   messageSource.setDefaultEncoding("UTF-8");
	   messageSource.setUseCodeAsDefaultMessage(true);
	   return messageSource;
   }
   
   @Bean
   public LocaleResolver localeResolver() {
      CookieLocaleResolver localeResolver = new CookieLocaleResolver();
      return localeResolver;
   }
   
   @Override
   public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
       exceptionResolvers.add(new ErrorServlet());
   }
   
   @Override
   public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	   registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	   registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
   }
   
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("lang");
      registry.addInterceptor(localeChangeInterceptor);
   }
}