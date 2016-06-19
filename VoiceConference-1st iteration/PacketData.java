/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package VoiceConference;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leshan
 */
public class PacketData implements Serializable {
    
    public byte tempBuffer[] = new byte[500];
    private static PacketData packet;
    public long seqNo;
    public byte[] sendBuf; 

    public PacketData() {
    }
    
    
    
    /*adds seq no to the packet from the constructor*/
    public PacketData(long seqNo) {
  
        this.seqNo=seqNo;
    }

    
    
    
    

    /**
     * @return the tempBuffer
     */
    public byte[] getTempBuffer() {
        return this.tempBuffer;
    }

    /**
     * @param aTempBuffer the tempBuffer to set
     */
    public void setTempBuffer(byte[] aTempBuffer) {
        this.tempBuffer = aTempBuffer;
    }

    /**
     * @return the seqNo
     */
    public long getSeqNo() {
        return this.seqNo;
    }

    

    /**
     * @return the packet
     */
    public static PacketData getPacket() {
        return packet;
    }

    /**
     * @param aPacket the packet to set
     */
    public static void setPacket(PacketData aPacket) {
        packet = aPacket;
    }
    
    //to serialize the object
    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(b))){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }
    
    
    //to deserialize the byte stream
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(new BufferedInputStream(b))){
                return o.readObject();
            }
        }
    }
    
    //to count the packet loss
    
    
    
    
    //unit test to check serializing and deserializing happens
    public static void main(String[] args) {
        PacketData myPacket=new PacketData();
        
        byte[] buffer=new byte[10];
        byte[] sendBuf=new byte[500];
        PacketData receivedPacket=null;
        
        myPacket.setTempBuffer(buffer);
        myPacket.seqNo=11;
        try {
            sendBuf=PacketData.serialize(myPacket);
        } catch (IOException ex) {
            Logger.getLogger(PacketData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            receivedPacket=(PacketData)PacketData.deserialize(sendBuf);
        } catch (IOException ex) {
            Logger.getLogger(PacketData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PacketData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(receivedPacket.getSeqNo()+"\n");
    }
    
}

 
