
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

    private static void buildHeapFile(File inFile, File outFile, int pagesize) throws FileNotFoundException, IOException {
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
        
        BTree T = new BTree (3); //b+ tree class
        String index_key = "";  //key of b+ tree

        ByteArrayOutputStream pBAOS = new ByteArrayOutputStream(pagesize);
        DataOutputStream pStream = new DataOutputStream(pBAOS);
        
        boolean end_file = false;
        boolean start = true;
        while (!end_file) {
            int c = in_txt.read();  //read one byte

            if (c == '\r')
                continue;

            if (c == '\n') {     //finish record
                if (start)
                    continue;
                rec_pg++;
                total_rec ++;
                start = true;
                pStream.flush();
                pBAOS.writeTo(out_txt);
                pBAOS.reset();
                T.insert(index_key);
                index_key = "";
            } else
                start = false;
            if (c == ',' || c == '\n' || c == '\r') {
                String s = new String(buf, 0, ind);
                if (field_ind == 0 || field_ind == 7 || field_ind == 11) {
                    try {
                        int int_tmp = Integer.parseInt(s.trim());
                        pStream.writeInt(int_tmp);
                    } catch (NumberFormatException e) {
                        for(int n = 0; n < 4; n++){
                            pStream.write((byte)0);
                        }
                    }
                }else {
                    s = s.trim();
                    int over = field_size[field_ind] - s.length();
                    if (over < 0) {
                        String news = s.substring(0,field_size[field_ind]);
                        s  = news;
                    }
                    pStream.writeBytes(s);
                    for(int j = 0;j < over; j++){
                        pStream.write((byte)0);
                    }
                }
                ind = 0;
                if (c == '\n')
                    field_ind = 0;
                else
                    field_ind++;

            } else if (c == -1) {
                end_file = true;     

            } else {
                if(ind < 1024){
                    buf[ind++] = (char)c;

                }else{
                    end_file = true;
                }
                continue;
            }

            if (rec_pg >= rec_num
                || end_file && rec_pg > 0
                || end_file && page == 0) {
                int i = 0;

                for (i=0; i<(pagesize % rec_size); i++)
                    pStream.writeByte(0);

                rec_pg = 0;
                page++;
            }

        }

        in_txt.close();
        out_txt.close();
        
        T.checkpoint ();
        T.indexwrite (pagesize);
        
        System.out.println("Success!!!");
        System.out.println("Total record counts : " + (total_rec - 1));
        System.out.println("Records of page : " + rec_num);
        System.out.println("Page counts : " + page);

        long e_Time = System.currentTimeMillis();
        long run_Time = e_Time - s_Time;
        System.out.println("Run times : " + run_Time + "ms");
        }
    }
