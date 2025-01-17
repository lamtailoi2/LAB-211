package model;

import java.text.DecimalFormat;

public class Menu {

    private String code;
    private String name;
    private int price;
    private String ingredient;

    public Menu() {
    }

    public Menu(String code, String name, int price, String ingredient) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.ingredient = ingredient;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        String temp = "";
        /*Formatting Price */
        DecimalFormat df = new DecimalFormat("#,##0");

        for (String i : ingredient.split("#")) {
            temp = temp + String.format("%s\n", i.trim());
        }
        return String.format(
                "Code       : %s\n"
                + "Name       : %s\n"
                + "Price      : %s Vnd\n"
                + "Ingredients:\n%s",
                code,
                name,
                df.format(price),
                temp.trim()
        );
    }

}
