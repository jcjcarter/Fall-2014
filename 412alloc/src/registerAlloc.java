import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;


/**
 * @author Ace
 *
 */
public class registerAlloc {

  //Line count for program being read
  private static int programLineCount;
  
  //Vector class that holds the opCode, Op1, Op2, and Op3
  //private Vector lineVectorOP;
  
  //The program in vector line form
  private static HashMap<Integer, Vector> allocationActions = new HashMap<Integer, Vector>();
  
  // Class variable that holds the register names and the live ranges, index 0 is the start and index 1 is the end for the array
  private static HashMap<String, int[]> registerList = new HashMap<String, int[]>();

  // Class variable that holds the register names and the live ranges listed out i.e. 0, .., 100 in an arrayList
  private static HashMap<String, ArrayList<Integer>> registerRanges =
      new HashMap<String, ArrayList<Integer>>();
  
  //Class variable that keeps track of X input physical registers available in a set for the bottom-up algorithm
  private static Set<String> registerAvail = new TreeSet<String>();
  
  //Class variable that holds the number of live registers for each line
  private static HashMap<Integer, Integer> maxLiveHash = new HashMap<Integer, Integer>();

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // String input = "./412alloc -h 12 test_block_17.i > output_block.i";
    String fileLocation = "/Users/Ace/Downloads/HolderJar/block1.i 22";

    // fileLocation = args[0];

    // Check to see if the parameter -h is present
    if (hFlag(fileLocation)) {
      System.exit(0);
    }
    
    //Look for the number of registers or throw an error if not there
    if(!generateXRegisters(fileLocation)){
      //REMEMBER THAT THE INPUT FROM THE COMMANDLINE WILL BE IN ARRAY#################^^^^^^^^^^^^^^^^^^@@@@@@@@@@@@@@@@@@@******************
      System.out.println("Failure to open '–h' as the input file.");//'-h' needs to be changed to an index that corresponds to the input of the file name 
      System.out.println("Will attempt to read from 'stdin'.");
      System.out.println("Cannot allocate with fewer than 2 registers.");
      System.exit(0);
    }
    
    /**Test to see if X registers are in the set
    Iterator<String> allRegistersHere = registerAvail.iterator();
    while(allRegistersHere.hasNext()){
      System.out.println(allRegistersHere.next());
    }*/
    
    // Opens the file, stores the program, and prints program
    //CHANGE SEARCH FOR .I THEN LOOP BACK TO GET THE FILE I NAME$$$$$$$$$$$$$$$$$$$$$$$
    readMicrosyntax(openAndRead("/Users/Ace/Downloads/HolderJar/block1.i"), programLineCount);//REMEMBER THAT THIS NEEDS TO BE CHANGED BACK TO FILELOCATION VARIABLE, ALSO ACCOUNT FOR THE ARRAY********************


