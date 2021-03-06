package com.qatrain.janushgame;

import java.io.*;

/**
 * Uses <code>System.Console</code>, <code>BufferedReader</code> or raw <code>System.in</code> to move on screen.
 * After every move, or set of moves, requires ENTER to be pressed.
 */
public class ConsoleMoves {
    protected static final char ESC_CODE = 0x1B;

    protected static final String ARROW_UP = ESC_CODE + "[A";
    protected static final String ARROW_DOWN = ESC_CODE + "[B";
    protected static final String ARROW_RIGHT = ESC_CODE + "[C";
    protected static final String ARROW_LEFT = ESC_CODE + "[D";

    //column to put cursor at start
    protected static final int TYPYING_COLUMN = 1;
    protected static final int TYPYING_ROW = 13;

    public static void main(String... args) throws IOException {

        //way 1
        Console console = System.console();
        if (console == null) {
            System.out.println(
                    "Can't run in non-interactive mode! Run in console using: \n" +
                            "     java -cp ./target/classes com.qatrain.janushgame.ConsoleMoves \n");
            System.exit(0);
        }

        System.out.println("Press ENTER after every move / set of moves. \n" +
                "Press x to exit. Clear screen before starting. \n" +
                "Use keyboard keys ^v<> or asdw:");

        //way 2
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int startColumn = 20;

        //position of cursor
        int x = 20;
        int y = 10;

        //char array is needed to recognise arrow keys
        //arrow keys are not like regular characters (a, b, c...)
        //arrow keys are not one character but set of such
        //therefore the charArray - to put them together and recognise as arrow key
        String charArray = "";

        boolean exit = false;
        while (!exit) {
            //initial position on screen
            gotoRowCol(20, startColumn);

            //----------
            //way 3: using System.in
            //int input = System.in.read();

            //----------
            //way 2: using buffered reader
            //int input = reader.read();
            //char[] chars = new char[3];
            //reader.read(chars);

            //----------
            //way 1: using console
            String input = console.readLine();

            //read all chars from input one by one
            for (char c : input.toCharArray()) {
                if (c == 'a' || (charArray + c).equals(ARROW_LEFT)) {
                    x--;
                    charArray = "";
                } else if (c == 'd' || (charArray + c).equals(ARROW_RIGHT)) {
                    x++;
                    charArray = "";
                } else if (c == 's' || (charArray + c).equals(ARROW_DOWN)) {
                    y++;
                    charArray = "";
                } else if (c == 'w' || (charArray + c).equals(ARROW_UP)) {
                    y--;
                    charArray = "";
                } else {
                    charArray += c;
                }

                //move cursor to position chosen by user pressing keys
                gotoRowCol(y, x);
                //put a stamp at that place
                System.out.print("*");

                if (c == 'x')
                    exit = true;
            }

        }

        //move cursor to top of screen
        gotoRowCol(1, 1);
    }

    /**
     * This method moves cursor to column / row.
     * See also http://www.termsys.demon.co.uk/vtansi.htm
     *
     * @param row row index from top
     * @param column column index from left
     */
    protected static void gotoRowCol(int row, int column) {
        System.out.print(String.format("%c[%d;%df", ESC_CODE, row, column));
    }
}
