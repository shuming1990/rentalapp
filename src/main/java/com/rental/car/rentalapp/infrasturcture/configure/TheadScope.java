package com.rental.car.rentalapp.infrasturcture.configure;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.Nullable;


public class TheadScope implements Scope{

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Nullable
    public Object remove(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Nullable
    public Object resolveContextualObject(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Nullable
    public String getConversationId() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
