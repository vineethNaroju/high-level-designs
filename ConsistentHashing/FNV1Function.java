package ConsistentHashing;

public class FNV1Function implements IHashFunction {

    @Override
    public long hash(String key) {
        final long p = 16777619;
        long hash = 2166136261L;
        for (int i = 0; i < key.length(); i++)
            hash = (hash ^ key.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
