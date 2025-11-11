package exercises;

import java.util.Scanner;

/**
 * Exercise 21 — Towers of Hanoi with Move Counting
 * <p>
 * From: Object-Oriented Data Structures Using Java (Ch. 3 Recursion)
 * <p>
 * TASK:
 * 1. Modify the classic Towers of Hanoi recursion so that it **counts**
 * the number of ring moves rather than printing each move.
 * 2. Use a static variable {@code count} to hold the total number of moves.
 * 3. Repeatedly prompt the user for the number of rings.
 * - If n < 0 → terminate the program.
 * - Else → run the solver and print the count.
 * <p>
 * Example I/O:
 * --------------------------------------
 * Enter number of rings (negative to quit): 3
 * Number of moves = 7
 * <p>
 * Enter number of rings (negative to quit): 5
 * Number of moves = 31
 * <p>
 * Enter number of rings (negative to quit): -1
 * Goodbye!
 * --------------------------------------
 * <p>
 * RECURSION REVIEW:
 * Base case: n == 0 → no moves.
 * Recursive case:
 * 1. Move n-1 disks from 'from' to 'aux'.
 * 2. Move disk n from 'from' to 'to'.
 * 3. Move n-1 disks from 'aux' to 'to'.
 * <p>
 * Total moves = 2ⁿ - 1
 */
public class TowersExercise21 {

    // static counter for number of moves
    private static int count = 0;

    /**
     * Recursive solver for the Towers of Hanoi.
     *
     * @param n    number of disks
     * @param from source peg
     * @param aux  auxiliary peg
     * @param to   destination peg
     */
    public static void solve(int n, char from, char aux, char to) {
        // TODO 1: Base case — if n == 0 → return.
        if (n == 0) {
            return;
        }

        solve(n - 1, from, to, aux);
        count++;
        // increment count for moving disk n
        solve(n - 1, aux, from, to);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of rings (negative to quit): ");
        int n = in.nextInt();
        while (n > 0) {
            count = 0; 
            solve(n, 'A', 'B', 'C'); // recursive call
            System.out.printf("Number of moves = %d%n", count);
            System.out.print("Enter number of rings (negative to quit): ");
            n = in.nextInt();
        }
        in.close();
        System.out.println("Goodbye!");
    }
}

