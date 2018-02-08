package com.intellecteu.vis.pgp;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Camel routes for PGP encryption and decryption
 * 
 * @author sergiipozharov
 */
@Component
public class PgpRouter extends RouteBuilder {
    public void configure() throws Exception {
        from("direct://pgpEncrypt").id("VIS_PGP_Encrypt")
                .log("\n*** Encrypting payload for [{{vis.pgp.publicKey.userid}}] User ID...")
                
                .setHeader("CamelPGPDataFormatKeyFileName",
                    simple("{{vis.pgp.publicKey.file}}"))

                .setHeader("CamelPGPDataFormatKeyUserid",
                        simple("{{vis.pgp.publicKey.userid}}"))
    
                .marshal().pgp("", "", "", true, true)
                
                .log("\n*** Encryption completed.");

        from("direct://pgpDecrypt").id("VIS_PGP_Decrypt")
                .log("\n*** Decrypting payload for [{{vis.pgp.publicKey.userid}}] User ID...")

                .setHeader("CamelPGPDataFormatKeyFileName",
                        simple("{{vis.pgp.privateKey.file}}"))

                .setHeader("CamelPGPDataFormatKeyUserid",
                        simple("{{vis.pgp.privateKey.userid}}"))
                
                .setHeader("CamelPGPDataFormatKeyPassword",
                        simple("{{vis.pgp.privateKey.password}}"))

                .unmarshal().pgp("", "", "", true, true)

                .log("\n*** Decryption completed.");
    }
}
