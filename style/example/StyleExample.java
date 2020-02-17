import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class StyleExample {

    public static void main(String[] args) throws FileNotFoundException {

        FileReader in = new FileReader("grades.txt");
        Scanner s = new Scanner(in);

        int n = s.nextInt(); s.nextLine();
        item[] arr = new item[n];
        int i=0;
        while(s.hasNext()) { String l; item i2;
            l= s.nextLine();
            i2 = new item(l);
            if(i2.g < 60)
                System.out.println(i2);
            arr[i++] = i2;
        }

        if(i != n)
            return;

        double s2 = 0;
        for(item i2 : arr)
            s2 += i2.g;

        double a = s2 / n;

        Arrays.sort(arr);

        double m;
        if(n % 2 == 0)
            m = (arr[n / 2].g + arr[n / 2 - 1].g) / 2;
        else
            m = arr[n / 2].g;

        System.out.println(a);
        System.out.println(m);
    }

}
