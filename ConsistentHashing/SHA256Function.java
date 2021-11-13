package ConsistentHashing;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Function implements IHashFunction {
    @Override
    public long hash(String key) {
        MessageDigest md;
        Long x = 0L;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] h = md.digest(key.getBytes(StandardCharsets.UTF_8));
            x = ByteBuffer.wrap(h).getLong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return x;
    }
}
