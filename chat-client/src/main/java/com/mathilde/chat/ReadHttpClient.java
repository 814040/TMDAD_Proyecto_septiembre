package com.mathilde.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadHttpClient {
  static ResponseEntity<String> message;
  static ResponseEntity<String> user;
  static ResponseEntity<String> chat;
  final static String MESSAGE_URI = "http://localhost:8080/message";
  final static String USER_URI = "http://localhost:8080/user";
  final static String CHAT_URI = "http://localhost:8080/chatroom";


  // collect information from message page
  public static String getMessage() {
    RestTemplate restTemplate = new RestTemplate();
    message = restTemplate.getForEntity(MESSAGE_URI, String.class);
    return message.getBody();
  }

  // collect information from user page
  public static String getUser() {
    RestTemplate restTemplate = new RestTemplate();
    user = restTemplate.getForEntity(USER_URI, String.class);
    return user.getBody();
  }

   // collect information from chat page
   public static String getChat() {
    RestTemplate restTemplate = new RestTemplate();
    chat = restTemplate.getForEntity(CHAT_URI, String.class);
    return chat.getBody();
  }

  // general function to collect string infomation
  public static List<String> getElement(String textJSON, String element){
    List<String> reponse = new ArrayList<>();
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        String value = (String) arr.get(element);
        reponse.add(value);
      } 
    }
    return reponse;
  }

  public static List<String> getUserList(){
    String textJSON = getUser();
    List<String> reponse = new ArrayList<>();
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        String user = (String) arr.get("username");
        reponse.add(user);
      } 
    }
    return reponse;
  }

  // connect user
  public static int connectUser(String username, String password){
    String textJSON = getUser();
    int reponse = -1;
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        String usernameJSON = (String) arr.get("username");
        if (usernameJSON.equals(username)){
          String passwordJSON = (String) arr.get("password");
          if (passwordJSON.equals(password)){
            //get id here
            reponse = (int) arr.get("iduser"); //Client Connected
            break;
          } else {reponse = 0; break;} //Incorrect Password
        } else {reponse = -1;}//Client Does Not Exist
      } 
    }
    return reponse;
  }


  // return list of people who chat with user
    public static List<Integer> subscribeChatList(int iduser){
    List<Integer> reponse = new ArrayList<>();
    String textJSON = ReadHttpClient.getChat();
    int indexStart = 0;
    int indexEnd = 0;    
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        int idChatRoom = (int) arr.get("idChatRoom");
        int iduser1 =  (int) arr.get("user1ChatRoom");
        int iduser2 = (int) arr.get("user2ChatRoom");
        int iduser3 = (int) arr.get("user3ChatRoom");
        int iduser4 = (int) arr.get("user4ChatRoom");
        int iduser5 = (int) arr.get("user5ChatRoom");
        if (iduser1 == iduser || iduser2 == iduser || iduser3 == iduser || iduser4 == iduser || iduser5 == iduser){
          reponse.add(idChatRoom);
        }
      } 
    }
    return reponse;
  }

  public static boolean isUserInChat(int chatRoom, int iduser){
    boolean reponse = false;
    String textJSON = ReadHttpClient.getChat();
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        int idChatRoom = (int) arr.get("idChatRoom");
        if (idChatRoom==chatRoom){
          int iduser1 =  (int) arr.get("user1ChatRoom");
          int iduser2 = (int) arr.get("user2ChatRoom");
          int iduser3 = (int) arr.get("user3ChatRoom");
          int iduser4 = (int) arr.get("user4ChatRoom");
          int iduser5 = (int) arr.get("user5ChatRoom");
          if (iduser1 == iduser || iduser2 == iduser || iduser3 == iduser || iduser4 == iduser || iduser5 == iduser){
            reponse=true;
          }
        }
      }
    }
    return reponse;
  }

  

  // return list of messages from a chat
  public static List<String> getMessageFromChat(int idchat){
    List<String> reponse = new ArrayList<>();

    String textJSON = ReadHttpClient.getMessage();

    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        int idsender = (int) arr.get("idSender");
        int idchatroom = (int) arr.get("idReceiver");
        String textmessage = (String) arr.get("textmessage");
        String date = (String) arr.get("date");
        if (idchatroom == idchat){
          String username = getUserById(idsender);
          reponse.add("SEND : "+textmessage +"\nTO : "+ username+"\nAT "+ date+"\n-------------");
        }
      }
    }
    return reponse;
  }

  // return list of chats for one person
  public static List<String> getChatList(){
    List<String> reponse = new ArrayList<>();

    String textJSON = ReadHttpClient.getChat();
    int iduser = MyCommands.getIdClient();
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++) {
      char charI = textJSON.charAt(i);
      if (charI == '{') {
        indexStart = i;
      }
      if (charI == '}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON, indexStart, indexEnd + 1));
        int idChatRoom = (int) arr.get("idChatRoom");
        int iduser1 = (int) arr.get("user1ChatRoom");
        int iduser2 = (int) arr.get("user2ChatRoom");
        int iduser3 = (int) arr.get("user3ChatRoom");
        int iduser4 = (int) arr.get("user4ChatRoom");
        int iduser5 = (int) arr.get("user5ChatRoom");
        if (iduser1 == iduser || iduser2 == iduser || iduser3 == iduser || iduser4 == iduser || iduser5 == iduser) {
          reponse.add(Integer.toString(idChatRoom));
        }
      }
    }
    return reponse;
  }

  static ArrayList<Long> getIdByUserList2(String mbr1, String mbr2){
    Long id0 = Long.valueOf(MyCommands.getIdClient());
    Long id1 = Long.valueOf(getIdByUser(mbr1));
    Long id2 = Long.valueOf(getIdByUser(mbr2));
    return new ArrayList<Long>(Arrays.asList(id0, id1, id2));
  }

  static ArrayList<Long> getIdByUserList3(String mbr1, String mbr2, String mbr3){
    Long id0 = Long.valueOf(MyCommands.getIdClient());
    Long id1 = Long.valueOf(getIdByUser(mbr1));
    Long id2 = Long.valueOf(getIdByUser(mbr2));
    Long id3 = Long.valueOf(getIdByUser(mbr3));
    return new ArrayList<Long>(Arrays.asList(id0, id1, id2, id3));
  }

  static ArrayList<Long> getIdByUserList4(String mbr1, String mbr2, String mbr3, String mbr4){
    Long id0 = Long.valueOf(MyCommands.getIdClient());
    Long id1 = Long.valueOf(getIdByUser(mbr1));
    Long id2 = Long.valueOf(getIdByUser(mbr2));
    Long id3 = Long.valueOf(getIdByUser(mbr3));
    Long id4 = Long.valueOf(getIdByUser(mbr4));
    return new ArrayList<Long>(Arrays.asList(id0, id1, id2, id3, id4));
  }

  static String getUserById(int iduser) {
    String textJSON = getUser();
    String reponse="";
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        int iduserJSON = (int) arr.get("iduser");
        if (iduserJSON==iduser){
          reponse = (String) arr.get("username");
          break;
        } else {reponse = "THIS USER DOES NOT EXIST";}
      } 
    }
    return reponse;
  }

  public static int getIdByUser(String username) {
    String textJSON = getUser();
    int reponse=-1;
    int indexStart = 0;
    int indexEnd = 0;
    for (int i = 0; i <textJSON.length(); i++){
      char charI = textJSON.charAt(i);
      if (charI=='{') {
        indexStart = i;
      }
      if (charI=='}') {
        indexEnd = i;
        JSONObject arr = new JSONObject(sliceRange(textJSON,indexStart, indexEnd+1));
        String usernameJSON = (String) arr.get("username");
        if (usernameJSON.equals(username)){
          reponse = (int) arr.get("iduser");
          break;
        }
      } 
    }
    return reponse;
  }

  private static String sliceRange(String s, int startIndex, int endIndex) {
    if (startIndex < 0) startIndex = s.length() + startIndex;
    if (endIndex < 0) endIndex = s.length() + endIndex;
    return s.substring(startIndex, endIndex);
  }

	public static String listStringToString(List<String> list) {
		String delim = "\n";
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < list.size() - 1) {
			sb.append(list.get(i));
			sb.append(delim);
			i++;
		}
		sb.append(list.get(i));
		String res = sb.toString();
		return res;
	}
}