
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

    @Override
    public void insert(String val, Node ptr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
