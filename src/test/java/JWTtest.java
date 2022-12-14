import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTtest {
    @Test
    public void test() throws Exception {
        // 生成密钥
        String key = "1145141919810_henghenghengaaaaaaaa";
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        System.out.println(secretKey);

        // 1. 生成 token
        String token = Jwts.builder()     // 创建 JWT 对象
                .setSubject("JSON Web Token")   // 设置主题（声明信息）
                .setExpiration(new Date(System.currentTimeMillis()+1000))
                .setId("fuckingToken")
                .signWith(secretKey)    // 设置安全密钥（生成签名所需的密钥和算法）
                .compact(); // 生成token（1.编码 Header 和 Payload 2.生成签名 3.拼接字符串）
        System.out.println(token);

        token = token + "s";

        // 2. 验证token，如果验证token失败则会抛出异常
//        try {
//            Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//            // OK, we can trust this token
//            System.out.println("验证成功");
//        } catch (JwtException e) {
//            //don't trust the token!
//            System.out.println("验证失败");
//        }

        // 3. 解析token
        Claims body = Jwts.parser()     // 创建解析对象
                .setSigningKey(secretKey)   // 设置安全密钥（生成签名所需的密钥和算法）
                .parseClaimsJws(token)  // 解析token
                .getBody(); // 获取 payload 部分内容
        System.out.println(body);
    }
}
