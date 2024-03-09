package View;
import Repository.IRepository;
import Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Faina;
import Model.Sare;
import Model.Zahar;
import Controller.Controller;
public class UI {
    private Controller controller;
    public UI(Controller controller)
    {
        this.controller=controller;
    }
    public void PrintMenu()
    {
        System.out.println("1.ADD");
        System.out.println("2.PRINT THE PRODUCTS WITH A PRICE HIGHER THAN 20RON/KG");
        System.out.println("3.REMOVE A PRODUCT");
        System.out.println("0.EXIT");
    }
    public void run()
    {
        int choice=0;
        Scanner sc=new Scanner(System.in);
        do {
            PrintMenu();
            choice=sc.nextInt();
            sc.nextLine();
            switch (choice)
            {default:
                try {
                    throw new IllegalArgumentException("Invalid choice. Please enter 0, 1, 2 or 3.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 0:
                break;
            case 1:

                String option;
                int price;
                System.out.println("a. Flour");
                System.out.println("b. Sugar");
                System.out.println("c. Salt");
                option = sc.nextLine();// Read the user's choice

                switch (option) {
                    case "a":
                        System.out.print("Enter the price: ");
                        price = sc.nextInt();
                        sc.nextLine(); // Consume the newline character left in the buffer
                        controller.addToRepo(new Faina(price));
                        break;
                    case "b":
                        System.out.print("Enter the price: ");
                        price = sc.nextInt();
                        sc.nextLine(); // Consume the newline character left in the buffer
                        controller.addToRepo(new Zahar(price));
                        break;
                    case "c":
                         System.out.print("Enter the price:");
                         price=sc.nextInt();
                         sc.nextLine();
                         controller.addToRepo(new Sare(price));
                         break;
                    default:
                        System.out.println("Invalid choice.");
                }
                break;

                case 2:
                List<Item> result = controller.filterrepo(20);
                for(Item item : result)
                {
                    System.out.println(item.toString());
                }
                System.out.println("Products with a higher price than 20 ron/kg");
                break;
                case 3:
                    String option2;
                    int price2;
                    System.out.println("a. Flour");
                    System.out.println("b. Sugar");
                    System.out.println("c. Salt");
                    option2 = sc.nextLine(); // Read the user's choice

                    switch (option2) {
                        case "a":
                            System.out.print("Enter the price: ");
                            price2 = sc.nextInt();
                            sc.nextLine();
                            controller.removeFromRepo(new Faina(price2));
                            break;
                        case "b":
                            System.out.print("Enter the price: ");
                            price2 = sc.nextInt();
                            sc.nextLine();
                            controller.removeFromRepo(new Zahar(price2));
                            break;
                        case "c":
                            System.out.print("Enter the price: ");
                            price2=sc.nextInt();
                            sc.nextLine();
                            controller.removeFromRepo(new Sare(price2));
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }






            }
        }while(choice!=0);
    }


}
