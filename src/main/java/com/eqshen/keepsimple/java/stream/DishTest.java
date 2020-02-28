package com.eqshen.keepsimple.java.stream;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DishTest extends BaseTest {

    /**
     *
     */
    @Test
    public void testStream(){
        Dish dish1 = new Dish("番茄炒蛋",true,160, Dish.Type.OTHER);
        Dish dish2 = new Dish("酸辣土豆丝",true,100, Dish.Type.OTHER);
        Dish dish3 = new Dish("烤鱼",false,300, Dish.Type.FISH);
        Dish dish4 = new Dish("爆炒小龙虾",false,200, Dish.Type.MEAT);
        Dish dish5 = new Dish("猪头肉",false,500, Dish.Type.MEAT);

        List<Dish> menu = new ArrayList();
        menu.addAll(Arrays.asList(dish1,dish2,dish3,dish4,dish5));

        List<String> menuName = menu.stream().filter(d->d.getCalories()>150)
                .map(dish -> dish.getName())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(menuName);
        menuName.forEach(System.out::println);

        int allCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println("allCalories:" + allCalories);

    }

    @Test
    public void testMap(){
        String[] words = new String[]{"Hello","World"};
        Arrays.stream(words)
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct().forEach(e->{System.out.println(e);});


        //
        List<Integer> list = Arrays.asList(3,1,2);
        System.out.println(list.stream().map(n -> n * n).collect(Collectors.toList()));

        //
        List<Integer> list2 = Arrays.asList(1,2,3);
        list.stream().flatMap(
                e -> list2.stream().map(it -> new int[]{e,it})
        ).forEach(e->{
            System.out.println("("+e[0]+","+e[1]+")");
        });

        //
        int sum = list.stream().reduce(0,(a,b)->a+b);
        System.out.println("Sum is :" + sum);
        //
        System.out.println(list.stream().sorted(Comparator.comparing(Integer::intValue))
                .collect(Collectors.toList()));

    }

    @Test
    public void testStreamConvert(){
        List<Integer> list = Arrays.asList(1,2,3,5,4,9,7,8);
        list.stream().mapToInt(Integer::intValue).sorted().forEach(System.out::print);
        System.out.println("============");

        Stream.iterate(new int[]{0,1},(int []arr)->{return new int[]{arr[1],arr[0]+ arr[1]};})
                .limit(20).forEach(t->{
            System.out.println(t[0]+"," + t[1]);
        });

        System.out.println("==================2");
        int sum = list.stream().collect(Collectors.reducing(0,Integer::intValue,(a,b)-> a+b));
        System.out.println(sum);
    }

}
