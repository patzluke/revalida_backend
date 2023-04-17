package org.ssglobal.training.codes;

import java.util.HashSet;
import java.util.Set;

import org.ssglobal.training.codes.service.UserService;

import jakarta.ws.rs.core.Application;

public class MyApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(UserService.class);
        return set;
        
    }
}
