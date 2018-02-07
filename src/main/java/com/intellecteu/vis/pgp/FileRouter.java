package com.intellecteu.vis.pgp;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Camel routes for file connectors
 * 
 * @author sergiipozharov 
 */
@Component
public class FileRouter extends RouteBuilder {

    private static final String FILE_OPTIONS = "?move=.done&moveFailed=.error";

    @Override
    public void configure() {
        from("file://{{vis.pgp.plain.in.dir}}" + FILE_OPTIONS).id("EncryptFileToFile")
                .log("\n*** Received file [${headers.CamelFileName}] from [{{vis.pgp.plain.in.dir}}] directory.")
                
                .to("direct://pgpEncrypt")
                
                .to("file://{{vis.pgp.enc.out.dir}}?fileName=${headers.CamelFileName}").id("SaveEncryptedFile")
                
                .log("\n*** Encrypted file saved to folder [{{vis.pgp.enc.out.dir}}] as [${headers.CamelFileNameProduced}]");
        
        from("file://{{vis.pgp.enc.in.dir}}" + FILE_OPTIONS).id("DecryptFileToFile")
                .log("\n*** Received file [${headers.CamelFileName}] from [{{vis.pgp.enc.in.dir}}] directory.")
                
                .to("direct:pgpDecrypt")
                
                .to("file://{{vis.pgp.plain.out.dir}}?fileName=${headers.CamelFileName}").id("SaveDecryptedFile")

                .log("\n*** Encrypted file saved to folder [{{vis.pgp.plain.out.dir}}] as [${headers.CamelFileNameProduced}]");
    }
}
