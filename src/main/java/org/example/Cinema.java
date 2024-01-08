package org.example;
import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private int numHalls;
    private int numRows;
    private int numSeatsPerRow;
    private int[][][] seats;

    public int getNumSeatsPerRow() {
        return numSeatsPerRow;
    }

    public int[] convertSeatIndexToSeatsArray(int seatIndex, int numSeats) {
        int[] seatsArray = new int[numSeats];
        for (int i = 0; i < numSeats; i++) {
            seatsArray[i] = seatIndex + i + 1;
        }
        return seatsArray;
    }

    public Cinema(int halls, int rows, int seatsPerRow) {
        numHalls = halls;
        numRows = rows;
        numSeatsPerRow = seatsPerRow;
        seats = new int[numHalls][numRows][numSeatsPerRow];
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 0; i < numHalls; i++) {
            for (int j = 0; j < numRows; j++) {
                for (int k = 0; k < numSeatsPerRow; k++) {
                    seats[i][j][k] = 0;
                }
            }
        }
    }

    public void bookSeats(int hallNumber, int row, int[] seatNumbers) {
        if (!isValidHall(hallNumber) || row <= 0 || row > numRows) {
            System.out.println("Некоректний номер залу або ряду");
            return;
        }

        for (int seat : seatNumbers) {
            if (seat <= 0 || seat > numSeatsPerRow) {
                System.out.println("Некоректний номер місця");
                return;
            }

            if (seats[hallNumber - 1][row - 1][seat - 1] == 1) {
                System.out.println("Це місце вже заброньоване, виберіть інше");
                return;
            }
        }

        for (int seat : seatNumbers) {
            seats[hallNumber - 1][row - 1][seat - 1] = 1;
        }
        System.out.println("Місця успішно заброньовані!");
    }

    public void cancelBooking(int hallNumber, int row, int[] seatNumbers) {
        if (isValidHall(hallNumber) && row > 0 && row <= numRows) {
            for (int seat : seatNumbers) {
                if (seat > 0 && seat <= numSeatsPerRow) {
                    seats[hallNumber - 1][row - 1][seat - 1] = 0;
                } else {
                    System.out.println("Некоректний номер місця");
                }
            }
            System.out.println("Бронювання скасовано!");
        } else {
            System.out.println("Некоректний номер залу або ряду");
        }
    }

    public boolean checkAvailability(int hallNumber, int numSeats) {
        if (isValidHall(hallNumber)) {
            for (int i = 0; i < numRows; i++) {
                int consecutiveEmptySeats = 0;
                for (int j = 0; j < numSeatsPerRow; j++) {
                    if (seats[hallNumber - 1][i][j] == 0) {
                        consecutiveEmptySeats++;
                        if (consecutiveEmptySeats == numSeats) {
                            return true;
                        }
                    } else {
                        consecutiveEmptySeats = 0;
                    }
                }
            }
            return false;
        } else {
            System.out.println("Некоректний номер залу");
            return false;
        }
    }

    public void printSeatingArrangement(int hallNumber) {
        if (isValidHall(hallNumber)) {
            System.out.print("\t|");
            for (int i = 1; i <= numSeatsPerRow; i++) {
                System.out.print("\t" + i);
            }
            System.out.println("\n");

            for (int i = 0; i < numRows; i++) {
                System.out.print((i + 1) + "\t|");
                for (int j = 0; j < numSeatsPerRow; j++) {
                    System.out.print("\t" + seats[hallNumber - 1][i][j]);
                }
                System.out.println();
            }
        } else {
            System.out.println("Некоректний номер залу");
        }
    }

    public int[][][] getSeats() {
        return seats;
    }

    private boolean isValidHall(int hallNumber) {
        return hallNumber > 0 && hallNumber <= numHalls;
    }

    public List<Integer> findBestAvailable(int hallNumber, int numSeats) {
        List<Integer> bestAvailableSeats = new ArrayList<>();

        if (isValidHall(hallNumber)) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numSeatsPerRow; j++) {
                    if (seats[hallNumber - 1][i][j] == 0) {
                        boolean isAvailable = true;
                        for (int k = 0; k < numSeats; k++) {
                            if (j + k >= numSeatsPerRow || seats[hallNumber - 1][i][j + k] == 1) {
                                isAvailable = false;
                                break;
                            }
                        }

                        if (isAvailable) {
                            for (int k = 0; k < numSeats; k++) {
                                bestAvailableSeats.add((i + 1) * numSeatsPerRow + j + k + 1);
                            }
                            return bestAvailableSeats;
                        }
                    }
                }
            }
        } else {
            System.out.println("Некоректний номер залу");
        }

        return bestAvailableSeats;
    }

    public void autoBook(int hallNumber, int numSeats) {
        List<Integer> bestAvailableSeats = findBestAvailable(hallNumber, numSeats);

        if (!bestAvailableSeats.isEmpty()) {
            for (int seatNumber : bestAvailableSeats) {
                int row = (seatNumber - 1) / numSeatsPerRow;
                int col = (seatNumber - 1) % numSeatsPerRow;
                seats[hallNumber - 1][row][col] = 1;
            }
            System.out.println("Місця успішно заброньовані!");
        } else {
            System.out.println("Неможливо забронювати місця. Виберіть інші параметри.");
        }
    }
}