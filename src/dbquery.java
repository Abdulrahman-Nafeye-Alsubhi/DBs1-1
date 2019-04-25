
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


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

    private static void search(File heapFile, int pagesize, String search_text) throws FileNotFoundException, IOException {
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
        InputStream heap_con = new FileInputStream(heapFile);
        boolean done = false;
        boolean res = false;
        int cur_rec = 0;
        while (!done) {

            String Hex ="";
            String deviceid ="0";
            int c = 0;
            for(int i = 0; i < 4; i++){   
                c = heap_con.read();
                if (c == -1) {
                    done = true;
                    break;
                }
                if(c==0){
                    deviceid += "00";
                }else{
                    if(Integer.toHexString(c).length() == 1){
                        deviceid += "0" + Integer.toHexString(c);
                    }else{
                        deviceid += Integer.toHexString(c);
                    }
                }
            }
            deviceid = String.valueOf(Integer.parseInt(deviceid,16));

            String arrivaltime ="";
            for(int i = 4; i < 28; i++){
                c = heap_con.read();
                if (c == -1) {
                    done = true;
                    break;
                }
               arrivaltime += Character.toString((char) c);
            }
            String da_name = deviceid + " " + arrivaltime; 
            if(da_name.contains(search_text)){    
                res = true;
            }
            heap_con.skip(rec_size - 28);
            if(done == true){
                break;
            }

            if(res == true){
                res = false;
                total_reresults++;
            }
            cur_rec++;
            total_rec++;
            if(cur_rec == rec_num){
                cur_page++;
                cur_rec = 0;
                heap_con.skip(remainsize);
            }
        }
        System.out.println("Success!!!" );
        System.out.println("Total result counts  :  " + total_reresults);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - s_Time;
        System.out.println("Run times : " + elapsedTime + "ms");
        heap_con.close();
    }
 }
