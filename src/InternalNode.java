
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

     /**
    Uses findPtrInex and calls itself recursively until find the value or find the position 
    where the value should be.
    @return the reference pointing to a leaf node.
      */
     public Reference search (String val) {
             Node nextPtr = ptrs[this.findPtrIndex(val)];
             return nextPtr.search(val);
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

    public void insertSimple (String val, Node ptr, int i) {
		
            System.arraycopy(keys, i, keys, i+1, lastindex+1-i);
            System.arraycopy(ptrs, i, ptrs, i+1, lastindex+1-i);
            keys[i]=val;
            ptrs[i]=ptr;
            lastindex++;
            readopt();
            UnnecessaryMethod();
    }

    public String redistribute () {
            InternalNode ns = (InternalNode) this.getNext();
            int newLastindex = (lastindex+ns.lastindex)/2;
            int keysShifted = Math.abs(lastindex-newLastindex);

            if (newLastindex<lastindex) { 
                    //redistribute keys
                    System.arraycopy(ns.keys, 1, ns.keys, keysShifted, ns.lastindex);
                    System.arraycopy(keys, newLastindex+1, ns.keys, 0, keysShifted);
                    //redistribute pointers
                    System.arraycopy(ns.ptrs, 0, ns.ptrs, keysShifted-1, ns.lastindex+1);
                    System.arraycopy(ptrs, newLastindex+1, ns.ptrs, 0, keysShifted);
            } else {
                    //redistribute keys
                    System.arraycopy(ns.keys, 1, keys, lastindex+1, keysShifted);
                    System.arraycopy(ns.keys, keysShifted+1, ns.keys, 0, ns.lastindex-keysShifted);
                    //redistribute pointers
                    System.arraycopy(ns.ptrs, 0, ptrs, lastindex, keysShifted+1);
                    System.arraycopy(ns.ptrs, keysShifted+1, ns.ptrs, 0, ns.lastindex-keysShifted);
            }

            ns.lastindex += lastindex-newLastindex-1;
            this.lastindex = newLastindex;		

            String toParent = ns.keys[0];
            ns.keys[0]="";

            readopt();
            ns.readopt();

            UnnecessaryMethod();
            ns.UnnecessaryMethod();
            return toParent;
    }

    protected void readopt() {
            for (int i=0;i<=this.lastindex;i++) {
                    ptrs[i].setParent(new Reference(this,i,false));
            }
    }

     public int findPtrIndex (String val){
            int i = 0;

            do {
                    if (((val.split("_")[0]).compareTo(keys[i].split("_")[0])>=0) && (i==lastindex || val.split("_")[0].compareTo(keys[i+1].split("_")[0])<0)) break;

                    i++;
            } while (i <= lastindex);

            return i; 
    }
    
}
