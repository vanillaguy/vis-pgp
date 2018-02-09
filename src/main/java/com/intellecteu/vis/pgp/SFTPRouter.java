package com.intellecteu.vis.pgp;import org.apache.camel.builder.RouteBuilder;import org.springframework.stereotype.Component;import static com.intellecteu.vis.pgp.consts.Constants.MOVE_FILE_OPTIONS;@Componentpublic class SFTPRouter extends RouteBuilder {    public void configure() throws Exception {        from(getFromSFTPURI() + MOVE_FILE_OPTIONS + "&password={{vis.from.sftp.password}}").id("FromSFTPToFile")                .autoStartup("{{vis.from.sftp.enable}}")                .log("\n*** Received file [${headers.CamelFileName}] from sftp directory {{vis.from.sftp.dir.path}}.")                .to("file://{{vis.from.sftp.out.dir}}").id("SaveToFile")                .log("\n*** File [${headers.CamelFileName}] saved to directory {{vis.from.sftp.out.dir}}");        from("file://{{vis.to.sftp.in.dir}}" + MOVE_FILE_OPTIONS).id("FromFileToSFTP").autoStartup("{{vis.to.sftp.enable}}")                .log("\n*** Received file [${headers.CamelFileName}] from directory {{vis.to.sftp.in.dir}}")                .to(getToSFTPURI() + "?password={{vis.to.sftp.password}}").id("SaveToSFTP")                .log("\n*** Sent file [${headers.CamelFileName}] to sftp directory {{vis.from.sftp.dir.path}}.");    }    private String getFromSFTPURI() {        return "sftp://{{vis.from.sftp.username}}@{{vis.from.sftp.hostname}}:{{vis.from.sftp.port}}/{{vis.from.sftp.dir.path}}";    }    private String getToSFTPURI() {        return "sftp://{{vis.to.sftp.username}}@{{vis.to.sftp.hostname}}:{{vis.to.sftp.port}}/{{vis.to.sftp.dir.path}}";    }}