package jaxrs.application;

import jaxrs.resource.CititorResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by Computer on 3/25/2017.
 */
@ApplicationPath("/restCititor")
public class CititorRestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(CititorResource.class));
    }
}
