package com.example.domain;

import lombok.Data;

@Data
public class HbtVO {
	private int TEST_CODE;
	private String TEST_NAME;
	private String PROTOCOL_INDICATOR;
	private byte HEADERSIZE;
	private byte PROTOCOL_VERSION;
	private byte PACKET_ID;
	private byte LF;
	private byte RF;
	private byte CE;
	private byte TR;
	private byte TO_A;
	private byte RC;
	private String OP_CODE;
	private byte SID_SERVICE_ID;
	private byte SID_GROUP_ID;
	private byte SID_SYSTEM_ID;
	private byte DID_SERVICE_ID;
	private byte DID_GROUP_ID;
	private byte DID_SYSTEM_ID;
	
	private String GREQ_AT_ID;
	
	private String SREQ_AT_ID;
	private String SREQ_AT_SIZE;
	private String SREQ_AT_DATA;
	
	private String AREQ_AT_ID;
	private String AREQ_AT_SIZE;
	private String AREQ_AT_PARAM;
	
	private String ARES_AT_ID;
	private String ARES_AT_RESULT;
	private String ARES_AT_SIZE;
	private String ARES_AT_PARAM;
	
	private String EREQ_AT_ID;
	private String EREQ_AT_SIZE;
	private String EREQ_AT_DATA;
	
	private byte TOTAL_SEQ;
	private float CYCLE;
	
	private String RESULT;
	
	private String IP;
	private int PORT;
	
	private int IS_REMOVED;
}
