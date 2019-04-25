
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

    private static void search(File heapFile, int pagesize, String search_text) {
        int rec_size = 0;
        long s_Time = System.currentTimeMillis();
        int[] field_size = { 4, 24, 24, 16, 10, 40, 20, 4, 32, 32, 32, 4, 8 };
        for (int i = 0; i < field_size.length ; i++) {
            rec_size += field_size[i];
        }
        int rec_num = (pagesize * 8) /  (rec_size * 8 + 1);  //floor comes for free
        int cur_page = 0;
        int remainsize = pagesize % rec_size;
        int total_rec = 0;
        int total_reresults = 0;
    }
}
