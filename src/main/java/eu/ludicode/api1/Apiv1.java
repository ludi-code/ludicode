package eu.ludicode.api1;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v1/")
public class Apiv1 extends ResourceConfig {

    public Apiv1() {
        packages("eu.ludicode.api1");
    }

}