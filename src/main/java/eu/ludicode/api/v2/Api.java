package eu.ludicode.api.v2;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v2/")
public class Api extends ResourceConfig {

    public Api() {
        packages("eu.ludicode.api.v2");
    }

}