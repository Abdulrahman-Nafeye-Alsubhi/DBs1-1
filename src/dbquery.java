
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONObject;
//this is dbquery main method
public class dbquery {
    public static void main (String args[])
            throws IOException, FileNotFoundException, Exception {
//        args = new String[2];
//        args[0]="19711 08/11/2017 02:19:50 PM";
//        args[1]="4096";
        if (args.length!=2){  //check parameter
            System.err.println("Please correct parameter");
            return;
        }
        File heapFile=new File("heap." + args[1]);
        search(heapFile,Integer.parseInt(args[1]),String.valueOf(args[0]));
    }

    private static void search(File heapFile, int pagesize, String search_text) throws FileNotFoundException, IOException, Exception {
        
        int rec_size = 0;
        long s_Time = System.currentTimeMillis();
        int[] field_size = { 4, 24, 24, 16, 10, 40, 20, 4, 32, 32, 32, 4, 8 };
        for (int i = 0; i < field_size.length ; i++) {
            rec_size += field_size[i];
        }
        
        int rec_of_page = pagesize / rec_size;
     InputStream heap_con = new FileInputStream(heapFile);
     Boolean search_done = false;
     String key = search_text;
    String result_index = "";
    int index = 0;
    String line;
     while(!search_done){
        try (Stream<String> lines = Files.lines(Paths.get("index." + String.valueOf(pagesize)))){
            line = lines.skip(index).findFirst().get();
            JSONObject obj = new JSONObject(line);
            JSONArray node = obj.getJSONArray("node");
            if(node.length() > 1){
                for(int ind = 0; ind < node.length(); ind++){
                    JSONObject node_obj = new JSONObject();
                    node_obj = node.getJSONObject(ind);
                    String key_json = node_obj.getString("key");
                    String left_ind = node_obj.getString("left");
                    String right_ind = node_obj.getString("right");
                    if(left_ind.equals("none") && right_ind.equals("none")){
                        if(key.contains(key_json)) {
                            result_index = node_obj.getString("self");
                        }
                        search_done = true;
                    }else{
                        if(ind == 0 && key.toString().compareTo(key_json)<=0){
                            index = Integer.parseInt(left_ind);
                        }else if(ind == (node.length() - 1) && key.toString().compareTo(key_json)>=0){
                            index = Integer.parseInt(right_ind);
                        }else if(ind != 0 && key.toString().compareTo(key_json)<=0 && key.toString().compareTo(node.getJSONObject(ind - 1).getString("key"))>=0){
                            index = Integer.parseInt(left_ind);
                        }
                    }
                }
            }else{
                JSONObject node_obj = new JSONObject();
                node_obj = node.getJSONObject(0);
                String key_json = node_obj.getString("key");
                String left_ind = node_obj.getString("left");
                String right_ind = node_obj.getString("right");
                if(left_ind.equals("none") && right_ind.equals("none")){
                    if(key.equals(key_json)) {
                        result_index = node_obj.getString("self");
                    }
                    search_done = true;
                }else{
                    if(key.toString().compareTo(key_json)<0){
                        index = Integer.parseInt(left_ind);
                    }else{
                        index = Integer.parseInt(right_ind);
                    }
                }
            }
        }
    }
      
    int brefore_point = 0;
    if(!result_index.equals("")){
        for(int a = 0;a < result_index.split("_").length; a++){
            int cur_point = (Integer.parseInt(result_index.split("_")[a])/ rec_of_page) * pagesize ;
            cur_point += (Integer.parseInt(result_index.split("_")[a]) % rec_of_page) * rec_size;
            heap_con.skip(cur_point - brefore_point);


                String deviceid ="0";
                int c = 0;
                // get deviceid field
                for(int i = 0; i < field_size[0]; i++){   
                    c = heap_con.read();

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

                //get arrivaltime field
                String arrivaltime ="";
                for(int i = field_size[0]; i < field_size[0] + field_size[1]; i++){
                    c = heap_con.read();
                   arrivaltime += Character.toString((char) c);
                }
                String da_name = deviceid + "    " + arrivaltime; //da name new field

                    System.out.print(da_name);
                    for(int ind = 2;ind < 13;ind++){
                        System.out.print("    ");
                        if(ind == 7 || ind == 11){
                            String int_con = "";
                            for(int i = 0; i < field_size[ind]; i++){   
                                c = heap_con.read();
                                if(c==0){
                                    int_con += "00";
                                }else{
                                    if(Integer.toHexString(c).length() == 1){
                                        int_con += "0" + Integer.toHexString(c);
                                    }else{
                                        int_con += Integer.toHexString(c);
                                    }
                                }
                            }
                            System.out.print(Integer.parseInt(int_con,16));
                        }else{
                            for(int i = 0; i < field_size[ind]; i++){
                                c = heap_con.read();
                               System.out.print(Character.toString((char) c));
                            }
                        }
                    }
                    System.out.println();
                    brefore_point = cur_point + rec_size;
        }
        System.out.println("Total reresults  :  " + result_index.split("_").length);
    }else{
        System.out.println("There is no results");
        System.out.println("Total reresults  :  " + 0);
    }
    
      long stopTime = System.currentTimeMillis();
      long elapsedTime = stopTime - s_Time;
      System.out.println("Run times : " + elapsedTime + "ms");
      heap_con.close();
    }

}