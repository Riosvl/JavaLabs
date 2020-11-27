import Car.Car;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class
 */
public class Main {
    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input count of car: ");
        int size = in.nextInt();
        Car[] car = new Car[size];
        CarArray(car);
        for (Car value : car){
            System.out.println(value.toString());
        }
        SetCarModelInfo(car, in);
        SetCarYearInfo(car, in);
        SetCarPriceInfo(car, in);
        in.close();
    }

    /**
     *
     * @param car Passing an array of objects
     * @param in Passing Scanner to scan data
     *
     *           With this method user can input data from keyboard
     */
    static void ScanCar(Car[] car, Scanner in) {
        for (int i = 0; i < car.length; i++) {
            car[i] = new Car();
            System.out.println("----------------------");
            System.out.print("Input id of car: ");
            car[i].setId(in.nextInt());
            in.nextLine();
            System.out.print("Input model of car: ");
            car[i].setModel(in.nextLine());
            System.out.print("Input price of car: ");
            car[i].setPrice(in.nextInt());
            System.out.print("Input register number of car: ");
            car[i].setReg_num(in.next());
            System.out.print("Input manufacture date of car: ");
            car[i].setYear(in.nextInt());
        }
    }

    /**
     * Prints random data about cars
     */
    static void CarArray(Car[] car) {
        Random random = new Random();
        String[] mas = {"C63 AMG", "ZAZ 968", "Camry", "Scenic", "Megan", "M5 F90", "Model S", "Lancer",
                "Wrangler", "RS6 C7"};
        String[] reg = {"ВС", "АА", "ВП", "СВ", "ВЛ", "ВК", "ВН", "АН", "ВР"};
        for (int i = 0; i < car.length; i++) {
            car[i] = new Car();
            car[i].setId(random.nextInt(8999)+1000);
            car[i].setModel(mas[random.nextInt(mas.length)]);
            car[i].setPrice(random.nextInt(50000) + 5000);
            car[i].setReg_num(reg[random.nextInt(reg.length)] + (random.nextInt(8999) + 1000) + reg[random.nextInt(reg.length)]);
            car[i].setYear(random.nextInt(30) + 1990);
        }
    }


    static void Print(Car value){
        System.out.println(value.toString());
    }
    /**
     * Find list of cars given model
     */
    static void FindCarModelInfo(Car[] car, String mod){
        for (Car value : car){
            if (value.getModel().equals(mod)){
                Print(value);
            }
        }
    }
    /**
     * Set list of cars given model
     */
    static void SetCarModelInfo(Car[] car, Scanner in){
        System.out.print("\nInput car model about which you wanna get info: ");
        in.nextLine();
        String mod = in. nextLine();
        FindCarModelInfo(car, mod);
    }



    /**
     *  Find list of cars given model which are using more than 'n' years
     */
    static void FindCarYearInfo(Car[] car, String mod, int year){
        for (Car value : car){
            if(value.getModel().equals(mod) && (2020 - value.getYear()) > year){
                Print(value);
            }
        }
    }
    /**
     *  Set list of cars given model which are using more than 'n' years
     */
    static void SetCarYearInfo(Car[] car, Scanner in){
        System.out.print("\nInput car model: ");
        String mod = in.nextLine();
        System.out.print("Input year of using car: ");
        int year = in.nextInt();
        FindCarYearInfo(car, mod, year);
    }



    /**
     * Find list of cars given manufacture year, with price bigger than given price
     */
    static void FindCarPriceInfo(Car[] car, int year, int price){
        for (Car value : car){
            if (value.getYear() == year && value.getPrice() > price){
                Print(value);
            }
        }
    }
    /**
     * Set list of cars given manufacture year, with price bigger than given price
     */
    static void SetCarPriceInfo(Car[] car, Scanner in){
        System.out.print("\nInput manufacture date of car: ");
        int year = in.nextInt();
        System.out.print("Input car price: ");
        int price = in.nextInt();
        FindCarPriceInfo(car, year, price);
    }
}