package com.example.concurrntupdateuitest.others;

import java.util.HashMap;
import java.util.Map;

public class Practise3 {
    public static void main(String[] args) {
        System.out.println(new Practise3().lengthOfLongestSubstring("tmmzuxt"));
        System.out.println(new Practise3().lengthOfLongestSubstring("abba"));
        System.out.println(new Practise3().lengthOfLongestSubstring("pwwkew"));
    }

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charMap = new HashMap<>();
        int maxLen = 0;
        int head = 0;
        char ch;
        int i = 0;
        for (; i < s.length(); i++){
                ch = s.charAt(i);
                if (charMap.get(ch) != null && charMap.get(ch) >= head){
                    maxLen = maxLen < (i - head) ? (i - head) : maxLen;
//                    System.out.println("----" + ch + " " + charMap.get(ch));
                    head = charMap.get(ch) + 1;
                }
               // maxLen = maxLen < (i - head) ? (i - head) : maxLen;
                charMap.put(ch, i);
        }
//        System.out.println(i);
//        System.out.println(head);
//        System.out.println(maxLen);
        maxLen = maxLen < (i - head) ? (i - head) : maxLen;

        return maxLen;
    }
}
