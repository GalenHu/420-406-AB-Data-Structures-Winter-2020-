package ca.qc.johnabbott.cs406;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie implements Lexicon {

    private static class TrieNode {
        public boolean end;
        public Map<Character, TrieNode> children = new HashMap<>();

        //Nodes don't have an end
        //Leaves have an end
        public TrieNode(boolean end) {
            this.end = end;
        }
    }

    //Root always have branches
    private TrieNode root = new TrieNode(false);

    //Add a word to the lexicon
    //input the word
    //no return/output
    @Override
    public void add(String word) {
        TrieNode current = root;
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            char letter = word.charAt(i);
            if (current.children.containsKey(letter)) {
                current = current.children.get(letter);
                if (i == wordLength -1) {
                    current.end = true;
                }
            }
            else {
                TrieNode node = new TrieNode(i == wordLength - 1);
                current.children.put(letter, node);
                current = node;
            }
        }
    }

    //Determine if the word is in the lexicon
    //input a String word
    //output true if its in false if its not
    @Override
    public boolean contains(String word) {
        return contains(word, root);
    }

    //Determine if the word is in the lexicon
    //input a String word, and the current node
    //output true if its in false if its not
    public boolean contains(String word, TrieNode trieNode) {
        //If word is blank, return leaf (true if its a leaf, false if its not a leaf)
        if (word == null || (word.isEmpty())) {
            return trieNode.end;
        }
        char currentLetter = word.charAt(0);
        //if one of the key contain the letter, return true and remove first/current letter from the word
        // otherwise return false
        if (!trieNode.children.containsKey(currentLetter)) {
            return false;
        } else {
            return contains(word.substring(1), trieNode.children.get(currentLetter));
        }
    }
}