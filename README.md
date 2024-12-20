# BinaryTreeSerialization

A Java application that demonstrates the serialization and deserialization of a binary tree with checksum validation. The program includes methods for:

 Serialization: Convert a binary tree into a space-separated string using preorder traversal.
 Deserialization: Reconstruct the binary tree from the serialized string and verify checksum integrity.
 Checksum validation: Use SHA-256 to generate checksums for node values.
 Tree visualization: Print the binary tree in an ASCII diagram format.

# Features

 Serialize a binary tree to a string.
 Deserialize a string back to a binary tree.
 Ensure data integrity using checksum verification during deserialization.
 Display the binary tree as an ASCII diagram for easy visualization.

# Installation

 Clone the repository to your local machine:
  git clone https://github.com/yourusername/BinaryTreeSerializer.git
 
# Prerequisites

 Java 8 or higher.
 Maven or Gradle (for dependency management, if necessary).

# Usage

# 1. Serialization and Deserialization Example
 The code demonstrates how to serialize and deserialize a binary tree. For example, the following binary tree:
       10
     /  \
    5    15
   / \
  2   7
can be serialized into a string, and then deserialized back into the tree. The checksums are used to verify the integrity of the data during deserialization.

# 2. Checksum Integrity
 When deserializing, the checksum of each node is verified. If any corruption or tampering is detected, an error is thrown:
  Integrity check failed as expected: Checksum mismatch for value 5. Expected 5A4D, got 51F4

# 3. Tree Visualization
 The tree can be printed in a rotated ASCII format:
 └── 10
    ├── 5
    │   ├── 2
    │   └── 7
    └── 15

# Code Explanation
  
  Node class: Represents a node in the binary tree. Each node holds a value, checksum, and pointers to the left and right child nodes.
  serialize(): Converts the binary tree into a space-separated string using preorder traversal.
  deserialize(): Converts the serialized string back into a binary tree while verifying the checksum of each node.
  printTreeAscii(): Prints the tree in a rotated ASCII diagram format.

# License

This project is licensed under the MIT License - see the LICENSE file for details.
