import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ReadYarnDump {

    public static void main(String[] args) {
        File file = new File("/Users/wuchang/work/workspace/yarn-scheduler.dump");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                try{
                    int index = line.indexOf("{");
                     String input = line.substring(index);

                     JSONObject obj = new JSONObject(input);


                      JSONArray queues = obj.getJSONObject("scheduler")
                             .getJSONObject("schedulerInfo")
                             .getJSONObject("rootQueue")
                             .getJSONArray("childQueues");
                      StringBuilder sb = new StringBuilder();

                    String maxResource = obj.getJSONObject("scheduler")
                            .getJSONObject("schedulerInfo")
                            .getJSONObject("rootQueue").getJSONObject("maxResources").get("memory").toString() +":"+ obj.getJSONObject("scheduler")
                            .getJSONObject("schedulerInfo")
                            .getJSONObject("rootQueue").getJSONObject("maxResources").get("vCores").toString() ;
                      sb.append(line.substring(0,index)+",max:["+maxResource+"],totalUsed:"+obj.getJSONObject("scheduler")
                              .getJSONObject("schedulerInfo")
                              .getJSONObject("rootQueue").getJSONObject("usedResources"));
                      for(int i=0;i<queues.length();i++){
                          JSONObject  queue = queues.getJSONObject(i);
                          sb.append("["+queue.getString("queueName")+" pending:"+queue.getInt("numPendingApps")+",active:"+queue.getInt("numActiveApps")+",used:("+ queue.getJSONObject("usedResources").get("memory")+","+queue.getJSONObject("usedResources").get("vCores")+")"  +"] ");
                      }
                      System.out.println(sb.toString());
                }catch(Exception e){

                    System.out.println("error");

                }



            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
