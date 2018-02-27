package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.spawnables.bonus.Bonus;
import fr.univangers.vajin.engine.entities.spawnables.bonus.BonusTarget;
import fr.univangers.vajin.engine.entities.spawnables.bonus.BonusType;
import fr.univangers.vajin.engine.entities.spawnables.food.Food;
import fr.univangers.vajin.engine.entities.spawnables.food.FoodSpawner;
import fr.univangers.vajin.engine.entities.spawnables.food.FoodRegistryImpl;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.field.Field;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineBuilder {

    private final static String MIN_PLAYERS = "min_players";
    private final static String MAX_PLAYERS = "max_players";
    private final static String POWERS_ALLOWED = "powers_allowed";
    private final static String TRUE = "true";
    private final static String FALSE = "false";
    private final static String FOOD_SET = "kitchen";
    private final static String BONUSES_SET = "bonuses";
    private final static String FOOD = "food";
    private final static String BONUS = "bonus";
    private final static String NAME = "name";
    private final static String GROWTH_FACTOR = "growth_factor";
    private final static String RESOURCE_KEY = "resource_key";
    private final static String PROBA_WEIGHT = "proba_weight";
    private final static String BONUS_TYPE = "type";
    private final static String BONUS_TARGET = "target";
    private final static String TAKER = "TAKER";
    private final static String ANYONE_BUT_TAKER = "anyone_but_taker";
    private final static String EVERYONE_BUT_TAKER = "everyone_but_taker";
    private final static String EVERYONE = "everyone";
    private final static String GAIN_LIFE = "gain_life";
    private final static String LOOSE_LIFE = "loose_life";
    private final static String GO_FASTER = "go_faster";
    private final static String GO_SLOWER = "go_slower";
    private final static String IMMATERIALITY = "immateriality";
    private final static String INVISIBILITY = "invisibility";
    private final static String MIN_FOOD = "min_simultaneous_food";
    private final static String MAX_FOOD = "max_simultaneous_food";
    private final static String MIN_BONUSES = "min_simultaneous_bonuses";
    private final static String MAX_BONUSES = "max_simultaneous_bonuses";

    private int minPlayer;
    private int maxPlayer;
    private Field field;
    private List<Food> availableFood;
    private List<Bonus> availableBonuses;
    private boolean powersAllowed;
    private int minFood;
    private int maxFood;
    private int minBonuses;
    private int maxBonuses;

    private Map<Integer, Snake> players;

    public EngineBuilder(Field field, int gameMode){
        this.field = field;
        this.availableFood = new ArrayList<>();
        this.availableBonuses = new ArrayList<>();
        this.players = new HashMap<>();

        loadGameModeConfigurationFile(gameMode);
    }




    public EngineBuilder addSnake(Integer id, Snake s){
        players.put(id, s);
        return this;
    }

    public EngineBuilder removeSnake(Integer id){
        players.remove(id);
        return this;
    }

    public GameEngine build() throws WrongPlayersNumberException {

        List<Entity> entities = new ArrayList<>();

        if (players.size()<minPlayer || players.size()>maxPlayer){
            throw new WrongPlayersNumberException(minPlayer, maxPlayer, players.size());
        }

        entities.add(new FoodSpawner(this.minFood, this.maxFood, new FoodRegistryImpl(availableFood)));
        
        return new MultiPlayerEngine(players, entities, field);

    }

    /**
     * Reading the game mode from the xml file.
     * The file contains the info about the min and max number of players, the available food and bonuses and if the players can
     * use their powers or not
     *
     * @param gameMode
     */
    private void loadGameModeConfigurationFile(int gameMode){

        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document= builder.parse(new File("gm"+gameMode+".xml"));

            Element rootNode = document.getDocumentElement();

            //Reading the min and max number of players
            this.minPlayer = Integer.valueOf(rootNode.getAttribute(MIN_PLAYERS));
            this.maxPlayer = Integer.valueOf(rootNode.getAttribute(MAX_PLAYERS));

            //Reading if the players can use their powers
            powersAllowed = !rootNode.hasAttribute(POWERS_ALLOWED) || !rootNode.getAttribute(POWERS_ALLOWED).equals(FALSE);


            NodeList childNodes = rootNode.getChildNodes();

            for (int i=0; i<childNodes.getLength(); i++){
                Node currentNode = childNodes.item(i);

                if (currentNode.getNodeName().equals(FOOD_SET)){
                    readFoodSet(currentNode);
                }
                else if (currentNode.getNodeName().equals(BONUSES_SET)){
                    readBonusSet(currentNode);
                }


            }

        }
        catch (final ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();

            //If the xml file contains error, setting a game with basic parameters
            availableFood.clear();
            availableBonuses.clear();
            minPlayer = 2;
            maxPlayer = 10;

        }

    }

    private void readFoodSet(Node foodSetNode){

        System.out.println("reading food set");

        this.minFood = Integer.valueOf(((Element) foodSetNode).getAttribute(MIN_FOOD));
        this.maxFood = Integer.valueOf(((Element) foodSetNode).getAttribute(MAX_FOOD));

        NodeList foodNodeList = foodSetNode.getChildNodes();

        for (int i = 0; i < foodNodeList.getLength(); i++) {

            Node foodNode = foodNodeList.item(i);

            if (foodNode.getNodeName().equals(FOOD)) {

                String name = ((Element) foodNode).getAttribute(NAME);
                int growthFactor = Integer.valueOf(((Element) foodNode).getAttribute(GROWTH_FACTOR));
                String resourceKey = ((Element) foodNode).getAttribute(RESOURCE_KEY);
                int probaWeight = Integer.valueOf(((Element) foodNode).getAttribute(PROBA_WEIGHT));

                availableFood.add(new Food(name, growthFactor, resourceKey, probaWeight));
            }
        }



    }

    private void readBonusSet(Node bonusSetNode){

        this.minBonuses = Integer.valueOf(((Element) bonusSetNode).getAttribute(MIN_BONUSES));
        this.maxBonuses = Integer.valueOf(((Element) bonusSetNode).getAttribute(MAX_BONUSES));

        NodeList bonusNodeList = bonusSetNode.getChildNodes();

        for (int i = 0; i < bonusNodeList.getLength(); i++) {

            Node bonusNode = bonusNodeList.item(i);

            if (bonusNode.getNodeName().equals(BONUS)) {

                String name = ((Element) bonusNode).getAttribute(NAME);
                String bonusTypeStr = ((Element) bonusNode).getAttribute(BONUS_TYPE);
                String bonusTargetStr = ((Element) bonusNode).getAttribute(BONUS_TARGET);
                String resourceKey = ((Element) bonusNode).getAttribute(RESOURCE_KEY);
                int probaWeight = Integer.valueOf(((Element) bonusNode).getAttribute(PROBA_WEIGHT));

                BonusType bonusType;
                BonusTarget bonusTarget;


                switch (bonusTargetStr) {
                    case TAKER:
                        bonusTarget = BonusTarget.TAKER;
                        break;
                    case EVERYONE_BUT_TAKER:
                        bonusTarget = BonusTarget.EVERYONE_BUT_TAKER;
                        break;
                    case ANYONE_BUT_TAKER:
                        bonusTarget = BonusTarget.ANYONE_BUT_TAKER;
                        break;
                    case EVERYONE:
                        bonusTarget = BonusTarget.EVERYONE;
                        break;
                    default:
                        bonusTarget = null; //Should never happen
                        break;
                }

                switch (bonusTypeStr) {
                    case GAIN_LIFE:
                        bonusType = BonusType.GAIN_LIFE;
                        break;
                    case LOOSE_LIFE:
                        bonusType = BonusType.LOOSE_LIFE;
                        break;
                    case GO_FASTER:
                        bonusType = BonusType.GO_FASTER;
                        break;
                    case GO_SLOWER:
                        bonusType = BonusType.GO_SLOWER;
                        break;
                    case IMMATERIALITY:
                        bonusType = BonusType.IMMATERIALITY;
                        break;
                    case INVISIBILITY:
                        bonusType = BonusType.INVISIBILITY;
                        break;
                    default:
                        bonusType = null; //Should never happen
                        break;
                }

                availableBonuses.add(new Bonus(bonusType, bonusTarget, resourceKey, probaWeight, name));
            }
        }
    }




}
