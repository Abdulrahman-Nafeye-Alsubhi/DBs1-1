
public abstract class Node {
    
    int degree;                // Degree of the tree
    int lastindex;             // What is the last key/ptr index currently in use?
    int thispoint;
    int leftpoint;
    int rightpoint;
    Node[] ptrs;    
    //  Node children. 
    //  Leaf Nodes 's pointers are all null. 

    String[] keys;      // Node search keys. 

    Node next, prev;   
    //  The nodes appear after / before me at the same level. The first node of a 
    //  level's prev is null and the last node of a level's next is null.

    Reference parentref;     
    // What is the reference to this node in its parent node?
    // Root's parentref is null.

    public String myname; // This nodes unique name (used for printouts)

    /**
   Constructor which is called by {@link LeafNode} and {@link InternalNode}
   @param d the node degree 
   @param n the next sibling
   @param p the previous sibling
     */
    protected Node (int d, Node n, Node p){
        degree = d;
        ptrs = new Node[degree]; 
        keys = new String[degree];
        next = n;
        prev = p;
        for(int i = 0; i < degree; i++ ){
            keys[i] = "";
        }
        if (p != null) p.next = this;
        if (n != null) n.prev = this;

        lastindex = 0;
        parentref = null;

        myname = BTree.nextNodeName();
    }

    public abstract Reference search (String val);

    /**
    Get the parent reference of current node
    @return a reference which refers to the parend node, or null if this node is the root
      */
     public Reference getParent () {
             return parentref;
     }

    public abstract void insert (String val, Node ptr);

    void UnnecessaryMethod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
