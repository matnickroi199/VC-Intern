import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileWriter;

public class Test4 {

    public static class Point {
        public int x;
        public int y;
        public double d = 0;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return this.x + ", " + this.y;
        }

        @Override
        public boolean equals(Object o) {
            if(o == this) {
                return true;
            }
            if (!(o instanceof Point)) {
                return false;
            }

            Point p = (Point) o;
            return (x == p.x) && (y == p.y);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + this.x;
            hash = 71 * hash + this.y;
            return hash;
        }
    }
    public static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);

    }
    public static void main(String[] args) {
        Random rand = new Random();
        Set<Point> list = new HashSet<>();
        Point[] l = {new Point(800,800), new Point(4000,800), new Point(2400,2400)};

        while (list.size() < 8000){
            int a = rand.nextInt(800) + 400;
            int b = rand.nextInt(800) + 400;
            Point tmp = new Point(a,b);

            tmp.d = distance(tmp, l[0]);
            if(tmp.d <= 400) {
                list.add(tmp);
            }
        }
        while (list.size() < 18000){
            int a = rand.nextInt(1000) + 3500;
            int b = rand.nextInt(1000) + 300;
            Point tmp = new Point(a,b);

            tmp.d = distance(tmp, l[1]);
            if(tmp.d <= 500) {
                list.add(tmp);
            }
        }
        while (list.size() < 30000){
            int a = rand.nextInt(1200) + 1800;
            int b = rand.nextInt(1200) + 1800;
            Point tmp = new Point(a,b);

            tmp.d = distance(tmp, l[2]);
            if(tmp.d <= 600) {
                list.add(tmp);
            }
        }
        try {
            File file = new File("/home/phamthang/IdeaProjects/Test/src/output4.txt");
            FileWriter writer = new FileWriter(file);
            StringBuilder str = new StringBuilder();

            for(Point i : list) {
                str.append(i.toString()).append("\n");
            }
            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
