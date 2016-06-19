/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package VoiceConference;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 *
 * @author leshan
 */
public class RunServer extends Thread {

	private static int port  = 5000;
        

	public void run(){
            
            long startTime,endTime,diff;//to get the time count to get the packet loss

	    try
	    {
       
	         // Construct the socket
	        DatagramSocket socket = new DatagramSocket( RunServer.port ) ; /*Non Recoverable*/

	        System.out.println( "The server is ready..." ) ;
			
                // Create a packet
               VoiceConference us = new VoiceConference();
               us.captureAudio();
               
               startTime = new Date().getTime();
               
               Statistics stats = new Statistics();//cretaes an object from Statistics class
	      
	        for( ;; ) {
	            try{
                            byte[] recvBuf = new byte[1000];//to get the received buffer
                    // Receive a packet (blocking)
                            DatagramPacket packet = new DatagramPacket( recvBuf, VoiceConference.packetsize ) ;  /*Recoverable*/

                            socket.receive( packet ) ;     /*Recoverable*/


                            if(packet.getData()!=null){

                                PacketData receivedpacket=(PacketData)PacketData.deserialize(packet.getData());

                                stats.addPcktSeqNo(receivedpacket.getSeqNo());
                                
                                endTime = new Date().getTime();
                                
                                //reset log every 60 sec
                                if((endTime - startTime)> 60000){
                                    
                                    System.out.println("No of lost packets in previous min - "+stats.getLossCount());
                                    startTime = new Date().getTime();//reset timer
                                    
                                    stats = new Statistics();
                                    
                                    
                                    
                                }
                                
                                VoiceConference.sourceDataLine.write(receivedpacket.getTempBuffer(), 0, 500); 
                            }
	            }
	            catch(Exception e)
	            {
			        e.printStackTrace(); 
			
                    }
	            
            }  
    	}catch( Exception e )
    	{
        	e.printStackTrace();
		
    	}
    }	
		
}
