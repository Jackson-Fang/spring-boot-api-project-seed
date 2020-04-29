package com.company.project.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: chenyin
 * @date: 2020/4/1 下午7:54
 */
public class Algo {
    public static void main(String[] args) {
        String s = "cbabcb";
//        System.out.println(lengthOfLongestSubstring(s));
        Student student= new Student("a");
        test(student);
        System.out.println(student);
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    public static void test(Student student) {
        student = new Student("b");
    }
     static  class Student{
        String name;

         public Student(String name) {
             this.name = name;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             this.name = name;
         }
     }

}
