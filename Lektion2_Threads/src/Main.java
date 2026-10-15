public class Main {
    public static void main(String[] args) {
        Common commonClass = new Common();

        ThreadIn threadIn = new ThreadIn(commonClass);
        ThreadOut threadOut = new ThreadOut(commonClass);

        threadIn.start();
//        while(commonClass.getString() == null);
        while (!threadIn.isInterrupted()) ;

        threadOut.start();

    }
}

