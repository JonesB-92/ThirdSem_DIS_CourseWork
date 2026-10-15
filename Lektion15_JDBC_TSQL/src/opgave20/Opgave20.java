package opgave20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Opgave20 {

    public static void main(String[] args) {
        /** OPGAVE 20
         *
         */
        try {
            System.out.println("Program startet");
            Connection minConnection;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                    "databaseName=DIS_bank;user=sa;password=;");

//            minConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            minConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            Statement stmt = minConnection.createStatement();

            Scanner brugerInput = new Scanner(System.in);

            //* Indlæs frakonto fra bruger
            System.out.println("Indtast venligst kontonr., du vil overføre fra.");
            int frakonto = brugerInput.nextInt();

            //* Indlæs tilkonto fra bruger
            System.out.println("Indtast venligst kontonr., du vil overføre til.");
            int tilkonto = brugerInput.nextInt();

            //* Indlæs beløb fra bruger
            System.out.println("Indtast venligst beløbet, du vil overføre for.");
            int beløbtilKonto = brugerInput.nextInt();

            minConnection.setAutoCommit(false);

            //* Check at frakonto eksisterer og find saldoen
            ResultSet selectSaldoFraKonto = stmt.executeQuery("select saldo from konto with(updlock) where kontonr= " + frakonto);
            if (selectSaldoFraKonto.next()) {
                //* Kontroller at saldo er større end det beløb, der ønskes overført
                int saldoFrakonto = selectSaldoFraKonto.getInt(1);
                System.out.println("Saldo på frakonto: " + saldoFrakonto);

                if (saldoFrakonto >= beløbtilKonto) {
                    //* Check at tilkonto eksisterer og find saldoen
                    ResultSet selectSaldoTilKonto = stmt.executeQuery("select saldo from konto with(updlock) where kontonr= " + tilkonto);
                    if (selectSaldoTilKonto.next()) {

                        int saldoTilkonto = selectSaldoTilKonto.getInt(1);
                        System.out.println("Saldo på tilkonto: " + saldoTilkonto);

                        //* Beregn de nye saldi
                        int nysaldoFrakonto = saldoFrakonto - beløbtilKonto;
                        int nysaldoTilkonto = saldoTilkonto + beløbtilKonto;

                        brugerInput.nextLine();
                        String samtidighedskontrol = brugerInput.nextLine();

                        //* Opdater frakonto med den nye saldo
                        stmt.execute("update konto set saldo = " + nysaldoFrakonto + " where kontonr= " + frakonto);
                        //* Opdater tilkonto med den nye saldo
                        stmt.execute("update konto set saldo = " + nysaldoTilkonto + " where kontonr= " + tilkonto);

                        System.out.println("Nye saldo på frakonto: " + nysaldoFrakonto);
                        System.out.println("Nye saldo på tilkonto: " + nysaldoTilkonto);

                        minConnection.commit();

                    } else {
                        System.out.println("Konto med kontonr " + tilkonto + " findes ikke.");
                        minConnection.rollback();
                    }
                } else {
                    System.out.println("Saldo på frakonto: " + saldoFrakonto + " må ikke være < beløbet (" + beløbtilKonto + ")");
                    minConnection.rollback();
                }
            } else {
                System.out.println("Konto med kontonr " + frakonto + " findes ikke.");
                minConnection.rollback();
            }

            ResultSet res = stmt.executeQuery("select * from konto");

            while (res.next()) {
                System.out.println("Konto " + res.getInt(1) + " har saldo " + res.getInt(2) + " og ejer " + res.getString(3));
            }
            if (stmt != null)
                stmt.close();
            if (minConnection != null)
                minConnection.close();
        } catch (
                Exception e) {
            System.out.print("fejl:  " + e.getMessage());
        }

    }


}

