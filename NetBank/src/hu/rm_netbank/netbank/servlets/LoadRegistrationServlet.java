package hu.rm_netbank.netbank.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.rm_netbank.netbank.db.dao.CityDao;
import hu.rm_netbank.netbank.db.dao.GenderDao;
import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.preparedstatementwriter.DummyPreparedStatmentWriter;
import hu.rm_netbank.netbank.db.resultsetreader.city.SelectAllCityResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.gender.SelectAllGenderResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.city.SelectAllCitySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.gender.SelectAllGenderSqlBuilder;

public class LoadRegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CityDao cityDao = new CityDao();
		cityDao.openConnection();
		List<City> cities = cityDao.read(new SelectAllCitySqlBuilder(), new DummyPreparedStatmentWriter(), new SelectAllCityResultSetReader());
		cityDao.closeConnection();

		GenderDao genderDao = new GenderDao();
		genderDao.openConnection();
		List<Gender> genders = genderDao.read(new SelectAllGenderSqlBuilder(), new DummyPreparedStatmentWriter(), new SelectAllGenderResultSetReader());
		genderDao.closeConnection();

		request.setAttribute("cities", cities);
		request.setAttribute("genders", genders);
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue()[0]);
		}

		request.getRequestDispatcher("/registration.jsp")
				.forward(request, response);
	}

}