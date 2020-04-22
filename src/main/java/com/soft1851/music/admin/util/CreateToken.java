package com.soft1851.music.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String getToken(String num) {
        //密匙
        String key = "NiitScsWL";
        Date start = new Date();
        //一小时有效时间

        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(5);
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
                    .withIssuer("niit").withIssuedAt(start).withExpiresAt(end)
                    .sign(Algorithm.HMAC256(Base64.getEncoder().encodeToString(key.getBytes())));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(token);
        return token;
    }

    /**
     * 获取登陆用户ID
     *
     * @param token
     * @return
     */


    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            //获得num
            return jwt.getClaim("uid").asString();
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
    public static boolean verify(String token) {
        try {
            //密匙
            String key = "NiitScsWL";
            key = Base64.getEncoder().encodeToString(key.getBytes());
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            //判断时间等等是否合法
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {

            return false;
        }
    }



    public static void main(String[] args) throws UnsupportedEncodingException {
        String num = "123";

        String token = CreateToken.getToken(num );
        String loginstate = CreateToken.getUserId(token);
        System.out.println("负载学号为：" + loginstate);
        System.out.println("token合法性：" + CreateToken.verify(token));
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
        boolean verify = CreateToken.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlY29kZSI6IjAiLCJ1aWQiOiIxMjMiLCJpc3MiOiJuaWl0IiwiZXhwIjoxNTg2OTM5NjgyLCJpYXQiOjE1ODY5Mzk2NTJ9.AlPUF5zUzHzpdgMIfEd5o-TNcKHPjRvkQLdz40luWb0");
        System.out.println(verify);
    }

}


















