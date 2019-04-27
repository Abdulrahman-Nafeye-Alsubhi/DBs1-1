
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
}
