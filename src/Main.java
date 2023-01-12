package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.Scanner;

public class Main {
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
    static Scanner input = new Scanner(System.in);
    static String fuelStation, fuelType;
    static int dayFuelCost,fuelTypeChoice, fuelStationChoice;

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
            System.out.println("Pilih pengisian stasiun bahan bakar: ");
            System.out.println("1. Pertamina");
            System.out.println("2. Shell");
            System.out.print("Masukan Pilihan Anda : ");
            fuelStationChoice = input.nextInt();

            while (fuelStationChoice < 1 || fuelStationChoice > 2){
                System.out.println("\n");
                System.out.println("Masukan Pilihan yang sudah disediakan dan Pilihlah pengisian stasiun bahan bakar: ");
                System.out.println("1. Pertamina");
                System.out.println("2. Shell");
                System.out.print("Masukan Pilihan Anda : ");
                fuelStationChoice = input.nextInt();
            }

//          PERTAMINA
            if (fuelStationChoice == 1){
                    fuelStation = "Pertamina";
                    System.out.println("Pilih pengisian tipe bahan bakar Pertamina: ");
                    System.out.println("1. Pertalite");
                    System.out.println("2. Pertamax");
                    System.out.println("3. Pertamax Turbo");
                    System.out.print("Masukan Pilihan anda : ");
                     fuelTypeChoice = input.nextInt();
                    System.out.print("\n");

                    while (fuelTypeChoice < 1 || fuelTypeChoice > 3) {
                        System.out.println("Pilih pengisian tipe bahan bakar Pertamina: ");
                        System.out.println("1. Pertalite");
                        System.out.println("2. Pertamax");
                        System.out.println("3. Pertamax Turbo");
                        System.out.print("Masukan Pilihan anda : ");
                        fuelTypeChoice = input.nextInt();
                        System.out.print("\n");
                    }

                    switch (fuelTypeChoice){
                        case 1:
                            fuelType = "Pertalite";
                            break;
                        case 2:
                            fuelType = "Pertamax";
                            break;
                        case 3:
                            fuelType = "Pertamax Turbo";
                            break;
                    }
                    System.out.print("Anda menggunakan Bahan Bakar "+ fuelType+" Masukan Pengeluaran anda Rp. /L : Rp. ");
                    dayFuelCost = input.nextInt();
            }

//          SHELL
            if (fuelStationChoice == 2){
                    fuelStation = "Shell";
                    System.out.println("Pilih pengisian tipe bahan bakar Shell : ");
                    System.out.println("1. Super");
                    System.out.println("2. V Power");
                    System.out.print("Masukan Pilihan anda : ");
                    fuelTypeChoice = input.nextInt();
                    System.out.print("\n");

                    while (fuelTypeChoice < 1 || fuelTypeChoice > 2) {
                        System.out.println("Pilih pengisian tipe bahan bakar Shell : ");
                        System.out.println("1. Super");
                        System.out.println("2. V Power");
                        System.out.print("Masukan Pilihan anda : ");
                        fuelTypeChoice = input.nextInt();
                        System.out.print("\n");
                    }

                    switch (fuelTypeChoice){
                        case 1:
                            fuelType = "Super";
                            break;
                        case 2:
                            fuelType = "V Power";
                            break;
                    }
                    System.out.print("Anda menggunakan Bahan Bakar "+ fuelType+" Masukan Pengeluaran anda Rp. /L : Rp. ");
                    dayFuelCost = input.nextInt();
            }

            // get epochValue using getEpochSecond
            long createdAtTime = now.getEpochSecond();
            long updatedAtTime = now.getEpochSecond();

            // query simpan
//            String sql = "INSERT INTO fuelCosts (fuel_station,fuel_type,fuel_price, fuel_liter, created_at, updated_at) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";
              String sql = "INSERT INTO fuelCosts (fuel_station, fuel_type, fuel_price , fuel_liter,  created_at, updated_at) VALUE ('+fuelStation+',"+fuelType+","+dayFuelCost+",(SELECT "+dayFuelCost +"/super_price FROM shellPrices ORDER BY created_at ASC LIMIT 1),"+createdAtTime+","+updatedAtTime+")";


            // simpan data
            stmt.execute(String.format(sql));

            System.out.println("Data berhasil ditambahkan !!!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show Data
    static void showDataPetrolCosts() {
        String sql = "SELECT * FROM fuelCosts DESCY ORDER BY id_fuel DESC";
        try {
            rs = stmt.executeQuery(sql);

            System.out.println("+--------------------------------+");
            System.out.println("|    PENGELUARAN BIAYA BENSIN    |");
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
//                 Harga Bahan Bakar Pertamina
                 case  1:
                     System.out.println("+-----------------------------------------------------+");
                     System.out.println("| Silakan masukan harga bahan bakar Pertamina terbaru |");
                     System.out.println("+-----------------------------------------------------+");
                     System.out.println("Silakan masukan harga bahan bakar terbaru :");
                     System.out.print("Harga Bahan Bakar Pertalite : ");
                     int pertalitePrice = input.nextInt();
                     System.out.print("Harg Bahan Bakar Pertamax : ");
                     int pertamaxPrice = input.nextInt();
                     System.out.print("Harg Bahan Bakar Turbo : ");
                     int pertamaxTurboPrice = input.nextInt();
                     // get epochValue using getEpochSecond
                     long createdAtTimePertamina = now.getEpochSecond();
                     long updatedAtTimePertamina = now.getEpochSecond();

                     // Query Insert
                     String sqlpertamina = "INSERT INTO pertaminaPrices (pertalite_price, pertamax_price, pertamax_turbo_price, created_at, updated_at) VALUE('%s', '%s', '%s', '%s', '%s')";
                     sqlpertamina = String.format(sqlpertamina, pertalitePrice, pertamaxPrice, pertamaxTurboPrice, createdAtTimePertamina, updatedAtTimePertamina);

                     System.out.print("Data bahan bakar Pertamina baru berhasil ditambahkan !!!");

                     // simpan data
                     stmt.execute(sqlpertamina);

                     break;

//                 Harga bahan bakar Shell
                     case  2:
                         System.out.println("+-------------------------------------------------+");
                         System.out.println("| Silakan masukan harga bahan bakar Shell terbaru |");
                         System.out.println("+-------------------------------------------------+");
                     System.out.print("Harga Bahan Bakar Super : ");
                     int superPrice = input.nextInt();
                     System.out.print("Harg Bahan Bakar V Power : ");
                     int vPowerPrice = input.nextInt();
                     // get epochValue using getEpochSecond
                     long createdAtTimeShell = now.getEpochSecond();
                     long updatedAtTimeShell = now.getEpochSecond();

                     // Query Insert
                     String sqlShell = "INSERT INTO shellPrices (super_price, vpower_price, created_at, updated_at) VALUE('%s', '%s', '%s', '%s')";
                         sqlShell = String.format(sqlShell, superPrice, vPowerPrice, createdAtTimeShell, updatedAtTimeShell);

                     System.out.print("Data bahan bakar Shell baru berhasil ditambahkan !!!");

                     // simpan data
                     stmt.execute(sqlShell);

                     break;
                 default:
                     System.out.println("Pilihan salah!");

             }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

