
import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;


public class dbclassjava {

    public static void selectexample(Connection myConnection) {
        try {
            Statement stmt = myConnection.createStatement();
            ResultSet res = stmt.executeQuery("select navn,stilling from person");
            while (res.next()) {
                System.out.println(res.getString(1) + "    " + res.getString(2));
            }
            if (res != null) res.close();
            if (stmt != null) stmt.close();
            if (myConnection != null) myConnection.close();
        } catch (Exception e) {
            System.out.println("error:  " + e.getMessage());
        }
    }

    public static void insertexample(Connection myConnection) {
        try {
            System.out.println("We will now make a person work in a company");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Give the cpr ");
            String cprstr = inLine.readLine();
            System.out.println("Give the company no.");
            String companystr = inLine.readLine();
            Statement stmt = myConnection.createStatement();
            stmt.execute("insert into ansati values ('" + cprstr + "'," + companystr + ")");
            System.out.println("The registration is done");
            if (stmt != null) stmt.close();
            if (myConnection != null) myConnection.close();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 547: {
                    if (e.getMessage().indexOf("cprforeign") != -1)
                        System.out.println("cpr-number does not exist");
                    if (e.getMessage().contains("firmaforeign"))
                        System.out.println("company. does not exist");
                    break;
                }
                case 2627: {
                    System.out.println("the person already works in that company");
                    break;
                }
                default:
                    System.out.println("errorSQL:  " + e.getMessage());
            }
            ;
        } catch (Exception e) {
            System.out.println("error:  " + e.getMessage());
        }
    }
    public static void storedprocedure(Connection myConnection) {
        // TODO Auto-generated method stub
        try {
            System.out.println("Type Customer cpr");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            String parm = inLine.readLine();
            CallableStatement stmt = myConnection.prepareCall("{call totalsaldo(?,?)}");
            stmt.setString(1, parm);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();
            int j;
            j = stmt.getInt(2);
            System.out.println(j);
            System.out.println("End");
            if (myConnection != null) myConnection.close();
        } catch (Exception e) {
            System.out.println("error:  " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void bankexample(Connection myConnection) {
        try {
            // read the information from the keyboard
            int fromaccount, toaccount, amount, frombalance = 0, tobalance = 0;
            System.out.println("Welcome to this money transfer program");
            System.out.println("From what account do you wish to transfer money");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            String fa = inLine.readLine();
            fromaccount = Integer.parseInt(fa);
            System.out.println("To which account do you wish to transfer money");
            String ta = inLine.readLine();
            toaccount = Integer.parseInt(ta);
            System.out.println("What amount do you wish to transfer");
            String a = inLine.readLine();
            amount = Integer.parseInt(a);
            //create connection
            myConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            // 	start the transaction
            myConnection.setAutoCommit(false);
            if (fromaccount == toaccount)
                throw new Myexception("You must supply different accountnumbers", myConnection);
            // Check the existence and find the balance for the first account
            String sql = "select saldo from konto where kontonr = ?";
            PreparedStatement stmt = myConnection.prepareStatement(sql);
            stmt.clearParameters();
            stmt.setInt(1, fromaccount);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                frombalance = res.getInt(1);
                if (frombalance < amount)
                    throw new Myexception("There is not enough money on the from-account", myConnection);
            } else
                throw new Myexception("The from-account does not exist", myConnection);
            res.close();
            // Check the existence and find the balance for the second account
            stmt.clearParameters();
            stmt.setInt(1, toaccount);
            res = stmt.executeQuery();
            if (res.next())
                tobalance = res.getInt(1);
            else
                throw new Myexception("The to-account does not exist", myConnection);
            res.close();
            // the stop to test the concurrency
            System.out.println("The program has reached the stop - Press Return to continue");
            String dummy = inLine.readLine();
            //calculate new balances
            frombalance = frombalance - amount;
            tobalance = tobalance + amount;
            //make the updates
            sql = "update konto set saldo = ? where kontonr = ?";
            stmt = myConnection.prepareStatement(sql);
            stmt.clearParameters();
            stmt.setInt(1, frombalance);
            stmt.setInt(2, fromaccount);
            stmt.execute();
            stmt.clearParameters();
            stmt.setInt(1, tobalance);
            stmt.setInt(2, toaccount);
            stmt.execute();
            myConnection.commit();
            System.out.println("The money has now been transfered");
        } catch (Myexception e) {
            try {
                e.con.rollback();
            } catch (SQLException e1) {
            }
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("error:  " + e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        // setting the connection
        Connection myConnection;
        String server = "localhost\\SQLEXPRESS";
        //virker det ikke - prøv kun med localhost
        String dbnavn = "oracleeksempeldb";
        String login = "xx";
        String password = "";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        myConnection = DriverManager.getConnection("jdbc:sqlserver://" + server + ";databaseName=" + dbnavn +
                ";user=" + login + ";password=" + password + ";");
        //Class.forName("oracle.jdbc.driver.OracleDriver");
        //myConnection = DriverManager.getConnection("jdbc:oracle:thin://@xx.xx.xxx.xxx:1521:xe","system","torben07");
        System.out.println("Type s for Selectexample, i for Insertexample, p for Storedprocedureexample eller b for Bankexample");
        BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
        String in = inLine.readLine();
        switch (in.charAt(0)) {
            case 's':
                selectexample(myConnection);
                break;
            case 'i':
                insertexample(myConnection);
                break;
            case 'p':
                storedprocedure(myConnection);
                break;
            case 'b':
                bankexample(myConnection);
                break;
            default:
                System.out.println("Wrong input");
        }

    }

}