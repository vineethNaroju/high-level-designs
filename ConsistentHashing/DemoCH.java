package ConsistentHashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoCH {

    public static void main(String[] args) {
        int serverCount = 10;
        Map<CacheServer, AtomicInteger> freq = new HashMap<>();
        List<CacheServer> servers = new ArrayList<>();

        for(int i=0; i<serverCount; ++i) {
            CacheServer cs = new CacheServer("127.0.0.1:340" + i, 10);
            servers.add(cs);
            freq.put(cs, new AtomicInteger());
        }

        IHashFunction hashFunction = new SHA256Function();

        ConsistentHashRing hashRing = new ConsistentHashRing(hashFunction);

        for(CacheServer server : servers) {
            hashRing.addServer(server);
        }

        CacheServer temp;
        int reqCount = 100000;

        for(int i=0; i<reqCount; ++i) {
            temp = hashRing.getNearestCacheServer("key-" + i);
            freq.get(temp).incrementAndGet();
        }

        for(CacheServer cs : freq.keySet()) {
            System.out.println(cs + "|" + freq.get(cs));
        }

        System.out.println("Removing-------" + servers.get(0));

        hashRing.removeServer(servers.get(0));

        for(int i=0; i<reqCount; ++i) {
            temp = hashRing.getNearestCacheServer("key-" + i);
            freq.get(temp).incrementAndGet();
        }

        for(CacheServer cs : freq.keySet()) {
            System.out.println(cs + "|" + freq.get(cs));
        }
    }
}
