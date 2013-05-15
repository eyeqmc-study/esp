package com.eyeq.esp.system.config;

import java.util.ArrayList;
import java.util.List;

import nz.net.ultraq.web.thymeleaf.LayoutDialect;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * @author Hana Lee
 * @since 0.1.1 2013. 2. 2. 오후 2:09:56
 * @revision $LastChangedRevision: 6102 $
 * @date $LastChangedDate: 2013-02-21 21:03:48 +0900 (목, 21 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.eyeq.esp.web.controller", excludeFilters = { @ComponentScan.Filter(Configuration.class) })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	private List<HttpMessageConverter<?>> messageConverters;

	/**
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.addAll(getMessageConverters());
	}

	/**
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
		registry.addResourceHandler("/ckeditor/**").addResourceLocations(
				"/ckeditor/");
	}

	/**
	 * The message converters for the content types we support.
	 * 
	 * @return the message converters; returns the same list on subsequent calls
	 */
	private List<HttpMessageConverter<?>> getMessageConverters() {
		if (this.messageConverters == null) {
			this.messageConverters = new ArrayList<HttpMessageConverter<?>>();

			MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();
			ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
			byteArrayHttpMessageConverter
					.setSupportedMediaTypes(getSupportedMediaTypes());

			this.messageConverters.add(mappingJacksonHttpMessageConverter);
			this.messageConverters.add(byteArrayHttpMessageConverter);
		}
		return this.messageConverters;
	}

	private List<MediaType> getSupportedMediaTypes() {
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.IMAGE_GIF);
		mediaTypes.add(MediaType.IMAGE_JPEG);
		mediaTypes.add(MediaType.IMAGE_PNG);

		return mediaTypes;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("Messages");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean
	public ITemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setCacheable(false);
		resolver.setCharacterEncoding("UTF-8");

		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.setMessageSource(messageSource());
		engine.addDialect(new LayoutDialect());

		return engine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");

		return resolver;
	}

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		requestMappingHandlerAdapter.setSynchronizeOnSession(true);
		return requestMappingHandlerAdapter;
	}

	/*
	 * @Bean public SimpleMappingExceptionResolver
	 * simpleMappingExceptionResolver() { SimpleMappingExceptionResolver
	 * exceptionResolver = new SimpleMappingExceptionResolver();
	 * exceptionResolver.setOrder(2); Properties mappings = new Properties();
	 * mappings.setProperty(
	 * "org.springframework.web.HttpSessionRequiredException",
	 * "redirect:/?articleSubmitDuprecate=problem");
	 * exceptionResolver.setExceptionMappings(mappings); return
	 * exceptionResolver; }
	 */
}
