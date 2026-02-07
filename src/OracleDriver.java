public class OracleDriver {
    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.OracleDriver");
        System.out.println("Oracle Driver Loaded Successfully");
    }
}
