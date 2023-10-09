# Build Your Own World Design Document

**Partner 1: Myrah

**Partner 2: Fei

## Classes and Data Structures
###  Main

###  Engine
#### Instance variables
* TERenderer ter
* public StringBuilder fullInput
* private boolean spotlight
* private long lastRenderTime

#### Methods
* public void saveGame(StringBuilder fullInput)
* public World interactWithInputSource(InputSource inputSource, boolean isLive, boolean is_Replay)
* public void interactWithKeyboard()
* public TETile[][] interactWithInputString(String input)
* public TETile[][] interactWithInputString(String input, boolean isLive)
* public World interactWithInputStringWorld(String input, boolean isLive, boolean is_Replay)
#### Helper function
* private void render()
* private void drawWorldWithHUD()
* private Long getSeedfromInput(InputSource inputSource, boolean isLive)
* private static String readContentsAsString(File file)
* private static byte[] readContents(File file)
* public class Timing
* public static void pause(int delay)
* public void saveGame(StringBuilder fullInput)

### public interface InputSource
#### public class KeyboardInputSource implements InputSource
#### public class StringInputDevice implements InputSource
* method: public char getNextKey();
* method:public boolean possibleNextInput();
#### public interface MouseListener
* method: void onMouseMoved();

###  TERenderer
#### Instance variables
* public static final int TILE_SIZE;
* private int width;
* private int height;
* private int xOffset;
* private int yOffset;
* private static final int SPOTLIGHT_SIZE;
#### Methods
* public void initialize(int w, int h, int xOff, int yOff)
* public void initialize(int w, int h)
* public void renderFrame(TETile[][] world)
* public void renderSpotlightFrame(TETile[][] world, Position p)

### TETile
#### Instance variables
* private final char character; 
* private final Color textColor;
* private final Color backgroundColor;
* private final String description;
* private final String filepath;
#### Constructor
* public TETile(char character, Color textColor, Color backgroundColor, String description,
  String filepath)
* public TETile(char character, Color textColor, Color backgroundColor, String description)
* public TETile(TETile t, Color textColor)
* public void draw(double x, double y)
* public char character()
* public String description()
* public static TETile colorVariant(TETile t, int dr, int dg, int db, Random r)
* private static int newColorValue(int v, int dv, Random r)
* private static int newColorValue(int v, int dv, Random r)

###  World
#### Instance variables
* TETile[][] world
* Random RANDOM
* List<Room> roomList
* public Avatar player;

#### Constructor
* public World(long seed)
* public void genWorld(): includes construct player
* public TETile[][] drawWorld()
* public void drawPlayer(Avatar player)
* private void drawHallwayConnectRooms()
* private void drawWall()
* private Room genRandRoom()
* private void fillwithrooms()
* private void drawRooms()
* public boolean isWall(Position p)
* public boolean outofbounds(int x, int y)
* public static void fillBoardWithNothing(TETile[][] world)
* public TETile[][] getWorld()
* public void setWorld(TETile[][] world)
* public List<Room> getRoomList()

### Game
#### Instance variables
* boolean palying;
#### Constructor
* public Game(World world)
#### Methods
* boolean playing()
* public void playGame(World world, char c)

### Door
#### Instance variables
* private Position p;
* private World world;
#### Constructor
* public Door(World world)
#### Methods
* Position getP()


### Avatar
#### Instance variables
* private Position p;
* private static InputSource inputSource;
* private World world;
#### Constructor
* public Avatar(World world)
#### Methods
* public void movePosition(World world, char c))
* Position getP()

### Room
#### Instance variables
* private int width;
* private int height;
* private Position p;
* private Position c;
#### Constructor
* public Room(Position p, int width, int height)
#### Methods
* public void drawRoom(TETile[][] tiles)
* public static void drawHallway(TETile[][] tiles, Position p1, Position p2)

### Position
#### Instance variables
* private int x;
* private int y;
#### Constructor
*  public Position(int x, int y)
#### Methods
* public Position shift(int xoff, int yoff)

### DrawInterface
#### Methods
* public static void drawFrame(String s)
* public static void drawHUD(String s)
* public static void drawLandInterface()


## Persistence
* keep fullinput String tracking all the process keep tracking the world Object, 
* put player inside of world object; 
* save and read files from local direcotry;

## Extra Feature:
### when Game is palying, Hit 'C' for toggle ON/OFF for Spotlight Mode; 
### Landing page adding REPLAY function; 
### Reach the land in the swimming pool to win the Game: Winning Game will not be saved to reload/reply.

## Algorithms (we use 2 + 4)
1. Make random rooms of random width/length and spread randomly across world
2. ** Place a number of randomly sized and positioned rooms.If a room overlaps an existing room, it is discarded. then connect rooms with Hallways.
3. start with one room. Pick a random location, create a random room there. Pick another random location, create a random room there. Continue for a certain number of rooms. Then go back and randomly create some more hallways between existing rooms
4. place one room first,  and branching out Pathway and another room into empty space. check if it's fills requirement, finished. 
5. ** Place rooms and hallway with only floor first,  after all connected, then generate wall all at once. 

## Reference

* Rooms and Mazes: A Procedural Dungeon Generator
http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
https://github.com/munificent/hauberk/blob/db360d9efa714efb6d937c31953ef849c7394a39/lib/src/content/dungeon.dart
https://zhuanlan.zhihu.com/p/30724817
* Basic BSP Dungeon generation
http://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation
* Herbert Wolverson - Procedural Map Generation Techniques
  https://www.youtube.com/watch?v=TlLIOgWYVpI

