package com.edusofter.educv.persistence;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edusofter.educv.model.Education;
import com.edusofter.educv.model.Experience;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "EDUCV";
	private static final int DATABASE_VERSION = 3;
	private Dao<Education, Integer> educationDao = null;
	private Dao<Experience, Integer> experienceDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Education.class);
			TableUtils.createTable(connectionSource, Experience.class);
			// POPULATE INITIAL DATA
			populateEducation();
			populateExperience();

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	private void populateExperience() {
		Dao<Experience, Integer> dao = getExperienceDao();
		try {
			Experience ex = new Experience(
					"Responsable de IT",
					"A cargo de la supervisión de equipos y redes de la Oficina "
							+ "Comercial de España en Toronto. Gestión de servidores. Soporte a usuarios. " +
							"Desarrollador web de www.winesfromspaincanada.com.",
					"Agosto 2012", "Diciembre 2013", "ICEX", Experience.LANG_ES);
			dao.create(ex);
			ex = new Experience(
					"Desarrollador de Aplicaciones Android",
					"Planificación, análisis, "
							+ "diseño, programación,pruebas y soporte a usuarios de"
							+ " aplicaciones a medida.", "Abril 2012",
					"Agosto 2012", "Kernet", Experience.LANG_ES);
			dao.create(ex);
			ex = new Experience(
					"Desarrollador de Aplicaciones Android",
					"Planificación, análisis, "
							+ "diseño, programación,pruebas y soporte a usuarios de"
							+ " aplicaciones a medida. Desarrollo principal: versión móvil nativa de web de tiendas online.",
					"Marzo 2011", "Enero 2012", "Freelance", Experience.LANG_ES);
			dao.create(ex);
			 ex = new Experience(
						"IT Manager",
						"In charge of the supervision of the equipment, network and users at the Spanish Trade Comission Office in Toronto. " +
								"Web development of www.winesfromspaincanada.com.",
						"August 2012", "December 2013", "ICEX", Experience.LANG_EN);
				dao.create(ex);
				ex = new Experience(
						"Android Applications Developer",
						"Planning, requirements analysis, design, coding, unit testing and users support in ad-hoc projects of Android based applications.", "April 2012",
						"August 2012", "Kernet", Experience.LANG_EN);
				dao.create(ex);
				ex = new Experience(
						"Android Applications Developer",
						"Planning, requirements analysis, design, coding, unit testing and users support in ad-hoc projects of Android based applications. Main project: native app for online store website.",
						"March 2011", "January 2012", "Freelance", Experience.LANG_EN);
				dao.create(ex);

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't insert row", e);
		}

	}

	private void populateEducation() {
		Dao<Education, Integer> dao = getEducationDao();
		try {
			//ESPAÑOL
			Education ed = new Education("Desarrollo aplicaciones Android",
					"Curso (50h) Online MiridiaX.net", "2013", "",Education.LANG_ES);
			dao.create(ed);
			ed = new Education("Ingeniero Técnico Informática de Sistemas",
					"Escuela de Ingeniería Informática de Oviedo", "2004",
					"2010",Education.LANG_ES);
			dao.create(ed);
			ed = new Education("Desarrollo web Avanzado",
					"Curso 50h (Apache, Php, Struts, Symfony, Ruby) por la "
							+ "Escuela Ingeniería Informática de Oviedo",
					"2009", "",Education.LANG_ES);
			dao.create(ed);
			ed = new Education("Bachillerato Tecnológico", "", "2002", "2004",Education.LANG_ES);
			dao.create(ed);
			ed = new Education("Graduado en ESO", "", "1998", "2002",Education.LANG_ES);
			dao.create(ed);
			
			// INGLES
			ed = new Education("Android applications development",
					"Online course (50h) MiridiaX.net", "2013", "",Education.LANG_EN);
			dao.create(ed);
			ed = new Education("IT Engineer",
					"Escuela de Ingeniería Informática de Oviedo", "2004",
					"2010",Education.LANG_EN);
			dao.create(ed);
			ed = new Education("Advanced web development",
					"Course 50h (Apache, Php, Struts, Symfony, Ruby) by the "
							+ "Escuela Ingeniería Informática de Oviedo",
					"2009", "",Education.LANG_EN);
			dao.create(ed);
			ed = new Education("Technological High School", "", "2002", "2004",Education.LANG_EN);
			dao.create(ed);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't insert row", e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Education.class, true);
			TableUtils.dropTable(connectionSource, Experience.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}

	}

	public Dao<Education, Integer> getEducationDao() {
		if (educationDao == null) {
			try {
				educationDao = getDao(Education.class);
			} catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(),
						"Can't create Education DAO", e);
			}
		}
		return educationDao;
	}

	public Dao<Experience, Integer> getExperienceDao() {
		if (experienceDao == null) {
			try {
				experienceDao = getDao(Experience.class);
			} catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(),
						"Can't create Experience DAO", e);
			}
		}
		return experienceDao;
	}

	@Override
	public void close() {
		super.close();
		educationDao = null;
	}

}
