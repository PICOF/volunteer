import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Date;


public class jedis
{
    @Test
    public void testJedis()
    {
        //1、生成一个jedis对象，这个对象负责和指定Redis节点进行通信
        Jedis jedis = new Jedis("localhost", 6379);
        //带密码需要执行认证方法，这里我的Redis没有设密码就不用管
        //jedis.auth("123456");

        //2、jedis存入数据
//        jedis.set("hello", "world");
        //3、jedis获取数据
        String value = jedis.get("hello");

        Logger logger = LoggerFactory.getLogger(jedis.class);
        logger.warn("从Redis中存取数据："+value);
        System.out.println(value);
    }
    @Test
    public void time(){
        long t1=new Date().getTime();
        long t2=System.currentTimeMillis();
        System.out.println("Date:"+t1+"\nSystem:"+t2);
    }
}
