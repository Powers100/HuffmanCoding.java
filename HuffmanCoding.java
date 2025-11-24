/** 
* Student name:  Donovan Powers
* Completion date: 11/24/25
* Programming Assignment 4
*
* HuffmanCoding.java: The program builds a Huffman Tree according to character frequency,
* and traverses the Huffman Tree to assign Prefix Code for each character.
*/

import java.util.*;
import java.io.*;

public class HuffmanCoding{

    public Hashtable<Character, Integer> getChararacterFrequency(String aStr){
        Hashtable<Character, Integer> freqTable = new Hashtable<>();

        for (int i = 0; i < aStr.length(); i++) { // For each char in the provided string
            char c = aStr.charAt(i);

            if (c == ' ') { // Skip spaces
                continue;
            }

            // Increase count if the character already exists
            if (freqTable.containsKey(c)) {
                freqTable.put(c, freqTable.get(c) + 1);
            }
            // Otherwise add the character to the hashtable
            else {
                freqTable.put(c, 1);
            }
        }
        
        return freqTable;
    }
    public Hashtable<Character, Integer> getChararacterFrequency(File aFile){
        Hashtable<Character, Integer> freqTable = new Hashtable<>();

        try (Scanner scnr = new Scanner(new FileInputStream(aFile))) {
            if (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                for (int i = 0; i < line.length(); i++) { // For each char in the line
                    char c = line.charAt(i);

                    if (c == ' ') { // Skip spaces
                        continue;
                    }

                    // Increase count if the character already exists
                    if (freqTable.containsKey(c)) {
                        freqTable.put(c, freqTable.get(c) + 1);
                    }
                    // Otherwise add the character to the hashmap
                    else {
                        freqTable.put(c, 1);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + aFile);
        }
        catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        
        return freqTable;
    }
    public HuffmanTreeNode buildHuffmanTree(Hashtable<Character, Integer> ht){
        PriorityQueue<HuffmanTreeNode> minHeap = new PriorityQueue<>();

        // Insert each character as a leaf node into the min-heap
        for (Character key : ht.keySet()) {
            int freq = ht.get(key);
            minHeap.add(new HuffmanTreeNode(key, freq));
        }

        // Build the Huffman tree
        while (minHeap.size() > 1) {
            // take the two smallest nodes
            HuffmanTreeNode left = minHeap.poll();
            HuffmanTreeNode right = minHeap.poll();

            // combine them into a parent node and set the left and right children
            HuffmanTreeNode parent = new HuffmanTreeNode(left.getFrequency() + right.getFrequency());
            parent.setLeft(left);
            parent.setRight(right);

            // push parent into the min-heap
            minHeap.add(parent);
        }


        // return the final node in the heap which is the root of the new Huffman tree
        return minHeap.poll(); 
    }

    public Hashtable<Character, String> buildCodeTable(HuffmanTreeNode huffmanTree){
        Hashtable<Character, String> codeTable = new Hashtable<>();
        buildCodesRecursive(huffmanTree, "", codeTable);
        return codeTable;
    }

    private void buildCodesRecursive(HuffmanTreeNode node, String code, Hashtable<Character, String> table) {
        if (node == null) {
            return;
        }

        // If this node is a leaf, add and return
        if (node.getLeft() == null && node.getRight() == null) {
            table.put(node.getKey(), code);
            return;
        }

        // Go left and append a "0"
        buildCodesRecursive(node.getLeft(), code + "0", table);

        // Go right and append a "1"
        buildCodesRecursive(node.getRight(), code + "1", table);
    }

        
    public String encoding(String aStr, Hashtable<Character, String> codeTable){
        String encodedStr = "";

        // For each char in aStr find the binary code for it, and append it to encodedStr
        for (int i = 0; i < aStr.length(); i++) { 
            String binaryStr = codeTable.get(aStr.charAt(i));
            encodedStr += binaryStr;
        }
  
        return encodedStr;
    }

    public String decode(String binaryStr, HuffmanTreeNode huffmanTree){
        return decodeRecursive(binaryStr, 0, huffmanTree, huffmanTree);
    }

    private String decodeRecursive(String binaryStr, int index, HuffmanTreeNode currentNode, HuffmanTreeNode root) {
        // Base case when the end of the binary string is reached:
        if (index == binaryStr.length()) {
            // If currentNode is a leaf, return its char
            if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                return String.valueOf(currentNode.getKey());
            }

            // If not a leaf return nothing
            return "";
        }

        // If currentNode is a leaf, decode that char, restart from the root
        if (currentNode.getLeft() == null && currentNode.getRight() == null) {
            return currentNode.getKey() + decodeRecursive(binaryStr, index, root, root);
        }

        char bit = binaryStr.charAt(index);

        if (bit == '0') {
            // Go left and increment the index
            return decodeRecursive(binaryStr, index + 1, currentNode.getLeft(), root);
        }
        if (bit == '1') {
            // Go right and increment the index
            return decodeRecursive(binaryStr, index + 1, currentNode.getRight(), root);
        }

        // for an invalid bit return nothing
        return "";
    }

}