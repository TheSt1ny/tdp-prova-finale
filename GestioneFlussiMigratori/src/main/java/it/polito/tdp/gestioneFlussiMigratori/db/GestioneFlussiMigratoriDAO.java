package it.polito.tdp.gestioneFlussiMigratori.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestioneFlussiMigratori.model.Adiacenza;
import it.polito.tdp.gestioneFlussiMigratori.model.Country;

public class GestioneFlussiMigratoriDAO {
	
	public List<Country> getAllAfricanCountries(Map<String, Country> idMap) {
		
		String sql = "SELECT DISTINCT * "
				+ "FROM country_population AS c "
				+ "WHERE c.Region LIKE '%AFRICA%' "
				+ "ORDER BY c.Country";
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Country> list = new LinkedList<Country>();

			while (rs.next()) {

				if (idMap.get(rs.getString("c.Country")) == null) {

					Country c = new Country(rs.getString("c.Country").trim(), rs.getString("c.Region"), rs.getInt("c.Population"), 0, 0);
					idMap.put(c.getCountry(), c);
					list.add(c);
				} else
					list.add(idMap.get(rs.getString("c.Country")));
			}

			conn.close();

			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	
	public List<Country> getAllEuropeanCountries(Map<String, Country> idMap) {
		
		String sql = "SELECT DISTINCT * "
				+ "FROM country_population AS c "
				+ "WHERE c.Region LIKE '%EUROPE%' "
				+ "ORDER BY c.Country";
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Country> list = new LinkedList<Country>();

			while (rs.next()) {

				if (idMap.get(rs.getString("c.Country")) == null) {

					Country c = new Country(rs.getString("c.Country").trim(), rs.getString("c.Region"), rs.getInt("c.Population"), 0, 0);
					idMap.put(c.getCountry(), c);
					list.add(c);
				} else
					list.add(idMap.get(rs.getString("c.Country")));
			}

			conn.close();

			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Adiacenza> getAdiacenza() {
		
		String sql = "SELECT DISTINCT c.Country, b.country_border_name "
				+ "FROM country_population AS c, country_borders AS b "
				+ "WHERE c.Country=b.country_name AND (c.Region LIKE '%AFRICA%' OR c.Region LIKE '%EUROPE%') AND c.Country < b.country_border_name "
				+ "ORDER BY c.Country";
		
		List<Adiacenza> result = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Adiacenza(rs.getString("c.Country").trim(), rs.getString("b.country_border_name").trim()));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
