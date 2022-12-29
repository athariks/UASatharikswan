import java.sql.*;
import java.util.Scanner;
import java.time.*;

public class Demo {
        // Menyiapkan paramter JDBC untuk koneksi ke datbase
        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://aessa.space/fuels";
        static final String USER = "user_daffa";
        static final String PASS = "admin06";

        // Menyiapkan objek yang diperlukan untuk mengelola database
        static Connection conn;
        static Statement stmt;
        static ResultSet rs;

        // create current instance object
        static Instant now = Instant.now();

        // Input Keyboard
        static  Scanner input= new Scanner(System.in);

        public static void main(String[] args) {

            try {
                // register driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                while (!conn.isClosed()) {
                    System.out.println("Connected to Database !!!");
                    showMenu();
                }
                stmt.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    // Show Menu
static void showMenu() {
    System.out.println("\n========= MENU UTAMA =========");
    System.out.println("1. Insert Data");
    System.out.println("2. Show Data");
    System.out.println("3. Edit Data");
    System.out.println("4. Delete Data");
    System.out.println("0. Keluar");
    System.out.println("");
    System.out.print("PILIHAN> ");

    try {
//        int pilihan = Integer.parseInt(input.readLine());
        int pilihan = input.nextInt();
        switch (pilihan) {
            case 0:
                System.exit(0);
                break;
            case 1:
                insertBiayaBensin();
                break;
            case 2:
                showDataBiayaBensin();
                break;
            case 3:
                updateBiayaBensin();
                break;
            case 4:
                deleteBiayaBensin();
                break;
            default:
                System.out.println("Pilihan salah!");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Insert Data
static void insertBiayaBensin() {
    try {
        // ambil input dari user
        System.out.print("Harga Bensin : ");
        float hargaBensin = input.nextFloat();
        System.out.print("Liter Bensin: ");
        float  literBensin = input.nextFloat();
        // get epochValue using getEpochSecond
        long epochValue = now.getEpochSecond();

        // query simpan
        String sql = "INSERT INTO fuelCosts (fuel_price, fuel_liter, created_at) VALUE('%s', '%s', '%s')";
        sql = String.format(sql, hargaBensin, literBensin, epochValue);

        // simpan data
        stmt.execute(sql);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
// Show Data
static void showDataBiayaBensin()  {
    String sql = "SELECT * FROM fuelCosts";
    try {
        rs = stmt.executeQuery(sql);

        System.out.println("+--------------------------------+");
        System.out.println("| DATA PENGELUARAN BIAYA BENSIN  |");
        System.out.println("+--------------------------------+");
        while (rs.next()) {
            int hargaBensin = rs.getInt("fuel_price");
            float literBensin = rs.getFloat("fuel_liter");
            long dateTime = rs.getInt("created_at")-25200;
            //  Reformat
            String date =  new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(dateTime*1000));

            System.out.println(String.format("Pengeluaran Rp. %s untuk %s liter pada %s", hargaBensin, literBensin, date));
            System.out.println(date);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//  Update Data
static void updateBiayaBensin() {
        try {
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int idBensin = input.nextInt();
            System.out.print("Harga Bensin: ");
            int hargaBensin =input.nextInt();
            System.out.print("Liter Bensin: ");
            Float literBensin = input.nextFloat();
            long updatedTime = now.getEpochSecond();

            // query update
            String sql = "UPDATE fuelCosts SET fuel_price='%s', fuel_liter='%s', updated_at='%s' WHERE id_fuel=%d";
            sql = String.format(sql, hargaBensin, literBensin, updatedTime,idBensin);

            // update data buku
            stmt.execute(sql);

            System.out.println("Data berhasil diubah");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//  Delete Data
static void deleteBiayaBensin() {
    try {
        // ambil input dari user
        System.out.print("ID yang mau dihapus: ");
        int idFuel = input.nextInt();
        // buat query hapus
        String sql = String.format("DELETE FROM fuelCosts WHERE id_fuel=%d", idFuel);
        // hapus data
        stmt.execute(sql);

        System.out.println("Data telah terhapus...");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}