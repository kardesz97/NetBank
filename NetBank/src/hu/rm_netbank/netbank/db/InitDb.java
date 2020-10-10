package hu.rm_netbank.netbank.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import hu.rm_netbank.netbank.db.dao.AbstractDatabaseDao;
import hu.rm_netbank.netbank.db.dao.CityDao;
import hu.rm_netbank.netbank.db.dao.CountryDao;
import hu.rm_netbank.netbank.db.dao.DatabaseDao;
import hu.rm_netbank.netbank.db.dao.GenderDao;
import hu.rm_netbank.netbank.db.dao.UserBalanceDao;
import hu.rm_netbank.netbank.db.entity.City;
import hu.rm_netbank.netbank.db.entity.Country;
import hu.rm_netbank.netbank.db.entity.Gender;
import hu.rm_netbank.netbank.db.entity.UserBalance;
import hu.rm_netbank.netbank.db.preparedstatementwriter.DummyPreparedStatmentWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.city.CreateCityPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.country.CreateCountryPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.gender.CreateGenderPreparedStatementWriter;
import hu.rm_netbank.netbank.db.preparedstatementwriter.userbalance.CreateUserBalancePreparedStatementWriter;
import hu.rm_netbank.netbank.db.resultsetreader.city.SelectAllCityResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.country.SelectAllCountryResultSetReader;
import hu.rm_netbank.netbank.db.resultsetreader.gender.SelectAllGenderResultSetReader;
import hu.rm_netbank.netbank.db.sqlbuilder.city.CreateCitySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.city.SelectAllCitySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.country.CreateCountrySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.country.SelectAllCountrySqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.gender.CreateGenderSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.gender.SelectAllGenderSqlBuilder;
import hu.rm_netbank.netbank.db.sqlbuilder.userbalance.CreateUserBalanceSqlBuilder;
import hu.rm_netbank.netbank.util.PasswordHandler;

public class InitDb extends AbstractDatabaseDao<Object> {

	private static final Random random = new Random(20200425);
	private static final int USER_BALANCE_LIMIT = 2000;

