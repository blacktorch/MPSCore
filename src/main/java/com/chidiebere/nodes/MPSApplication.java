/*
 * Copyright (c) 2020, Chidiebere
 * */

package com.chidiebere.nodes;

import com.chidiebere.data.Subject;
import com.chidiebere.interfaces.INewSubscriberData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * @author Chidiebere Onyedinma
 * **/

public class MPSApplication {

    private String host;
    private int port;

    public MPSApplication(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    public Subscriber createSingleAppSubscriber(Subject subject, INewSubscriberData listener) throws IOException {
        return new Subscriber(new Socket(InetAddress.getByName(host), port), subject, listener);
    }

    public Subscriber createSingleAppSubscriber(List<Subject> subjects, INewSubscriberData listener) throws IOException {
        return new Subscriber(new Socket(InetAddress.getByName(host), port), subjects, listener);
    }

    public Publisher createSingleAppPublisher(List<Subject> subjects) throws IOException {
        return new Publisher(new Socket(InetAddress.getByName(host), port), subjects);
    }

    public Publisher createSingleAppPublisher(Subject subject) throws IOException {
        return new Publisher(new Socket(InetAddress.getByName(host), port), subject);
    }

    public Subscriber createMultiAppSubscriber(Subject subject, INewSubscriberData listener) throws IOException {
        return new Subscriber(host, port, subject, listener);
    }

    public Subscriber createMultiAppSubscriber(List<Subject> subjects, INewSubscriberData listener) throws IOException {
        return new Subscriber(host, port, subjects, listener);
    }

    public Publisher createMultiAppPublisher(List<Subject> subjects) throws IOException {
        return new Publisher(host, port, subjects);
    }

    public Publisher createMultiAppPublisher(Subject subject) throws IOException {
        return new Publisher(host, port, subject);
    }

}
