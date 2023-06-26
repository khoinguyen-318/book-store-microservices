package com.bookstore.order.query.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class OrderQueryServiceTest {
    @Test
    void test(){
        List<Object1> list1 = Arrays.asList(
                new Object1("1"),
                new Object1("2"),
                new Object1("3"),
                new Object1("4"),
                new Object1("5")
        );
        List<Object2> list2 = Arrays.asList(
                new Object2("1","Name-1"),
                new Object2("2","Name-2"),
                new Object2("3","Name-3"),
                new Object2("4","Name-4"),
                new Object2("5","Name-5")
        );
        List<Object3> list3 = Arrays.asList(
                new Object3("1","Age-1"),
                new Object3("2","Age-2"),
                new Object3("3","Age-3"),
                new Object3("4","Age-4"),
                new Object3("5","Age-5")
        );
        final List<?> combineList = list1.stream()
                .flatMap(e1 -> list2.stream().filter(e2 -> e2.id.equals(e1.id)))
                .flatMap(e3 -> list3.stream().filter(e4 -> e4.id.equals(e3.id)))
                .toList();
        combineList.forEach(System.out::println);

    }
    class Object1 {
        public String id;

        public Object1(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Object1{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
    class Object2 {
        public String id;
        public String name;


        public Object2(String id,String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Object2{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    class Object3 {
        public String id;
        public String age;


        public Object3(String id,String age) {
            this.id = id;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Object3{" +
                    "id='" + id + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }
}
