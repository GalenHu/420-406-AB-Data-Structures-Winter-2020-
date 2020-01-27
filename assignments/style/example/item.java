
public class item  {
    long i;
    String first;
    String last;
    item(String line) {
        String[] s = line.split(",");
        i = Long.parseLong(s[0]);
        String[] s2 = s[1].split("_");
        first = s2[0];
        last = s2[1];
        g = Double.parseDouble(s[2]);
    }
    double g;

    public int compareTo(item o) {
        return (int) (g - o.g);
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %f", i, first, last, g);
    }
}
