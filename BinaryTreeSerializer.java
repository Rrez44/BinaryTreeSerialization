import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class Node {
    int value;
    String checksum;
    Node left;
    Node right;

    Node(int value, String checksum) {
        this.value = value;
        this.checksum = checksum;
    }
}

public class BinaryTreeSerializer {
  private static String computeChecksum(int value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(String.valueOf(value).getBytes());
            // Convert bytes to hex (truncate to first 4 bytes for brevity)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4 && i < hash.length; i++) {
                sb.append(String.format("%02X", hash[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No SHA-256 algorithm available");
        }
    }

}
