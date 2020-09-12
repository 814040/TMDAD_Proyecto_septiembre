package com.mathilde.chat;

import lombok.Data;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


@ShellComponent
public class MyCommands {

    private static int ID_CLIENT = -1;
    List <String> emptyList = new ArrayList<>();

    @ShellMethod("Get String Element From Page (user or message)")
    public String getElement(String page, String element) {
        String text;
        if (page.equals("message")){text = ReadHttpClient.getMessage();}
        else if (page.equals("user")){text = ReadHttpClient.getUser();}
        else {return "PAGE DOES NOT EXIST";}
        return ReadHttpClient.listStringToString(ReadHttpClient.getElement(text, element));
    }

    @ShellMethod("Get User List")
    public String userList() {
        return ReadHttpClient.listStringToString(ReadHttpClient.getUserList());
    }

    @ShellMethod("Connect User")
    public String connect(String username, String password) {
        String message= "";
        int connection = ReadHttpClient.connectUser(username,password);
        if (connection >0){
            message = "CLIENT CONNECTED";
            ID_CLIENT = connection;
            WebSocketEventSender.connectWebSocket();
        }
        else if (connection == 0) message = "INCORRECT PASSWORD";
        else if (connection == -1) message = "USER DOES NOT EXIST";
        
        return message;
    }  

    @ShellMethod("Disconnect User")
    public String disconnect() {
        StompSession session = WebSocketEventSender.getSession();
        MyStompSessionHandler.disconnect(session);
        ID_CLIENT = -1;
        return "CLIENT DISCONNECTED";
    } 

    /*@ShellMethod("Add User -- Choose your password")
    public String addUser(String username, String password) {
        WebSocketEventSender.createUser(username,password);
        System.out.println("shell");
        return "NEW USER " + username+ "CREATED";
    } */


    @ShellMethod("Write Message")
    public String writeMessage(String receivername, String textmessage) {

        String reponse="";
        if (ID_CLIENT==-1){
            reponse = "YOU MUST CONNECT FIRST";
        }
        else{
            int idreceiver;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date time = new Date();
            String date = formatter.format(time);
            if(ID_CLIENT!=1&&receivername.equals("all")){reponse="SENDING MESSAGE FAILED";}
            else if (receivername.equals("all")&&ID_CLIENT==1){//superuser
                idreceiver=-1;
                WebSocketEventSender.addMessage(idreceiver, ID_CLIENT, ReadHttpClient.getUserById(ID_CLIENT), date,textmessage);
                reponse ="NEW MESSAGE CREATED";
            }
            else {
                idreceiver = ReadHttpClient.getIdByUser(receivername);
                WebSocketEventSender.addMessage(idreceiver, ID_CLIENT, ReadHttpClient.getUserById(ID_CLIENT), date,textmessage);
                reponse="NEW MESSAGE CREATED";
            }
        }
        return reponse;
    }

    @ShellMethod("Write Message in a Chat")
    public String writeMessageChat(String idChat, String textmessage) {
        String reponse="";
        if (ID_CLIENT==-1){
            reponse = "YOU MUST CONNECT FIRST";
        }
        else{
            if (ReadHttpClient.isUserInChat(Integer.parseInt(idChat), ID_CLIENT)) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date time = new Date();
                String date = formatter.format(time);
                WebSocketEventSender.addMessage(Integer.parseInt(idChat), ID_CLIENT, "Chat " + idChat + " - " + ReadHttpClient.getUserById(ID_CLIENT), date, textmessage);
                reponse = "NEW MESSAGE CREATED";
            }
            else {reponse = "IMPOSSIBLE : YOU ARE NOT MEMBRE OF THIS CHAT";}
        }
        return reponse;
    }

    @ShellMethod("Create Chat ---- Enter 2 member's names")
    public String createChat2(String mbr1, String mbr2) {
        String reponse="";
        if (ID_CLIENT==-1){
            reponse = "YOU MUST CONNECT FIRST";
        }
        else{
            ArrayList<Long> idUserList = ReadHttpClient.getIdByUserList2(mbr1, mbr2);
            WebSocketEventSender.createChat(idUserList);
            reponse = "CHAT SUCCESSFULLY CREATED";;
        }
        return reponse;
    }

    @ShellMethod("Create Chat ---- Enter 3 member's names")
    public String createChat3(String mbr1, String mbr2,String mbr3) {
        String reponse="";
        if (ID_CLIENT==-1){
            reponse = "YOU MUST CONNECT FIRST";
        }
        else{
            ArrayList<Long> idUserList = ReadHttpClient.getIdByUserList3(mbr1, mbr2, mbr3);
            WebSocketEventSender.createChat(idUserList);
            reponse = "CHAT SUCCESSFULLY CREATED";
        }
        return reponse;
    }

    @ShellMethod("Create Chat ---- Enter 4 member's names")
    public String createChat4(String mbr1, String mbr2,String mbr3,String mbr4) {
        String reponse="";
        if (ID_CLIENT==-1){
            reponse = "YOU MUST CONNECT FIRST";
        }
        else{
            ArrayList<Long> idUserList = ReadHttpClient.getIdByUserList4(mbr1, mbr2, mbr3, mbr4);
            WebSocketEventSender.createChat(idUserList);
            reponse = "CHAT SUCCESSFULLY CREATED";
        }
        return reponse;
    }


    @ShellMethod("Get Chat List")
    public String chatList() {
        String reponse;
        if (ID_CLIENT == -1){
            reponse = "YOU MUST CONNECT FIRST";
        } 
        else {
            List<String> reponselist = ReadHttpClient.getChatList();
            if (reponselist.equals(emptyList)) reponse = "NO CHAT WITH THIS USER";
            else reponse = ReadHttpClient.listStringToString(reponselist);
        }
        return reponse;
    }

    @ShellMethod("Get Message List from a Chat")
    public String messageByChat(String chatRoom) {
        int chatroom = Integer.parseInt(chatRoom);
        String reponse;
        if (ID_CLIENT == -1){
            reponse = "YOU MUST CONNECT FIRST";
        } 
        else {
            List<String> reponselist = ReadHttpClient.getMessageFromChat(chatroom);
            if (reponselist.equals(emptyList)) reponse = "NO MESSAGE IN THIS CHAT";
            else if (ReadHttpClient.isUserInChat(chatroom, ID_CLIENT)){
                reponse = ReadHttpClient.listStringToString(reponselist);}
                else {reponse = "IMPOSSIBLE : YOU ARE NOT MEMBRE OF THIS CHAT";}
        }
        return reponse;
    }

    public static int getIdClient(){
        return ID_CLIENT;
    }

}
