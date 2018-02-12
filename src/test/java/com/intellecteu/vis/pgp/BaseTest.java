package com.intellecteu.vis.pgp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = VisPgpApplication.class)
@UseAdviceWith
@TestPropertySource("classpath:test.properties")
@DirtiesContext
public abstract class BaseTest {
    @Autowired
    protected CamelContext camelContext;


    @Before
    public void mockRoutes() throws Exception {
        mockRoute("FromSFTPToFile", new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct://fromSFTP");
                weaveById("SaveToFile").replace().to("mock://toFile");
            }
        });

        mockRoute("FromFileToSFTP", new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct://fromFile");
                weaveById("SaveToSFTP").replace().to("mock://toSFTP");
            }
        });

        mockRoute("DecryptFileToFile", new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct://decryptFile");
                weaveById("SaveDecryptedFile")
                        .replace()
                            .to("mock://saveDecryptedFile");
            }
        });
        mockRoute("EncryptFileToFile", new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct://encryptFile");
                weaveById("SaveEncryptedFile")
                        .replace()
                        .to("mock://saveEncryptedFile")
                        .to("direct://decryptFile");
            }
        });


        camelContext.start();
    }

    private void mockRoute(String routeId, AdviceWithRouteBuilder builder) throws Exception {
        camelContext.getRouteDefinition(routeId).adviceWith(camelContext, builder);

    }
}
