package com.excilys.cdb.spring;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {
 
   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] { RootConfig.class, SpringSecurityConfig.class };
   }
 
   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { WebMvcConfig.class };
   }
 
   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }
   
   @Override
   protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
       final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
       dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
       return dispatcherServlet;
   }
   
   @Override
   protected Filter[] getServletFilters() {
       return new Filter[] { new HiddenHttpMethodFilter() };
   }
}