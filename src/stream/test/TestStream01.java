package stream.test;

import stream.pojo.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/9 11:29
 */
public class TestStream01 {

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

    /**
     * 筛选员工中已满18周岁的人，并形成新的集合
     * @思路
     * List<Person> list = new ArrayList<Person>();
     * for(Person person : personList) {
     *     if(person.getAge() >= 18) {
     *          list.add(person);
     *     }
     * }
     */
    public static void test1(){
        List<Person> list=initList();

        list.stream().forEach(x->System.out.println("old:"+x.toString()));

        List<Person> newList=list.stream().filter(x -> x.getAge()>=18).collect(Collectors.toList());

        newList.stream().forEach(x-> System.out.println("new :"+x.toString()));
    }

    public static void test2(){
        List<Map<String, Object>> list=new ArrayList<>();

        Map<String,Object> map1=new HashMap<>();
        map1.put("name","noah");
        map1.put("id",1);
        map1.put("age",16);

        Map<String,Object> map2=new HashMap<>();
        map2.put("name","curry");
        map2.put("id",2);
        map2.put("age",19);

        Map<String,Object> map3=new HashMap<>();
        map3.put("name","curry");
        map3.put("id",3);
        map3.put("age",22);

        list.add(map1);
        list.add(map2);
        list.add(map3);

        //Stream
        List<Map<String,Object>> streamList=list.stream().filter(x->x.get("name").toString().equals("curry"))
                .collect(Collectors.toList());
        streamList.forEach(x-> System.out.println(x));

        //old
        List<Map<String,Object>> retList=new ArrayList<>();
        for (Map<String,Object> map:list){
            if (map.get("name").equals("curry")){
                Map<String,Object> hashmap=new HashMap<>();
                hashmap.putAll(map);
                retList.add(hashmap);
            }
        }
        for (Map<String,Object> map:retList){
            Iterator<Map.Entry<String,Object>> iterator=map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,Object> next=iterator.next();
                System.out.println(next.getKey()+" : "+next.getValue());
            }
        }
    }

    /**
     * 获取String集合中最长的元素
     * @思路
     * List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "sunliu");
     * String max = "";
     * int length = 0;
     * int tempLength = 0;
     * for(String str : list) {
     *     tempLength = str.length();
     *     if(tempLength > length) {
     *         length  = str.length();
     *         max = str;
     *      }
     * }
     * @return zhangsan
     */
    public static void test3(){
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "sunliu");
        Comparator<? super String> comparator=Comparator.comparing(String::length);
        String max= String.valueOf(list.stream().max(comparator));
        System.out.println(max);
    }

    /**
     * 获取数值最大值
     */
    public static void test4(){
        List<Integer> list = Arrays.asList(1, 17, 27, 7);
        Integer max= list.stream().max(Integer::compareTo).get();

        //自定义排序
        Integer max2=list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }).get();

        System.out.println(max.toString());
        System.out.println(max2.toString());
    }

    public static void test5(){
        List<Person> list=initList();
        Optional<Person> res=list.stream().max(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge().compareTo(o2.getAge());
            }
        });

        System.out.println(res.toString());
    }

    public static void test6(){
        List<Integer> list=Arrays.asList(15,6,4,6,73);
        System.out.println(list.stream().filter(x->x>10).count());
    }

    public static void test7(){
        List<String> list=Arrays.asList("zhangshang","noah","james","curry");

        //@return
        //zhangshang
        //noah
        //james
        //curry
        //list.stream().forEach(x->x.toUpperCase());

        //@return
        //ZHANGSHANG
        //NOAH
        //JAMES
        //CURRY
        List<String> collect=list.stream().map(x->x.toUpperCase()).collect(Collectors.toList());
        List<String> collect2=list.stream().map(String::toUpperCase).collect(Collectors.toList());


        list.stream().forEach(x-> System.out.println(x));
        collect.stream().forEach(x-> System.out.println(x));
        collect2.stream().forEach(x-> System.out.println(x));
    }

    /**
     * 整数数组每个元素+3
     * @思路
     * List<Integer> list = Arrays.asList(1, 17, 27, 7);
    List<Integer> list2 = new ArrayList<Integer>();
    for(Integer num : list) {
    list2.add(num + 3);
    }
     @return [4, 20, 30, 10]
     */
    public static void test8() {
        List<Integer> list = Arrays.asList(1, 17, 27, 7);
        List<Integer> collect = list.stream().map(x -> x + 3).collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void test9(){
        List<Person> list=initList();

        List<Person> collect=list.stream().map(x->{
            x.setSalary(x.getSalary()+2000);
            return x;
        }).collect(Collectors.toList());

        collect.stream().forEach(System.out::println);
    }

    /**
     * 将两个字符数组合并成一个新的字符数组
     * @return [[a, d, f, g, w, t, s, s, c, w, t]]
     */
    public static void test10(){
        List<String> list=Arrays.asList("a,d,f,g,w,t","s,s,c,w,t");

        List<String> collect=list.stream().flatMap(x->{
            String[] array=x.split(",");
            Stream<String> stream=Arrays.stream(array);
            return stream;
        }).collect(Collectors.toList());

        System.out.println(collect);
    }

    /**
     * 求Integer集合的元素之和、乘积和最大值
     *
     */
    private static void test11() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        //求和
        Optional<Integer> reduce = list.stream().reduce((x,y) -> x+ y);
        System.out.println("求和:"+reduce);
        //求积
        Optional<Integer> reduce2 = list.stream().reduce((x,y) -> x * y);
        System.out.println("求积:"+reduce2);
        //求最大值
        Optional<Integer> reduce3 = list.stream().reduce((x,y) -> x>y?x:y);
        System.out.println("求最大值:"+reduce3);
    }

    /**
     * 求所有员工的工资之和和最高工资
     */
    public static void test12(){
        List<Person> list=initList();

        Optional<Double> reduceSum = list.stream().map(Person::getSalary).reduce(Double::sum);
        Optional<Double> reduceMax= list.stream().map(Person::getSalary).reduce(Double::max);

        System.out.println(reduceSum);
        System.out.println(reduceMax);
    }

    /**
     * 取出大于18岁的员工转为map
     *
     */
    public static void test13(){
        List<Person> list=initList();
        Map<String,Person> map=list.stream().filter(x->x.getAge()>16).collect(Collectors.toMap(Person::getName,y->y));

        Iterator<Map.Entry<String,Person>> iterator=map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Person> next=iterator.next();
            System.out.println(next.getKey()+" : "+next.getValue());
        }
    }

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //test6();
        //test7();
        //test8();
        //test9();
        //test10();
        //test11();
        //test12();
        test13();

    }
}
