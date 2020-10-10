package hu.rm_netbank.netbank.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import hu.rm_netbank.netbank.db.dao.CityDao;
import hu.rm_netbank.netbank.db.dao.GenderDao;
import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.preparedstatementwriter.DummyPreparedStatmentWriter;
import hu.rm_netbank.netbank.db.resultsetreader.city.SelectAllCityResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.gender.SelectAllGenderResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.city.SelectAllCitySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.gender.SelectAllGenderSqlBuilder;

public class RequestUtil {

	private RequestUtil() {
		// to prevent instantiation
	}

	public static void loadGenderMap(HttpServletRequest request) {
		GenderDao genderDao = new GenderDao();
		genderDao.openConnection();
		List<Gender> genders = genderDao.read(new SelectAllGenderSqlBuilder(), new DummyPreparedStatmentWriter(),
				new SelectAllGenderResultSetReader());
		genderDao.closeConnection();
		Map<Long, String> genderMap = genders.stream()
				.collect(Collectors.toMap(Gender::getGenderId, Gender::getName));
		request.setAttribute("genderMap", genderMap);
	}

	public static void loadCityMap(HttpServletRequest request) {
		CityDao cityDao = new CityDao();
		cityDao.openConnection();
		List<City> cities = cityDao.read(new SelectAllCitySqlBuilder(), new DummyPreparedStatmentWriter(),
				new SelectAllCityResultSetReader());
		cityDao.closeConnection();
		Map<Long, String> cityMap = cities.stream()
				.collect(Collectors.toMap(City::getCityId, City::getName));
		request.setAttribute("cityMap", cityMap);
	}

}