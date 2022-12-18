package battleship;

import java.util.Scanner;

import static battleship.Field.checkWin;

public class Main {

    public static void main(String[] args) {
        boolean checkWin = false;
        Field p1field = new Field();
        Field p2field = new Field();
        Field p1fogOfWar = new Field();
        Field p2fogOfWar = new Field();

        // Player1
        System.out.println("Player 1, place your ships on the game field");
        p1field.print();
        for (Ship ship : Ship.values()) {
            p1field.setShip(ship);
            p1field.print();
        }

        pass();

        // Player2
        System.out.println("Player 2, place your ships on the game field");
        p2field.print();
        for (Ship ship : Ship.values()) {
            p2field.setShip(ship);
            p2field.print();
        }

        pass();

        while (!checkWin) {

            // Player 1 turn

            p2fogOfWar.print();
            System.out.println("---------------------");
            p1field.print();
            System.out.println("Player 1, it's your turn:");
            p2fogOfWar.doShot(p2field.getField());

            checkWin = checkWin(p2field);
            if (checkWin) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }

            pass();

            // Player 2 turn

            p1fogOfWar.print();
            System.out.println("---------------------");
            p2field.print();
            System.out.println("Player 2, it's your turn:");
            p1fogOfWar.doShot(p1field.getField());


            checkWin = checkWin(p1field);
            if (checkWin) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            }

            pass();
        }

    }

    public static void pass() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner sc = new Scanner(System.in);
        String check = sc.nextLine();
    }
}