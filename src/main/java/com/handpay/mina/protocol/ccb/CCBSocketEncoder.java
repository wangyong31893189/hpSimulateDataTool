package com.handpay.mina.protocol.ccb;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.handpay.core.common.util.ObjectUtil;
import com.handpay.mina.protocol.ccb.obj.CCBResponse;
import com.handpay.mina.protocol.ccb.obj.CommonErrorRes;
import com.handpay.mina.server.ConsoleTextArea;
import com.handpay.util.MD5Util;

public class CCBSocketEncoder implements ProtocolEncoder {
	private ConsoleTextArea logger = ConsoleTextArea.getInstance(this.getClass());

	private String MD5Key;// abcdefgh12345678ccbttf21

	public String getMD5Key() {
		return MD5Key;
	}

	public void setMD5Key(String mD5Key) {
		MD5Key = mD5Key;
	}

	@Override
	public void dispose(IoSession session) throws Exception {

	}

	private String encode(CCBResponse res) {
		if (res instanceof CommonErrorRes) {
			CommonErrorRes resObj = (CommonErrorRes) res;
			String encBody = resObj.getResult() + resObj.getErrorMsg()
					+ res.getTime() + this.MD5Key;
			resObj.setSign(MD5Util.getMD5Str(encBody, "GB2312").toLowerCase());
			StringBuffer body = new StringBuffer();			
			body.append(resObj.getResult()).append("~");
			body.append(resObj.getErrorMsg()).append("~");
			body.append(resObj.getTime()).append("~");
			body.append(resObj.getSign());
			return body.toString();
		}else {
			return null;
		}
	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
//		if (!(message instanceof CCBResponse)) {
//			logger.error("response message error");
//			throw new Exception("response message error");
//		}
//		CCBResponse res = (CCBResponse) message;
//		res.setTime(ObjectUtil.getCurrentDateStr("yyyyMMddHHmmss"));
//		String responseBody = this.encode(res);
		String responseBody =message.toString();
	
		if (ObjectUtil.isNull(responseBody)) {
			throw new Exception("create response fail");
		}
		byte[] bodyBytes = responseBody.getBytes("utf-8");
//		String header = ObjectUtil.convertInt2String(bodyBytes.length, 8);
		IoBuffer buffer = IoBuffer.allocate(bodyBytes.length, false);
//		buffer.put(header.getBytes("GB2312"));
		buffer.put(bodyBytes);
		buffer.flip();
		out.write(buffer);

	}

}
