
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int totalOwnerPercentage = 0;
    static int ownerShip = 0;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
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

        String[] employeesArray = new String[1];


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
                    ownersArray = addNewOwner(input, ownersArray);
                    print("Ny lista av ägare");
                    printAll(ownersArray);
                    streck();
                    break;
                case 3:
                    print("ändra ägare");
                    break;
                case 4:
                    print("Ta bort");
                    break;
                default:
                    print("Gör ett val mellan 0-4");
            }
        } while (subChoice != 0);
    }

    private static int[] addNewOwner(Scanner input, int[] ownersArray) {
        int[] newOwnersArray = new int[ownersArray.length + 1];
        int shareToTake;
        int ownerToReduce;


        print("Ange ny ägares andel>>");
        ownerShip = input.nextInt();

        if (ownerShip <= 0 || ownerShip >= 100) {
            println("Ange ett värde från 1-99%!");
            return ownersArray;
        }

        int totalReduction = 0;

        while (totalReduction < ownerShip) {
            printAll(ownersArray);
            print("Vilken ägare vill du ta ifrån?>>");
            ownerToReduce = input.nextInt() - 1;

            if (ownerToReduce < 0 || ownerToReduce >= ownersArray.length) {
                print("Välj en av ägarna!");
                continue;
            }

            print("Hur mycket % vill du ta från ägare " + (ownerToReduce + 1) + " ?>>");
            shareToTake = input.nextInt();


            if (shareToTake <= 0) {
                print("Minst 1%!");

            } else if (shareToTake > ownersArray[ownerToReduce]) {
                print("Max " + ownersArray[ownerToReduce] + "%!");

            } else if (totalReduction + shareToTake > ownerShip) {
                println("Du behöver bara ta " + (ownerShip - totalReduction) + "% mer.");

            } else {
                ownersArray[ownerToReduce] -= shareToTake;
                totalReduction += shareToTake;
            }
        }
        for (int i = 0; i < ownersArray.length; i++) {
            newOwnersArray[i] = ownersArray[i];
        }
        newOwnersArray[ownersArray.length] = ownerShip;

        return newOwnersArray;
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