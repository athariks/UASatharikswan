package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.Scanner;

public class Demo {
    // Menyiapkan paramter JDBC untuk koneksi ke datbase

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://aessa.space/fuels";
    static final String USER = "user_daffa";
    static final String PASS = "admin06";
    static int SuperPrice, VPowerPrice, VpowerPlusPrice, PertalitePrice, PertamaxPrice, PertamaxTurboPrice;

    // Menyiapkan objek yang diperlukan untuk mengelola database
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    // create current instance object
    static Instant now = Instant.now();

    // Input Keyboard
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                System.out.println("\n");

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
        System.out.println("5. Insert New Data Fuel Station Price");
        System.out.println("6. Update Data Fuel Station Price");
        System.out.println("0. Keluar");
        System.out.print("\n");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = input.nextInt();
            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertDataPetrolCosts();
                    break;
                case 2:
                    showDataPetrolCosts();
                    break;
                case 3:
                    updateDataPetrolCosts();
                    break;
                case 4:
                    deleteDataPetrolCosts();
                    break;
                case 5:
                    insertFuelStationPrices();
                    break;
//            case 6:
//                updateFuelStationPrices();
//                break;
                default:
                    System.out.println("Pilihan salah!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert Data
    static void insertDataPetrolCosts() {
        try {
            // ambil input dari user
            System.out.print("Fuel Station: ");
            String fuelStation = input.next();
            System.out.print("Fuel Type : ");
            String fuelType = input.next();
            System.out.print("Fuel Price: ");
            float fuelPrice = input.nextFloat();
            System.out.print("Fuel Liter: ");
            float fuelLiter = input.nextFloat();
            // get epochValue using getEpochSecond
            long createdAtTime = now.getEpochSecond();
            long updatedAtTime = now.getEpochSecond();

            // query simpan
            String sql = "INSERT INTO fuelCosts (fuel_station,fuel_type,fuel_price, fuel_liter, created_at, updated_at) VALUE('%s', '%s', '%s', '%s', '%s', '%s')";
            sql = String.format(sql, fuelStation, fuelType, fuelPrice, fuelLiter, createdAtTime, updatedAtTime);

            // simpan data
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show Data
    static void showDataPetrolCosts() {
        String sql = "SELECT * FROM fuelCosts";
        try {
            rs = stmt.executeQuery(sql);

            System.out.println("+--------------------------------+");
            System.out.println("| DATA PENGELUARAN BIAYA BENSIN  |");
            System.out.println("+--------------------------------+");
            while (rs.next()) {
                int hargaBensin = rs.getInt("fuel_price");
                float literBensin = rs.getFloat("fuel_liter");
                long dateTime = rs.getInt("created_at") - 25200;
                //  Reformat
                String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(dateTime * 1000));

                System.out.printf("Pengeluaran pada tanggal %s sebanyak Rp. %s untuk %s liter %n", date, hargaBensin, literBensin);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Update Data
    static void updateDataPetrolCosts() {
        try {
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int idBensin = input.nextInt();
            System.out.print("Harga Bensin: ");
            int hargaBensin = input.nextInt();
            System.out.print("Liter Bensin: ");
            Float literBensin = input.nextFloat();
            long updatedTime = now.getEpochSecond();

            // query update
            String sql = "UPDATE fuelCosts SET fuel_price='%s', fuel_liter='%s', updated_at='%s' WHERE id_fuel=%d";
            sql = String.format(sql, hargaBensin, literBensin, updatedTime, idBensin);

            // update data buku
            stmt.execute(sql);

            System.out.println("Data berhasil diubah");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  Delete Data
    static void deleteDataPetrolCosts() {
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

    //Insert Data Petrol Prices
    static void insertFuelStationPrices() {
        try {

            System.out.println("Pilih bahan bakar yang akan dimasukan harganya");
            System.out.println("1. Pertamax");
            System.out.println("2. Shell");
            System.out.print("Pilihan : ");
            int selectedFuelStation = input.nextInt();

             switch (selectedFuelStation){
                 case  1:
                     System.out.println("Silakan masukan harga bahan bakar terbaru :");
                     System.out.print("Harg Bahan Bakar Pertalite : ");
                     int pertalitePrice = input.nextInt();
                     System.out.print("Harg Bahan Bakar Pertamax : ");
                     int pertamaxPrice = input.nextInt();
                     System.out.print("Harg Bahan Bakar Turbo : ");
                     int pertamaxTurboPrice = input.nextInt();
                     // get epochValue using getEpochSecond
                     long createdAtTime = now.getEpochSecond();
                     long updatedAtTime = now.getEpochSecond();

                     // Query Insert
                     String sql = "INSERT INTO pertaminaPrices (pertalite_price, pertamax_price, pertamax_turbo_price, created_at, updated_at) VALUE('%s', '%s', '%s', '%s', '%s')";
                     sql = String.format(sql, pertalitePrice, pertamaxPrice, pertamaxTurboPrice, createdAtTime, updatedAtTime);

                     System.out.print("Data bahan bakar berhasil ditambahkan !!!");

                     // simpan data
                     stmt.execute(sql);

                     break;

                 default:

                     break;

             }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

