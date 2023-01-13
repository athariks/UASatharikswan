//package src;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class ConnectionDB {
//
//    public static String USERNAME = "user_daffa";// username yang digunakan untuk mengakses database tersebut
//    public static String PASSWORD = "admin06";// password yang digunakan untuk mengakses database tersebut. Jika tidak menggunakan password, kosongkan saja bagian tersebut
//    public static int PORT = 3306;//port mysql
//    public static String DATABASE = "fuels";// database yang akan dikoneksikan
//    public static String IP_ADDRESS = "aessa.space";// ip address server MySQL. Jika dengan koneksi LAN atau internet ganti dengan nomor ip komputer server tempat dimana menginstal MySQL Server
//
////  Constructor
//    public static final Connection connect(){
//        Connection con=null; // inisialisasi interface Connection
//        try{
//            Class.forName("com.mysql.jdbc.Driver");// load driver
//            con=DriverManager.getConnection("jdbc:mysql://"+IP_ADDRESS+":"+PORT+"/"+DATABASE,USERNAME, PASSWORD);// menghubungkan database dengan method getConnection menggunakan atribut yang telah di definisikan diatas
//        }
//        catch(ClassNotFoundException | SQLException e){
//            System.out.println("Koneksi Gagal !\n"+e.getMessage());
//        }
//        return con;
//    }
//}
