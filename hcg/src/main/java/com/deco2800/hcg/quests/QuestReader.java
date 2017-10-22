package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.enemyentities.EnemyType;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ResourceLoadException;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.GameManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Quest reader handles quest loading from files
 *
 * @author Harry Guthrie
 */
public class QuestReader {

    static ArrayList<Integer> validEnemyID = new ArrayList<>(Arrays.asList(1,2,3,4));

    /**
     *  A function which loads all the quests into the quest manager, this utalizes the loadQuest function, and it then
     *  adds all the files in the folder to the hash map of quests titles to quests.
     */
    public Map<String,Quest> loadAllQuests() {

        HashMap<String,Quest> quests = new HashMap<>();
        String questsFolder = "resources/quests/";
        final File jQuestFile = new File(questsFolder);
        Quest q;
        for (String fp: jQuestFile.list()) {
            try {
                q = loadQuest(questsFolder + fp);
                if (quests.containsKey(q.getTitle())) {
                    throw new ResourceLoadException("Quest title is a duplicate (" + q.getTitle() + ") in file (" +
                            fp +")");
                }
                quests.put(q.getTitle(),q);
            } catch (JsonSyntaxException | ResourceLoadException e) {
                throw new ResourceLoadException("Failure during quest file load: " + fp, e);
            }
        }
        return quests;
    }

    /**
     * Loads a particular quest and returns the Quest object, or it throws and informative error as to why the file
     * being passed into the function is wrong.
     *
     * @param fp
     * @return
     * @throws IOException
     */
    public Quest loadQuest(String fp) {
        //Make sure the file is a json file
        if (!fp.substring(fp.length() - ".json".length(),fp.length()).equals(".json")) {
            throw new ResourceLoadException("All files in the quest resources files must be .json files");
        }

        //Containers for the information in the quest
        String title; //Name of the quest to be displayed
        HashMap<String,Integer> rewards; // items to amount for reward
        EnumMap<EnemyType, Integer> killRequirement; //Kills for enemy ID required
        HashMap<String, Integer> itemRequirement; //Item required to complete quest
        String description;

        //Get the inital json obj
        JsonObject jQuest;
        BufferedReader reader = null;
        try {
            JsonParser parser = new JsonParser();
            reader = new BufferedReader(new FileReader(fp));
            jQuest = (JsonObject) parser.parse(reader);
            reader.close();
        } catch (JsonSyntaxException | IOException | ResourceLoadException e){
            throw new ResourceLoadException("Unable to load and parse Quest File - invalid JSON file is likely cause", e);
        }

        //Validate the json obj
        title = jQuest.get("title").toString();
        title = title.replaceAll("^\"|\"$", "");
        if (title.equals("")) {
            throw new ResourceLoadException("");
        }

        //The item requirements and rewards are stored as a mapping between item ID and count
        JsonObject itemReqs = jQuest.getAsJsonObject("iReq");
        itemRequirement = parseItemQuantityHashMap(title,itemReqs);
        JsonObject itemRewards = jQuest.getAsJsonObject("rewards");
        rewards = parseItemQuantityHashMap(title,itemRewards);

        description = jQuest.get("optDesc").toString();
        description = description.replaceAll("^\"|\"$", "");

        JsonObject killReqs = jQuest.getAsJsonObject("kReq");
        killRequirement = parseKillReqMap(title,killReqs);

        return new Quest(title,rewards,killRequirement,itemRequirement,description);
    }

    private HashMap<String,Integer> parseItemQuantityHashMap(String title, JsonObject iqMap) {
        //Create Instance of the Item Manager class for checking valid items
        ItemManager itemManager = (ItemManager) GameManager.get().getManager(ItemManager.class);

        HashMap<String,Integer> returnMap = new HashMap<>();
        if (!iqMap.entrySet().isEmpty()) {
            for (Map.Entry i:iqMap.entrySet()) {
                //For each entry in the item req obj make sure it is valid

                //No duplicate entries allowed
                if (returnMap.containsKey(i.getKey())) {
                    throw new ResourceLoadException("Can't add the same key (" +
                            i.getKey().toString() +
                            ") twice into the item requirements for quest (" +
                            title + ")");
                }

                //Key and Value must not be empty
                if (i.getKey().toString().equals("")) {
                    throw new ResourceLoadException("Can't add an empty item requirement key for quest (" +
                            title + ")");
                }
                if (i.getValue().toString().equals("")) {
                    throw new ResourceLoadException("Can't add an empty item requirement amount for quest (" +
                            title + ")");
                }

                //Make sure the amount of the item is an integer
                int count;
                try {
                    count = Integer.parseUnsignedInt(i.getValue().toString());

                    if (count < 1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    throw new ResourceLoadException("Can't add a non positive integer item requirement amount for quest (" +
                            title + ")");
                }

                //The item key must be an actual item
                if (itemManager.getNew(i.getKey().toString()) == null) {
                    throw new ResourceLoadException("Can't add an item which currently does not exist in the Item Manager");
                }

                //Create new instance of valid item - catch if fails (invalid)
                Item entryItem = itemManager.getNew(i.getKey().toString());
                if (entryItem == null) {
                    throw new ResourceLoadException("Item (" + i.getKey().toString() +
                                                    ") is not a valid item in quest (" +
                                                    title + ")");
                }
                //Item names use the _ when creating, but " " when checking
                returnMap.put(i.getKey().toString(),Integer.parseUnsignedInt(i.getValue().toString()));
            }
        }
        return returnMap;
    }

    private EnumMap<EnemyType, Integer> parseKillReqMap(String title, JsonObject krmMap) {
        EnumMap<EnemyType, Integer> returnKRM = new EnumMap<>(EnemyType.class);

        if (!krmMap.entrySet().isEmpty()) {

            for (Map.Entry<String,JsonElement> enemyMap: krmMap.entrySet()) {
                String forQConst = ") for quest (";
                //Key and value must not be empty
                if (enemyMap.getKey().equals("")) {
                    throw new ResourceLoadException("Can't add an kill requirement key to node (" +
                            enemyMap.getKey() + forQConst + title + ")");
                }
                if (enemyMap.getValue().toString().equals("")) {
                    throw new ResourceLoadException("Can't add an kill requirement value to node (" +
                            enemyMap.getKey() + forQConst + title + ")");
                }

                EnemyType et;
                try {
                    et = EnemyType.valueOf(enemyMap.getKey());
                } catch (IllegalArgumentException e) {
                    throw new ResourceLoadException("Invalid enemy type supplied (" +
                            enemyMap.getKey()+ forQConst + title + ")");
                }


                if (returnKRM.containsKey(et)) {
                    throw new ResourceLoadException("Can't add the same enemy key (" +
                                                    enemyMap.getKey() +
                                                    ") twice into the kill requirements node for quest (" +
                                                    title + ")");
                }

                int killCount = 0;
                try {
                    killCount = Integer.parseUnsignedInt(enemyMap.getValue().toString());
                    if (killCount < 1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e){
                    throw new ResourceLoadException("Can't add a non valid enemy kill count to enemy type (" +
                                                    enemyMap.getKey() + ")");

                }
                returnKRM.put(et,killCount);
            }



        }
        return returnKRM;
    }
}
