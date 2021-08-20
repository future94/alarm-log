package com.future94.alarm.log.warn.workweixin;

import com.future94.alarm.log.common.dto.AlarmInfoContext;
import com.future94.alarm.log.common.utils.OkHttpUtils;
import com.future94.alarm.log.common.utils.ThrowableUtils;
import com.future94.alarm.log.warn.common.BaseWarnService;
import com.future94.alarm.log.common.utils.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author weilai
 */
public class WorkWeixinWarnService extends BaseWarnService {

    private final Logger logger = LoggerFactory.getLogger(WorkWeixinWarnService.class);

    private static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";

    private static final Integer TOKEN_EXPIRES_IN = 7000000;

    private final Gson gson = new Gson();

    private Pair<String, Long> tokenCache = new Pair<>();

    private String to;

    private Integer applicationId;

    private String corpid;

    private String corpsecret;

    public WorkWeixinWarnService(String to, Integer applicationId, String corpid, String corpsecret) {
        this.to = to;
        this.applicationId = applicationId;
        this.corpid = corpid;
        this.corpsecret = corpsecret;
    }

    /**
     * 微信授权请求，GET类型，获取授权响应，用于其他方法截取token
     */
    private String toAuth(String getTokenUrl) throws IOException {
        String resp = OkHttpUtils.getInstance().get(getTokenUrl);
        logger.info("get work weixin token resp:{}", resp);
        return resp;
    }

    private String getToken() throws IOException {
        if (Objects.isNull(tokenCache.getKey()) || Objects.isNull(tokenCache.getValue()) || (System.currentTimeMillis() - tokenCache.getValue() > TOKEN_EXPIRES_IN)) {
            String resp = toAuth(String.format(GET_TOKEN_URL, corpid, corpsecret));
            Map<String, Object> map = gson.fromJson(resp,
                    new TypeToken<Map<String, Object>>() {
                    }.getType());
            String token = map.get("access_token").toString();
            tokenCache = new Pair<>(token, System.currentTimeMillis());
        }
        return tokenCache.getKey();
    }

    private String createPostData(String touser, String msgtype, String contentValue) {
        WorkWeixinSendParam wcd = new WorkWeixinSendParam();
        wcd.setTouser(touser);
        wcd.setAgentid(applicationId);
        wcd.setMsgtype(msgtype);
        Map<String, Object> content = new HashMap<>();
        content.put("content", contentValue);
        wcd.setText(content);
        return gson.toJson(wcd);
    }

    private String toUser(String[] receiver) {
        String[] arr = this.toString(receiver).split(",");
        StringBuilder sb = new StringBuilder();
        //将不含有@符号的邮箱地址取出
        for (String str : arr) {
            if (!str.contains("@")) {
                sb.append(str).append("|");
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * 数组转字符串
     **/
    private String toString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            sb.append(str).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    @Override
    protected void doSend(AlarmInfoContext context, Throwable throwable) throws Exception {
        String data = createPostData(toUser(to.split(",")), WorkWeixinSendMsgTypeEnum.TEXT.name(), ThrowableUtils.workWeixinContent(context, throwable));
        String url = String.format(SEND_MESSAGE_URL, getToken());
        String resp = OkHttpUtils.getInstance().post(url, data);
        logger.info("send work weixin message call [{}], param:{}, resp:{}", url, data, resp);
    }
}
