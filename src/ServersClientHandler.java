public class ServersClientHandler extends Thread{

    private Server server;


    public ServersClientHandler(Server server) {
        this.server = server;
    }

    @Override
    public void run(){
        //wait for messages




    }

    public void receiveMessage(String message){
        server.publishMessage(message,this);
    }

    public void sendMessageToClient(String message){



    }
}
