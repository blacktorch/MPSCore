package com.chidiebere.nodes;

import com.chidiebere.data.Subject;
import com.chidiebere.interfaces.INewSubscriberData;
import com.chidiebere.nodes.Publisher;
import com.chidiebere.nodes.Subscriber;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class MPSApplication {

    private String host;
    private int port;

    public MPSApplication(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    public Subscriber createSubscriber(Subject subject, INewSubscriberData listener) throws IOException {
        return new Subscriber(new Socket(host, port), subject, listener);
    }

    public Subscriber createSubscriber(List<Subject> subjects, INewSubscriberData listener) throws IOException {
        return new Subscriber(new Socket(host, port), subjects, listener);
    }

    public Publisher createPublisher(){
        return new Publisher();
    }

}
