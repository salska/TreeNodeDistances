// Java implementation of the approach
import java.util.*;

class GFG {

    static class Pair<T, V> {
        T first;
        V second;

        Pair() {
        }

        Pair(T first, V second) {
            this.first = first;
            this.second = second;
        }
    }



    static int MAX = 100001;

    // Array to store level of each node
    static int[] level = new int[MAX];

    // Utility Function to store level of all nodes
    static void FindLevels(Node root) {
        if (root == null)
            return;

        // queue to hold tree node with level
        Queue<Pair<Node, Integer>> q = new LinkedList<>();

        // let root node be at level 0
        q.add(new Pair<>(root, 0));
        Pair<Node, Integer> p = new Pair<>();

        // Do level Order Traversal of tree
        while (!q.isEmpty()) {
            p = q.poll();

            // Node p.first is on level p.second
            level[p.first.data] = p.second;

            // If left child exits, put it in queue
            // with current_level +1
            if (p.first.left != null)
                q.add(new Pair<>(p.first.left, p.second + 1));

            // If right child exists, put it in queue
            // with current_level +1
            if (p.first.right != null)
                q.add(new Pair<>(p.first.right, p.second + 1));
        }
    }

    // Stores Euler Tour
    static int[] Euler = new int[MAX];

    // index in Euler array
    static int idx = 0;

    // Find Euler Tour
    static void eulerTree(Node root) {

        // store current node's data
        Euler[++idx] = root.data;

        // If left node exists
        if (root.left != null) {

            // traverse left subtree
            eulerTree(root.left);

            // store parent node's data
            Euler[++idx] = root.data;
        }

        // If right node exists
        if (root.right != null) {

            // traverse right subtree
            eulerTree(root.right);

            // store parent node's data
            Euler[++idx] = root.data;
        }
    }

    // checks for visited nodes
    static int[] vis = new int[MAX];

    // Stores level of Euler Tour
    static int[] L = new int[MAX];

    // Stores indices of the first occurrence
    // of nodes in Euler tour
    static int[] H = new int[MAX];

    // Preprocessing Euler Tour for finding LCA
    static void preprocessEuler(int size) {
        for (int i = 1; i <= size; i++) {
            L[i] = level[Euler[i]];

            // If node is not visited before
            if (vis[Euler[i]] == 0) {

                // Add to first occurrence
                H[Euler[i]] = i;

                // Mark it visited
                vis[Euler[i]] = 1;
            }
        }
    }

    // Sparse table of size [MAX][LOGMAX]
    // M[i][j] is the index of the minimum value in
    // the sub array starting at i having length 2^j
    static int[][] M = new int[MAX][18];

    // Utility function to preprocess Sparse table
    static void preprocessLCA(int N) {
        for (int i = 0; i < N; i++)
            M[i][0] = i;

        for (int j = 1; 1 << j <= N; j++)
            for (int i = 0; i + (1 << j) - 1 < N; i++)
                if (L[M[i][j - 1]] < L[M[i + (1 << (j - 1))][j - 1]])
                    M[i][j] = M[i][j - 1];
                else
                    M[i][j] = M[i + (1 << (j - 1))][j - 1];
    }

    // Utility function to find the index of the minimum
    // value in range a to b
    static int LCA(int a, int b) {
        // Subarray of length 2^j
        int j = (int) (Math.log(b - a + 1) / Math.log(2));
        if (L[M[a][j]] <= L[M[b - (1 << j) + 1][j]])
            return M[a][j];

        else
            return M[b - (1 << j) + 1][j];
    }

    // Function to return distance between
    // two nodes n1 and n2
    static int findDistance(int n1, int n2) {
        // Maintain original Values
        int prevn1 = n1, prevn2 = n2;

        // Get First Occurrence of n1
        n1 = H[n1];

        // Get First Occurrence of n2
        n2 = H[n2];

        // Swap if low>high
        if (n2 < n1) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        // Get position of minimum value
        int lca = LCA(n1, n2);

        // Extract value out of Euler tour
        lca = Euler[lca];

        // return calculated distance
        return level[prevn1] + level[prevn2] - 2 * level[lca];
    }

    static void preProcessing(Node root, int N) {
        // Build Tree
        eulerTree(root);

        // Store Levels
        FindLevels(root);

        // Find L and H array
        preprocessEuler(2 * N - 1);

        // Build sparse table
        preprocessLCA(2 * N - 1);
    }

}

// This code is contributed by
// sanjeev2552
