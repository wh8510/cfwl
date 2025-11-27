package org.example.cfwl.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    @Value("${token.userSecretKey}")
    private String userSecretKey;
    @Value("${token.adminSecretKey}")
    private String adminSecretKey;
    /**
     * 加密token.
     */
    //user
    public String getUserToken(Long userId, String userName) {
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        String token = JWT
                .create()
                .withClaim("userId",userId)
                .withClaim("userName" ,userName)
                // .withClaim("userRole", userRole)
                .withClaim("timeStamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256(userSecretKey));
        return token;
    }
    public String getAdminToken(Long adminId,String adminName) {
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        String token = JWT
                .create()
                // .withClaim("adminId",adminId)
                .withClaim("adminName" ,adminName)
                // .withClaim("adminRole", adminRole)
                .withClaim("timeStamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256(adminSecretKey));
        return token;
    }

    /**
     * 解析token.
     * {
     * "userId": "weizhong",
     * "userRole": "ROLE_ADMIN",
     * "timeStamp": "134143214"
     * }
     */
    //student
    public Map<String, String> userToken(String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(userSecretKey))
                .build().verify(token);
        Claim userId = decodedjwt.getClaim("userId");
        Claim userName = decodedjwt.getClaim("userName");
        // Claim userRole = decodedjwt.getClaim("userRole");
        Claim timeStamp = decodedjwt.getClaim("timeStamp");
        map.put("userId", String.valueOf(userId.asLong()));
        map.put("userName", userName.asString());
        // map.put("userRole", userRole.asString());
        map.put("timeStamp", timeStamp.asLong().toString());
        return map;
    }
    public Map<String, String> adminToken(String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(adminSecretKey))
                .build().verify(token);
        Claim adminId= decodedjwt.getClaim("adminId");
        Claim adminName = decodedjwt.getClaim("adminName");
        // Claim adminRole = decodedjwt.getClaim("adminRole");
        Claim timeStamp = decodedjwt.getClaim("timeStamp");
        // map.put("adminId", String.valueOf(adminId.asLong()));
        map.put("adminName", adminName.asString());
        // map.put("adminRole", adminRole.asString());
        map.put("timeStamp", timeStamp.asLong().toString());
        return map;
    }
}