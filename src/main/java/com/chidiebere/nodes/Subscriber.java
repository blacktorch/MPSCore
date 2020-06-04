package com.chidiebere.nodes;

import com.chidiebere.data.Message;
import com.chidiebere.data.Subject;
import com.chidiebere.interfaces.INewSubscriberData;
import com.chidiebere.network.Transport;
import com.chidiebere.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Subscriber implements Runnable {

    private Socket participant;
    private Transport transport;
    private List<String> subjectTitles;
    private INewSubscriberData dataListener;
    private boolean attached;

    protected Subscriber(Socket socket, List<Subject> subjects, INewSubscriberData dataListener) {
        attached = true;
        this.dataListener = dataListener;
        for (Subject subject : subjects){
            subjectTitles.add(subject.toString());
        }
       init(socket);
    }

    protected Subscriber(Socket socket, Subject subject, INewSubscriberData dataListener) {
        this.dataListener = dataListener;
        subjectTitles.add(subject.toString());
        init(socket);
    }

    private void init(Socket socket){
        this.participant = socket;
        try {
            transport = new Transport(participant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void attach(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void detach(){
        if (participant != null){
            try {
                participant.close();
                attached = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        JSONArray subs = new JSONArray(subjectTitles);
        JSONObject intent = new JSONObject();
        intent.put(Constants.TYPE, Participants.SUBSCRIBER);
        intent.put(Constants.TIME_STAMP, System.currentTimeMillis());
        intent.put(Constants.SUBJECTS, subs);
        try {
            transport.sendMessage(intent);
        } catch (IOException e) {
            attached = false;
            e.printStackTrace();
        }

        while (attached){
            try {
                if (transport.isDataAvailable()) {
                    JSONObject data = transport.receiveMessage();
                    List<String> subjects = new ArrayList<>();
                    Iterator<String> it = data.keys();
                    while (it.hasNext()){
                        if (!it.next().equals(Constants.TIME_STAMP)){
                            subjects.add(it.next());
                        }
                    }
                    Message message = new Message(data, subjects, data.getLong(Constants.TIME_STAMP));
                    dataListener.onNewSubscriberData(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
