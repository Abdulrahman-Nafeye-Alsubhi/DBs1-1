
class Reference {
    
    private Node node;                // What node is the referencing node?
    private int index;                 
    // What is the pointer or key index within the
    //  referencing node?
    private boolean match;               
    // If this is returned as a result of a
    //  search, was there a successful match?

    /**
   Constructs a Reference object and initializes the fields with the parameters.
   @param p the node it refers
   @param i the index of the key (pointer) it refers to 
   @param m for the result of search, true if the key matchs the search value
     */
    public Reference (Node p, int i, boolean m){
            node = p;
            index = i;
            match = m;
    }

    boolean getMatch() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Node getNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
