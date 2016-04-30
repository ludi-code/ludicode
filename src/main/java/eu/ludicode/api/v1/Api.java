package eu.ludicode.api.v1;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v1/")
public class Api extends ResourceConfig {

    public Api() {
        packages("eu.ludicode.api.v1");
        register(MultiPartFeature.class);
    }

}