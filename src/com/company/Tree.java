package com.company;

public class Tree { /* дерево для римских чисел */

    private Node root; /* корень дерева */

    public void insert (String data) { /* вставка узла */
        Node node = new Node();
        node.data = data;
        node.depth = 0;
        if (root == null) root = node; /* добавляем корень */
        else {
            Node current = root;
            node.depth ++;
            while (true) {
                if (current.right_child == null) { /* справа у нас лежат листья */
                    current.right_child = node;
                    break;
                }
                else if (current.left_child == null){
                    node.left = true;
                    current.left_child = node;
                    break;
                }
                current = current.left_child;  /* слева дерево ветвится */
                node.depth ++;
            }
        }
    }

    public Node find (String data) { /* ищем узел в дереве */
        Node current = root;
        while (!current.data.equals (data)) {
            if (current.right_child != null) {
                if (current.right_child.data.equals (data)) return current.right_child;
            }
            if (current.left_child != null) {
                    current = current.left_child;
                    continue;
            }
            return null;
        }
        return current;
    }
    public int compare (String data1, String data2) { /* -1, если d1 < d2 */
        Node node1 = find (data1);                    /*  0, если d1 = d2 */
        Node node2 = find (data2);                    /*  1, если d1 > d2 */
        if (node1.depth < node2.depth) return -1;
        else if (node1.depth > node2.depth) return 1;
             else if (node1.left && !node2.left) return 1;
                  else if (!node1.left && node2.left) return -1;
                       else return 0;
    }
    public boolean is_substraction (String data1, String data2) { /* если поймали сочетание IV, IX, XL, XC, CD, CM */
        if (compare(data1, data2) < 0) {
            Node node1 = find (data1);
            Node node2 = find (data2);
            if (node1.depth == node2.depth - 1) return true; /* если глубина узлов отличается на 1 */
            else return false;
        }
        else return false;
    }
    public boolean allow_repeat (String data) { /* цифры I, X, C, M могут повторяться */
        Node node = find(data);
        if (node.left || node == root) return true;
        else return false;
    }

    private void print_tree (Node start) /* распечатка */
    {
        if (start != null) {
            print_tree (start.right_child);
            start.print_node();
            print_tree (start.left_child);
        }
    }
    public void print () {
        print_tree (root);
    }
}
