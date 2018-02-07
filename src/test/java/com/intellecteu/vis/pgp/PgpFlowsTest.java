package com.intellecteu.vis.pgp;


import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = VisPgpApplication.class)
@UseAdviceWith
public class PgpFlowsTest {

    @Autowired
    private CamelContext camelContext;
    
    @Produce(uri = "direct://encryptFile")
    private ProducerTemplate encryptFile;
    
    @Produce(uri = "direct://decryptFile")
    private ProducerTemplate decryptFile;
    
    @EndpointInject(uri = "mock://saveEncryptedFile")
    private MockEndpoint saveEncryptedFile;
    
    @EndpointInject(uri = "mock://saveDecryptedFile")
    private MockEndpoint saveDecryptedFile;
    
    
    @Test
    public void testEncryptDecryptFlow() throws InterruptedException {
        saveEncryptedFile.expectedMessageCount(1);

        saveDecryptedFile.expectedMessageCount(1);
        saveDecryptedFile.expectedBodiesReceived("This is the test message");

        encryptFile.sendBody("This is the test message");

        saveEncryptedFile.assertIsSatisfied();

        decryptFile.send(saveEncryptedFile.getExchanges().get(0));
        
        saveDecryptedFile.assertIsSatisfied();
        
    }
    
    @Before
    public void mockRoutes() throws Exception {
        mockRoute("EncryptFileToFile", new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct://encryptFile");
                weaveById("SaveEncryptedFile").replace().to("mock://saveEncryptedFile");
            }
        });
        
        mockRoute("DecryptFileToFile", new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct://decryptFile");
                weaveById("SaveDecryptedFile").replace().to("mock://saveDecryptedFile");
            }
        });
        
        camelContext.start();
    }
    
    private void mockRoute(String routeId, AdviceWithRouteBuilder builder) throws Exception {
        camelContext.getRouteDefinition(routeId).adviceWith(camelContext, builder);
        
    }
}
