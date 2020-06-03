package com.chidiebere.network;

import com.chidiebere.data.Subject;
import com.chidiebere.interfaces.INewSubscriberData;
import com.chidiebere.nodes.Publisher;
import com.chidiebere.nodes.Subscriber;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MPSApplication {

    Socket participant;

    public MPSApplication(String host, int port) throws IOException {
        participant = new Socket(host, port);
    }

    public Subscriber createSubscriber(INewSubscriberData listener){
        return new Subscriber();
    }

    public Publisher createPublisher(){
        return new Publisher();
    }

    public Subject createSubject() {
        return new Subject();
    }
}
