package com.vodafone.apigateway.filter;

import com.google.common.base.Strings;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.client.RestTemplate;

public class EnrichmentFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();

        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get("http://rest-service-jenkins.dxl-pre.vodafone.com/rest-service/greeting").asJson();
            System.out.println(jsonResponse.getBody().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject("http://rest-service-jenkins.dxl-pre.vodafone.com/rest-service/greeting", String.class);
            System.out.println(result);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if (!Strings.isNullOrEmpty(context.getRequest().getHeader("x-port-forwarded-for"))
                && !Strings.isNullOrEmpty(context.getRequest().getHeader("x-forwarded-for"))) {
            System.out.println(context.getRequest().getHeader("x-port-forwarded-for"));
            System.out.println(context.getRequest().getHeader("x-forwarded-for"));
            context.addZuulRequestHeader("x-msisdn", "value");
        }
        return null;
    }
}
