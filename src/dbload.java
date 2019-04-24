
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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

    private static void buildHeapFile(File inFile, File outFile, int pagesize) throws FileNotFoundException {
        long s_Time = System.currentTimeMillis();
        int rec_size = 0;
        int[] field_size = { 4, 24, 24, 16, 10, 40, 20, 4, 32, 32, 32, 4, 8 };
        for(int i = 0; i < field_size.length; i++){
            rec_size += field_size[i];
        }

        int rec_num = (pagesize * 8) /  (rec_size * 8 + 1); 
        BufferedReader in_txt = new BufferedReader(new FileReader(inFile));
        FileOutputStream out_txt = new FileOutputStream(outFile);

        char buf[] = new char[1024];

        int ind = 0;
        int rec_pg = 0;
        int page = 0;
        int field_ind = 0;
        int total_rec = 0;

        ByteArrayOutputStream pBAOS = new ByteArrayOutputStream(pagesize);
        DataOutputStream pStream = new DataOutputStream(pBAOS);
    }
}
