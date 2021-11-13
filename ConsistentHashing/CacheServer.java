package ConsistentHashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheServer {
    private final String ipAddress;
    long virtualNodeCount;
    private final List<String> virtualNodeList = new ArrayList<>();;

    private final Map<String, String> cache = new HashMap();

    CacheServer(String ipAddress) {
        this.ipAddress = ipAddress;
        virtualNodeCount = 1;
        initVirtualNodeList();
    }

    CacheServer(String ipAddress, long virtualNodeCount) {
        this.ipAddress = ipAddress;
        this.virtualNodeCount = virtualNodeCount;
        initVirtualNodeList();
    }

    private void initVirtualNodeList() {
        for(long i=0; i<virtualNodeCount; ++i) {
            virtualNodeList.add(ipAddress + "&vid:" + i);
        }
    }

    public List<String> getVirtualNodeList() {
        return virtualNodeList;
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public String toString() {
        return "CacheServer[ipAddress:" + ipAddress + ",virtualNodeCount:" + virtualNodeCount + "]";
    }
}
