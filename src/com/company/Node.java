package com.company;

public class Node { /* узел дерева */
    public String data; /* некоторые данные в узле */
    public Node left_child; /* левый потомок */
    public Node right_child; /* правый потомок */
    public int depth; /* глубина узла */
    public boolean left; /* true, если узел - левый потомок родителя */
    public void print_node(){ /* выводит на экран содержимое узла */
        System.out.println(data + " " + depth + " " + left);
    }
}
