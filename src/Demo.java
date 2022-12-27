import java.sql.*;
import java.util.Scanner;

public class Demo {

        // Menyiapkan paramter JDBC untuk koneksi ke datbase
        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://aessa.space/kim1b";
        static final String USER = "user_daffa";
        static final String PASS = "admin06";

        // Menyiapkan objek yang diperlukan untuk mengelola database
        static Connection conn;
        static Statement stmt;
        static ResultSet rs;

//        Input Keyboard
        static  Scanner input= new Scanner(System.in);

        public static void main(String[] args) {

            try {
                // register driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                while (!conn.isClosed()) {
//                    showMenu();
                }

                stmt.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        Show Menu
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

//    Insert Data
static void insertBiayaBensin() {
    try {
        // ambil input dari user
        System.out.print("Judul: ");
        String judul = input.nextLine();
        System.out.print("Pengarang: ");
        String pengarang = input.nextLine();

        // query simpan
        String sql = "INSERT INTO buku (judul, pengarang) VALUE('%s', '%s')";
        sql = String.format(sql, judul, pengarang);

        // simpan buku
        stmt.execute(sql);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
//    Show Data
static void showDataBiayaBensin() {
    String sql = "SELECT * FROM buku";
    try {
        rs = stmt.executeQuery(sql);

        System.out.println("+--------------------------------+");
        System.out.println("|    DATA BUKU DI PERPUSTAKAAN   |");
        System.out.println("+--------------------------------+");
        while (rs.next()) {
            int idBuku = rs.getInt("id_buku");
            String judul = rs.getString("judul");
            String pengarang = rs.getString("pengarang");

            System.out.println(String.format("%d. %s -- (%s)", idBuku, judul, pengarang));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//    Update Data
    static void updateBiayaBensin() {
        try {
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int idBuku = input.nextInt();
            System.out.print("Judul: ");
            String judul =input.nextLine();
            System.out.print("Pengarang: ");
            String pengarang = input.nextLine();

            // query update
            String sql = "UPDATE buku SET judul='%s', pengarang='%s' WHERE id_buku=%d";
            sql = String.format(sql, judul, pengarang, idBuku);

            // update data buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    Delete Data
static void deleteBiayaBensin() {
    try {

        // ambil input dari user
        System.out.print("ID yang mau dihapus: ");
        int idBuku = input.nextInt();

        // buat query hapus
        String sql = String.format("DELETE FROM buku WHERE id_buku=%d", idBuku);
        // hapus data
        stmt.execute(sql);

        System.out.println("Data telah terhapus...");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}