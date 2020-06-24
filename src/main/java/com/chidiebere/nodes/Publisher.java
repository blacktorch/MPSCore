/*
 * Copyright (c) 2020, Chidiebere
 * */

package com.chidiebere.nodes;

import com.chidiebere.data.Message;
import com.chidiebere.data.Subject;
import com.chidiebere.network.Transport;
import com.chidiebere.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chidiebere Onyedinma
 * **/

public class Publisher {

    private Socket participant;
    private Transport transport;
    private List<Subject> subjects;
    private boolean attached;

    protected Publisher(Socket socket, List<Subject> subjects) {
        this.subjects = subjects;
        init(socket);
    }

    protected Publisher(Socket socket, Subject subject) {
        this.subjects = new ArrayList<>();
        this.subjects.add(subject);
        init(socket);
    }

    protected Publisher(String host, int port, List<Subject> subjects) throws IOException {
        this.subjects = subjects;
        init(host, port);
    }

    protected Publisher(String host, int port, Subject subject) throws IOException {
        this.subjects = new ArrayList<>();
        this.subjects.add(subject);
        init(host, port);
    }

    private void init(Socket socket){
        this.participant = socket;
        attached = true;
        try {
            transport = new Transport(participant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(String host, int port) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(host), port);
        init(socket);
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

    public Message generateMessage(){
        JSONObject data = new JSONObject();
        List<String> subjectTitles = new ArrayList<>();
        for (Subject subject : this.subjects){
            data.put(subject.getTitle(), subject.getData());
            subjectTitles.add(subject.toString());
        }

        return new Message(data, subjectTitles, System.currentTimeMillis());

    }

    public void publishMessage() throws IOException {
        Message message = generateMessage();
        JSONArray subs = new JSONArray(message.getSubjectTitles());
        JSONObject dataToSend = new JSONObject();
        dataToSend.put(Constants.TYPE, Participants.PUBLISHER);
        dataToSend.put(Constants.SUBJECTS, subs);
        dataToSend.put(Constants.TIME_STAMP, message.getTimeStamp());
        dataToSend.put(Constants.DATA, message.getData());
        if (attached){
            //System.out.println("I am attached!!");
            transport.sendMessage(dataToSend);
        }
    }
}
