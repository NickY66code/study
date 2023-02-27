package stream.pojo;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/9 11:27
 */
public class Person {

    private String name;

    private Integer age;

    private Double salary;

    private String gender;
    private String place;

    public String getName() {
        return name;
    }

    public Person(String name, Integer age, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Person(String name, Integer age, Double salary, String gender, String place) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
        this.place = place;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                ", place='" + place + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
