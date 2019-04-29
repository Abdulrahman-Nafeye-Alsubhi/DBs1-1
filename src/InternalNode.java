
public class InternalNode extends Node{

    /**
    Construct an InternalNode object and initialize it with the parameters.
    @param d degree
    @param p0 the pointer at the left of the key
    @param k1 the key value
    @param p1 the pointer at the right of the key
    @param n the next node
    @param p the previous node
      */
     public InternalNode (int d, Node p0, String k1, Node p1, Node n, Node p){

             super (d, n, p);
             ptrs [0] = p0;
             keys [1] = k1;
             ptrs [1] = p1;
             lastindex = 1;

             if (p0 != null) p0.setParent (new Reference (this, 0, false));
             if (p1 != null) p1.setParent (new Reference (this, 1, false));
     }

    @Override
    public Reference search(String val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(String val, Node ptr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
