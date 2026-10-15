public class ThreadOut extends Thread {
    private Common commonClass;

    public ThreadOut(Common commonClass) {
        this.commonClass = commonClass;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                System.out.println(i + " " + commonClass.getString());
                this.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
