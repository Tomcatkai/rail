package com.zr.rail.utils.jwt;

import com.zr.rail.utils.Constants;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

/**
 * @description: 测试类
 * @author: KaiZhang
 * @create: 2018-09-13 11:29
 **/
public class TestJwtUtil {
    //    eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE0OTM4MDU5NDIsInN1YiI6IntcInJvbGVJZFwiOjEsXCJ1c2VySWRcIjoxMDAwMX0iLCJleHAiOjE0OTM4MDU5NjJ9.vtFppRcuvOaPZfx8LQhUkA0KbkDmA0J1KECvfbEKTjc
    //    eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE0OTM4MDU5NDQsInN1YiI6IntcInJvbGVJZFwiOjEsXCJ1c2VySWRcIjoxMDAwMX0iLCJleHAiOjE0OTM4MDYwMDR9.8M9OT8bA53rGPFQqs_UJMhw-jLsncYasiSptniL7-MU

    @Test
    public void testGeneralKey() throws Exception {
        JwtUtil jwt = new JwtUtil();
        User user = new User();
        user.setUserId(10001L);
        user.setRoleId(1L);
        String subject = JwtUtil.generalSubject(user);
        String token = jwt.createJWT(Constants.JWT_ID, subject, Constants.JWT_REFRESH_TTL);
        System.out.println(token);
    }

    @Test
    public void testParseJWT() throws Exception {
        JwtUtil jwt = new JwtUtil();
        Claims claims = jwt.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJyb2xlSWRcIjoxLFwidXNlcklkXCI6MTAwMDF9IiwiZXhwIjoxNTM2ODI1MTE4LCJpYXQiOjE1MzY4MjE1MTgsImp0aSI6Imp3dCJ9.4zSijO2awjUY37nTXkMg7zkoUyPyb4M1RESh6ZMYPec");
        System.out.println(claims.getSubject());
    }
}
