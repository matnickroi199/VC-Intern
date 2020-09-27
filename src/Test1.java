import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Test1 {
    public static void main(String[] args) {
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        final int DUPLICATE = 50000;
        final int SIZE = 200000;
        Random rand = new Random();

        while(s1.size() < DUPLICATE) {
            int i = rand.nextInt();
            s1.add(i);
            s2.add(i);
        }
        while(s1.size() < SIZE) {
            s1.add(rand.nextInt());
        }
        while(s2.size() < SIZE) {
            s2.add(rand.nextInt());
        }
        System.out.print("Set 1: ");
        System.out.print(s1.size());
        System.out.print("\nSet 2: ");
        System.out.print(s2.size());

        Set<Integer> union = new HashSet<>(s1);
        union.addAll(s2);
        System.out.print("\nUnion: ");
        System.out.print(union.size());

        Set<Integer> intersection = new HashSet<>(s1);
        intersection.retainAll(s2);
        System.out.print("\nIntersection: ");
        System.out.print(intersection.size());

        System.out.print("\nFinished.");
    }
}
