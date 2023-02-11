package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@SpringBootApplication
public class FirebaseTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirebaseTestApplication.class, args);
		setFirebase() ;
		
		try {
			sendToToken();
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
		
		
		
	}

	
	  public static void setFirebase() {

			FileInputStream serviceAccount;
			try {
				Resource resource = new ClassPathResource("dps-ebbbe-firebase-adminsdk-16pgd-3a2367e91b.json");
				serviceAccount = new FileInputStream(resource.getFile());
				FirebaseOptions options =  FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
				FirebaseApp.initializeApp(options);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				//
				e.printStackTrace();
			}
		}
	  
	  
	  public static void sendToToken() throws FirebaseMessagingException {
		    // [START send_to_token]
		    // This registration token comes from the client FCM SDKs.
		    String registrationToken = "eYQefEzPTV26MjM7Roe3zC:APA91bHkHAp9wr0DUSVNEpvIRp4VFZiLd2L-FD9QnJ1wN9XK4ebHHVhNjg-A4vrQeFA_dqFGuwxnY98EVI4oVhndIX4cfgEvVrDCnpdUd0TY_6viOZiSNxvr3tDcW40nXCAiC-6dhXn-";

		    Notification.Builder nb =  Notification.builder();
		    nb.setTitle("BURN 5");
		    nb.setBody("This is the 5 Message");
		    
		    
		    Notification notification = nb.build();
		    
		    System.out.println("NOTIFICATION");
		    System.out.println(notification.toString());
		    
		    // See documentation on defining a message payload.
		    Message message = Message.builder()
		    	.setNotification(notification)
		    	.putData("data1", "apple")
		        .putData("data2", "mango")
		        .setToken(registrationToken)
		        .build();
		    
		    System.out.println("MESSAGE");
		    System.out.println(message.toString());

		    // Send a message to the device corresponding to the provided
		    // registration token.
		    String response = FirebaseMessaging.getInstance().send(message);
		    // Response is a message ID string.
		    System.out.println("Successfully sent message: " + response);
		    // [END send_to_token]
	}
}
