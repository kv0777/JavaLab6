package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class CinemaTest {

    @Test
    public void testBookSeats() {
        Cinema cinema = new Cinema(1, 5, 5);
        int hallNumber = 1;
        int row = 1;
        int[] seats = {1, 2, 3};

        cinema.bookSeats(hallNumber, row, seats);

        for (int seat : seats) {
            assertEquals(1, cinema.getSeats()[hallNumber - 1][row - 1][seat - 1]);
        }
    }

    @Test
    public void testCancelBooking() {
        Cinema cinema = new Cinema(1, 5, 5);
        int hallNumber = 1;
        int row = 1;
        int[] seats = {1, 2, 3};

        cinema.bookSeats(hallNumber, row, seats);

        cinema.cancelBooking(hallNumber, row, seats);

        for (int seat : seats) {
            assertEquals(0, cinema.getSeats()[hallNumber - 1][row - 1][seat - 1]);
        }
    }

    @Test
    public void testFindBestAvailable() {
        Cinema cinema = new Cinema(1, 5, 5);
        int hallNumber = 1;
        int numSeats = 3;

        cinema.bookSeats(hallNumber, 1, new int[]{1, 2, 3});

        List<Integer> bestSeats = cinema.findBestAvailable(hallNumber, numSeats);

        assertEquals(numSeats, bestSeats.size());
    }
}