package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.service.Producer;

@Component
public class Standalone_App implements ApplicationRunner{

	  @Autowired
	  Producer myKafkaProducer;
	  
	  @Value("${my.kafka.producer.numofmesages}")
	  private String numOfMessagestoProcess;
	  
	  @Value("${my.kafka.producer.timetokeeprunningmins}")
	  private String timetokeeprunningmins;
	  
	  @Value("${ my.kafka.app.mode}")
	  private String mode;

	  Random ran = new Random(); 
	  StringBuilder sb_selector= new StringBuilder();
	  StringBuilder sb_add = new StringBuilder();
	  StringBuilder sb_enrich = new StringBuilder();
	  final String addPayload_part1= "{\"sacc_last_updated\": \"2020-11-13 01:41:01\",  \"ipaddress\": \"10.107.40.87\",  \"enriched_flg\": 0,  \"ngam_origin_flg\": 0,  \"manage_option\": \"4\",  \"asset_id\": \"";
	  final String addPayload_part2= "\",  \"is_commercial\": true,  \"result\": \"ADD\",  \"bulk_event_id\": null,  \"hostname\": \"W1022B2S72\",  \"asset_identifier\": \"22B2S72\",  \"sa_version\": \"2.2.1.76\",  \"asset_type\": \"SERVICETAG\",  \"pending_flg\": \"0\",  \"bu_id\": \"11\",  \"accnt_id\": [    \"8291\"  ],  \"sa_registration_id\": \"b2nepire5rhkzvt7cwrmgj8d2ke61djz\",  \"event_created_by\": \"RULES ENGINE\",  \"active_flg\": 1,  \"event_id\": 261159,  \"support_assist_flg\": 1,  \"ui_event_id\": null,  \"group_id\": [    \"8291:11644:4420\"  ],  \"site_id\": [    \"8291:11644\"  ],  \"os_name\": \"Microsoft Windows 10 Enterprise\",\"is_commercial\":1,  \"operation\": \"ADD\"}";
	  //final String addPayload = "{\"last_updated\": \"2020-11-13 01:41:01\",\"ipaddress\": \"10.107.40.87\",\"enriched_flg\": 0,\"asset_id\": \"4RXPOV4\",\"is_commercial\": true,\"result\": \"ADD\",\"bulk_event_id\": null,\"active_flg\": 1,\"event_id\": 261159,\"is_commercial\":1,\"operation\": \"AS\"}";
	  //final String enrichmentPayload = "{\"last_updated\": \"2020-11-13 01:41:01\",\"ipaddress\": \"10.107.40.87\",\"enriched_flg\": 0,\"asset_id\": \"4RXPOV4\",\"is_commercial\": true,\"bulk_event_id\": null,\"active_flg\": 1,\"event_id\": 261159,\"is_commercial\":1,\"operation\": \"ENRICHMENT\"}";
	  final String enrichmentPayload_part1 = "{\"asset_id\": \"";
      final String enrichmentPayload_part2 = "\", \"operation\": \"ENRICHMENT\", \"asset_tag\": \"A6158205\",  \"is_commercial\":true}";
      final String enrichmentProcessorPayload_part1= "{\"area_code\": \"US\",  \"area_desc\": \"United States\",  \"asset_id\": \"";
      final String enrichmentProcessorPayload_part2 ="\",  \"asset_id_type\": \"SVC TAG ID\",  \"asset_identifier\": \"FQ4FNH2\",  \"asset_type\": \"SERVICETAG\",  \"bu_desc\": \"United States\",  \"city\": \"GRENOBLE\",  \"cntrct_stat_desc\": \"ACTIVE\",  \"cntrct_stat_id\": \"1\",  \"country\": \"United States\",  \"cust_buid\": \"11\",  \"cust_num\": \"1717\",  \"end_of_lease_flg\": \"Y\",  \"event_created_by\": \"ENRICHMENT-PROCESSOR\",  \"fmly\": \"BANDONFBTX\",  \"fmly_prnt\": \"BANDONBTX\",  \"highest_lvl_offer_code\": \"PS\",  \"incremental_flg\": \"Y\",  \"init_cntrct_code\": \"I\",  \"is_commercial\": true,  \"iso_ctry_code_2\": \"US\",  \"iso_ctry_code_3\": \"USA\",  \"itm_cls_code\": \"1I002\",  \"itm_cls_desc\": \"LAT 5300 2-IN-1,NBK,BANDON BTX\",  \"itm_num\": \"710-65683\",  \"lcl_chnl_code\": \"PREF\",  \"mfr_id\": \"1\",  \"mfr_num\": \"DELL\",  \"mfr_srl_num\": \"CN015DR51296379B04AB\",  \"operation\": \"ENRICHMENT\",  \"ord_src_type\": \"ORD\",  \"order_num\": \"080038493\",  \"postal_code\": \"38030 \",  \"prod_grp\": \"Commercial\",  \"prod_line\": \"LATITUDE 5300 2-IN-1\",  \"prod_lob\": \"Latitude\",  \"prod_type\": \"Client Solutions PBU\",  \"rgn_abbr\": \"AMER\",  \"rgn_desc\": \"AMERICAS\",  \"shipd_dts_gmt\": \"2020-07-01 00:00:00\",  \"src_ord_type_code\": \"FR REL Order\",  \"svc_lvl_code\": \"TS\",  \"svc_lvl_desc\": \"P, ProSupport\"}";
      final String enrichmentWarrantyLoaderPayload_part1 ="{  \"cntrct_end_dts_gmt\": \"2021-04-12 18:00:59\",  \"warranty_code\": \"PSP\",  \"asset_id\": \"";
      final String enrichmentWarrantyLoaderPayload_part2 = "\",  \"cntrct_strt_dts_gmt\": \"2018-04-11 19:00:00\",  \"warranty_desc\": \"ProSupport Plus\",  \"is_commercial\": true,  \"operation\": \"ENRICHMENT\",  \"event_created_by\": \"WARRANTY-LOADER\"}";
      int numOfMessageProcessed =0;
	  int numOfAddProcessed =0;
	  int numOfEnrichProcessorProcessed =0;
	  int numOfWarrantyLoaderProcessed =0;
	  int numOfEnrichProcessed =0;
      
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		if(mode.equals("performance")) {
		int numberOfMessages = Integer.parseInt(numOfMessagestoProcess);
		  int selector;
		  long timeToRun = Integer.parseInt(timetokeeprunningmins)*60*1000;
		  
		  
	      if( numberOfMessages<0)
	      {
	    	  int i=0;
	    	  long t= System.currentTimeMillis();
	    	  long end = t+timeToRun;
	    	  
	    	  while(System.currentTimeMillis() < end)
	    	  {
	    		  i=i+1;
	    		  selector=Math.abs(ran.nextInt()%4);
	    		  //System.out.println("S E LE C T OT R"+selector);
	    		  generateMessageToKafka(i,selector);
	    	  }
	      }
	      else {
	      for(int i=0;i<numberOfMessages;i++)
	      {
	    	  selector=ran.nextInt()%4;
	    	  generateMessageToKafka(i,selector);
	      }
	      }
	      
	      System.out.println("numOfMessageProcessed="+numOfMessageProcessed);
	      System.out.println("numOfAddProcessed="+numOfAddProcessed);
	      System.out.println("numOfEnrichProcessorProcessed="+numOfEnrichProcessorProcessed);
	      System.out.println("numOfWarrantyLoaderProcessed="+numOfWarrantyLoaderProcessed);
	      System.out.println("numOfEnrichProcessed="+numOfEnrichProcessed);
	      
	      BufferedWriter writer = null;
	      try {
	      writer = new BufferedWriter(new FileWriter("SerilizerTestMsgGenStats_"+System.currentTimeMillis()+".log"));

	      writer.write("numOfMessageProcessed="+numOfMessageProcessed+"\n");
	      writer.write("numOfAddProcessed="+numOfAddProcessed+"\n");
	      writer.write("numOfEnrichProcessorProcessed="+numOfEnrichProcessorProcessed+"\n");
	      writer.write("numOfWarrantyLoaderProcessed="+numOfWarrantyLoaderProcessed+"\n");
	      writer.write("numOfEnrichProcessed="+numOfEnrichProcessed+"\n");
	      writer.close();
	      }
	      catch(Exception ex)
	      {
	    	  writer.close();  
	      }
		}// if mode=performance.
	      System.exit(0);
	}

	private void generateMessageToKafka(int i, int selector) {
		//System.out.println("S E LE C T OT R"+selector);
	 switch (selector)
  	  {
  	  case 0:{
  		//System.out.println("0 0 0 0 0 0 ");
  		payloadSelector(addPayload_part1, addPayload_part2, i);
  		numOfAddProcessed++;
  		break;
  	      }
  	  case 1:{
  		//System.out.println("1 1 1 1 1 1");
  		payloadSelector(enrichmentPayload_part1, enrichmentPayload_part2, i);
  		numOfEnrichProcessed++;
  		break;
  	  }
  	 case 2:{
  		//System.out.println("2 2 2 2 2 2 2 ");
  		payloadSelector(enrichmentProcessorPayload_part1, enrichmentProcessorPayload_part2, i);
  		numOfEnrichProcessorProcessed++;
  		break;
  	  }
  	case 3:{
  		//System.out.println("3 3 3 3 3 3 3 ");
  		payloadSelector(enrichmentWarrantyLoaderPayload_part1, enrichmentWarrantyLoaderPayload_part2, i);
  		numOfWarrantyLoaderProcessed++;
  		break;	
  	}
  	default:{
  		//System.out.println("D D D D D D D");
  		payloadSelector(addPayload_part1, addPayload_part2, i);
  		numOfAddProcessed++;
  		break;
  	 }
  	  }
	 numOfMessageProcessed++;
		  System.out.println("data="+sb_selector.toString());
		  myKafkaProducer.sendDataToKafka(sb_selector.toString());
	}

	private void payloadSelector(final String addPayload_part1, final String addPayload_part2, int i) {
		sb_selector.setLength(0);
  		sb_selector.append(addPayload_part1);
  		sb_selector.append(i+"RXPO");
  		sb_selector.append(addPayload_part2);
	}

}
