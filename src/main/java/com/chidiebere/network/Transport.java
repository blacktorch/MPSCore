/*
 * Copyright (c) 2020, Chidiebere
 * */

package com.chidiebere.network;

import com.chidiebere.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Chidiebere Onyedinma
 * **/

public class Transport {

    private OutputStream out;
    private InputStream in;

    public Transport(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
    }

    public JSONObject receiveMessage() throws IOException, JSONException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        while ((read = in.read()) != 0) {
            if (read == -1){
                throw new IOException();
            }
            buffer.write(read);
        }
        String message = buffer.toString();

        return new JSONObject(message);

    }

    public void sendMessage(JSONObject json) throws IOException {
            OutputStreamWriter osw = new OutputStreamWriter(out);
            osw.write(json.toString());
            osw.write(0);
            osw.flush();

    }

    public boolean isDataAvailable() throws IOException {
        return in.available() > Constants.MINIMUM_BUFFER_SIZE;
    }

}
