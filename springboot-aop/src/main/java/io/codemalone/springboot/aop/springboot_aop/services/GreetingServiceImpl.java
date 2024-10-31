package io.codemalone.springboot.aop.springboot_aop.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements IGreeting {

    @Override
    public String sayHello(String person, String phrase) {
       String result =  phrase + " " + person;
       System.out.println(result);
       return result;
    }

    @Override
    public String sayHelloError(String person, String phrase) {      
        throw new UnsupportedOperationException("Unimplemented method 'sayHelloError'");
    }

}
