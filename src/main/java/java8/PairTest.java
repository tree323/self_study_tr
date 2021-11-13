package java8;


import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @ Author     ：tangran
 * @ Date       ：Created in 上午11:34 2021/10/24
 */
public class PairTest {
    public static void main(String[] args) {
        float f1 = 10.0f;
        float f2 = 10f;
        System.out.println(f1 == f2);

        f1 = 0.1f;
        for (int i = 0; i < 11; i++) {
            f1 += 0.1f;
        }
        f2 = 0.1f * 11;
        System.out.println(f1 == f2);

        BigDecimal bg1 = new BigDecimal("0.0");
        BigDecimal pointOne = new BigDecimal("0.1");
        for (int i = 0; i < 11; i++) {
            bg1 = bg1.add(pointOne);
        }
        BigDecimal bg2 = new BigDecimal("0.1");
        BigDecimal eleven = new BigDecimal("11");
        bg2 = bg2.multiply(eleven);
        System.out.println(bg1.compareTo(bg2) == 0);

        BigInteger i1 = new BigInteger("123456789");
        BigInteger i2 = new BigInteger("123456789123456789");
        BigInteger sum = i1.add(i2);
        System.out.println(sum.toString());

        BigDecimal d1 = new BigDecimal("123.456");
        BigDecimal d2 = new BigDecimal("123.456000");
        System.out.println(d1.equals(d2));
        System.out.println(d1.equals(d2.stripTrailingZeros()));
        System.out.println(d1.compareTo(d2));

    }
}
