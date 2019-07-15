package br.com.restWithSpringBoot.converter;

import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter{

	protected YamlJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper);
	}

}
