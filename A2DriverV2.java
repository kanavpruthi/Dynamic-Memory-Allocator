import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class A2DriverV2 {
    public static void main(String args[]) throws IOException {
        long startTime = System.nanoTime();
        File myObj = new File("./test51V2.txt");
        FileWriter fw = new FileWriter("./test51V2AVLout_bhavuk.txt");
        Scanner sc = new Scanner(myObj);
        int numTestCases;
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size,2);
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument;
                int result = -5;
                switch (command) {
                    case "Allocate":
                        argument = sc.nextInt();
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        argument = sc.nextInt();
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        argument = sc.nextInt();
                        obj.Defragment();
                        break;
                    default:
                        break;
                }
                if (obj.freeBlk.sanity() == false || obj.allocBlk.sanity() == false) {
                    System.out.println("sanity broke");

                }
                if (result != -5) {
                    String str = String.valueOf(result);
                    fw.write(str);
                    fw.write("\n");
                }
            }

        }
        fw.close();
        sc.close();
        long stopTime = System.nanoTime();
        System.out.println((stopTime - startTime)/1000000000.0);
    }
}