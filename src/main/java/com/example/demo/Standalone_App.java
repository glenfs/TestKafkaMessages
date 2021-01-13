package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.example.demo.service.Producer;

public class Standalone_App implements ApplicationRunner{

	  @Autowired
	  Producer myKafkaProducer;
	  
	  @Value("${my.kafka.producer.numofmesages}")
	  private String numOfMessagestoProcess;
	  
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		int numberOfMessages = Integer.parseInt(numOfMessagestoProcess);
		  
		  StringBuilder sb_add = new StringBuilder();
		  StringBuilder sb_enrich = new StringBuilder();
		  final String addPayload_part1= "{\"last_updated\": \"2020-11-13 01:41:01\",\"ipaddress\": \"10.107.40.87\",\"enriched_flg\": 0,\"asset_id\": \"";
		  final String addPayload_part2= "\",\"is_commercial\": true,\"result\": \"ADD\",\"bulk_event_id\": null,\"active_flg\": 1,\"event_id\": 261159,\"is_commercial\":1,\"operation\": \"ADD\"}";
		  //final String addPayload = "{\"last_updated\": \"2020-11-13 01:41:01\",\"ipaddress\": \"10.107.40.87\",\"enriched_flg\": 0,\"asset_id\": \"4RXPOV4\",\"is_commercial\": true,\"result\": \"ADD\",\"bulk_event_id\": null,\"active_flg\": 1,\"event_id\": 261159,\"is_commercial\":1,\"operation\": \"AS\"}";
		  //final String enrichmentPayload = "{\"last_updated\": \"2020-11-13 01:41:01\",\"ipaddress\": \"10.107.40.87\",\"enriched_flg\": 0,\"asset_id\": \"4RXPOV4\",\"is_commercial\": true,\"bulk_event_id\": null,\"active_flg\": 1,\"event_id\": 261159,\"is_commercial\":1,\"operation\": \"ENRICHMENT\"}";
		  final String enrichmentPayload_part1 = "{\"last_updated\": \"2020-11-13 01:41:01\",\"ipaddress\": \"10.107.40.87\",\"enriched_flg\": 0,\"asset_id\": \"";
	      final String enrichmentPayload_part2= "\",\"is_commercial\": true,\"bulk_event_id\": null,\"active_flg\": 1,\"event_id\": 261159,\"is_commercial\":1,\"operation\": \"ENRICHMENT\"}";
	      
	      
	      for(int i=0;i<numberOfMessages;i++)
	      {
	    	  sb_add.setLength(0);
	    	  sb_enrich.setLength(0);
	    	  sb_add.append(addPayload_part1);
	    	  sb_add.append(i+"RXPO");
	    	  sb_add.append(addPayload_part2);
	    	  
	    	  sb_enrich.append(enrichmentPayload_part1);
	    	  sb_enrich.append(i+"RXPQ");
	    	  sb_enrich.append(enrichmentPayload_part2);
	    	  
	    	  System.out.println("data="+sb_add.toString());
	    	  System.out.println("data="+sb_enrich.toString());
	    	  myKafkaProducer.sendDataToKafka(sb_add.toString());
	    	  myKafkaProducer.sendDataToKafka(sb_enrich.toString());
	      }

	}

}
