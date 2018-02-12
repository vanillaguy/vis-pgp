package com.intellecteu.vis.pgp;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;

public class SFTPRouterTest extends BaseTest {

    @Produce(uri = "direct://fromSFTP")
    private ProducerTemplate fromSFTP;

    @Produce(uri = "direct://fromFile")
    private ProducerTemplate fromFile;

    @EndpointInject(uri = "mock://toFile")
    private MockEndpoint toFile;

    @EndpointInject(uri = "mock://toSFTP")
    private MockEndpoint toSFTP;

    @Test
    public void testSFTPRoutesFlowSuccess() throws InterruptedException {
        toFile.expectedMessageCount(1);

        toSFTP.expectedBodiesReceived("This is the test message");

        fromFile.sendBody("This is the test message");

        toSFTP.assertIsSatisfied();

        fromSFTP.send(toSFTP.getExchanges().get(0));

        toFile.assertIsSatisfied();
    }
    
}