	public static void main(String[] args) {
		try {
			new InitDb().run();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void run() throws FileNotFoundException, IOException, SQLException {
		System.out.println("Adatbázis feltöltése mintaadatokkal elkezdődött.");
		List<String> countryAndCityNamesAndMappings = load("res/countries-cities.txt");

		openConnection();
		ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
		scriptRunner.runScript(new FileReader("db/init-db.sql"));
		closeConnection();

		populateGender();
		populateCountry(countryAndCityNamesAndMappings);
		populateCity(countryAndCityNamesAndMappings);
		populateUserBalance();
	}

	private static void populateGender() {
		System.out.print("Gender tábla feltöltése...");
		Gender gender1 = Gender.builder()
				.withName("Woman")
				.build();
		Gender gender2 = Gender.builder()
				.withName("Man")
				.build();
		DatabaseDao<Gender> dao = new GenderDao();
		dao.openConnection();
		dao.create(new CreateGenderSqlBuilder(), new CreateGenderPreparedStatementWriter(gender1));
		dao.create(new CreateGenderSqlBuilder(), new CreateGenderPreparedStatementWriter(gender2));
		dao.closeConnection();
		System.out.println("kész");
	}

	private static void populateCountry(List<String> countryAndCityNamesAndMappings) {
		System.out.print("Country tábla feltöltése...");
		List<String> countryNames = countryAndCityNamesAndMappings.stream()
				.map(line -> line.split("\\t")[1].trim())
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		DatabaseDao<Country> dao = new CountryDao();
		dao.openConnection();
		for (String countryName : countryNames) {
			Country country = Country.builder()
					.withName(countryName)
					.build();
			dao.create(new CreateCountrySqlBuilder(), new CreateCountryPreparedStatementWriter(country));
		}
		dao.closeConnection();
		System.out.println("kész");
	}

	private static void populateCity(List<String> countryAndCityNamesAndMappings) {
		System.out.print("City tábla feltöltése...");
		CountryDao countryDao = new CountryDao();
		countryDao.openConnection();
		List<Country> countries = countryDao.read(new SelectAllCountrySqlBuilder(), new DummyPreparedStatmentWriter(),
				new SelectAllCountryResultSetReader());
		countryDao.closeConnection();
		Map<String, Long> countryNameToCountryIdMap = countries.stream()
				.collect(Collectors.toMap(Country::getName, Country::getCountryId));
		List<City> cities = countryAndCityNamesAndMappings.stream()
				.map(line -> City.builder()
						.withCountryId(countryNameToCountryIdMap.get(line.split("\\t")[1]))
						.withName(line.split("\\t")[0])
						.build())
				.collect(Collectors.toList());
		DatabaseDao<City> dao = new CityDao();
		dao.openConnection();
		for (City city : cities) {
			dao.create(new CreateCitySqlBuilder(), new CreateCityPreparedStatementWriter(city));
		}
		dao.closeConnection();
		System.out.println("kész");
	}

	private static void populateUserBalance() {
		System.out.print("User_balance tábla feltöltése...");
		List<UserBalance> userBalances = generateUserBalance();
		UserBalance firstUserInfo = userBalances.get(0);
		UserBalance admin = UserBalance.builder()
				.withUserName("admin")
				.withFirstName(firstUserInfo.getFirstName())
				.withLastName(firstUserInfo.getLastName())
				.withEmail(firstUserInfo.getEmail())
				.withPasswordHash(PasswordHandler.generateHash("admin"))
				.withCityId(firstUserInfo.getCityId())
				.withDateOfBirth(firstUserInfo.getDateOfBirth())
				.withGenderId(firstUserInfo.getGenderId())
				.withTotalBalance(3000L)
				.withChangeDate(firstUserInfo.getChangeDate())
				.withIntroText("Lenni legjobb felhasználó.")
				.build();
		userBalances.set(0, admin);
		DatabaseDao<UserBalance> dao = new UserBalanceDao();
		dao.openConnection();
		for (UserBalance userBalance : userBalances) {
			dao.create(new CreateUserBalanceSqlBuilder(), new CreateUserBalancePreparedStatementWriter(userBalance));
		}
		dao.closeConnection();
		System.out.println("kész");
	}

	private static List<UserBalance> generateUserBalance() {
		List<String> firstNames = load("res/first-names.txt");
		List<String> lastNames = load("res/last-names.txt");
		CityDao cityDao = new CityDao();
		cityDao.openConnection();
		List<City> cities = cityDao.read(new SelectAllCitySqlBuilder(), new DummyPreparedStatmentWriter(),
				new SelectAllCityResultSetReader());
		cityDao.closeConnection();
		GenderDao genderDao = new GenderDao();
		genderDao.openConnection();
		List<Gender> genders = genderDao.read(new SelectAllGenderSqlBuilder(), new DummyPreparedStatmentWriter(),
				new SelectAllGenderResultSetReader());
		genderDao.closeConnection();
		List<UserBalance> userBalances = new ArrayList<>(USER_BALANCE_LIMIT);
		for (int counter = 0; counter < USER_BALANCE_LIMIT; counter++) {
			String randomFirstName = firstNames.get(random.nextInt(firstNames.size()));
			String randomLastName = lastNames.get(random.nextInt(lastNames.size()));
			String email = randomFirstName + "." + randomLastName + "@gmail.com";
			String username = randomLastName.substring(0, 3)
					.toLowerCase()
					+ randomFirstName.substring(0, 3)
							.toLowerCase()
					+ counter;
			String passwordHash = new Random().ints(48, 123)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
					.limit(10)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					.toString();
			Long cityId = cities.get(random.nextInt(cities.size()))
					.getCityId();
			LocalDate dateOfBirth = LocalDate.of(random.nextInt(91) + 1920, random.nextInt(12) + 1, 1)
					.plusDays(random.nextInt(31));
			Long genderId = genders.get(random.nextInt(genders.size()))
					.getGenderId();
			Long totalBalance = 3000L;
			LocalDateTime changeDate = LocalDateTime.now();
			String introText = "Lenni második legjobb felhasználó";

			UserBalance userBalance = UserBalance.builder()
					.withUserName(removeAccents(username.toLowerCase()))
					.withFirstName(randomFirstName)
					.withLastName(randomLastName)
					.withEmail(removeAccents(email).toLowerCase())
					.withPasswordHash(passwordHash)
					.withCityId(cityId)
					.withDateOfBirth(dateOfBirth)
					.withGenderId(genderId)
					.withTotalBalance(totalBalance)
					.withChangeDate(changeDate)
					.withIntroText(introText)
					.build();
			userBalances.add(userBalance);
		}
		return userBalances;
	}

	private static String removeAccents(String text) {
		Map<String, String> replacement = new HashMap<>();
		replacement.put("á", "a");
		replacement.put("é", "e");
		replacement.put("í", "i");
		replacement.put("ó", "o");
		replacement.put("ö", "o");
		replacement.put("ő", "o");
		replacement.put("ú", "u");
		replacement.put("ü", "u");
		replacement.put("ű", "u");
		for (Entry<String, String> entry : replacement.entrySet()) {
			text = text.replaceAll(entry.getKey(), entry.getValue());
		}
		return text;
	}

	private static List<String> load(String fileName) {
		List<String> elements = new ArrayList<>();
		try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
			while (scanner.hasNextLine()) {
				String element = scanner.nextLine();
				elements.add(element);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Hiba a betöltés közben.");
			e.printStackTrace();
		}
		return elements;
	}

}