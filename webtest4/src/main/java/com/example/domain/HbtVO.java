package com.example.domain;

import lombok.Data;

@Data
public class HbtVO {
	private int testCode;
	private String testName;
	private String protocolIndicator;
	private byte headerSize;
	private byte protocolVersion;
	private byte packetId;
	private byte lf;
	private byte rf;
	private byte ce;
	private byte lt;
	private byte to;
	private byte rc;
	private String opCode;
	private byte sidService;
	private byte sidgroup;
	private byte sidSystem;
	private byte didService;
	private byte didgroup;
	private byte didSystem;
	private byte atId;
	private byte atSize;
	private byte atData;
	private byte atParam;
	private byte atResult;
	private byte totalSeq;
	private float cycle;
}
