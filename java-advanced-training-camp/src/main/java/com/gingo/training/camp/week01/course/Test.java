package com.gingo.training.camp.week01.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        String s1 = "J3R3J4L34K3J4L";
        for (int i = 0; i < s1.length();) {
            char c = s1.charAt(i);
            char c2 = s1.charAt(i + 1);
            i+=2;
        }

    }
}
