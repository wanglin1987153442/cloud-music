package com.soft1851.music.admin.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.soft1851.music.admin.domain.entity.SysRole;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author wl
 * @ClassNameCreateToken
 * @Description TODO
 * @Date 2020/1/13
 * @Version 1.0
 */
@Slf4j
public class CreateToken {
    /**
     * 生成token
     *
     * @param num
     * @return
     */
    public static String getToken(String num, String role, String seecret) {
        //密匙

        Date start = new Date();
        //一小时有效时间

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
        //locadatetime转换成date

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Date end = Date.from(zonedDateTime.toInstant());
        System.out.println(end);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = null;
        try {
            token = JWT.create().withHeader(map).withClaim("uid", num)
                    .withClaim("role", role)
                    .withIssuer("niit").withIssuedAt(start).withExpiresAt(end)
                    .sign(Algorithm.HMAC256(Base64.getEncoder().encodeToString(seecret.getBytes())));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(token);
        return token;
    }

    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            //获得num
            return jwt.getClaim("uid").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getUserRole(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            //获得num
            return jwt.getClaim("role").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static boolean checkTime(String token) {
        DecodedJWT jwt = JWT.decode(token);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        if (jwt.getExpiresAt().after(date)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断token是否合法
     *
     * @param token
     * @return
     */
    public static boolean verify(String token, String secret) {
        try {
            //密匙

            DecodedJWT jwt;

            secret = Base64.getEncoder().encodeToString(secret.getBytes());
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            //判断时间等等是否合法

            jwt = verifier.verify(token);

            return true;

        } catch (Exception exception) {
            return false;
        }
    }



    public static void main(String[] args) throws UnsupportedEncodingException {
        String num = "123";
        SysRole role1 = SysRole.builder().roleId(1).roleName("admin").description("管理员").build();
        SysRole role2 = SysRole.builder().roleId(2).roleName("editor").description("小编").build();
        List<SysRole> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        String token = CreateToken.getToken(num, JSONObject.toJSONString(roles), "1111");
        String loginstate = CreateToken.getUserRole(token);
        System.out.println("负载学号为：" + loginstate);
        System.out.println("token合法性：" + CreateToken.verify(token, "1111"));
        DecodedJWT jwt = JWT.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlY29kZSI6IjAiLCJ1aWQiOiIxMjMiLCJpc3MiOiJuaWl0IiwiZXhwIjoxNTg2OTIxNzQ3LCJpYXQiOjE1ODY5MjE3NDR9.UFMicS8SqCRKJ6PHEXunHWeKUgyl4-OrpckJvNda3GA");
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        if (jwt.getExpiresAt().after(date)) {
            System.out.println("过期时间：      " + jwt.getExpiresAt());

        } else {
            System.out.println("时间已经过期");
        }
        boolean verify = CreateToken.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJzb2Z0MTg1MSIsInJvbGUiOiJbe1wicm9sZUlkXCI6MSxcInJvbGVOYW1lXCI6XCJhZG1pblwifSx7XCJyb2xlSWRcIjoyLFwicm9sZU5hbWVcIjpcImVkaXRvclwifV0iLCJpc3MiOiJuaWl0IiwiZXhwIjoxNTg3NjI3ODg0LCJpYXQiOjE1ODc2Mjc4Nzl9.pmSYp7mUAi3_VTeVNfJuI5Hd6q1KeqnClhvbq1rdSPQ", "111111");
        System.out.println(verify);
    }

}


















