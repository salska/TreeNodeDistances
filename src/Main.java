public class Main
{

    public static void main(String[] args) {
        // Number of nodes
        int N = 8;

        /* Constructing tree given in the above figure */
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        root.right.left.right = new Node(8);

        // Function to do all preprocessing
        GFG.preProcessing(root, N);

        System.out.println("Dist(4, 5) = " + GFG.findDistance(4, 5));
        System.out.println("Dist(4, 6) = " + GFG.findDistance(4, 6));
        System.out.println("Dist(3, 4) = " + GFG.findDistance(3, 4));
        System.out.println("Dist(2, 4) = " + GFG.findDistance(2, 4));
        System.out.println("Dist(8, 5) = " + GFG.findDistance(8, 5));
    }
}
