
import java.io.File;
import java.io.IOException;


public class dbload {
    public static void main (String args[])
            throws IOException {
        if(args[0].equals("-p")) {
            if (args.length!=3){
                System.err.println("Please correct parameter");
                return;
            }
            File source_File=new File(args[2]);
            File target_File=new File("heap." + args[1]);
            buildHeapFile(source_File,target_File, Integer.parseInt(args[1]));
        }
    }

    private static void buildHeapFile(File source_File, File target_File, int parseInt) {
        
    }
}
