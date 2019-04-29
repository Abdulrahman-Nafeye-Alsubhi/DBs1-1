
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
    
     /**
    Insert (val, ptr) into this node. Uses insertSimple, redistribute etc.
    Insert into parent recursively if necessary
    @param val the value to insert
    @param ptr the pointer to insert 
      */
     public void insert (String val, Node ptr) {

             int toIndex = findKeyIndex(val);

             // if not full then just insert the key
             if (!full()) {
                     insertSimple(val,ptr,toIndex);
                     return;
             }
             // otherwise make a new right sibling for the current node, redistribute.
             Node ns = null;
             if (toIndex>lastindex) {
                     ns = new InternalNode(degree,null,val,ptr,next,(Node) this);
             } else {
                     ns = new InternalNode(degree, null,keys[lastindex], ptrs[lastindex], next,(Node) this);	
                     lastindex--;
                     insertSimple(val,ptr,toIndex);
             }

             String toParent = redistribute();
             // recursively insert into parent if exists
             if (getParent()!=null) parentref.getNode().insert(toParent, ns);				
             else new InternalNode(degree,this,toParent,ns,null,null);
     }

    private void insertSimple(String val, Node ptr, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String redistribute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
