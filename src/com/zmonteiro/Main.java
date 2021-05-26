package com.zmonteiro;

import com.zmonteiro.entities.Products;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        String path = "/home/me/inp.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            List<Products> productsList = new ArrayList<>();
            String line = br.readLine();
            while(line != null){
                String [] fields = line.split(",");
                productsList.add(new Products(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            double avg = productsList.stream()
                    .map(p -> p.getPrice())
                    .reduce(0.0, (x, y) -> x + y) / productsList.size();

            System.out.println("Average price: " + String.format("%.2f", avg));

            Comparator<String> comp = (st1, st2) -> st1.toUpperCase(Locale.ROOT).compareTo(st2.toUpperCase(Locale.ROOT));

            List<String> productNames = productsList.stream()
                    .filter(p -> p.getPrice() < avg)
                    .map(p -> p.getName())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            productNames.forEach(System.out::println);


        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
    }

    }
}
