package stream.test;

import stream.pojo.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/9 17:09
 */
public class TestStream02 {
    public static List<Person> initList(){
        List<Person> list=new ArrayList<>();
        Person p1=new Person("noah",16,5000.0);
        Person p2=new Person("james",18,9000.0);
        Person p3=new Person("kobe",22,11000.0);

        list.add(p1);
        list.add(p2);
        list.add(p3);

        return list;
    }

    public static List<Person> initList2(){
        List<Person> list=new ArrayList<>();
        Person p1=new Person("noah",16,5000.0,"male","北京");
        Person p2=new Person("james",18,9000.0,"female","上海");
        Person p3=new Person("haha",19,91000.0,"male","上海");
        Person p4=new Person("vivi",15,19000.0,"female","北京");
        Person p5=new Person("kk",99,9200.0,"male","广州");
        Person p6=new Person("kobe",22,11000.0,"female","广州");

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);

        return list;
    }

    public static void test1(){
        List<Person> list=initList();
        //统计员工人数
        System.out.println(list.stream().collect(Collectors.counting()));
        //求平均工资
        System.out.println(list.stream().collect(Collectors.averagingDouble(Person::getSalary)));
        //求最高工资
        System.out.println(list.stream().map(Person::getSalary).collect(Collectors.maxBy(Double::compare)));
        //求工资之和
        System.out.println(list.stream().collect(Collectors.summingDouble(Person::getSalary)));
        //一次性统计所有信息
        System.out.println(list.stream().collect(Collectors.summarizingDouble(Person::getSalary)));
    }

    public static void test2(){
        List<Person> list=initList2();

        //将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> collect = list.stream().collect(Collectors.partitioningBy(x -> x.getSalary() >= 8000));
        // 将员工按性别分组
        Map<String, List<Person>> collect1 = list.stream().collect(Collectors.groupingBy(Person::getGender));
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> collect2 = list.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.groupingBy(Person::getPlace)));

        System.out.println(collect);
        System.out.println(collect1);
        System.out.println(collect2);
    }

    public static void test3(){
        List<Person> list=initList2();
        System.out.println(list.stream().map(Person::getName).collect(Collectors.joining(",")));
    }

    public static void test4(){
        List<Person> list=initList2();
        // 按工资升序排序（自然排序）
        System.out.println(list.stream()
                .sorted(Comparator.comparing(Person::getSalary))
                .map(Person::getName).collect(Collectors.toList()));
        // 按工资倒序排序
        System.out.println(list.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName));
        // 先按工资再按年龄升序排序
        System.out.println(list.stream().sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge))
                .map(Person::getName));
        // 先按工资再按年龄自定义排序（降序）
        System.out.println(list.stream().sorted((p1, p2) -> {
            if (p1.getSalary().equals(p2.getSalary())) {
                return p2.getAge() - p1.getAge();
            } else {
                return (int) (p2.getSalary() - p1.getSalary());
            }
        }).collect(Collectors.toList()));
    }

    public static void test5(){
        String[] arr1 = { "a", "b", "c", "d" };
        String[] arr2 = { "d", "e", "f", "g" };

        Stream<String> arr11 = Stream.of(arr1);
        Stream<String> arr12 = Stream.of(arr2);

        // concat:合并两个流 distinct：去重
        List<String> collect = Stream.concat(arr11, arr12).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> collect1 = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(collect1);
        System.out.println(collect2);
    }

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }
}
