package kr.tracom.platform.net.config;

import lombok.Data;

@Data
public class TimsConfig {
    private String platformId;
    private String channelName;
    private int channelPort;
    private int readTimeout;
    private int writeTimeout;

    private String headerType;
    private String endianType;
    private String strEncoding;

    private int maxPacketSize;
    private int maxPacketCount;
    private byte indicator;
    private byte version;

    private int apduOffset;

    private byte retryCount;
    private byte responseTimeOut;
    private byte transferRoute;
    private byte crcEnable;
    private byte remoteFlowControl;
    private byte localFlowControl;

    public TimsConfig() {
        this.channelName = "BIS-CHANNEL";
        this.channelPort = 8083;
        this.headerType = "A";
        this.endianType = "little";
        this.strEncoding = "EUC-KR";

        this.indicator = 0x41;
        this.maxPacketSize = 65536;
        this.maxPacketCount = 256;
        this.version = 0x01;
        this.apduOffset = 6;
        this.readTimeout = 120;

        this.retryCount = 3;
        this.responseTimeOut = 3;
        this.crcEnable = 1;
        this.transferRoute = 0;
        this.remoteFlowControl = 0;
        this.localFlowControl = 0;
    }
    
    
    public TimsConfig(byte protocolIndicator, byte protocolVersion, byte lf, byte rf, byte ce, byte tr, byte to, byte rc) {
        this.channelName = "BIS-CHANNEL";
        this.channelPort = 8083;
        this.headerType = "A";
        this.endianType = "little";
        this.strEncoding = "EUC-KR";

        this.indicator = protocolIndicator;
        this.maxPacketSize = 65536;
        this.maxPacketCount = 256;
        this.version = protocolVersion;
        this.apduOffset = 6;
        this.readTimeout = 120;

        this.retryCount = rc;
        this.responseTimeOut = to;
        this.crcEnable = ce;
        this.transferRoute = tr;
        this.remoteFlowControl = rf;
        this.localFlowControl = lf;
    }
    
    
    
}
