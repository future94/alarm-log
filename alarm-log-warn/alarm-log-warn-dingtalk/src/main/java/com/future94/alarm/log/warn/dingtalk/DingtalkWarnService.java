package com.future94.alarm.log.warn.dingtalk;

import com.future94.alarm.log.common.context.AlarmInfoContext;
import com.future94.alarm.log.common.context.AlarmLogContext;
import com.future94.alarm.log.common.utils.OkHttpUtils;
import com.future94.alarm.log.common.utils.ThrowableUtils;
import com.future94.alarm.log.warn.common.BaseWarnService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author weilai
 */
public class DingtalkWarnService extends BaseWarnService {

    private final Logger logger = LoggerFactory.getLogger(DingtalkWarnService.class);

    private final String token;

    private final String secret;

    private final Gson gson = new Gson();

    private static final String ROBOT_SEND_URL = "https://oapi.dingtalk.com/robot/send?access_token=";

    public DingtalkWarnService(String token, String secret) {
        this.token = token;
        this.secret = secret;
    }

    public String sendRobotMessage(String message) throws Exception {
        DingtalkSendParam param = new DingtalkSendParam();
        param.setMsgtype(DingtalkSendMsgTypeEnum.TEXT.getType());
        param.setText(new DingtalkSendParam.Text(message));
        return OkHttpUtils.getInstance().post(getSign(), gson.toJson(param));
    }

    /**
     * 获取签名
     * @return 返回签名
     */
    private String getSign() throws Exception {
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return ROBOT_SEND_URL + token + "&timestamp=" + timestamp + "&sign=" + URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), "UTF-8");
    }

    @Override
    protected void doSend(AlarmInfoContext context, Throwable throwable) throws Exception {
        String resp = sendRobotMessage(AlarmLogContext.getAlarmMessageContext().dingtalkContent(context, throwable, AlarmLogContext.getSimpleConfig()));
        logger.info("send dingtalk message resp:{}", resp);
        throw new RuntimeException();
    }
}
