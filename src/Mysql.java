import java.sql.*;

public class Mysql {

    public static class Country {
        public String code;
        public String name;
        public String continent;
        public double surfaceArea;
        public int population;
        public double gnp;
        public int capital;
    }

    public static class City {
        public int id;
        public String name;
        public int population;

        public City(int id, String name, int population) {
            this.id = id;
            this.name = name;
            this.population = population;
        }
    }

    public static void getMostPopulousCityInCountry (Connection conn, String name) throws SQLException {
        String sql = "select city.name, city.population, id " +
                "from city join country " +
                "on city.country = country.code " +
                "where country.name = ? " +
                "order by city.population desc " +
                "limit 1";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            int id = result.getInt("id");
            String cname = result.getString("name");
            int population = result.getInt("population");
            City c = new City(id, cname, population);
            System.out.println(c.name + " is the most populous city in " + name + " with a population of "+ c.population);
        } else {
            System.out.println("The name of the country does not exist");
        }
    }
    public static void getMostPopulousCityInContinent (Connection conn, String name) throws SQLException {
        String sql = "select city.name, city.population, id " +
                "from city join country " +
                "on city.country = country.code " +
                "where country.continent = ? " +
                "order by city.population desc " +
                "limit 1";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            int id = result.getInt("id");
            String cname = result.getString("name");
            int population = result.getInt("population");
            City c = new City(id, cname, population);
            System.out.println(c.name + " is the most populous city in " + name + " with a population of "+ c.population);
        } else {
            System.out.println("The name of the continent does not exist");
        }
    }

    public static void getMostPopulousCapital(Connection conn) throws SQLException {
        String sql = "select * from city " +
                "where city.id in (select capital from country) " +
                "order by city.population desc limit 1";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        if(result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            int population = result.getInt("population");
            City c = new City(id, name, population);
            System.out.println("The most populous capital city in the world is " + c.name + " with a population of " + c.population);
        }
    }

    public static void getMostPopulousCapitalInContinent(Connection conn, String cont) throws SQLException {
        String sql = "select * from city join country on city.country = country.code " +
                "where city.id in (select capital from country) " +
                "and country.continent = ? " +
                "order by city.population desc limit 1";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, cont);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            int population = result.getInt("population");
            City c = new City(id, name, population);
            System.out.println("The most populous capital city in " + cont + " is " + c.name + " with a population of " + c.population);
        } else {
            System.out.println("The name of the continent does not exist");
        }
    }

    public static void getListCountCitiesDesc(Connection conn) throws SQLException {
        String sql = "select country.name, count(city.id) as number_of_cities " +
                "from country join city " +
                "on city.country = country.code " +
                "group by country.name " +
                "order by number_of_cities desc";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Countries according to the number of cities gradually decreased:");
        System.out.println("Country\tNumber of cities");
        while (result.next()) {
            String name = result.getString(1);
            int number = result.getInt(2);
            System.out.println(name + "\t" + number);
        }
    }

    public static void getListCountDensityDesc(Connection conn) throws SQLException {
        String sql = "select name, population/surface_area as density " +
                "from country " +
                "where population > 0 " +
                "order by density desc";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        System.out.println("Countries according to the density of population decreased:");
        System.out.println("Country\tDensity");
        while (result.next()) {
            String name = result.getString(1);
            double number = result.getDouble(2);
            System.out.printf("%s\t%.2f\n", name, number);
        }
    }

    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/intern?useSSL=false";
        String username = "root";
        String password = "123456";

        try {
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                getMostPopulousCityInCountry(conn, "Viet Nam");
                getMostPopulousCityInContinent(conn, "NA");
                getMostPopulousCapital(conn);
                getMostPopulousCapitalInContinent(conn, "AS");
                getListCountCitiesDesc(conn);
                getListCountDensityDesc(conn);
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
