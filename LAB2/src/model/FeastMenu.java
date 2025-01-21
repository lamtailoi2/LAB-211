package model;

import java.text.DecimalFormat;

public class FeastMenu {

    private String code;
    private String name;
    private int price;
    private String ingredient;

    public FeastMenu() {
    }

    public FeastMenu(String code, String name, int price, String ingredient) {
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
        /*Formatting Price */
        DecimalFormat df = new DecimalFormat("#,##0");
        StringBuilder sb = new StringBuilder();
        for (String i : ingredient.split("#")) {

            sb.append(String.format("%s\n", i.trim()).replaceAll("\"", ""));
        }
        return String.format(
                "Code       : %s\n"
                + "Name       : %s\n"
                + "Price      : %s Vnd\n"
                + "Ingredients:\n%s",
                code,
                name,
                df.format(price),
                sb.toString().trim()
        );
    }

}
