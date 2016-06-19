/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package VoiceConference;

/**
 *
 * @author leshan
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.* ;

public class RunClient extends Thread {


	private static DatagramSocket socket = null;

	private static int port = 5000;
        
        private static long sequenceNo=0;

	private  static void captureAndPlay() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean stopCapture = false;
        try {
            int readCount;
            while (!stopCapture) {
                readCount = VoiceConference.targetDataLine.read(VoiceConference.tempBuffer, 0, VoiceConference.tempBuffer.length);  //capture sound into tempBuffer
                if (readCount > 0) {
                    sequenceNo++;
                    sequenceNo=sequenceNo%Integer.MAX_VALUE;
                    
                    byteArrayOutputStream.write(VoiceConference.tempBuffer, 0, readCount);
                    
                    PacketData myPacket=new PacketData(sequenceNo);//creates a new packet object from the Packet class.gives the seq no as the parameter
                    
                    myPacket.setTempBuffer(VoiceConference.tempBuffer);
                    byte[] sendBuf=new byte[500];
                    sendBuf=PacketData.serialize(myPacket);//get the serialized packet
					
                    // sourceDataLine.write(tempBuffer, 0, 500);   //playing audio available in tempBuffer
				   
		    DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, VoiceConference.host, port);  
                    //System.out.println(new String(packet.getData()) ) ;
                                        
                    
                    // Send the packet
                    socket.send( packet ) ;
                    
                                    
                }
                
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

   public void run()
   {
       // Check the whether the arguments are given
     
		try {
		
			// Convert the arguments to ensure that they are valid
			
			
           //Q1: Create a datagram socket object here
    		RunClient.socket = new DatagramSocket();
			
        
		 
			VoiceConference obj = new VoiceConference();			 
			obj.captureAudio();
			RunClient.captureAndPlay();
			 
      }
      catch( Exception e )
      {
            e.printStackTrace();
      }
      finally{
            
            socket.close() ;
      
      }
     
   }
 

						
}
