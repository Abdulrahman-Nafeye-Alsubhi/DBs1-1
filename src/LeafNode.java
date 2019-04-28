
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

    public int findKeyIndex (String val){
            int i = 1;
            while (i<=lastindex) {
                    if (val.split("_")[0].compareTo(keys[i-1].split("_")[0])>0 && val.split("_")[0].compareTo(keys[i].split("_")[0])<=0) break;	
                    i++;
            }
            return i;
    }

    public boolean full () {
            return lastindex == degree - 1;
    }

    public void insertSimple (String val, Node ptr, int i){
            System.arraycopy(keys, i, keys, i+1, lastindex+1-i);
            keys[i] = val;
            lastindex++;
            UnnecessaryMethod();
    }

    public String redistribute (){  
            Node ns = this.getNext();
            int newLastindex = (lastindex+ns.lastindex+1)/2;
            int keysShifted = Math.abs(lastindex-newLastindex);

            if (newLastindex<lastindex) {
                    System.arraycopy(ns.keys, 1, ns.keys, keysShifted+1, ns.lastindex);
                    System.arraycopy(keys, newLastindex+1, ns.keys, 1, keysShifted);
            } else {
                    System.arraycopy(ns.keys, 1, keys, lastindex+1, keysShifted);
                    System.arraycopy(ns.keys, 1+keysShifted, ns.keys, 1, ns.lastindex-keysShifted);
            }

            ns.lastindex += lastindex-newLastindex;
            lastindex = newLastindex;

            UnnecessaryMethod();
            ns.UnnecessaryMethod();
            return ns.keys[1];
    }

    protected void UnnecessaryMethod() {
            // set keys to 0 so that tests pass.
            int i = lastindex+1;
            while (i<degree) {
                    ptrs[i]=null;
                    keys[i]="";
                    i++;
            }
    }
     
     public Node getNext () {
            return next;
    }
}
