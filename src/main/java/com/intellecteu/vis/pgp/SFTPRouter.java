package com.intellecteu.vis.pgp;import org.apache.camel.builder.RouteBuilder;import org.springframework.stereotype.Component;import static com.intellecteu.vis.pgp.consts.Constants.MOVE_FILE_OPTIONS;@Componentpublic class SFTPRouter extends RouteBuilder {    public void configure() throws Exception {        from("sftp://{{vis.from.sftp.uri}}" + MOVE_FILE_OPTIONS + "&password={{vis.from.sftp.password}}").id("FromSFTPToFile")                .autoStartup("{{vis.from.sftp.enable}}")                .log("\n*** Received file [${headers.CamelFileName}] from sftp directory {{vis.from.sftp.uri}}.")                .to("file://{{vis.from.sftp.out.dir}}").id("SaveToFile")                .log("\n*** File [${headers.CamelFileName}] saved to directory {{vis.from.sftp.out.dir}}");        from("file://{{vis.to.sftp.in.dir}}" + MOVE_FILE_OPTIONS).id("FromFileToSFTP").autoStartup("{{vis.to.sftp.enable}}")                .log("\n*** Received file [${headers.CamelFileName}] from directory {{vis.to.sftp.in.dir}}")                .to("sftp://{{vis.to.sftp.uri}}?password={{vis.to.sftp.password}}").id("SaveToSFTP")                .log("\n*** Sent file [${headers.CamelFileName}] to sftp directory {{vis.to.sftp.uri}}.");    }}