package ConsistentHashing;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashRing {

    private final IHashFunction hashFunction;
    private final SortedMap<Long, CacheServer> hashRing = new TreeMap();

    ConsistentHashRing(IHashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public void addServer(CacheServer server) {
        List<String> virtualNodesList = server.getVirtualNodeList();
        long virtualNodeHash;

        for(String virtualNode : virtualNodesList) {
            virtualNodeHash = hashFunction.hash(virtualNode);
            // need to move data from next hash server to current server
            hashRing.put(virtualNodeHash, server);
            System.out.println(virtualNodeHash + "|" + virtualNode);
        }
    }

    public void removeServer(CacheServer server) {
        List<String> virtualNodesList = server.getVirtualNodeList();

        for(String virtualNode : virtualNodesList) {
            hashRing.remove(hashFunction.hash(virtualNode));
        }
    }

    public CacheServer getNearestCacheServer(String key) {
        long keyHash = hashFunction.hash(key);

        if(!hashRing.containsKey(keyHash)) {
            SortedMap<Long, CacheServer> tailRing = hashRing.tailMap(keyHash);

            if(tailRing.isEmpty()) {
                keyHash = hashRing.firstKey();
            } else {
                keyHash = tailRing.firstKey();
            }
        }

        return hashRing.get(keyHash);
    }
}
