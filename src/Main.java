
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int totalOwnerPercentage = 0;
    static int ownerShip = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String restaurantName;
        int totalOwners;

        while (true) {
            print("Ange restaurangens namn>>");
            restaurantName = input.nextLine();

            if (restaurantName.length() <= 10) {
                println("För kort namn minst 10 bokstäver!");
            } else {
                break;
            }
        }

        while (true) {
            print("Antal ägare>>");
            totalOwners = input.nextInt();

            if (totalOwners <= 0) {
                println("Minst en ägare!");

            } else {
                break;
            }
        }


        int[] ownersArray = new int[totalOwners];
        if (totalOwners == 1) {
            ownersArray[0] = 100;
            totalOwnerPercentage = 100;
        } else {
            for (int i = 0; i < totalOwners - 1; i++) {
                while (true) {

                    print("Ange ägare " + (i + 1) + " ägarandel>>");
                    ownerShip = input.nextInt();

                    if (ownerShip <= 0) {

                        print("En ägare måste äga nåt");
                    } else {
                        if (totalOwnerPercentage + ownerShip > 100) {

                            print("För stor andel");

                        } else {

                            ownersArray[i] = ownerShip;
                            totalOwnerPercentage += ownerShip;

                            break;
                        }
                    }
                }
            }
            ownersArray[totalOwners - 1] = 100 - totalOwnerPercentage;
            println(Arrays.toString(ownersArray));
        }

        println("\nVälkommen till " + restaurantName + "!");

        String[] menuChoices = new String[]{
                "1.Ägare(visa alla, lägg till, ändra, ta bort)",
                "2.Anställd(visa alla, lägg till, ändra, ta bort)",
                "3.Skriv ut sammanställning",
                "0.Avsluta"
        };

        println("\nVälj ett av dessa menyalternativ: ");

        printMenu1(menuChoices);

        int choice;
        do {
            print("Ange siffran för menyval>>");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    subChoice(input, ownersArray);
                    break;
                case 2:
                    print("2.Anställd(visa alla, lägg till, ändra, ta bort)\n");
                    break;
                case 3:
                    print("3.Skriv ut sammanställning\n");
                    break;
                default:
                    println("Gör ett val mellan 0-3\n");
                    printMenu1(menuChoices);

            }
        } while (choice != 0);

//        String[] employeesArray = new String[1];


    }

    public static void subChoice(Scanner input, int[] ownersArray) {
        streck();
        int subChoice;
        do {
            print("""
                    Vad vill du göra med Ägare?
                    1.Visa alla ägare
                    2.Lägg till en ny Ägare
                    3.Ändra en ägare
                    4.Ta bort en ägare
                    0.Tillbaka till huvudmeny
                    Ange siffran för menyval>>""");

            subChoice = input.nextInt();
            switch (subChoice) {
                case 1:
                    streck();
                    printAll(ownersArray);
                    streck();
                    break;
                case 2:
                    streck();
                    while (true) {
                        print("Ange ägarens andel>>");
                        ownerShip = input.nextInt();
                        if (ownerShip < 0 || ownerShip >= 100) {
                            print("Ange ett värde mellan 1-99! Försök igen!");
                        } else break;
                    }
                    printAll(ownersArray);
                    correctOwnership(ownersArray, ownerShip, false);
                    int[] newOwnersArray = new int[ownersArray.length + 1];
                    for (int i = 0; i < ownersArray.length; i++) {
                        newOwnersArray[i] = ownersArray[i];
                    }
                    newOwnersArray[newOwnersArray.length-1] = ownerShip;
                    printAll(newOwnersArray);
                    streck();
                    break;
                case 3:
                    print("ändra ägare");
                    break;
                case 4:
                    print("Ta bort");
                    break;
                default:
                    println("Gör ett val mellan 0-4");
            }
        } while (subChoice != 0);
    }

    private static int[] correctOwnership(int[] arrayParam, int wantedOwnership, boolean giveAwayOwnership) {
        String giveOrTake = giveAwayOwnership ? "fördelas ut" : "tas fram";
        String infoGiveOrTake = giveAwayOwnership ? "ge till" : "ta ifrån";
        do {
            println("Det är " + wantedOwnership + " procentenheter som behöver " + giveOrTake + " ");
            if (giveAwayOwnership) {
                println("Vilken ägare vill du ge ägarandelar till?");
            } else {
                println("Vilken ägare vill du ta ägarandelar av");
            }
            for (int i = 0; i < arrayParam.length; i++) {
                println("Ägare " + (i + 1) + ":" + arrayParam[i] + "%");
            }

            int ownerIndex;

            while (true) {
                print("Ange siffran för vilken ägare du vill " + infoGiveOrTake + ">>");
                ownerIndex = input.nextInt() - 1;
                if (arrayParam[ownerIndex] == 1 && !giveAwayOwnership) {
                    println("Ägare " + ownerIndex + " har bara 1% kvar. Du kan inte ta bort det sista");
                } else if (ownerIndex > arrayParam.length - 1) {
                    println("Felaktigt val. Prova igen...");
                } else break;
            }
            int correctOwnership;
            while (true) {
                print("Hur många procentenheter vill du " + infoGiveOrTake + " ägare " + (ownerIndex + 1) + "?>>");
                correctOwnership = input.nextInt();
                if (correctOwnership > wantedOwnership) {
                    println("Du kan inte ta mer än " + wantedOwnership + "%");
                } else if (correctOwnership > (arrayParam[ownerIndex] - 1)) {
                    println("Du kan endast ta " + (arrayParam[ownerIndex] - 1) + " procentenheter från ägaren...");
                } else break;
            }
            ownerIndex--;
            int takeAwayOwnership = correctOwnership;
            if (giveAwayOwnership) {
                arrayParam[ownerIndex + 1] += takeAwayOwnership;
            } else {
                arrayParam[ownerIndex + 1] -= takeAwayOwnership;
            }
            wantedOwnership -= takeAwayOwnership;

        } while (wantedOwnership != 0);

        return arrayParam;
    }

    private static void printAll(int[] ownersArray) {
        for (int i = 0; i < ownersArray.length; i++) {
            println("Ägare " + (i + 1) + " äger " + ownersArray[i] + "%\n");
        }
    }

    private static void streck() {
        System.out.println("----------------------------------------------------------------------------------------");
    }

    public static void printMenu1(String[] menuChoices) {
        for (String menuChoice : menuChoices) {
            println(menuChoice);
        }
    }

    private static void print(String msg) {
        System.out.print(msg);
    }

    private static void println(String msg) {
        System.out.println(msg);
    }
}