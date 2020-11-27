package Car;

public class Car {
    private int id, year, price;
    private String model;
    private String reg_num;

    public void setPrice(int price) { this.price = price; }
    public void setReg_num(String reg_num) { this.reg_num = reg_num; }
    public void setModel(String model) { this.model = model; }
    public void setId(int id) { this.id = id; }
    public void setYear(int year) { this.year = year; }



    public String getReg_num() { return reg_num; }
    public String getModel() { return model; }
    public int getPrice() { return price; }
    public int getYear() { return year; }
    public int getId() { return id; }


    public String toString(){
        return "\t-----------" + "\t\tID: " + this.id + "\t\tModel: " + this.model + "\t\tRegister number: "+ reg_num +
                "\t\tYear: " + this.year + "\t\tPrice: " + this.price;
    }
}
