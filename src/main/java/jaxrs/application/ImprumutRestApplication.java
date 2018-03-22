package jaxrs.application;

import jaxrs.resource.ImprumutResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Computer on 3/26/2017.
 */
@ApplicationPath("/restImprumut")
public class ImprumutRestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(ImprumutResource.class));
    }
}