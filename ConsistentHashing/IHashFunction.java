package ConsistentHashing;

public interface IHashFunction {
    long hash(String key);
}