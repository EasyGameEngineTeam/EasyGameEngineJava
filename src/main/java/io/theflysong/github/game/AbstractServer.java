package io.theflysong.github.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServer extends AbstractDist {
    protected List<Socket> clients = new ArrayList();
    protected List<DataOutputStream> sendStreams = new ArrayList();
    protected List<DataInputStream> receiveStreams = new ArrayList();
    protected ServerSocket server;

    protected DataOutputStream getS2CSendStream(int client) throws IOException {
        return new DataOutputStream(clients.get(client).getOutputStream());
    }

    protected DataInputStream getC2SReceiveStream(int client) throws IOException {
        return new DataInputStream(clients.get(client).getInputStream());
    }

    protected void accept() throws IOException {
        clients.add(server.accept());
        sendStreams.add(getS2CSendStream(clients.size() - 1));
        receiveStreams.add(getC2SReceiveStream(clients.size() - 1));
    }

    protected AbstractServer(int port) throws IOException {
        super(Dist.SERVER);
        this.server = new ServerSocket(port);
        this.server.setSoTimeout(60000);
    }
}
