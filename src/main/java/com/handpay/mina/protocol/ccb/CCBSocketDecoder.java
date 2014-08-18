package com.handpay.mina.protocol.ccb;

import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.handpay.mina.server.ConsoleTextArea;

public class CCBSocketDecoder extends CumulativeProtocolDecoder {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this.getClass());
	
	private CCBProtocolParser parser;
	
	public CCBProtocolParser getParser() {
		return parser;
	}

	public void setParser(CCBProtocolParser parser) {
		this.parser = parser;
	}

	private int convertPackageLength(String str){
		try{
			return Integer.valueOf(str);
		}catch(Exception ex){
			return -1;
		}
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		int readLen = in.remaining();
		logger.info("total package size:" + readLen);
		if (readLen <= 0) {
			return true;
		}
//		java.io.ByteArrayOutputStream buf = (java.io.ByteArrayOutputStream)session.getAttribute("buffer");
//		if (buf == null){
//			buf = new java.io.ByteArrayOutputStream();
//			session.setAttribute("buffer", buf);
//		}
		/**
		 * 读取包头+包体
		 */
		byte[] dataBytes = new byte[readLen];
		in.get(dataBytes);
//		buf.write(dataBytes);
//		session.setAttribute("buffer", buf);
		/**
		 * 分解包头内容
		 */
		String totalStr = new String(dataBytes, "utf-8");
		
//		BufferedInputStream bis = new BufferedInputStream(in);  
		/*String header = totalStr.substring(0,8);
		
		int length = this.convertPackageLength(header);
		if (length > buf.toByteArray().length - 8){
			logger.warn("not total get all package");
			return false;
		}*/
		
		String[] totalStrs=totalStr.split("\n");
		String method="";
		
		/**
		 * 分解包体内容
		 */		
		String body = "";//totalStr.substring(8);
		if(totalStrs!=null&&totalStrs.length>0){
			String[] methods=totalStrs[0].split(" ");
			method=methods[0];
			if("get".equalsIgnoreCase(method)){
				body=methods[1];
			}else if("post".equalsIgnoreCase(method)){
				if(methods[1].indexOf("?")!=-1){
					body=methods[1]+"&"+totalStrs[totalStrs.length-1];
				}else{
					body=methods[1]+"?"+totalStrs[totalStrs.length-1];					
				}
			}
			
			//其它头部信息
		}
		/**
		 * 解析请求报文
		 */
		Map<String,String> req = this.parser.parse(body);
		if (req == null){
			logger.error("not parse request body");
			throw new Exception("not parse request body");
		}
		out.write(req);
		session.removeAttribute("buffer");
		
		return true;
	}

	
}
