
public class BTree {
    Node root;
    
    int degree;
    
    private static int nodeNames = 1;
    
    /**
   Construct a BTree object with degree d. 
   @param d the degree of BTree
     */
    public BTree (int d) {
        degree = d;
        root = null;
    }
}
