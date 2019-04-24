
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

    private static void buildHeapFile(File inFile, File outFile, int pagesize) {
        long s_Time = System.currentTimeMillis();
        int rec_size = 0;
        int[] field_size = { 4, 24, 24, 16, 10, 40, 20, 4, 32, 32, 32, 4, 8 };
        for(int i = 0; i < field_size.length; i++){
            rec_size += field_size[i];
        }

        int rec_num = (pagesize * 8) /  (rec_size * 8 + 1); 
    }
}
