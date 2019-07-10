package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			List<Season> list = new ArrayList<>();
			
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Race> getAllRacesBySeason(Season season) {
		String sql = "SELECT * FROM races WHERE year=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, season.getYear());
			
			ResultSet rs = st.executeQuery();
			
			List<Race> list = new ArrayList<>();
			
			while (rs.next()) {
								
				list.add(new Race(rs.getInt("raceId"),
						rs.getInt("year"),
						rs.getInt("round"),
						rs.getInt("circuitId"),
						rs.getString("name"),
						rs.getDate("date").toLocalDate(),
						null,
						rs.getString("url")));
				
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getPilotiArrivatiAlTraguardo(int year, Race r) {
		String sql = "SELECT COUNT(*) as tot FROM races, results WHERE races.`raceId`=results.`raceId` AND results.`statusId`=1 AND races.`year`=? AND races.raceId=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setInt(2, r.getRaceId());
			
			ResultSet rs = st.executeQuery();
			int tot;
			
			if (rs.next()) {
				tot = rs.getInt("tot");
				conn.close();
				return tot;
			}
			conn.close();
			return 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Driver> getPilotiByRace(Race gara) {
		String sql = "SELECT * FROM results AS r, drivers AS d WHERE r.`driverId`=d.`driverId` AND r.`raceId`=?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, gara.getRaceId());
			
			ResultSet rs = st.executeQuery();
			
			List<Driver> list = new ArrayList<>();
			
			while (rs.next()) {
								
				list.add(new Driver(rs.getInt("driverId"),
						rs.getString("driverRef"),
						null,
						null,
						rs.getString("forename"),
						rs.getString("surname"),
						rs.getDate("dob").toLocalDate(),
						rs.getString("nationality"),
						rs.getString("url")));
				
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
