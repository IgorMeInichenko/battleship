package battleship;

import java.util.Scanner;

public class Field {
    char[][] field;

    public char[][] getField(){
        return this.field;
    }
    public Field() {
        field = new char[10][10];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = '~';
            }
        }
    }

    public void setShip(Ship ship) {
        Scanner scanner = new Scanner(System.in);
        int[] coordinates = null;
        boolean isCoordinatesValid = false;
        System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                ship.getPrintName(), ship.getLength());
        while (!isCoordinatesValid) {
            coordinates = toDigitCoordinates(scanner.nextLine());
            try {
                isCoordinatesValid = validateCoordinates(ship, coordinates);
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage() + " Try again:");
            }
        }
        for (int i = Math.min(coordinates[0], coordinates[2]); i <= Math.max(coordinates[0], coordinates[2]); i++) {
            for (int j = Math.min(coordinates[1], coordinates[3]); j <= Math.max(coordinates[1], coordinates[3]); j++) {
                field[i][j] = 'O';
            }
        }
        //scanner.close();
    }

    public void print() {
        char letter = 'A';
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < field.length; i++) {
            System.out.print(letter++); // A, B, C and so on.
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }
    public static boolean checkWin(Field field) {
        for (char[] chars : field.getField()) {
            for (char aChar : chars) {
                if (aChar == 'O') {
                    return false;
                }
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
        return true;
    }

    static int[] toDigitCoordinates(String coordinates) {
        int[] result = new int[4];
        result[0] = coordinates.split(" ")[0].charAt(0) - 65;
        result[1] = Integer.parseInt(coordinates.split(" ")[0].substring(1)) - 1;
        result[2] = coordinates.split(" ")[1].charAt(0) - 65;
        result[3] = Integer.parseInt(coordinates.split(" ")[1].substring(1)) - 1;
        return result;
    }

    private boolean validateCoordinates(Ship ship, int[] coordinates) throws IllegalAccessException {
        boolean isValid = true;

        if ((coordinates[0] == coordinates[2]) == (coordinates[1] == coordinates[3])) {
            throw new IllegalAccessException("Error! Wrong ship location!");
        }
        if (Math.abs((coordinates[0] - coordinates[2]) - (coordinates[1] - coordinates[3])) + 1 != ship.getLength()) {
            throw new IllegalAccessException("Error! Wrong length of the " + ship.getPrintName() + "!");
        }
        for (int i = Math.min(coordinates[0], coordinates[2]); i <= Math.max(coordinates[0], coordinates[2]); i++) {
            for (int j = Math.min(coordinates[1], coordinates[3]); j <= Math.max(coordinates[1], coordinates[3]); j++) {
                if (checkNeighbors(this.field, i, j, 'O')) {
                    throw new IllegalAccessException("Error! You placed it too close to another one.");
                }
            }
        }

        return isValid;
    }

    private boolean checkNeighbors(char[][] field, int row, int col, char target) {
        if (field[row][col] == target) {
            return true;
        }
        if (col > 0) {
            if (field[row][col - 1] == target) {
                return true;
            }
            if (row > 0) {
                if (field[row - 1][col] == target) {
                    return true;
                }
                if (field[row - 1][col - 1] == target) {
                    return true;
                }
            }
            if (row < 9) {
                if (field[row + 1][col] == target) {
                    return true;
                }
                if (field[row + 1][col - 1] == target) {
                    return true;
                }
            }
        }
        if (col < 9) {
            if (field[row][col + 1] == target) {
                return true;
            }
            if (row > 0) {
                if (field[row - 1][col] == target) {
                    return true;
                }
                if (field[row - 1][col + 1] == target) {
                    return true;
                }
            }
            if (row < 9) {
                if (field[row + 1][col] == target) {
                    return true;
                }
                if (field[row + 1][col + 1] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    public void doShot(char[][] sourceField) {
        Scanner scanner = new Scanner(System.in);
        boolean isShotValid = false;
        int[] coordinates = null;
        while (!isShotValid) {
            coordinates = toDigitCoordinatesShot(scanner.nextLine());
            try {
                isShotValid = validateShotCoordinates(coordinates);
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage() + " Try again:");
            }
        }
        if (sourceField[coordinates[0]][coordinates[1]] == '~') {
            field[coordinates[0]][coordinates[1]] = 'M';
            sourceField[coordinates[0]][coordinates[1]] = 'M';
            print();
            System.out.println("You missed. Try again:");
        }
        if (sourceField[coordinates[0]][coordinates[1]] == 'O') {
            field[coordinates[0]][coordinates[1]] = 'X';
            sourceField[coordinates[0]][coordinates[1]] = 'X';
            print();
            if (checkNeighbors(sourceField, coordinates[0],coordinates[1],'O')){
                System.out.println("You hit a ship! Try again:");
            }
            else {
                System.out.println("You sank a ship! Specify a new target:");
            }

        }
    }

    private boolean validateShotCoordinates(int[] coordinates) throws IllegalAccessException {
        boolean isValid = true;
        if (coordinates[0] < 0 || coordinates[0] > 9) {
            throw new IllegalAccessException("Error! You entered the wrong coordinates!");
        }
        if (coordinates[1] < 0 || coordinates[1] > 9) {
            throw new IllegalAccessException("Error! You entered the wrong coordinates!");
        }
        return isValid;
    }

    public int[] toDigitCoordinatesShot(String coordinates) {
        int[] result = new int[2];
        result[0] = coordinates.split(" ")[0].charAt(0) - 65;
        result[1] = Integer.parseInt(coordinates.split(" ")[0].substring(1)) - 1;
        return result;
    }
}