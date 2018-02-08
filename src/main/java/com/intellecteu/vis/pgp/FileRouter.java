package com.intellecteu.vis.pgp;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.intellecteu.vis.pgp.consts.Constants.MOVE_FILE_OPTIONS;

/**
 * Camel routes for file connectors
 * 
 * @author sergiipozharov 
 */
@Component
public class FileRouter extends RouteBuilder {



    @Override
    public void configure() {
        from("file://{{vis.pgp.plain.in.dir}}" + MOVE_FILE_OPTIONS).id("EncryptFileToFile").autoStartup("{{vis.pgp.file.enc.enable}}")
                .log("\n*** Received file [${headers.CamelFileName}] from [{{vis.pgp.plain.in.dir}}] directory.")
                
                .to("direct://pgpEncrypt")
                
                .to("file://{{vis.pgp.enc.out.dir}}?fileName=${headers.CamelFileName}").id("SaveEncryptedFile")
                
                .log("\n*** Encrypted file saved to folder [{{vis.pgp.enc.out.dir}}] as [${headers.CamelFileNameProduced}]");
        
        from("file://{{vis.pgp.enc.in.dir}}" + MOVE_FILE_OPTIONS).id("DecryptFileToFile").autoStartup("{{vis.pgp.file.dec.enable}}")
                .log("\n*** Received file [${headers.CamelFileName}] from [{{vis.pgp.enc.in.dir}}] directory.")
                
                .to("direct:pgpDecrypt")
                
                .to("file://{{vis.pgp.plain.out.dir}}?fileName=${headers.CamelFileName}").id("SaveDecryptedFile")

                .log("\n*** Decrypted file saved to folder [{{vis.pgp.plain.out.dir}}] as [${headers.CamelFileNameProduced}]");
    }
}
