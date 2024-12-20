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

    public static String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString().trim();
    }

    private static void serializeHelper(Node node, StringBuilder sb) {
        if (node == null) {
            sb.append("null ");
            return;
        }
        sb.append(node.value).append(":").append(node.checksum).append(" ");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    public static Node deserialize(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        String[] tokens = data.trim().split("\\s+");
        Queue<String> queue = new LinkedList<>(Arrays.asList(tokens));
        return deserializeHelper(queue);
    }

    private static Node deserializeHelper(Queue<String> queue) {
        if (queue.isEmpty()) {
            throw new IllegalArgumentException("Invalid serialized data: unexpected end");
        }

        String token = queue.poll();
        if ("null".equals(token)) {
            return null;
        }

        String[] parts = token.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid node format: " + token);
        }

        String valStr = parts[0];
        String chkStr = parts[1];
        int value;
        try {
            value = Integer.parseInt(valStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid node value: " + valStr);
        }

        String expected = computeChecksum(value);
        if (!expected.equals(chkStr)) {
            throw new IllegalArgumentException("Checksum mismatch for value " + value +
                    ". Expected " + chkStr + ", got " + expected);
        }

        Node node = new Node(value, chkStr);
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        return node;
    }
  /**
     * Prints the tree in an ASCII diagram form (rotated):
     *
     *         10
     *       /    \
     *      5      15
     *     / \
     *    2   7
     */
    public static void printTreeAscii(Node root) {
        if (root == null) {
            System.out.println("(empty)");
            return;
        }
        printAsciiHelper(root, "", true);
    }

    private static void printAsciiHelper(Node node, String prefix, boolean isTail) {
        if (node.right != null) {
            printAsciiHelper(node.right, prefix + (isTail ? "│   " : "    "), false);
        }

        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.value);

        if (node.left != null) {
            printAsciiHelper(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    // Example usage
    public static void main(String[] args) {
        // Example 1: A small, balanced tree
        //        10
        //       /  \
        //      5    15
        //     / \
        //    2   7
        Node root = new Node(10, computeChecksum(10));
        root.left = new Node(5, computeChecksum(5));
        root.right = new Node(15, computeChecksum(15));
        root.left.left = new Node(2, computeChecksum(2));
        root.left.right = new Node(7, computeChecksum(7));

        String serialized = serialize(root);
        System.out.println("Serialized Tree 1: " + serialized);
        Node deserializedRoot = deserialize(serialized);
        System.out.println("Deserialized Tree 1 (ASCII diagram):");
        printTreeAscii(deserializedRoot);

        // Tamper with data to test integrity
        String tampered = serialized.replace("5:", "50:");
        try {
            deserialize(tampered);
        } catch (IllegalArgumentException e) {
            System.out.println("Integrity check failed as expected: " + e.getMessage());
        }

        // Example 2: A single-node tree
        Node single = new Node(42, computeChecksum(42));
        String singleSerialized = serialize(single);
        System.out.println("\nSerialized Single-Node Tree: " + singleSerialized);
        Node singleDeserialized = deserialize(singleSerialized);
        System.out.println("Deserialized Single-Node Tree (ASCII diagram):");
        printTreeAscii(singleDeserialized);

        // Example 3: A tree with only left children
        //    10
        //   /
        //  9
        // /
        //8
        Node leftChain = new Node(10, computeChecksum(10));
        leftChain.left = new Node(9, computeChecksum(9));
        leftChain.left.left = new Node(8, computeChecksum(8));

        String leftChainSerialized = serialize(leftChain);
        System.out.println("\nSerialized Left-Chain Tree: " + leftChainSerialized);
        Node leftChainDeserialized = deserialize(leftChainSerialized);
        System.out.println("Deserialized Left-Chain Tree (ASCII diagram):");
        printTreeAscii(leftChainDeserialized);

        // Example 4: A more complete tree
        //         20
        //        /  \
        //      10    30
        //     /  \   / \
        //    5   15 25 35
        Node completeRoot = new Node(20, computeChecksum(20));
        completeRoot.left = new Node(10, computeChecksum(10));
        completeRoot.right = new Node(30, computeChecksum(30));
        completeRoot.left.left = new Node(5, computeChecksum(5));
        completeRoot.left.right = new Node(15, computeChecksum(15));
        completeRoot.right.left = new Node(25, computeChecksum(25));
        completeRoot.right.right = new Node(35, computeChecksum(35));

        String completeSerialized = serialize(completeRoot);
        System.out.println("\nSerialized More Complete Tree: " + completeSerialized);
        Node completeDeserialized = deserialize(completeSerialized);
        System.out.println("Deserialized More Complete Tree (ASCII diagram):");
        printTreeAscii(completeDeserialized);
    }
}
