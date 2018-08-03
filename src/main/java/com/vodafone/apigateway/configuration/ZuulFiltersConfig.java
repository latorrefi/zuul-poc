package com.vodafone.apigateway.configuration;

import com.vodafone.apigateway.filter.EnrichmentFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulFiltersConfig {

    @Bean
    public EnrichmentFilter sampleFilter() {
        return new EnrichmentFilter();
    }}
