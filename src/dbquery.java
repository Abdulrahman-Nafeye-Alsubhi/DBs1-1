
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class dbquery {
    public static void main (String args[])
            throws IOException, FileNotFoundException, Exception {
        if (args.length!=2){  //check parameter
            System.err.println("Please correct parameter");
            return;
        }
        File heapFile=new File("heap." + args[1]);
        search(heapFile,Integer.parseInt(args[1]),String.valueOf(args[0]));
    }

    private static void search(File heapFile, int parseInt, String valueOf) {
        
    }
}
