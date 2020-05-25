package org.lebedeva;

import org.assertj.core.data.Index;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;

import static java.time.LocalDate.ofYearDay;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class Tests {

    @Test
    public void test1() {
        Optional<String> givenOptional = Optional.of("something");
        assertThat(givenOptional)
                .isPresent()
                .hasValue("something");
    }

    @Test
    public void test2() {
        LocalDate givenLocalDate = LocalDate.of(2016, 7, 8);
        LocalDate todayDate = LocalDate.now();

        assertThat(givenLocalDate)
                .isBefore(LocalDate.of(2020, 7, 8))
                .isAfterOrEqualTo(LocalDate.of(1989, 7, 8));

        assertThat(todayDate)
                .isAfter(LocalDate.of(1989, 7, 8))
                .isToday();

        LocalDateTime givenLocalDateT = LocalDateTime.of(2016, 7, 8, 12, 0);
        assertThat(givenLocalDate)
                .isBefore(LocalDate.from(LocalDateTime.of(2020, 7, 8, 11, 2)));

        LocalTime givenLocalTime = LocalTime.of(12, 15);
        assertThat(givenLocalTime)
                .isAfter(LocalTime.of(1, 0))
                .hasSameHourAs(LocalTime.of(12, 0));
    }

    @Test
    public void test3() {
        Predicate<String> predicate = s -> s.length() > 4;
        assertThat(predicate)
                .accepts("aaaaa", "bbbbb")
                .rejects("a", "b")
                .acceptsAll(asList("aaaaa", "bbbbb"))
                .rejectsAll(asList("a", "b"));
    }

    @Test
    public void test4() {
        //FlatExtracting - это специальный служебный метод, который использует лямбды Java 8 для извлечения свойств из элементов Iterable _. _

        List<LocalDate> givenList = asList(ofYearDay(2016, 5), ofYearDay(2015, 6));
        assertThat(givenList)
                .flatExtracting(LocalDate::getYear)
                .contains(2015);

        assertThat(givenList)
                .flatExtracting(LocalDate::isLeapYear)
                .contains(true);

        assertThat(givenList)
                .flatExtracting(Object::getClass)
                .contains(LocalDate.class);

        assertThat(givenList)
                .flatExtracting(LocalDate::getYear, LocalDate::getDayOfMonth)
                .contains(2015, 6);
    }

    @Test
    public void test5() {
        //Satisfies позволяет вам быстро проверить, удовлетворяет ли объект всем предоставленным утверждениям.

        String givenString = "someString";

        assertThat(givenString)
                .satisfies(s -> {
                    assertThat(s).isNotEmpty();
                    assertThat(s).hasSize(10);
                });
    }

    @Test
    public void test6() {
        //HasOnlyOneElement позволяет проверить, содержит ли экземпляр Iterable только один элемент, удовлетворяющий предоставленным утверждениям.
        List<String> givenList = Arrays.asList("");

        assertThat(givenList)
                .hasOnlyOneElementSatisfying(s -> assertThat(s).isEmpty());

        String emptyString = "";
        assertThat(emptyString)
                .matches(String::isEmpty);
    }

    @Test
    public void test7() {
        Dog fido = new Dog("Fido", 5.25f);

        Dog fidosClone = new Dog("Fido", 5.25f);

        assertThat(fido)
                .isNotEqualTo(fidosClone);// isEqualTo () сравнивает ссылки на объекты
        assertThat(fido)
                .isEqualToComparingFieldByFieldRecursively(fidosClone);

        assertThat("".isEmpty()).isTrue();
    }

    @Test
    public void test8() {
        List<String> list = Arrays.asList("1", "2", "3");

        assertThat(list)
                .isNotEmpty()
                .contains("1")
                .doesNotContainNull()
                .containsSequence("2", "3");

        assertThat(5.1).isEqualTo(5, withPrecision(1d));

//        assertThat(someCharacter)
//                .isNotEqualTo('a')
//                .inUnicode()
//                .isGreaterThanOrEqualTo('b')
//                .isLowerCase();

//        assertThat(someFile)
//                .exists()
//                .isFile()
//                .canRead()
//                .canWrite();

//        assertThat(map)
//                .isNotEmpty()
//                .containsKey(2)
//                .doesNotContainKeys(10)
//                .contains(entry(2, "a"));
    }

    @Test
    public void test9() {
        assertThat(Runnable.class).isInterface();
        assertThat(Exception.class).isAssignableFrom(NoSuchElementException.class);

        //assertThat(ex).hasNoCause().hasMessageEndingWith("c");  Throwable
    }

    @Test
    public void test10() {
        String name = "I am Mkyong!";

        assertThat(name)
                .as("if failed display this msg!")
                .isEqualTo("I am Mkyong!")
                .isEqualToIgnoringCase("I AM mkyong!")
                .startsWith("I")
                .endsWith("!")
                .containsIgnoringCase("mkyong");
    }

    @Test
    public void test11() {
        List<String> list = Arrays.asList("Java", "Rust", "Clojure");

        assertThat(list)
                .hasSize(3)
                .contains("Java", "Clojure")
                .contains("Java", Index.atIndex(0))
                .contains("Rust", Index.atIndex(1))
                .contains("Clojure", Index.atIndex(2))
                .doesNotContain("Node JS");
    }

    @Test
    public void test12() {
        assertThatThrownBy(() -> divide(1, 0))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("zero")
                .hasMessage("/ by zero");

        assertThatThrownBy(() -> {
            List<String> list = Arrays.asList("one", "two");
            list.get(2);
        })
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("2");

    }

    int divide(int input, int divide) {
        return input / divide;
    }


    public class Dog {
        private String name;
        private Float weight;

        public Dog(String name, Float weight) {
            this.name = name;
            this.weight = weight;
        }
    }

}
