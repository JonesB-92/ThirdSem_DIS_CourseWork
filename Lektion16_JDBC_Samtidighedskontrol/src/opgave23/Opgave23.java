package opgave23;

import java.sql.*;
import java.util.Scanner;

public class Opgave23 {

    public static void main(String[] args) {
        /** OPGAVE 23
         * • Indlæs et kontonummer fra bruger
         * • Vis alle oplysninger om den pågældende konto
         * • Indlæs en ny saldo fra bruger
         * • Opdater saldo på den pågældende konto til den værdi brugeren har indtastet
         */
        try {
            System.out.println("Program startet");
            Connection minConnection;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                    "databaseName=DIS_Bank;user=sa;password=;");

//            minConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            Statement stmt = minConnection.createStatement();

//            updateSaldoPessimist(minConnection, stmt);
            updateSaldoOptimist(minConnection, stmt);


            ResultSet res = stmt.executeQuery("select * from konto");

            while (res.next()) {
                System.out.println("Konto " + res.getInt(1) + " har saldo " + res.getInt(2) + " og ejer " + res.getString(3));
            }

            if (stmt != null)
                stmt.close();
            if (minConnection != null)
                minConnection.close();
        } catch (Exception e) {
            System.out.print("fejl:  " + e.getMessage());
        }
    }


    static void updateSaldoPessimist(Connection minConnection, Statement stmt) throws SQLException {
        minConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        minConnection.setAutoCommit(false);

        Scanner brugerInput = new Scanner(System.in);
        stmt = minConnection.createStatement();

        //* Indlæs kontonr fra bruger
        System.out.println("Indtast venligst kontonummeret, du vil ændre saldo på.");
        int kontonr = brugerInput.nextInt();

        //Vis alle oplysninger om den pågældende konto
        try {
            ResultSet selectAllFraKonto = stmt.executeQuery("select * from konto where kontonr= " + kontonr);
            if (selectAllFraKonto.next()) {
                System.out.println("Konto " + selectAllFraKonto.getInt(1) + " har saldo " + selectAllFraKonto.getInt(2) + " og ejer " + selectAllFraKonto.getString(3));

            }
            //Indlæs en ny saldo fra bruger
            System.out.println("Indtast venligst den nye saldo");
            int nySaldo = brugerInput.nextInt();


            //Opdater saldo på den pågældende konto til den værdi brugeren har indtastet
            stmt.execute("update konto set saldo = " + nySaldo + " where kontonr= " + kontonr);

            System.out.println("Committer nu!");
            minConnection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    static void updateSaldoOptimist(Connection minConnection, Statement stmt) throws SQLException {
        minConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

        Scanner brugerInput = new Scanner(System.in);
        stmt = minConnection.createStatement();

        //* Indlæs kontonr fra bruger
        System.out.println("Indtast venligst kontonummeret, du vil ændre saldo på.");
        int kontonr = brugerInput.nextInt();


        //Vis alle oplysninger om den pågældende konto
        try {
            ResultSet selectAllFraKonto = stmt.executeQuery("select * from konto where kontonr= " + kontonr);
            if (selectAllFraKonto.next()) {
                System.out.println("Konto " + selectAllFraKonto.getInt(1) + " har saldo " + selectAllFraKonto.getInt(2) + " og ejer " + selectAllFraKonto.getString(3));
                int kontonummerOld = selectAllFraKonto.getInt(1);
                int saldoOld = selectAllFraKonto.getInt(2);
                String ejerOld = selectAllFraKonto.getString(3);

                //Indlæs en ny saldo fra bruger
                System.out.println("Indtast venligst den nye saldo");
                int nySaldo = brugerInput.nextInt();

                minConnection.setAutoCommit(false);

                //Select igen for at tjekke om data er ændret
                ResultSet selectAllFraKontoNew = stmt.executeQuery("select * from konto where kontonr= " + kontonr);
                if (selectAllFraKontoNew.next()) {
                    int kontonummerNew = selectAllFraKontoNew.getInt(1);
                    int saldoNew = selectAllFraKontoNew.getInt(2);
                    String ejerNew = selectAllFraKontoNew.getString(3);

                    if (kontonummerOld == kontonummerNew &&
                            saldoOld == saldoNew &&
                            ejerOld.equals(ejerNew)) {

                        //Opdater saldo på den pågældende konto til den værdi brugeren har indtastet
                        stmt.execute("update konto set saldo = " + nySaldo + " where kontonr= " + kontonr);

                        System.out.println("Committer nu!");
                        minConnection.commit();
                    } else {
                        System.out.println("Data er blevet ændret - Rollback.");
                        minConnection.rollback();
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void updateSaldoVersionering(Connection minConnection, Statement stmt) throws SQLException {
        minConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        minConnection.setAutoCommit(false);

        Scanner brugerInput = new Scanner(System.in);
        stmt = minConnection.createStatement();

//        stmt.execute("alter database DIS_Bank set allow_snapshot_isolation on");
        stmt.execute("set transaction isolation level Snapshot");

        //* Indlæs kontonr fra bruger
        System.out.println("Indtast venligst kontonummeret, du vil ændre saldo på.");
        int kontonr = brugerInput.nextInt();

        //Vis alle oplysninger om den pågældende konto
        try {
            ResultSet selectAllFraKonto = stmt.executeQuery("select * from konto where kontonr= " + kontonr);
            if (selectAllFraKonto.next()) {
                System.out.println("Konto " + selectAllFraKonto.getInt(1) + " har saldo " + selectAllFraKonto.getInt(2) + " og ejer " + selectAllFraKonto.getString(3));

            }
            //Indlæs en ny saldo fra bruger
            System.out.println("Indtast venligst den nye saldo");
            int nySaldo = brugerInput.nextInt();


            //Opdater saldo på den pågældende konto til den værdi brugeren har indtastet
            stmt.execute("update konto set saldo = " + nySaldo + " where kontonr= " + kontonr);

            System.out.println("Committer nu!");
            minConnection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

