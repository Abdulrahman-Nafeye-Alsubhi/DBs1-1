
public class BTree {

    static String nextNodeName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
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

    void insert(String val) {
        if (root == null) {
                // This is the first insert in the tree - time to create a node
                root = new LeafNode  (degree, val, null, null);
        } else {
            // First search where to put the new key
            Reference l = root.search (val);
            if (l.getMatch ()){
                //System.out.println(val + " is already in the tree.");
            }
            else {
                // The tree doesn't contain this key so call insert on the leaf where it should appear

                l.getNode ().insert (val, null);

                // Check to see if we have a new root and update if necessary
                if (root.getParent () != null)
                        root = root.getParent ().getNode ();
            }
        }
    }
}
