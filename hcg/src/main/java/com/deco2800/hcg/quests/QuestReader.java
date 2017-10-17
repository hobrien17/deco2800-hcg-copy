package com.deco2800.hcg.quests;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ResourceLoadException;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.GameManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Quest reader handles quest loading from files
 *
 * @author Harry Guthrie
 */
public class QuestReader {
    /**
     *  A function which loads all the quests into the quest manager, this utalizes the loadQuest function, and it then
     *  adds all the files in the folder to the hash map of quests titles to quests.
     */
    public HashMap<String,Quest> loadAllQuests() throws ResourceLoadException {
        HashMap<String,Quest> quests = new HashMap<>();
        String questsFolder = "resources/quests/";
        final File jQuestFile = new File(questsFolder);
        Quest q;
        for (String fp: jQuestFile.list()) {
            try {
                q = loadQuest(fp);
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
    public Quest loadQuest(String fp) throws ResourceLoadException {
        //Make sure the file is a json file
        if (fp.substring(fp.length() - ".json".length(),fp.length()) != ".json") {
            throw new ResourceLoadException("All files in the quest resources files must be .json files");
        }

        //Containers for the information in the quest
        String title; //Name of the quest to be displayed
        HashMap<Item,Integer> rewards = new HashMap<>(); // items to amount for reward
        HashMap<Integer,HashMap<Integer, Integer>> killRequirement = new HashMap<>(); //Kills for enemy ID required
        HashMap<Item, Integer> itemRequirement = new HashMap<>(); //Item required to complete quest
        String description;

        //Get the inital json obj
        JsonObject jQuest;
        try {
            JsonParser parser = new JsonParser();
            BufferedReader reader = new BufferedReader(new FileReader(fp));
            jQuest = (JsonObject) parser.parse(reader);
            reader.close();
        } catch (JsonSyntaxException | IOException | ResourceLoadException e){
            throw new ResourceLoadException("Unable to load and parse Quest File", e);
        }

        //Validate the json obj
        title = jQuest.get("title").toString();
        if (title == "") {
            throw new ResourceLoadException("");
        }

        //The item requirements and rewards are stored as a mapping between item ID and count
        JsonObject itemReqs = jQuest.getAsJsonObject("iReq");
        itemRequirement = parseItemQuantityHashMap(title,itemReqs);
        JsonObject itemRewards = jQuest.getAsJsonObject("rewards");
        rewards = parseItemQuantityHashMap(title,itemRewards);

        //The kill requirements are a mapping between node{enemyID:killAmount}
        JsonObject killReqs = jQuest.getAsJsonObject("kReq");
        killRequirement = parseKillReqMap(title,killReqs);

        return new Quest(title,rewards,killRequirement,itemRequirement);
    }

    private HashMap<Item,Integer> parseItemQuantityHashMap(String title, JsonObject iqMap) throws ResourceLoadException {
        //Create Instance of the Item Manager class for checking valid items
        ItemManager itemManager = (ItemManager) GameManager.get().getManager(ItemManager.class);

        HashMap<Item,Integer> returnMap = new HashMap<>();
        if (iqMap.entrySet().size() > 0) {
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
                if (i.getKey().toString() == "") {
                    throw new ResourceLoadException("Can't add an empty item requirement key for quest (" +
                            title + ")");
                }
                if (i.getValue().toString() == "") {
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

                //Create new instance of valid item
                Item entryItem = itemManager.getNew(i.getKey().toString());
                returnMap.put(entryItem,Integer.parseUnsignedInt(i.getValue().toString()));
            }
        }
        return returnMap;
    }

    private HashMap<Integer,HashMap<Integer, Integer>> parseKillReqMap(String title, JsonObject krmMap) {
        GameManager gameManager = GameManager.get();

        HashMap<Integer,HashMap<Integer, Integer>> returnKRM = new HashMap<>();
        HashMap<Integer, Integer> killCount;

        if (krmMap.entrySet().size() > 0) {
            for (Map.Entry node: krmMap.entrySet()) {
                //Check for valid world node
                if (!gameManager.getWorldMap().getContainedNodes().contains(node)) {
                    throw new ResourceLoadException("Can't add invalid worlds node.")
                }
                
                //Make sure there are no duplicate nodes IDs
                if (returnKRM.containsKey(node.getKey())) {
                    throw new ResourceLoadException("Can't add the same key (" +
                            node.getKey().toString() +
                            ") twice into the kill requirements node for quest (" +
                            title + ")");
                }

                //Key and value must not be empty
                if (node.getKey().toString() == "") {
                    throw new ResourceLoadException("Can't add an empty node key in the kill requirements for quest (" +
                            title + ")");
                }
                if (node.getValue().toString() == "") {
                    throw new ResourceLoadException("Can't add an empty node value in the kill requirements for quest (" +
                            title + ")");
                }

                //Parse the enemy ID to kill
                JsonObject thisNodeEnemyKills = krmMap.getAsJsonObject(node.getKey().toString());
                if (thisNodeEnemyKills.size() < 1) {
                    throw new ResourceLoadException("Can't add a node with no kill requirements to the kill " +
                                                    "requirements for quest (" + title + ")");
                }

                killCount = new HashMap<>();
                //Check each of the node kill reqs
                for (Map.Entry enemyCount: thisNodeEnemyKills.entrySet()) {
                    //Key and value must not be empty
                    if (node.getKey().toString() == "") {
                        throw new ResourceLoadException("Can't add an kill requirement key to node (" +
                                                        node.getKey().toString() + ") for quest (" +
                                                        title + ")");
                    }
                    if (node.getValue().toString() == "") {
                        throw new ResourceLoadException("Can't add an kill requirement value to node (" +
                                node.getKey().toString() + ") for quest (" +
                                title + ")");
                    }

                    //Todo check to make sure it is a valid enemy ID - Note assumed numeric, change as needed
                    try {
                        Integer.parseUnsignedInt(enemyCount.getKey().toString());
                        int count = Integer.parseUnsignedInt(enemyCount.getValue().toString());
                        if (count < 1) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        throw new ResourceLoadException("Can't add a non positive integer item requirement amount for quest (" +
                                title + ")");
                    }

                    //Todo make sure its not a duplicate enemy ID - make sure the below function handles it
                    if (killCount.containsKey(enemyCount.getKey().toString())) {
                        throw new ResourceLoadException("Can't add the same enemy key (" +
                                                        enemyCount.getKey().toString() +
                                                        ") in node (" +
                                                        node.getKey().toString() +
                                                        ") twice into the kill requirements node for quest (" +
                                                        title + ")");
                    }


                    killCount.put(Integer.parseUnsignedInt(enemyCount.getKey().toString()),
                                  Integer.parseUnsignedInt(enemyCount.getValue().toString()));



                }

                //Todo - confirm that the node int will always be uint
                returnKRM.put(Integer.parseUnsignedInt(node.getKey().toString()),killCount);
            }
        }
        return returnKRM;
    }
}
