import redis.embedded.RedisServer;

import java.io.IOException;

public class RedisServerTes {

    private static RedisServer redisServer = null;
    private static int PORT = 63799;

    public static void start() throws IOException {
        redisServer = new RedisServer(PORT);
        redisServer.start();
    }

    public static void stop(){
        redisServer.stop();
    }
}
