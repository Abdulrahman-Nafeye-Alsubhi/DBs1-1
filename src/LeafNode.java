
class LeafNode extends Node {

    /**
    Construct a LeafNode object and initialize it with the parameters.
    @param d the degree of the leafnode
    @param k the first key value of the node
    @param n the next node 
    @param p the previous node
      */
     public LeafNode (int d, String k, Node n, Node p){
             super (d, n, p);
             keys [1] = k;
             lastindex = 1;
     }

    @Override
    public Reference search(String val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     /**
    Insert val into this, creating split
    and recursive insert into parent if necessary
    Note that ptr is ignored.
    @param val the value to insert
    @param ptr (not used now, use null when calling this method)
      */
     public void insert (String val, Node ptr){

        int toIndex = findKeyIndex(val);

        // if leafnode not full then just insert the key
        if (!full()) {
                insertSimple(val,null,toIndex);
                return;
        }
        // otherwise make a new right sibling for the current node, redistribute.
        Node ns = null;
        if (toIndex>lastindex) {
                ns = new LeafNode(degree, val, this.next,(Node) this);
        } else {
                ns = new LeafNode(degree, keys[lastindex], this.next,(Node) this);
                lastindex--;
                insertSimple(val,null,toIndex);
        }

        String toParent = redistribute();	
        //insert into parent
        if (this.parentref!=null) this.getParent().getNode().insert(toParent, ns);
        else new InternalNode(degree,this,toParent,ns,null,null); 
    }

    private int findKeyIndex(String val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean full() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void insertSimple(String val, Object object, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String redistribute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
