package data;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private RedisRepository redis ;
    public Data(){
        redis = new RedisRepository("172.16.20.67", 6379,"yC6kQPqLlr5GxJenKXV6");
    }

    public void getData(String dbName,String id) {
//        System.out.println(redis.takeInMap("basic_profile", "100018824465187"));
        System.out.println(redis.takeInMap(dbName, id));
    }

    public Map<String,Integer> getMutualFriendCount(String uid){
        Map<String,Integer> result = new HashMap<>();
        List<String> listFriendUIDs = getListFriendUIDs(uid);
        for(String uidX:listFriendUIDs){
            List<String> listFriendUIDXs = getListFriendUIDs(uidX);
            result.put(uidX,compareListSimilarity(listFriendUIDs,listFriendUIDXs));
            //System.out.println(uidX);
        }
        return result;
    }

    private List<String> getListFriendUIDs(String uid){
//        System.out.println(redis.existKey("friend_list",uid));
//        if(!redis.existKey("friend_list",uid))
//            throw new IllegalArgumentException("no have this key !!");
        String listFriendUIDs = redis.takeInMap("friend_list",uid);
      //  JsonObject jo = new JsonParser().parse(listFriendUIDs).getAsJsonObject();
        JsonArray arr = new JsonArray();
        if(listFriendUIDs != "") {
            arr = new JsonParser().parse(listFriendUIDs).getAsJsonArray();
        }
        List<String> listUIDs = new ArrayList<>();
        int i = 0;
        for(JsonElement j:arr)
        {
            if(j.getAsJsonObject().get("fid") != null)
                listUIDs.add(j.getAsJsonObject().get("fid").getAsString());
        }
        return listUIDs;
    }
    private int compareListSimilarity(List<String> l1,List<String> l2){
        int result = 0;
        for(String str:l1){
            if(l2.contains(str))
                result += 1;
        }
        return result;
    }


    public static void main(String[] args) {
        // 2) "group_member"
        // 3) "group_joined"
        // 4) "basic_profile"
        // 5) "feed_basic"
        // 6) "basic_profile__family"
        // 7) "basic_profile__work"
        // 8) "s-token"
        // 9) "phoneuid"
        //10) "basic_profile__education"
        //11) "TopFriendsDB"
        //12) "friend_reaction_basic"
        //13) "friend_comment_basic"
        //14) "friend_list"
        //15) "friend_group_basic"

        Data d = new Data();
        d.getData("friend_reaction_basic","100009627011566"); //100009478034704
//        d.getData("friend_list","100006161446511");  //100022107041935  (100006161446511  100000176448170  )
//        Map<String,Integer> mutualCount = d.getMutualFriendCount("100006161446511");
//        for(String str:mutualCount.keySet()){
//            System.out.println(str +" : "+ mutualCount.get(str));
//        }
    }
}
