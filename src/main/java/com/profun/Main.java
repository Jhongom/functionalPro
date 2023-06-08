package com.profun;

import com.profun.clases.persona;
import com.profun.clases.producto;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        persona p1 = new persona(1,"Jose riascos", 10);
        persona p2 = new persona(2,"Kevin Diaz", 17);
        persona p3 = new persona(3,"Jhon Gomez", 30);
        persona p4 = new persona(4,"Federico Monroy", 25);

        producto pr1 = new producto(1,"Lavadora", 2000);
        producto pr2 = new producto(2,"TV", 230);
        producto pr3 = new producto(3,"TV", 3000);
        producto pr4 = new producto(4,"Computador", 5000);


        List<persona> listaPer = Arrays.asList(p1,p2,p3,p4);
        List<producto> listaProd = Arrays.asList(pr1,pr2,pr3,pr4);

        /*  Imperativa
        for(int i = 0 ; i < listaPer.size() ; i++){
            System.out.println(listaPer.get(i));
        }*/


        /* 2014
        for (persona p : listaPer){
            System.out.println(p);
        }
        */

        //ForEach con parametro p que recorre, y luego que quiero hacer con ese parametro
        //listaPer.forEach(p -> System.out.println(p));


        //Si el parametro antes de -> es igual a lo que envio despues del ->
        //se puede resumir con el ::
        //listaPer.forEach(System.out::println);



        // 1 - FILTER (PARAM : PREDICATE)

        /*  Se crea una lista para tener un resultado con el toList. del listado
            de personas la api stream deja filtrar, p es la variable que recorre y
            a esta le digo que tome el dato de edad y hago la comparacion
            luego la imprimo
        */
        List<persona> filtrado = listaPer.stream().filter(p -> p.getEdad() > 18).toList();

        //System.out.println(filtrado);


        // 2 - MAP

        /*  Se crea una lista, pero esta lista como enteros porque quiero convertir
            los datos de esa lista a enteros, esto con el stream.map, obteniendo la edad
            del parametro p que recorre la lista, y la transformo con toList

         */

        //List<Integer> comoNumeros = listaPer.stream().map(p -> p.getEdad()).toList();
        //System.out.println("comoNumeros = " + comoNumeros);

        List<String> todosCoder = listaPer.stream().map(p -> "Coder " + p.getNombre()).toList();
        //System.out.println("Cambio de nombre = " + todosCoder);


        // 3 - SORTED -> Ordenar a traves de una coleccion

        /*  Se realiza un comparador para comparar los objetos de acuerdo a lo que quereemos


        */

        Comparator<persona> ordenPorNombre = (persona per1, persona per2) -> per1.getNombre().compareTo(per2.getNombre());
        List<persona> ordenAsc =listaPer.stream().sorted(ordenPorNombre).toList();

        //System.out.println("ordenAsc = " + ordenAsc);

        //Orden descendente

        Comparator<persona> ordenDescen = (persona per1, persona per2) -> per2.getNombre().compareTo(per1.getNombre());
        List<persona> descen = listaPer.stream().sorted(ordenDescen).toList();

        //System.out.println("ordenDescen = " + descen);


        // 4 - Match -> AnyMatch = match con al menos un elemento

        Predicate<persona> predicadoRepetido = persona -> persona.getNombre().startsWith("k");

        boolean hayUno = listaPer.stream().anyMatch(predicadoRepetido);
        //System.out.println("hay al menos Uno = " + hayUno);

        boolean todosMatch = listaPer.stream().allMatch(p -> p.getNombre().startsWith("J"));
        //System.out.println("todosMatch = " + todosMatch); //Sale falso porque no todos empiezan con j
        
        boolean todosconK = listaPer.stream().noneMatch(predicadoRepetido);
        //System.out.println("¿todosEmpiezanConK? = " + todosconK);


        // 5 - LIMIT/SKIP  -> sirve para paginacion

        List<persona> listaSkipeada = listaPer.stream().skip(2).toList();//se salta los 2 primeros datos
        //System.out.println("listaSkipeada = " + listaSkipeada);

        List<persona> limita = listaPer.stream().limit(2).toList();
        //System.out.println("limita la lista= " + limita);

        //Combinacion de skip y limit para realizar paginacion

        int numeroPagina = 0;
        int tamanoPagina = 2;

        List<persona> tomandoValo = listaPer.stream().skip(numeroPagina * tamanoPagina).
                                        limit(tamanoPagina).toList();
        //System.out.println("tomando 2 valores por skip = " + tomandoValo);

        // 6 - COLLECTORS

        /*
        *       map es integer porque Precio es tipo INTEGER
        */
        Map<Integer, List<producto>> Listasdo = listaProd.stream().filter(p -> p.getPrecio() > 2500).
                                                    collect(Collectors.groupingBy(producto::getPrecio));
        //System.out.println("Listasdo por precio = " + Listasdo);

        /*  El collectors esta contando los nombres que aaprecen, en este caso TV sale 2 veces
         *  y se retorna un String que es el nombre y el long que es el numero de veces que
            aparece cada nombre
         */
        Map<String, Long> porNombre = listaProd.stream().
                            collect(Collectors.groupingBy( producto::getNombre, Collectors.counting()));
        //System.out.println("porNombre = " + porNombre);

        //Sumatoria de precios por producto

        Map<String, Integer> agrupacionPrecio = listaProd.stream().
                collect(Collectors.groupingBy(producto::getNombre, Collectors.summingInt(producto::getPrecio)));
        //System.out.println("agrupacionPrecio = " + agrupacionPrecio);


        // Suma y resumen

        //IntSummary porque el precio esta como int
        IntSummaryStatistics estadistica =   listaProd.stream().
                collect(Collectors.summarizingInt(producto::getPrecio));
        //System.out.println("estadistica = " + estadistica);


        // 7 - Reduce

        //Tener solo la suma de los producto


        Optional<Integer> precio= listaProd.stream().map(producto::getPrecio).reduce((a,b) -> a+b);
        System.out.println("precio = " + precio.get());



    }
}