    System.out.println("Finished.");
  }
  
  /**
   * Fills the arrayList for generating all the numbers between start and end
   * @param regName
   * @param startIndex
   * @param endIndex
   */
  public static void fillInLiveRanges(String regName, int startIndex, int endIndex){
    ArrayList<Integer> linesLiveRange = new ArrayList<Integer>();
    for(int i = startIndex; i <= endIndex; i++){
      linesLiveRange.add(i);
      //System.out.print(i +" ");
    }
    //System.out.println(regName);
    registerRanges.put(regName, linesLiveRange);
    return;
  }
  
  /**
   * Parses the command line to find the number of registers to produce
   * and places that number of registers into the class variable Set<String>
   * holding all the registers.
   * @param filePath
   */
  public static boolean generateXRegisters(String filePath){
    String strToNumber = "", regName = "pr";
    int numIndex, numRegisters;
    int inputLength = filePath.length();
    for(int i = 0; i < inputLength; i++){
      //System.out.println(filePath.charAt(i));
      if(Character.isDigit(filePath.charAt(i))){
        if(Character.isWhitespace(filePath.charAt(i-1))){
          numIndex = i;
          while(Character.isDigit(filePath.charAt(numIndex))){
            //System.out.println("Bong: " + filePath.charAt(numIndex));
            strToNumber += filePath.charAt(numIndex);
            if(numIndex +1 >= inputLength){
              break;
            }else{
            numIndex++;
            }
          }
          //System.out.println("Bong: " + strToNumber);
          numRegisters = Integer.parseInt(strToNumber);
          if (numRegisters < 2){
            System.out.println("Cannot allocate with fewer than 2 registers.");
            System.exit(0);
          }
          for(int j = 0; j < numRegisters; j++){
            regName += Integer.toString(j);
            //add to the set
            registerAvail.add(regName);
            regName = "pr";
          }
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Methods prints out all the available options for parameters.
   * 
   * @param commandLine
   * @param exitProgram
   * @return
   */
  public static boolean hFlag(String commandLine) {
    if (commandLine.contains("-h")) {

      System.out.println(" ");
      System.out.println("Command Syntax: "+"\n\t    412alloc k filename [-h] [-l]\n\n" + "\n Required arguments:" +"\n\t    k     specifies the number of register available" +
      "\n\t filename  is the pathname (absolute or relative) to the input file\n\n" + "\n Optional flags:" + "\n\t    -h    prints this message" +
          "\n\t    -l    additive flag; increases detail written to './LogFile'");
      return true;
    }
    return false;
  }

  /**
   * Opens up a text file and prints all the characters in the text.
   * 
   * @param filename
   * @return
   */
  public static HashMap<Integer, String> openAndRead(String filename) {
    // Structure reverses the file and does not hold comments
    Stack<String> lifo = new Stack<String>();

    // line index from bottom to top
    int stackIndex = 0;

    // line index from top to bottom
    int countToBottom = 0;

    // lines popped from stack
    String ilocLine = "";

    //Used to iterator through the registers found in the program going top down
    Set<String> printRegList;
    
    // storage of the program
    HashMap<Integer, String> dataStruct = new HashMap<Integer, String>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      // read in each line from the block
      while ((line = reader.readLine()) != null) {

        // Skip pass the comment section of the file
        if (line.contains("/")) {
          continue;
        }

        readTopDown(line, countToBottom);
        countToBottom++;

        // Places the lines onto a stack, to prepare for bottom up reading
        lifo.push(line);

      }
      reader.close();
    } catch (Exception e) {
      System.err.format("Exception occurred trying to read '%s'.", filename);
      e.printStackTrace();
    }

    printRegList = registerList.keySet();
    Iterator<String> pass = printRegList.iterator();
    String keyHere;
    while (pass.hasNext()) {
      keyHere = pass.next();
      //System.out.println(keyHere);
      //System.out.println("First appeared: " + registerList.get(keyHere)[0]);
      //System.out.println("Last appeared: " + registerList.get(keyHere)[1]);
      fillInLiveRanges(keyHere,registerList.get(keyHere)[0],registerList.get(keyHere)[1]);
    }

    //Produce MaxLive for Each from the top to bottom read, countToBottom -1 to account for the extra increment
    maxLiveLines(countToBottom-1);
    
    while (!lifo.isEmpty()) {
      ilocLine = (String) lifo.pop();

      // Fills the data structure with the ILOC Program
      dataStruct.put(stackIndex, ilocLine);
      
      //System.out.println(stackIndex);
      programLineCount = stackIndex;
      // increment counter
      stackIndex++;
    }
    //System.out.println(programLineCount);
    return dataStruct;
  }
  
  /**
   * The function MaxLive updates a class variable HashMap where
   * the key is the line and value is number of live registers.
   * @param programLength
   */
  public static void maxLiveLines(int programLength){
    int liveRegistersCounted = 0;

    for(int i = programLength; i>= 0; i--){
      Set <String> listedRegistersSet = registerList.keySet();
      Iterator<String> listedRegister = listedRegistersSet.iterator();
      while(listedRegister.hasNext()){
        if(registerRanges.get(listedRegister.next()).contains(i)){
          //System.out.println(i);
          liveRegistersCounted++;
        }
      }
      //System.out.println("Line Number: "+ i);
      //System.out.println("\t Number of live Registers: " + liveRegistersCounted);
      maxLiveHash.put(i, liveRegistersCounted);
      liveRegistersCounted = 0;
    }
    return;
  }

  /**
   * Will write to HashMap to demonstrate the live range.
   * 
   * @param textLine
   */
  public static void readTopDown(String textLine, int lineIndex) {
    String buildToken = "";
    // Holds the start
    for (int i = 0; i < textLine.length(); i++) {
      if (Character.isWhitespace(textLine.charAt(i)) != true) {
        buildToken += textLine.charAt(i);
        // System.out.println(textLine.charAt(i));
        continue;
      }
      // Live ranges for all the registers in the program
      liveRanges(buildToken, lineIndex);

      buildToken = "";
    }
    return;
  }

  /**
   * Finds the live ranges for the registers in the ILOC program.
   * 
   * @param tokenWord
   * @param lineIndex
   */
  public static void liveRanges(String tokenWord, int lineIndex) {
    int[] regIndices = new int[2];
    // index of the comma in a operation
    int commaIndex;
    String firstReg = "", secondReg = "";

    if (tokenWord != "") {
      if (tokenWord.length() >= 2) {
        if (tokenWord.contains(",")) {
          commaIndex = tokenWord.indexOf(",");
          // parse the string to find the first register
          for (int i = 0; i < commaIndex; i++) {
            firstReg += tokenWord.charAt(i);
          }
          // parse the string to find the second register
          for (int i = commaIndex + 1; i < tokenWord.length(); i++) {
            secondReg += tokenWord.charAt(i);
          }
          /*
           * Takes care of the first register in updating the live range
           */
          if (firstReg.charAt(0) == 'r' && testForRegNum(firstReg.charAt(1))) {

            if (!registerList.containsKey(firstReg)) {
              regIndices = new int[2];
              // the first occurrance of the register in the program
              regIndices[0] = lineIndex;
              // the last occurrance of the register in the program
              regIndices[1] = lineIndex;
              registerList.put(firstReg, regIndices);
              // System.out.println("First found: "+ buildToken + " at Line: " + lineIndex);
            } else {
              regIndices = new int[2];
              // the first occurrance of the register in the program
              regIndices[0] = registerList.get(firstReg)[0];
              // the last occurrance of the register in the program
              regIndices[1] = lineIndex;
              // update the indices list
              registerList.put(firstReg, regIndices);
              // System.out.println("Updated: "+ buildToken + " at Line: " + lineIndex);
            }
          }
          /*
           * Takes care of the second register in updating the live range
           */
          if (secondReg.charAt(0) == 'r' && testForRegNum(secondReg.charAt(1))) {

            if (!registerList.containsKey(secondReg)) {
              regIndices = new int[2];
              // the first occurrance of the register in the program
              regIndices[0] = lineIndex;
              // the last occurrance of the register in the program
              regIndices[1] = lineIndex;
              registerList.put(secondReg, regIndices);
              // System.out.println("First found: "+ buildToken + " at Line: " + lineIndex);
              return;
            } else {
              regIndices = new int[2];
              // the first occurrance of the register in the program
              regIndices[0] = registerList.get(secondReg)[0];
              // the last occurrance of the register in the program
              regIndices[1] = lineIndex;
              // update the indices list
              registerList.put(secondReg, regIndices);
              // System.out.println("Updated: "+ buildToken + " at Line: " + lineIndex);
              return;
            }
          }
        }// end of if-statement testing for a comma
      }
    }

    if (tokenWord != "") {
      if (tokenWord.length() >= 2) {
        if (tokenWord.charAt(0) == 'r' && testForRegNum(tokenWord.charAt(1))) {
          if (!registerList.containsKey(tokenWord)) {
            regIndices = new int[2];
            // the first occurrance of the register in the program
            regIndices[0] = lineIndex;
            // the last occurrance of the register in the program
            regIndices[1] = lineIndex;
            registerList.put(tokenWord, regIndices);
            // System.out.println("First found: "+ buildToken + " at Line: " + lineIndex);
          } else {
            regIndices = new int[2];
            // the first occurrance of the register in the program
            regIndices[0] = registerList.get(tokenWord)[0];
            // the last occurrance of the register in the program
            regIndices[1] = lineIndex;
            // update the indices list
            registerList.put(tokenWord, regIndices);
            // System.out.println("Updated: "+ buildToken + " at Line: " + lineIndex);
          }
        }
      }
    }
    return;
  }

  /**
   * Determines if the token word is a register number
   * 
   * @param number
   * @return
   */
  public static boolean testForRegNum(char number) {
    char[] alphaNumbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    for (int i = 0; i < alphaNumbers.length; i++) {
      if (number == alphaNumbers[i]) {
        return true;
      }
    }
    return false;
  }

  /**
   * Appends characters to the class variable tokenWord.
   * 
   * @param alphabet
   */
  public static void readMicrosyntax(HashMap<Integer, String> storedData, int numberOfLinesCounted) {//numberOfLinesCounted is used as the Key for allocationActions as lines, must be decremented
    String buildToken = "", textLine = "";
    // char[] alphaChars = {'s','t','o','r','e',
    // 'l','a','d','I',',','h','i','f','u','b','p','n','m','=','>','0','1','2','3','4','5','6','7','8','9'};
    //System.out.println("In the readMirco function: " + numberOfLinesCounted);
    for (int j = 0; j < storedData.size(); j++) {
      //Vector class that holds the opCode, Op1, Op2, and Op3
      Vector lineVectorOP = new Vector();
      textLine = storedData.get(j);
      // Prints out all the lines in the stack from the bottom up
       //System.out.println(textLine);
      for (int i = 0; i < textLine.length(); i++) {

        if (testForCharacters(textLine.charAt(i))&& i+1 < textLine.length()&& !Character.isWhitespace(textLine.charAt(i+1))) {
          buildToken += textLine.charAt(i);
          continue;
        }else{
          buildToken+= textLine.charAt(i);
        }
        if (buildToken != ""&& !buildToken.contains("\n") &&!buildToken.contains("\t")&&!buildToken.contains(" ")) {
          // Prints out all the tokens in the line
           System.out.println(buildToken);
           /**
            * Now I need to write functions that will taken in buildToken as a String parameter and
            * return booleans if that string fits the description. If the boolean is true then I need
            * fill in the vector for the corresponding operation. If there is a comma present, write
            * a method for that.
            **/
        }
        buildToken = "";
      }
      allocationActions.put(numberOfLinesCounted, lineVectorOP);
    //numberOfLinesCounted is used as the Key for allocationActions as lines, must be decremented
      numberOfLinesCounted--;
      System.out.println("\tEnd of the line...");
    }
    return;
    // cases for load, loadI, and lshift
    // lMicrosyntax(textLine.charAt(i));
    // case for store (sub?)
    // sMicrosyntax(textLine.charAt(i));
    // case for output
    // case for nop
    // case for add
    // case for mult
    // case for rshift (r00?)
    // case for constant
    // case for ","
    // case for "=>"
  }

  public static boolean testForCharacters(char Character) {
    char[] alphaChars =
        {'s', 't', 'o', 'r', 'e', 'l', 'a', 'd', 'I', ',', 'h', 'i', 'f', 'u', 'b', 'p', 'n', 'm',
            '=', '>', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    for (int i = 0; i < alphaChars.length; i++) {
      if (alphaChars[i] == Character) {
        return true;
      }
    }
    return false;
  }


  /**
   * Finds the microsyntax load and loadl.
   * 
   * @param alphabet
   */
  public static void lMicrosyntax(char alphabet) {
    String tokenWord2 = "";
    if (alphabet == 'l' && tokenWord2 == "") {
      tokenWord2 += alphabet;
      // System.out.println("Found this l: " + alphabet);
      // System.out.println("The token word:" + tokenWord +"2");
      return;
    }
    if (alphabet == 's' && tokenWord2 == "l") {
      tokenWord2 += alphabet;
      // System.out.println("Found this s: " + alphabet);
      System.out.println("The token word: " + tokenWord2);
      return;
    }
    if (alphabet == 'h' && tokenWord2 == "ls") {
      tokenWord2 += alphabet;
      System.out.println("Found this h: " + alphabet);
      // System.out.println("The token word: " + tokenWord);
      return;
    }
    if (alphabet == 'i' && tokenWord2 == "lsh") {
      tokenWord2 += alphabet;
      System.out.println("Found this i: " + alphabet);
      System.out.println("The token word: " + tokenWord2);
      return;
    }
    if (alphabet == 'f' && tokenWord2 == "lshi") {
      tokenWord2 += alphabet;
      System.out.println("Found this f: " + alphabet);
      System.out.println("The token word: " + tokenWord2);
      return;
    }
    if (alphabet == 't' && tokenWord2 == "lshif") {
      tokenWord2 += alphabet;
      System.out.println("Found this t: " + alphabet);
      System.out.println("The token word: " + tokenWord2);
      return;
    }
    // token word lshift should be found
    if (alphabet == '\t' || alphabet == ' ') {
      if (tokenWord2 == "lshift") {
        System.out.println(tokenWord2);
        tokenWord2 = "";
        return;
      }
    }
    if (alphabet == 'o' && tokenWord2 == "l") {
      tokenWord2 += alphabet;
      return;
    }
    if (alphabet == 'a' && tokenWord2 == "lo") {
      tokenWord2 += alphabet;
      return;
    }
    if (alphabet == 'd' && tokenWord2 == "loa") {
      tokenWord2 += alphabet;
      return;
    }
    // token word load should be found
    if (alphabet == '\t' || alphabet == ' ') {
      if (tokenWord2 == "load") {
        System.out.println(tokenWord2);
        tokenWord2 = "";
        return;
      }
    }
    if (alphabet == 'I' && tokenWord2 == "load") {
      tokenWord2 += alphabet;
      return;
    }
    // token word loadI should be found
    if (alphabet == '\t' || alphabet == ' ') {
      if (tokenWord2 == "loadI") {
        System.out.println(tokenWord2);
        tokenWord2 = "";
        return;
      }
    }
    return;
  }

  /**
   * Finds the microsyntax store.
   * 
   * @param alphabet
   */
  public static void sMicrosyntax(char alphabet) {
    String tokenWord = "";
    if (alphabet == 's' && tokenWord == "") {
      tokenWord += alphabet;
      return;
    }
    if (alphabet == 't' && tokenWord == "s") {
      tokenWord += alphabet;
      return;
    }
    if (alphabet == 'o' && tokenWord == "st") {
      tokenWord += alphabet;
      return;
    }
    if (alphabet == 'r' && tokenWord == "sto") {
      tokenWord += alphabet;
      return;
    }
    if (alphabet == 'e' && tokenWord == "stor") {
      tokenWord += alphabet;
      return;
    }
    return;
  }
}
