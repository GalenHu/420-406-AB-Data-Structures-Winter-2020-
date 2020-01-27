import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class StyleExample {

    public static void main(String[] args) throws FileNotFoundException {

        FileReader in = new FileReader("grades.txt");
        Scanner scanner = new Scanner(in);

        Grade grades = new Grade[numberOfStudent]
        int current = scanner.nextInt(); scanner.nextLine();
            grades[] arr = new grades[][current];
        int numberOfStudent=0;
        while(scanner.hasNext()) { String l; grades i2;
            l= scanner.nextLine();
            i2 = new grades(l);
            if(i2.grade < 60)
                System.out.println(i2);
            arr[numberOfStudent++] = i2;
        }

        if(numberOfStudent != current)
            return;

        double s2 = 0;
        for(grades i2 : arr)
            s2 += i2.grade;

        double a = s2 / current;

        Arrays.sort(arr);

        double m;
        if(current % 2 == 0)
            m = (arr[current / 2].getGrade() + arr[current / 2 - 1].getGrade()) / 2;
        else
            m = arr[current / 2].getGrade();

        System.out.println(a);
        System.out.println(m);
    }

}
