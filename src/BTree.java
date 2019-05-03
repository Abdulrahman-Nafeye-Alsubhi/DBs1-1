
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class BTree {

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
    
    public static String nextNodeName() {
            return "n" + String.valueOf(nodeNames++);
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

    public void checkpoint () {
        int pt = 0;
        if (root == null)
                System.out.println("EMPTY TREE");
        else{
            Node no = root;
            while(no != null){
                no.thispoint = pt;
                pt++;
                Node next_no = null;
                if(no.next != null){
                    next_no = no.next;
                }
                while(next_no != null ){
                    next_no.thispoint = pt;
                    pt++;
                    next_no = next_no.next;
                }
                no = no.ptrs[0];
            }
        }
    }

    public void indexwrite (int nbyte) throws IOException {
        String index_file = "index." + String.valueOf(nbyte);
        PrintWriter indexoutFile = new PrintWriter(new FileWriter(index_file));
            if (root == null)
                    System.out.println("EMPTY TREE");
            else{
                Node no = root;
                while(no != null){
                    String key_con = "{'node' : [";
                    for (int i = 0; i < no.keys.length; i++) {
                        if(no.keys[i] != ""){
                            key_con += "{";
                            key_con += "'key' : '" + no.keys[i]+ "',";
                            key_con += "'self' : '" + no.keys[i].split("_")[1];
                            for(int j = 2; j < no.keys[i].split("_").length; j++){
                                key_con += "_" + no.keys[i].split("_")[j];
                            }
                            key_con +=  "',";
                            if(no.ptrs[i-1] != null)
                                key_con += "'left' : '" + no.ptrs[i-1].thispoint + "',";
                            else
                                key_con += "'left' : 'none',";
                            if(no.ptrs[i] != null)
                                key_con += "'right' : '" + no.ptrs[i].thispoint + "'";
                            else
                                key_con += "'right' : 'none'";
                            key_con += "},";
                        }
                    }
                    key_con += "]}";
                    key_con = key_con.trim();
                    indexoutFile.write(key_con);
                    indexoutFile.write("\n");
                    Node next_no = null;
                    if(no.next != null){
                        next_no = no.next;
                    }
                    while(next_no != null ){
                        key_con = "{'node' : [";
                        for (int i = 0; i < next_no.keys.length; i++) {
                            if(next_no.keys[i] != ""){
                                key_con += "{";
                                key_con += "'key' : '" + next_no.keys[i].split("_")[0] + "',";
                                key_con += "'self' : '" + next_no.keys[i].split("_")[1];
                                for(int j = 2; j < next_no.keys[i].split("_").length; j++){
                                    key_con += "_" + next_no.keys[i].split("_")[j];
                                }
                                 key_con +=  "',";
                                if(next_no.ptrs[i-1] != null)
                                    key_con += "'left' : '" + next_no.ptrs[i-1].thispoint + "',";
                                else
                                    key_con += "'left' : 'none',";
                                if(next_no.ptrs[i] != null)
                                    key_con += "'right' : '" + next_no.ptrs[i].thispoint + "'";
                                else
                                    key_con += "'right' : 'none'";
                                key_con += "},";
                            }
                        }
                        key_con += "]}";
                        key_con = key_con.trim();
                        indexoutFile.write(key_con);
                        next_no = next_no.next;
                        indexoutFile.write("\n");
                    }

                    no = no.ptrs[0];
                }

            }
        indexoutFile.close();
    }
}
