package com.intellecteu.vis.pgp;


import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;

public class PgpFlowsTest extends BaseTest {

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
        saveDecryptedFile.expectedBodiesReceived("This is the test message");

        encryptFile.sendBody("This is the test message");
        
        saveEncryptedFile.assertIsSatisfied();
        saveDecryptedFile.assertIsSatisfied();
        
    }
}
