/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package VoiceConference;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author leshan
 */
public class Statistics {
    //Store packet sequence numbers
	public ArrayList<Long> list; 

	public Statistics()
	{
		this.list = new ArrayList<Long>();
	}

	//Add sequence number to list
	public void addPcktSeqNo(long seq)
	{
		this.list.add(seq);
	}

	//calculate the current loss 
	public long getLossCount()
	{
		Collections.sort(list);
		return list.get(list.size()-1)- list.get(0) - list.size()+1;
	}
	
}
