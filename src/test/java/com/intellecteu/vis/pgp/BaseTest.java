package com.intellecteu.vis.pgp;import org.apache.camel.CamelContext;import org.apache.camel.builder.AdviceWithRouteBuilder;import org.apache.camel.test.spring.CamelSpringBootRunner;import org.apache.camel.test.spring.UseAdviceWith;import org.junit.runner.RunWith;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.test.context.TestPropertySource;@RunWith(CamelSpringBootRunner.class)@SpringBootTest(classes = VisPgpApplication.class)@UseAdviceWith@TestPropertySource("classpath:test.properties")public abstract class BaseTest {    @Autowired    protected CamelContext camelContext;    protected void mockRoute(String routeId, AdviceWithRouteBuilder builder) throws Exception {        camelContext.getRouteDefinition(routeId).adviceWith(camelContext, builder);    }}