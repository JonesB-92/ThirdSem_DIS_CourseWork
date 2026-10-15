import java.util.Scanner;

public class ThreadIn extends Thread {
    private Common commonClass;

    public ThreadIn(Common commonClass) {
        this.commonClass = commonClass;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (commonClass.getString() == null) {
            System.out.println("Skriv en besked: ");
            String besked = scanner.nextLine();
            scanner.close();
            commonClass.setString(besked);
            this.interrupt();
        }
    }
}
