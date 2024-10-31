package io.codemalone.springboot.aop.springboot_aop.services;

public interface IGreeting {

    String sayHello(String person, String phrase);
    String sayHelloError(String person, String phrase);

}
